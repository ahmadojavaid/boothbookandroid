package com.jobesk.boothbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jobesk.boothbook.models.EventTypeModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = "DatabaseHelper";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(DBModelHome.CREATE_TABLE);
        db.execSQL(DBLeads.CREATE_TABLE);
        db.execSQL(EventTypeModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DBModelHome.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBLeads.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EventTypeModel.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void deleteDatabase() {

        SQLiteDatabase db = this.getWritableDatabase();

        //delete database here
        db.execSQL("DROP TABLE IF EXISTS " + DBModelHome.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBLeads.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EventTypeModel.TABLE_NAME);
        onCreate(db);
    }

    public void insertBooking(DBModelHome booking) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

//        values.put(DBModelHome.COLUMN_ID, booking.getId());
        values.put(DBModelHome.column_booking_id, booking.getBooking_id());
        values.put(DBModelHome.column_created, booking.getCreated());
        values.put(DBModelHome.column_created_iso, booking.getCreated_iso());
        values.put(DBModelHome.column_changed, booking.getChanged());
        values.put(DBModelHome.column_changed_iso, booking.getChanged_iso());
        values.put(DBModelHome.column_status, booking.getStatus());
        values.put(DBModelHome.column_email, booking.getEmail());
        values.put(DBModelHome.column_phone, booking.getPhone());
        values.put(DBModelHome.column_packages, booking.getPackages());
        values.put(DBModelHome.column_signature_required, booking.getSignature_required());
        values.put(DBModelHome.column_signature_image, booking.getSignature_image());
        values.put(DBModelHome.column_customer_first_name, booking.getCustomer_first_name());
        values.put(DBModelHome.column_customer_last_name, booking.getCustomer_last_name());
        values.put(DBModelHome.column_customer_company, booking.getCustomer_company());
        values.put(DBModelHome.column_customer_street_address, booking.getCustomer_street_address());
        values.put(DBModelHome.column_customer_city, booking.getCustomer_city());
        values.put(DBModelHome.column_customer_country, booking.getCustomer_country());
        values.put(DBModelHome.column_customer_postcode, booking.getCustomer_postcode());
        values.put(DBModelHome.column_staff_written, booking.getStaff_written());
        values.put(DBModelHome.column_event_name, booking.getEvent_name());
        values.put(DBModelHome.column_event_time_start_formatted, booking.getEvent_time_start_formatted());
        values.put(DBModelHome.column_event_time_end_formatted, booking.getEvent_time_end_formatted());
        values.put(DBModelHome.column_event_date_uk, booking.getEvent_date_uk());
        values.put(DBModelHome.column_event_date_us, booking.getEvent_date_us());
        values.put(DBModelHome.column_event_date_iso, booking.getEvent_date_iso());
        values.put(DBModelHome.column_venue_name, booking.getEvent_name());
        values.put(DBModelHome.column_venue_address, booking.getVenue_address());
        values.put(DBModelHome.column_venue_postcode, booking.getVenue_postcode());
        values.put(DBModelHome.column_venue_business_country, booking.getVenue_business_country());
        values.put(DBModelHome.column_price_total, booking.getPrice_total());
        values.put(DBModelHome.column_notes_admin, booking.getNotes_admin());
        values.put(DBModelHome.column_notes_customer, booking.getNotes_customer());


        // insert row
        long id = db.insert(DBModelHome.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id


    }

    public DBModelHome getSingleBooking(long bookingID) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DBModelHome.TABLE_NAME,
                new String[]{

                        DBModelHome.COLUMN_ID,
                        DBModelHome.column_booking_id,
                        DBModelHome.column_created,
                        DBModelHome.column_created_iso,
                        DBModelHome.column_changed,
                        DBModelHome.column_changed_iso,
                        DBModelHome.column_status,
                        DBModelHome.column_email,
                        DBModelHome.column_phone,
                        DBModelHome.column_packages,
                        DBModelHome.column_signature_required,
                        DBModelHome.column_signature_image,

                        DBModelHome.column_customer_first_name,
                        DBModelHome.column_customer_last_name,
                        DBModelHome.column_customer_company,
                        DBModelHome.column_customer_street_address,
                        DBModelHome.column_customer_city,
                        DBModelHome.column_customer_country,
                        DBModelHome.column_customer_postcode,

                        DBModelHome.column_staff_written,

                        DBModelHome.column_event_name,
                        DBModelHome.column_event_time_start_formatted,
                        DBModelHome.column_event_time_end_formatted,
                        DBModelHome.column_event_date_uk,
                        DBModelHome.column_event_date_us,
                        DBModelHome.column_event_date_iso,


                        DBModelHome.column_venue_name,
                        DBModelHome.column_venue_address,
                        DBModelHome.column_venue_postcode,
                        DBModelHome.column_venue_business_country,

                        DBModelHome.column_price_total,

                        DBModelHome.column_notes_admin,
                        DBModelHome.column_notes_customer


                },


                DBModelHome.column_booking_id + "=?",
                new String[]{String.valueOf(bookingID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        DBModelHome note = new DBModelHome(
                cursor.getInt(cursor.getColumnIndex(DBModelHome.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_booking_id)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_created)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_created_iso)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_changed)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_changed_iso)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_status)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_email)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_phone)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_packages)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_signature_required)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_signature_image)),

                cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_first_name)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_last_name)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_company)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_street_address)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_city)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_country)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_postcode)),

                cursor.getString(cursor.getColumnIndex(DBModelHome.column_staff_written)),

                cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_name)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_time_start_formatted)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_time_end_formatted)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_uk)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_us)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_iso)),

                cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_name)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_address)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_postcode)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_business_country)),

                cursor.getString(cursor.getColumnIndex(DBModelHome.column_price_total)),

                cursor.getString(cursor.getColumnIndex(DBModelHome.column_notes_admin)),
                cursor.getString(cursor.getColumnIndex(DBModelHome.column_notes_customer))


        );

        // close the db connection
        cursor.close();

        return note;
    }

    //
    public List<DBModelHome> getAllBookings() {
        List<DBModelHome> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModelHome.TABLE_NAME;
//        + " ORDER BY " +
//                DBModelHome.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                DBModelHome note = new DBModelHome();

                note.setId(cursor.getInt(cursor.getColumnIndex(DBModelHome.COLUMN_ID)));
                note.setBooking_id(cursor.getString(cursor.getColumnIndex(DBModelHome.column_booking_id)));
                note.setCreated(cursor.getString(cursor.getColumnIndex(DBModelHome.column_created)));
                note.setCreated_iso(cursor.getString(cursor.getColumnIndex(DBModelHome.column_created_iso)));
                note.setChanged(cursor.getString(cursor.getColumnIndex(DBModelHome.column_changed)));
                note.setChanged_iso(cursor.getString(cursor.getColumnIndex(DBModelHome.column_changed_iso)));
                note.setStatus(cursor.getString(cursor.getColumnIndex(DBModelHome.column_status)));
                note.setEmail(cursor.getString(cursor.getColumnIndex(DBModelHome.column_email)));
                note.setPhone(cursor.getString(cursor.getColumnIndex(DBModelHome.column_phone)));
                note.setPackages(cursor.getString(cursor.getColumnIndex(DBModelHome.column_packages)));
                note.setSignature_required(cursor.getString(cursor.getColumnIndex(DBModelHome.column_signature_required)));
                note.setSignature_image(cursor.getString(cursor.getColumnIndex(DBModelHome.column_signature_image)));

                note.setCustomer_first_name(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_first_name)));
                note.setCustomer_last_name(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_last_name)));
                note.setCustomer_company(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_company)));
                note.setCustomer_street_address(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_street_address)));
                note.setCustomer_city(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_city)));
                note.setCustomer_country(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_country)));
                note.setCustomer_postcode(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_postcode)));

                note.setStaff_written(cursor.getString(cursor.getColumnIndex(DBModelHome.column_staff_written)));

                note.setEvent_name(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_name)));
                note.setEvent_time_start_formatted(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_time_start_formatted)));
                note.setEvent_time_end_formatted(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_time_end_formatted)));
                note.setEvent_date_uk(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_uk)));
                note.setEvent_date_us(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_us)));
                note.setEvent_date_iso(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_iso)));


                note.setVenue_name(cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_name)));
                note.setVenue_address(cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_address)));
                note.setVenue_postcode(cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_postcode)));
                note.setVenue_business_country(cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_business_country)));

                note.setPrice_total(cursor.getString(cursor.getColumnIndex(DBModelHome.column_price_total)));

                note.setNotes_admin(cursor.getString(cursor.getColumnIndex(DBModelHome.column_notes_admin)));
                note.setNotes_customer(cursor.getString(cursor.getColumnIndex(DBModelHome.column_notes_customer)));


                notes.add(note);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getBookingCount() {

        String countQuery = "SELECT  * FROM " + DBModelHome.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;

    }

    public int updateBooking(DBModelHome booking) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(DBModelHome.COLUMN_ID, booking.getId());
