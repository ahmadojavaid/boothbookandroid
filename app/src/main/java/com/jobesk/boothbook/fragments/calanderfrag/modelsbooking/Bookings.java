package com.jobesk.boothbook.fragments.calanderfrag.modelsbooking;


import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bookings {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("created_iso")
    @Expose
    private String created_iso;


    @SerializedName("event_name")
    @Expose
    private String eventName;

    private String changed;
    private String changed_iso;
    private String status;
    private String email;
    private String phone;
    private String packages;
    private String signature_required;
    private String signature_image;

    private Event event;

    private Customer customer;
    private Venue venue;
    private Price price;
    private Notes notes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    @SerializedName("staff")
    @Expose
    private Staff staff;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSignature_image() {
        return signature_image;
    }

    public void setSignature_image(String signature_image) {
        this.signature_image = signature_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated_iso() {
        return created_iso;
    }

    public void setCreated_iso(String created_iso) {
        this.created_iso = created_iso;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public String getChanged_iso() {
        return changed_iso;
    }

    public void setChanged_iso(String changed_iso) {
        this.changed_iso = changed_iso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getSignature_required() {
        return signature_required;
    }

    public void setSignature_required(String signature_required) {
        this.signature_required = signature_required;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
