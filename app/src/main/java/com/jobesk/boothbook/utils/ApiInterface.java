package com.jobesk.boothbook.utils;


import com.jobesk.boothbook.activities.login.LoginModel;
import com.jobesk.boothbook.fragments.calanderfrag.modelsbooking.BookingResponse;
import com.jobesk.boothbook.models.EventTypeModel;
import com.jobesk.boothbook.models.LocationReceiveModel;
import com.jobesk.boothbook.models.SaveLeadModel;
import com.jobesk.boothbook.models.SaveModelCallBack;
import com.jobesk.boothbook.models.SignatureReceiveModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("get/bookings")
    Call<BookingResponse> MyBookings(
            @Query("key") String key,
            @Query("secret") String secret,
            @Query("scope") String scope
//            @Query("start") String start,
//             @Query("end") String end
    );


    @GET("login")
    Call<LoginModel> LoginUser(
            @Query("username") String username,
            @Query("password") String password

    );


    @GET("get/event_types")
    Call<List<EventTypeModel>> GetTypesEvents(@Query("key") String key,
                                              @Query("secret") String secret

    );

    @POST("create/lead")
    Call<SaveModelCallBack> CreateLead(@Body SaveLeadModel model);


    @POST("create/signature")
    Call<SignatureReceiveModel> uploadSignature(@Query("key") String key,
                                                @Query("secret") String secret,
                                                @Query("booking_id") String booking_id,
                                                @Query("name") String name,
                                                @Query("title") String title,
                                                @Body String imageBase64);


    @GET("create/location")
    Call<LocationReceiveModel> uploadUserLatLong(@Query("key") String key,
                                                 @Query("secret") String secret,
                                                 @Query("latitude") double latitude,
                                                 @Query("longitude") double longitude,
                                                 @Query("created") double created,
                                                 @Query("uid") String uid

    );


//    @Query("key") String key,
//    @Query("secret") String secret,
//    @Query("first_name") String first_name,
//    @Query("last_name") String last_name,
//    @Query("company") String company,
//    @Query("email") String email,
//    @Query("telephone") String telephone,
//    @Query("mobile_phone_number") String mobile_phone_number,
//    @Query("street_address") String street_address,
//    @Query("city") String city,
//    @Query("postcode") String postcode,
//    @Query("event_date") String event_date,
//    @Query("event_time_start") String event_time_start,
//    @Query("event_time_end") String event_time_end,
//    @Query("event_type") String event_type,
//    @Query("event_name") String event_name,
//    @Query("venue_name") String venue_name,
//    @Query("venue_address") String venue_address,
//    @Query("venue_postcode") String venue_postcode,
//    @Query("pipeline_status") String pipeline_status,
//    @Query("additional_notes") String additional_notes

}