//        values.put(DBModelHome.column_booking_id, booking.getBooking_id());
        values.put(DBModelHome.column_created, booking.getCreated());
        values.put(DBModelHome.column_created_iso, booking.getCreated_iso());
        values.put(DBModelHome.column_changed, booking.getChanged());
        values.put(DBModelHome.column_changed_iso, booking.getChanged_iso());
        values.put(DBModelHome.column_status, booking.getStatus());
        values.put(DBModelHome.column_email, booking.getEmail());
        values.put(DBModelHome.column_phone, booking.getPhone());
        values.put(DBModelHome.column_packages, booking.getPackages());
        values.put(DBModelHome.column_signature_required, booking.getSignature_required());
        values.put(DBModelHome.column_signature_image, booking.getSignature_image());

        values.put(DBModelHome.column_customer_first_name, booking.getCustomer_first_name());
        values.put(DBModelHome.column_customer_last_name, booking.getCustomer_last_name());
        values.put(DBModelHome.column_customer_company, booking.getCustomer_company());
        values.put(DBModelHome.column_customer_street_address, booking.getCustomer_street_address());
        values.put(DBModelHome.column_customer_city, booking.getCustomer_city());
        values.put(DBModelHome.column_customer_country, booking.getCustomer_country());
        values.put(DBModelHome.column_customer_postcode, booking.getCustomer_postcode());
        values.put(DBModelHome.column_staff_written, booking.getStaff_written());
        values.put(DBModelHome.column_event_name, booking.getEvent_name());
        values.put(DBModelHome.column_event_time_start_formatted, booking.getEvent_time_start_formatted());
        values.put(DBModelHome.column_event_time_end_formatted, booking.getEvent_time_end_formatted());
        values.put(DBModelHome.column_event_date_uk, booking.getEvent_date_uk());
        values.put(DBModelHome.column_event_date_us, booking.getEvent_date_us());
        values.put(DBModelHome.column_event_date_iso, booking.getEvent_date_iso());
        values.put(DBModelHome.column_venue_name, booking.getEvent_name());
        values.put(DBModelHome.column_venue_address, booking.getVenue_address());
        values.put(DBModelHome.column_venue_postcode, booking.getVenue_postcode());
        values.put(DBModelHome.column_venue_business_country, booking.getVenue_business_country());
        values.put(DBModelHome.column_price_total, booking.getPrice_total());
        values.put(DBModelHome.column_notes_admin, booking.getNotes_admin());
        values.put(DBModelHome.column_notes_customer, booking.getNotes_customer());


        // updating row
        return db.update(DBModelHome.TABLE_NAME, values, DBModelHome.column_booking_id + " = ?",
                new String[]{String.valueOf(booking.getBooking_id())});
    }


    public void updateBookingImage(String bookingID, String base64Image) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "UPDATE " + DBModelHome.TABLE_NAME + " SET " + DBModelHome.column_signature_image + " = '" + base64Image + "' WHERE " + DBModelHome.column_booking_id + " = " + bookingID;

        db.execSQL(sql);

    }


    public void deleteNote(DBModelHome note) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBModelHome.TABLE_NAME, DBModelHome.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();

    }


    public List<DBModelHome> getFilteredBookings() {
        List<DBModelHome> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBModelHome.TABLE_NAME + "where";
//        + " ORDER BY " +
//                DBModelHome.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                DBModelHome note = new DBModelHome();

                note.setId(cursor.getInt(cursor.getColumnIndex(DBModelHome.COLUMN_ID)));
                note.setBooking_id(cursor.getString(cursor.getColumnIndex(DBModelHome.column_booking_id)));
                note.setCreated(cursor.getString(cursor.getColumnIndex(DBModelHome.column_created)));
                note.setCreated_iso(cursor.getString(cursor.getColumnIndex(DBModelHome.column_created_iso)));
                note.setChanged(cursor.getString(cursor.getColumnIndex(DBModelHome.column_changed)));
                note.setChanged_iso(cursor.getString(cursor.getColumnIndex(DBModelHome.column_changed_iso)));
                note.setStatus(cursor.getString(cursor.getColumnIndex(DBModelHome.column_status)));
                note.setEmail(cursor.getString(cursor.getColumnIndex(DBModelHome.column_email)));
                note.setPhone(cursor.getString(cursor.getColumnIndex(DBModelHome.column_phone)));
                note.setPackages(cursor.getString(cursor.getColumnIndex(DBModelHome.column_packages)));
                note.setSignature_required(cursor.getString(cursor.getColumnIndex(DBModelHome.column_signature_required)));

                note.setCustomer_first_name(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_first_name)));
                note.setCustomer_last_name(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_last_name)));
                note.setCustomer_company(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_company)));
                note.setCustomer_street_address(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_street_address)));
                note.setCustomer_city(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_city)));
                note.setCustomer_country(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_country)));
                note.setCustomer_postcode(cursor.getString(cursor.getColumnIndex(DBModelHome.column_customer_postcode)));

                note.setStaff_written(cursor.getString(cursor.getColumnIndex(DBModelHome.column_staff_written)));

                note.setEvent_name(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_name)));
                note.setEvent_time_start_formatted(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_time_start_formatted)));
                note.setEvent_time_end_formatted(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_time_end_formatted)));
                note.setEvent_date_uk(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_uk)));
                note.setEvent_date_us(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_us)));
                note.setEvent_date_iso(cursor.getString(cursor.getColumnIndex(DBModelHome.column_event_date_iso)));


                note.setVenue_name(cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_name)));
                note.setVenue_address(cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_address)));
                note.setVenue_postcode(cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_postcode)));
                note.setVenue_business_country(cursor.getString(cursor.getColumnIndex(DBModelHome.column_venue_business_country)));

                note.setPrice_total(cursor.getString(cursor.getColumnIndex(DBModelHome.column_price_total)));

                note.setNotes_admin(cursor.getString(cursor.getColumnIndex(DBModelHome.column_notes_admin)));
                note.setNotes_customer(cursor.getString(cursor.getColumnIndex(DBModelHome.column_notes_customer)));


                notes.add(note);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }


    public void insertLead(DBLeads lead) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

