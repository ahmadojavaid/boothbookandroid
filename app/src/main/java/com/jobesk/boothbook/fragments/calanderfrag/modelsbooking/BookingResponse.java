package com.jobesk.boothbook.fragments.calanderfrag.modelsbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class BookingResponse {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("bookings")
    @Expose
    private Map<String, Bookings> bookings;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(Map<String, Bookings> bookings) {
        this.bookings = bookings;
    }
}
