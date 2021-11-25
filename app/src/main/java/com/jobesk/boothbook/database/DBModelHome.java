package com.jobesk.boothbook.database;

public class DBModelHome {

    public static final String TABLE_NAME = "bookings";

    public static final String COLUMN_ID = "id";

    public static final String column_booking_id = "booking_id";
    public static final String column_created = "created";
    public static final String column_created_iso = "created_iso";
    public static final String column_changed = "changed";
    public static final String column_changed_iso = "changed_iso";
    public static final String column_status = "status";
    public static final String column_email = "email";
    public static final String column_phone = "phone";
    public static final String column_packages = "packages";
    public static final String column_signature_required = "signature_required";
    public static final String column_signature_image = "signature_image";
    public static final String column_customer_first_name = "customer_first_name";
    public static final String column_customer_last_name = "customer_last_name";
    public static final String column_customer_company = "customer_company";
    public static final String column_customer_street_address = "customer_street_address";
    public static final String column_customer_city = "customer_city";
    public static final String column_customer_country = "customer_country";
    public static final String column_customer_postcode = "customer_postcode";
    public static final String column_staff_written = "staff_written";
    public static final String column_event_name = "event_name";
    public static final String column_event_time_start_formatted = "event_time_start_formatted";
    public static final String column_event_time_end_formatted = "event_time_end_formatted";
    public static final String column_event_date_uk = "event_date_uk";
    public static final String column_event_date_us = "event_date_us";
    public static final String column_event_date_iso = "event_date_iso";
    public static final String column_venue_name = "venue_name";
    public static final String column_venue_address = "venue_address";
    public static final String column_venue_postcode = "venue_postcode";
    public static final String column_venue_business_country = "venue_business_country";
    public static final String column_price_total = "price_total";
    public static final String column_notes_admin = "notes_admin";
    public static final String column_notes_customer = "notes_customer";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + column_booking_id + " TEXT,"
                    + column_created + " TEXT,"
                    + column_created_iso + " TEXT,"
                    + column_changed + " TEXT,"
                    + column_changed_iso + " TEXT,"
                    + column_status + " TEXT,"
                    + column_email + " TEXT,"
                    + column_phone + " TEXT,"
                    + column_packages + " TEXT,"
                    + column_signature_required + " TEXT,"
                    + column_signature_image + " TEXT,"
                    + column_customer_first_name + " TEXT,"
                    + column_customer_last_name + " TEXT,"
                    + column_customer_company + " TEXT,"
                    + column_customer_street_address + " TEXT,"
                    + column_customer_city+ " TEXT,"
                    + column_customer_country + " TEXT,"
                    + column_customer_postcode + " TEXT,"
                    + column_staff_written + " TEXT,"
                    + column_event_name + " TEXT,"
                    + column_event_time_start_formatted + " TEXT,"
                    + column_event_time_end_formatted+ " TEXT,"
                    + column_event_date_uk + " TEXT,"
                    + column_event_date_us + " TEXT,"
                    + column_event_date_iso + " TEXT,"
                    + column_venue_name + " TEXT,"
                    + column_venue_address + " TEXT,"
                    + column_venue_postcode + " TEXT,"
                    + column_venue_business_country + " TEXT,"
                    + column_price_total + " TEXT,"
                    + column_notes_admin + " TEXT,"
                    + column_notes_customer + " TEXT"
                    + ")";


    int id;

    String

            booking_id,
            created,
            created_iso,
            changed,
            changed_iso,
            status,
            email,
            phone,
            packages,
            signature_required,
            signature_image,
            customer_first_name,
            customer_last_name,
            customer_company,
            customer_street_address,
            customer_city,
            customer_country,
            customer_postcode,
            staff_written,
            event_name,
            event_time_start_formatted,
            event_time_end_formatted,
            event_date_uk,
            event_date_us,
            event_date_iso,
            venue_name,
            venue_address,
            venue_postcode,
            venue_business_country,
            price_total,
            notes_admin,
            notes_customer;

    public DBModelHome() {

    }