//        values.put(DBModelHome.COLUMN_ID, booking.getId());
        values.put(DBLeads.column_id, lead.getLead_id());
        values.put(DBLeads.column_firstName, lead.getFirstName());
        values.put(DBLeads.column_lastName, lead.getLastname());
        values.put(DBLeads.column_companyName, lead.getCompanyName());
        values.put(DBLeads.column_email, lead.getEmail());
        values.put(DBLeads.column_telephone, lead.getTelephone());
        values.put(DBLeads.column_mobile_phone, lead.getMobilePhone());
        values.put(DBLeads.column_address, lead.getAddress());
        values.put(DBLeads.column_city, lead.getCity());
        values.put(DBLeads.column_postCode, lead.getPostcode());
        values.put(DBLeads.column_eventDate, lead.getEventDate());
        values.put(DBLeads.column_Time_start, lead.getEventTimeStart());
        values.put(DBLeads.column_Time_end, lead.getEventTimeEnd());
        values.put(DBLeads.column_Type, lead.getEventType());
        values.put(DBLeads.column_Name, lead.getEventName());
        values.put(DBLeads.column_Budget, lead.getEventBudget());
        values.put(DBLeads.column_contactPermission, lead.getContactPermission());
        values.put(DBLeads.column_additional_notes, lead.getAdditionalNotes());
        values.put(DBLeads.column_event_img, lead.getEventImage());
        values.put(DBLeads.column_updated, lead.getEventImage());
        values.put(DBLeads.column_venueName, lead.getVenueName());
        values.put(DBLeads.column_venueAddress, lead.getVenueAddress());
        values.put(DBLeads.column_venuePostcode, lead.getVenuPostCode());
        values.put(DBLeads.column_eventTypeID, lead.getEventTypeID());
        values.put(DBLeads.column_uploadedStatus, lead.getUploadedStatus());


        // insert row
        long id = db.insert(DBLeads.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id

    }

    public List<DBLeads> getAllLeadsNotUploaded() {

        List<DBLeads> leadsList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBLeads.TABLE_NAME + " WHERE uploadedStatus=0";
//        + " ORDER BY " +
//                DBModelHome.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                DBLeads lead = new DBLeads();

                lead.setId(cursor.getInt(cursor.getColumnIndex(DBLeads.COLUMN_ID)));
                lead.setLead_id(cursor.getString(cursor.getColumnIndex(DBLeads.column_id)));
                lead.setFirstName(cursor.getString(cursor.getColumnIndex(DBLeads.column_firstName)));
                lead.setLastname(cursor.getString(cursor.getColumnIndex(DBLeads.column_lastName)));
                lead.setCompanyName(cursor.getString(cursor.getColumnIndex(DBLeads.column_companyName)));
                lead.setEmail(cursor.getString(cursor.getColumnIndex(DBLeads.column_email)));
                lead.setTelephone(cursor.getString(cursor.getColumnIndex(DBLeads.column_telephone)));
                lead.setMobilePhone(cursor.getString(cursor.getColumnIndex(DBLeads.column_mobile_phone)));
                lead.setAddress(cursor.getString(cursor.getColumnIndex(DBLeads.column_address)));
                lead.setCity(cursor.getString(cursor.getColumnIndex(DBLeads.column_city)));
                lead.setPostcode(cursor.getString(cursor.getColumnIndex(DBLeads.column_postCode)));
                lead.setEventDate(cursor.getString(cursor.getColumnIndex(DBLeads.column_eventDate)));
                lead.setEventTimeStart(cursor.getString(cursor.getColumnIndex(DBLeads.column_Time_start)));
                lead.setEventTimeEnd(cursor.getString(cursor.getColumnIndex(DBLeads.column_Time_end)));
                lead.setEventType(cursor.getString(cursor.getColumnIndex(DBLeads.column_Type)));
                lead.setEventName(cursor.getString(cursor.getColumnIndex(DBLeads.column_Name)));
                lead.setEventBudget(cursor.getString(cursor.getColumnIndex(DBLeads.column_Budget)));
                lead.setContactPermission(cursor.getString(cursor.getColumnIndex(DBLeads.column_contactPermission)));
                lead.setAdditionalNotes(cursor.getString(cursor.getColumnIndex(DBLeads.column_additional_notes)));
                lead.setEventImage(cursor.getString(cursor.getColumnIndex(DBLeads.column_event_img)));
                lead.setUpdated(cursor.getString(cursor.getColumnIndex(DBLeads.column_updated)));
                lead.setVenueName(cursor.getString(cursor.getColumnIndex(DBLeads.column_venueName)));
                lead.setVenueAddress(cursor.getString(cursor.getColumnIndex(DBLeads.column_venueAddress)));
                lead.setVenuPostCode(cursor.getString(cursor.getColumnIndex(DBLeads.column_venuePostcode)));
                lead.setEventTypeID(cursor.getString(cursor.getColumnIndex(DBLeads.column_eventTypeID)));
                lead.setUploadedStatus(cursor.getString(cursor.getColumnIndex(DBLeads.column_uploadedStatus)));

                leadsList.add(lead);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return leadsList;
    }

    public List<DBLeads> getAllLeads() {

        List<DBLeads> leadsList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBLeads.TABLE_NAME;
//        + " ORDER BY " +
//                DBModelHome.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                DBLeads lead = new DBLeads();

                lead.setId(cursor.getInt(cursor.getColumnIndex(DBLeads.COLUMN_ID)));
                lead.setLead_id(cursor.getString(cursor.getColumnIndex(DBLeads.column_id)));
                lead.setFirstName(cursor.getString(cursor.getColumnIndex(DBLeads.column_firstName)));
                lead.setLastname(cursor.getString(cursor.getColumnIndex(DBLeads.column_lastName)));
                lead.setCompanyName(cursor.getString(cursor.getColumnIndex(DBLeads.column_companyName)));
                lead.setEmail(cursor.getString(cursor.getColumnIndex(DBLeads.column_email)));
                lead.setTelephone(cursor.getString(cursor.getColumnIndex(DBLeads.column_telephone)));
                lead.setMobilePhone(cursor.getString(cursor.getColumnIndex(DBLeads.column_mobile_phone)));
                lead.setAddress(cursor.getString(cursor.getColumnIndex(DBLeads.column_address)));
                lead.setCity(cursor.getString(cursor.getColumnIndex(DBLeads.column_city)));
                lead.setPostcode(cursor.getString(cursor.getColumnIndex(DBLeads.column_postCode)));
                lead.setEventDate(cursor.getString(cursor.getColumnIndex(DBLeads.column_eventDate)));
                lead.setEventTimeStart(cursor.getString(cursor.getColumnIndex(DBLeads.column_Time_start)));
                lead.setEventTimeEnd(cursor.getString(cursor.getColumnIndex(DBLeads.column_Time_end)));
                lead.setEventType(cursor.getString(cursor.getColumnIndex(DBLeads.column_Type)));
                lead.setEventName(cursor.getString(cursor.getColumnIndex(DBLeads.column_Name)));
                lead.setEventBudget(cursor.getString(cursor.getColumnIndex(DBLeads.column_Budget)));
                lead.setContactPermission(cursor.getString(cursor.getColumnIndex(DBLeads.column_contactPermission)));
                lead.setAdditionalNotes(cursor.getString(cursor.getColumnIndex(DBLeads.column_additional_notes)));
                lead.setEventImage(cursor.getString(cursor.getColumnIndex(DBLeads.column_event_img)));
                lead.setUpdated(cursor.getString(cursor.getColumnIndex(DBLeads.column_updated)));
                lead.setVenueName(cursor.getString(cursor.getColumnIndex(DBLeads.column_venueName)));
                lead.setVenueAddress(cursor.getString(cursor.getColumnIndex(DBLeads.column_venueAddress)));
                lead.setVenuPostCode(cursor.getString(cursor.getColumnIndex(DBLeads.column_venuePostcode)));
                lead.setEventTypeID(cursor.getString(cursor.getColumnIndex(DBLeads.column_eventTypeID)));
                lead.setUploadedStatus(cursor.getString(cursor.getColumnIndex(DBLeads.column_uploadedStatus)));

                leadsList.add(lead);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return leadsList;
    }

    public DBLeads getSingleLead(long leadID) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DBLeads.TABLE_NAME,
                new String[]{

                        DBLeads.COLUMN_ID,
                        DBLeads.column_id,
                        DBLeads.column_firstName,
                        DBLeads.column_lastName,
                        DBLeads.column_companyName,
                        DBLeads.column_email,
                        DBLeads.column_telephone,
                        DBLeads.column_mobile_phone,
                        DBLeads.column_address,
                        DBLeads.column_city,
                        DBLeads.column_postCode,
                        DBLeads.column_eventDate,
                        DBLeads.column_Time_start,
                        DBLeads.column_Time_end,
                        DBLeads.column_Type,
                        DBLeads.column_Name,
                        DBLeads.column_Budget,
                        DBLeads.column_contactPermission,
                        DBLeads.column_additional_notes,
                        DBLeads.column_event_img,
                        DBLeads.column_updated,
                        DBLeads.column_venueName,
                        DBLeads.column_venueAddress,
                        DBLeads.column_venuePostcode,
                        DBLeads.column_eventTypeID,
                        DBLeads.column_uploadedStatus,

                },

                DBLeads.COLUMN_ID + "=?",
                new String[]{String.valueOf(leadID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        DBLeads singleLead = new DBLeads(

                cursor.getInt(cursor.getColumnIndex(DBLeads.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_id)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_firstName)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_lastName)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_companyName)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_email)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_telephone)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_mobile_phone)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_address)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_city)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_postCode)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_eventDate)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_Time_start)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_Time_end)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_Type)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_Name)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_Budget)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_contactPermission)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_additional_notes)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_event_img)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_updated)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_venueName)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_venueAddress)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_venuePostcode)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_eventTypeID)),
                cursor.getString(cursor.getColumnIndex(DBLeads.column_uploadedStatus))

        );

        cursor.close();
        return singleLead;


    }

    public boolean updateLeadStatus(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE " + DBLeads.TABLE_NAME + " SET " + DBLeads.column_uploadedStatus + " = 1 WHERE " + DBLeads.COLUMN_ID + " = " + id;
        db.execSQL(strSQL);

        return true;
    }

    public void insertType(EventTypeModel typeModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

//        values.put(typeModel.column_id, typeModel.get());
        values.put(EventTypeModel.column_CATEGORY_ID, typeModel.getCateGoryID());
        values.put(EventTypeModel.column_label, typeModel.getLabel());

        // insert row
        long id = db.insert(EventTypeModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id

    }

    public List<EventTypeModel> getAllTypes() {
        List<EventTypeModel> notes = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + EventTypeModel.TABLE_NAME;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                EventTypeModel note = new EventTypeModel();

                note.setRow_id(cursor.getInt(cursor.getColumnIndex(EventTypeModel.COLUMN_ID)));
                note.setLabel(cursor.getString(cursor.getColumnIndex(EventTypeModel.column_label)));
                note.setCateGoryID(cursor.getString(cursor.getColumnIndex(EventTypeModel.column_CATEGORY_ID)));


                notes.add(note);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getTypeCount() {

        String countQuery = "SELECT * FROM " + EventTypeModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;

    }

    public int updateType(EventTypeModel eventModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EventTypeModel.column_CATEGORY_ID, eventModel.getCateGoryID());
        values.put(EventTypeModel.column_label, eventModel.getLabel());


        // updating row
        return db.update(EventTypeModel.TABLE_NAME, values, EventTypeModel.column_CATEGORY_ID + " = ?",
                new String[]{String.valueOf(eventModel.getCateGoryID())});
    }

}