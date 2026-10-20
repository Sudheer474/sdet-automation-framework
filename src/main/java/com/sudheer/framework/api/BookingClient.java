package com.sudheer.framework.api;

import com.sudheer.framework.config.ConfigReader;
import com.sudheer.framework.models.Booking;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * API layer: thin wrapper around the Restful-Booker endpoints.
 * Returns raw Response objects so each test decides what to assert.
 */
public class BookingClient {

    @Step("Create auth token")
    public String createToken() {
        return given().spec(RequestSpecFactory.base())
                .body(Map.of("username", ConfigReader.get("apiUsername"),
                             "password", ConfigReader.get("apiPassword")))
                .post("/auth")
                .then().statusCode(200)
                .extract().path("token");
    }

    @Step("Create booking")
    public Response createBooking(Booking booking) {
        return given().spec(RequestSpecFactory.base()).body(booking).post("/booking");
    }

    @Step("Get booking {id}")
    public Response getBooking(int id) {
        return given().spec(RequestSpecFactory.base()).get("/booking/{id}", id);
    }

    @Step("Update booking {id}")
    public Response updateBooking(int id, Booking booking, String token) {
        return given().spec(RequestSpecFactory.base())
                .cookie("token", token)
                .body(booking)
                .put("/booking/{id}", id);
    }

    @Step("Delete booking {id}")
    public Response deleteBooking(int id, String token) {
        return given().spec(RequestSpecFactory.base())
                .cookie("token", token)
                .delete("/booking/{id}", id);
    }

    @Step("Delete booking {id} without authentication")
    public Response deleteBookingWithoutAuth(int id) {
        return given().spec(RequestSpecFactory.base()).delete("/booking/{id}", id);
    }
}