    public DBModelHome(int id, String booking_id, String created, String created_iso, String changed, String changed_iso, String status, String email, String phone, String packages, String signature_required,String signature_image, String customer_first_name, String customer_last_name, String customer_company, String customer_street_address, String customer_city, String customer_country, String customer_postcode, String staff_written, String event_name, String event_time_start_formatted, String event_time_end_formatted, String event_date_uk, String event_date_us, String event_date_iso, String venue_name, String venue_address, String venue_postcode, String venue_business_country, String price_total, String notes_admin, String notes_customer) {
        this.id = id;
        this.booking_id = booking_id;
        this.created = created;
        this.created_iso = created_iso;
        this.changed = changed;
        this.changed_iso = changed_iso;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.packages = packages;
        this.signature_required = signature_required;
        this.signature_image = signature_image;
        this.customer_first_name = customer_first_name;
        this.customer_last_name = customer_last_name;
        this.customer_company = customer_company;
        this.customer_street_address = customer_street_address;
        this.customer_city = customer_city;
        this.customer_country = customer_country;
        this.customer_postcode = customer_postcode;
        this.staff_written = staff_written;
        this.event_name = event_name;
        this.event_time_start_formatted = event_time_start_formatted;
        this.event_time_end_formatted = event_time_end_formatted;
        this.event_date_uk = event_date_uk;
        this.event_date_us = event_date_us;
        this.event_date_iso = event_date_iso;
        this.venue_name = venue_name;
        this.venue_address = venue_address;
        this.venue_postcode = venue_postcode;
        this.venue_business_country = venue_business_country;
        this.price_total = price_total;
        this.notes_admin = notes_admin;
        this.notes_customer = notes_customer;
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

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
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

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public String getCustomer_company() {
        return customer_company;
    }

    public void setCustomer_company(String customer_company) {
        this.customer_company = customer_company;
    }

    public String getCustomer_street_address() {
        return customer_street_address;
    }

    public void setCustomer_street_address(String customer_street_address) {
        this.customer_street_address = customer_street_address;
    }

    public String getCustomer_city() {
        return customer_city;
    }

    public void setCustomer_city(String customer_city) {
        this.customer_city = customer_city;
    }

    public String getCustomer_country() {
        return customer_country;
    }

    public void setCustomer_country(String customer_country) {
        this.customer_country = customer_country;
    }

    public String getCustomer_postcode() {
        return customer_postcode;
    }

    public void setCustomer_postcode(String customer_postcode) {
        this.customer_postcode = customer_postcode;
    }

    public String getStaff_written() {
        return staff_written;
    }

    public void setStaff_written(String staff_written) {
        this.staff_written = staff_written;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_time_start_formatted() {
        return event_time_start_formatted;
    }

    public void setEvent_time_start_formatted(String event_time_start_formatted) {
        this.event_time_start_formatted = event_time_start_formatted;
    }

    public String getEvent_time_end_formatted() {
        return event_time_end_formatted;
    }

    public void setEvent_time_end_formatted(String event_time_end_formatted) {
        this.event_time_end_formatted = event_time_end_formatted;
    }

    public String getEvent_date_uk() {
        return event_date_uk;
    }

    public void setEvent_date_uk(String event_date_uk) {
        this.event_date_uk = event_date_uk;
    }

    public String getEvent_date_us() {
        return event_date_us;
    }

    public void setEvent_date_us(String event_date_us) {
        this.event_date_us = event_date_us;
    }

    public String getEvent_date_iso() {
        return event_date_iso;
    }

    public void setEvent_date_iso(String event_date_iso) {
        this.event_date_iso = event_date_iso;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getVenue_address() {
        return venue_address;
    }

    public void setVenue_address(String venue_address) {
        this.venue_address = venue_address;
    }

    public String getVenue_postcode() {
        return venue_postcode;
    }

    public void setVenue_postcode(String venue_postcode) {
        this.venue_postcode = venue_postcode;
    }

    public String getVenue_business_country() {
        return venue_business_country;
    }

    public void setVenue_business_country(String venue_business_country) {
        this.venue_business_country = venue_business_country;
    }

    public String getPrice_total() {
        return price_total;
    }

    public void setPrice_total(String price_total) {
        this.price_total = price_total;
    }

    public String getNotes_admin() {
        return notes_admin;
    }

    public void setNotes_admin(String notes_admin) {
        this.notes_admin = notes_admin;
    }

    public String getNotes_customer() {
        return notes_customer;
    }

    public void setNotes_customer(String notes_customer) {
        this.notes_customer = notes_customer;
    }
}
