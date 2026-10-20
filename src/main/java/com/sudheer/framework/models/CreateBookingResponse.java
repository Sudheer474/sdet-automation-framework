package com.sudheer.framework.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateBookingResponse(int bookingid, Booking booking) { }
