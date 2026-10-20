package com.sudheer.framework.utils;

import com.sudheer.framework.models.Booking;
import com.sudheer.framework.models.BookingDates;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/** Builds unique test data so parallel tests never collide. */
public final class TestDataFactory {

    private TestDataFactory() { }

    public static Booking newBooking() {
        String suffix = UUID.randomUUID().toString().substring(0, 6);
        return new Booking(
                "Test" + suffix,
                "User" + suffix,
                ThreadLocalRandom.current().nextInt(100, 1000),
                true,
                new BookingDates("2026-01-10", "2026-01-15"),
                "Breakfast");
    }

    public static Booking withPrice(Booking original, int newPrice) {
        return new Booking(original.firstname(), original.lastname(), newPrice,
                original.depositpaid(), original.bookingdates(), original.additionalneeds());
    }
}
