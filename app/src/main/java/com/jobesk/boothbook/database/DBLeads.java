package com.jobesk.boothbook.database;

public class DBLeads {


    public static final String TABLE_NAME = "leads";

    public static final String COLUMN_ID = "id";
    public static final String column_id = "lead_id";
    public static final String column_firstName = "firstName";
    public static final String column_lastName = "lastName";
    public static final String column_companyName = "companyName";
    public static final String column_email = "email";
    public static final String column_telephone = "telephone";
    public static final String column_mobile_phone = "mobilePhone";
    public static final String column_address = "address";
    public static final String column_city = "city";
    public static final String column_postCode = "postcode";
    public static final String column_eventDate = "eventDate";
    public static final String column_Time_start = "eventStartTime";
    public static final String column_Time_end = "eventEdTime";
    public static final String column_Type = "eventType";
    public static final String column_Name = "eventName";
    public static final String column_Budget = "eventBudget";
    public static final String column_contactPermission = "contactpermission";
    public static final String column_additional_notes = "additionalNotes";
    public static final String column_event_img = "eventImg";
    public static final String column_updated = "updated";
    public static final String column_venueName= "venueName";
    public static final String column_venueAddress= "venueAddress";
    public static final String column_venuePostcode= "venuePostcode";
    public static final String column_eventTypeID= "eventTypeID";
    public static final String column_uploadedStatus= "uploadedStatus";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + column_id + " TEXT,"
                    + column_firstName + " TEXT,"
                    + column_lastName + " TEXT,"
                    + column_companyName + " TEXT,"
                    + column_email + " TEXT,"
                    + column_telephone + " TEXT,"
                    + column_mobile_phone + " TEXT,"
                    + column_address + " TEXT,"
                    + column_city + " TEXT,"
                    + column_postCode + " TEXT,"
                    + column_eventDate + " TEXT,"
                    + column_Time_start + " TEXT,"
                    + column_Time_end + " TEXT,"
                    + column_Type + " TEXT,"
                    + column_Name + " TEXT,"
                    + column_Budget + " TEXT,"
                    + column_contactPermission + " TEXT,"
                    + column_additional_notes + " TEXT,"
                    + column_event_img + " TEXT,"
                    + column_updated + " TEXT,"
                    + column_venueName + " TEXT,"
                    + column_venueAddress + " TEXT,"
                    + column_venuePostcode + " TEXT,"
                    + column_eventTypeID + " TEXT,"
                    + column_uploadedStatus + " TEXT"
                    + ")";


    int id;
    String lead_id, firstName, lastname, companyName, email, telephone, mobilePhone, address, city, eventImage;
    String postcode, eventDate, eventTimeStart, eventTimeEnd, eventType, eventName, eventBudget, contactPermission, additionalNotes, updated;
    String venueName, venueAddress, venuPostCode;

    String eventTypeID, uploadedStatus;

    public DBLeads() {

    }

    public DBLeads(
            int id,
            String lead_id,
            String firstName,
            String lastname,
            String companyName,
            String email,
            String telephone,
            String mobilePhone,
            String address,
            String city,
            String postcode,
            String eventDate,
            String eventTimeStart,
            String eventTimeEnd,
            String eventType,
            String eventName,
            String eventBudget,
            String contactPermission,
            String additionalNotes,
            String eventImage,
            String updated,
            String venueName,
            String venueAddress,
            String venuPostCode,
            String eventTypeID,
            String uploadedStatus


    ) {


        this.id = id;
        this.lead_id = lead_id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.companyName = companyName;
        this.email = email;
        this.telephone = telephone;
        this.mobilePhone = mobilePhone;
        this.address = address;
        this.city = city;
        this.eventImage = eventImage;
        this.postcode = postcode;
        this.eventDate = eventDate;
        this.eventTimeStart = eventTimeStart;
        this.eventTimeEnd = eventTimeEnd;
        this.eventType = eventType;
        this.eventName = eventName;
        this.eventBudget = eventBudget;
        this.contactPermission = contactPermission;
        this.additionalNotes = additionalNotes;
        this.updated = updated;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venuPostCode = venuPostCode;
        this.eventTypeID = eventTypeID;
        this.uploadedStatus = uploadedStatus;

    }

    public String getEventTypeID() {
        return eventTypeID;
    }

    public void setEventTypeID(String eventTypeID) {
        this.eventTypeID = eventTypeID;
    }

    public String getUploadedStatus() {
        return uploadedStatus;
    }

    public void setUploadedStatus(String uploadedStatus) {
        this.uploadedStatus = uploadedStatus;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenuPostCode() {
        return venuPostCode;
    }

    public void setVenuPostCode(String venuPostCode) {
        this.venuPostCode = venuPostCode;
    }

    public String getEventTimeStart() {
        return eventTimeStart;
    }

    public void setEventTimeStart(String eventTimeStart) {
        this.eventTimeStart = eventTimeStart;
    }

    public String getEventTimeEnd() {
        return eventTimeEnd;
    }

    public void setEventTimeEnd(String eventTimeEnd) {
        this.eventTimeEnd = eventTimeEnd;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventBudget() {
        return eventBudget;
    }

    public void setEventBudget(String eventBudget) {
        this.eventBudget = eventBudget;
    }

    public String getContactPermission() {
        return contactPermission;
    }

    public void setContactPermission(String contactPermission) {
        this.contactPermission = contactPermission;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
}
