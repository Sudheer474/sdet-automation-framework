package com.sudheer.framework.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Request/response payload for the Restful-Booker /booking endpoints. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Booking(
        String firstname,
        String lastname,
        int totalprice,
        boolean depositpaid,
        BookingDates bookingdates,
        String additionalneeds) { }
