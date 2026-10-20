package com.sudheer.framework.tests.api;

import com.sudheer.framework.api.BookingClient;
import com.sudheer.framework.models.Booking;
import com.sudheer.framework.models.CreateBookingResponse;
import com.sudheer.framework.utils.TestDataFactory;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * API tests against the public Restful-Booker service.
 * Every test creates its own booking, so tests are independent and safe to run in parallel.
 */
public class BookingApiTests {

    private final BookingClient client = new BookingClient();
    private String token;

    @BeforeClass(alwaysRun = true)
    public void authenticate() {
        token = client.createToken();
    }

    @Test(groups = {"smoke", "api"})
    @Description("Creating a booking returns an id and echoes the submitted data")
    public void createBookingReturnsIdAndPayload() {
        Booking request = TestDataFactory.newBooking();

        CreateBookingResponse created = client.createBooking(request)
                .then().statusCode(200)
                .extract().as(CreateBookingResponse.class);

        Assert.assertTrue(created.bookingid() > 0, "Booking id should be generated");
        Assert.assertEquals(created.booking(), request);
    }

    @Test(groups = {"regression", "api"})
    @Description("GET response conforms to the booking JSON schema (contract check)")
    public void getBookingMatchesSchema() {
        int id = createBookingId();

        client.getBooking(id)
                .then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
    }

    @Test(groups = {"regression", "api"})
    @Description("Authenticated PUT updates the booking")
    public void updateBookingChangesPrice() {
        Booking original = TestDataFactory.newBooking();
        int id = client.createBooking(original).then().log().ifValidationFails().statusCode(200).extract().path("bookingid");

        Booking updated = TestDataFactory.withPrice(original, 999);
        client.updateBooking(id, updated, token).then().statusCode(200);

        Booking fetched = client.getBooking(id).then().statusCode(200).extract().as(Booking.class);
        Assert.assertEquals(fetched.totalprice(), 999);
    }

    @Test(groups = {"regression", "api"})
    @Description("A deleted booking can no longer be retrieved")
    public void deletedBookingReturns404() {
        int id = createBookingId();

        // Restful-Booker returns 201 for a successful delete (a known quirk of this demo API)
        client.deleteBooking(id, token).then().statusCode(201);
        client.getBooking(id).then().statusCode(404);
    }

    @Test(groups = {"regression", "api", "negative"})
    @Description("Deleting without a token is forbidden")
    public void deleteWithoutTokenIsForbidden() {
        int id = createBookingId();

        Response response = client.deleteBookingWithoutAuth(id);
        Assert.assertEquals(response.statusCode(), 403);
    }

    @Test(groups = {"regression", "api", "negative"})
    @Description("Unknown booking id returns 404")
    public void unknownBookingReturns404() {
        client.getBooking(999_999_999).then().statusCode(404);
    }

    private int createBookingId() {
        return client.createBooking(TestDataFactory.newBooking())
                .then().log().ifValidationFails()
                .statusCode(200)
                .extract().path("bookingid");
    }


}
