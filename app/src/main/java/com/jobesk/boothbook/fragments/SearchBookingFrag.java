package com.jobesk.boothbook.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.adapters.FilterAdapter;

import com.jobesk.boothbook.database.DBModelHome;
import com.jobesk.boothbook.database.DatabaseHelper;
import com.jobesk.boothbook.models.CalenderModel;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.RecyclerItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchBookingFrag extends Fragment {


    private View rootView;
    private ImageView backimg;
    private CalenderModel calenderModel;
    private ArrayList<CalenderModel> arrayList = new ArrayList<>();
    private ArrayList<CalenderModel> arrayListTemp = new ArrayList<>();
    private RecyclerView recyclerView;
    private FilterAdapter mAdapter;
    private Activity activity;
    private DatabaseHelper db;
    private String date, bookingID, lastName, staff, searchAll, firstName, status;
    private List<DBModelHome> bookingList;
    private TextView event_date_tv, displaying_tv;
    private String preYear, preMonth, preDay;
    private TextView booking_id_tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.frag_search_booking_detail, container, false);

        activity = (MainActivity) rootView.getContext();

        db = new DatabaseHelper(getActivity());
        bookingList = db.getAllBookings();

        preYear = getArguments().getString("year");
        preMonth = getArguments().getString("month");
        preDay = getArguments().getString("day");
        date = preYear + "-" + preMonth + "-" + preDay;

        bookingID = getArguments().getString("bookingID");
        lastName = getArguments().getString("lastName");
        staff = getArguments().getString("staff");
        searchAll = getArguments().getString("searchAll");
        status = getArguments().getString("status");
        firstName = getArguments().getString("firstName");

        event_date_tv = rootView.findViewById(R.id.event_date_tv);
        displaying_tv = rootView.findViewById(R.id.displaying_tv);
        booking_id_tv = rootView.findViewById(R.id.booking_id_tv);


        if (bookingID != null) {

            booking_id_tv.setText(" " + bookingID + "");

        }

        event_date_tv.setText(" " + date);


        backimg = rootView.findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStackImmediate();
            }
        });

//        for (int i = 0; i < bookingList.size(); i++) {

//            String dateVal = bookingList.get(i).getEvent_date_iso();
//            String bookingIDVal = bookingList.get(i).getBooking_id();
//            String lastNameVal = bookingList.get(i).getCustomer_last_name();
//            String staffVal = bookingList.get(i).getStaff_written();
//            String statusVal = bookingList.get(i).getStatus();

//            if (dateVal.equalsIgnoreCase(date) || bookingIDVal.equalsIgnoreCase(bookingID)) {

//                String eventDes = bookingList.get(i).getEvent_date_uk() + "," +
//                        bookingList.get(i).getEvent_time_start_formatted() + "-" + bookingList.get(i).getEvent_time_start_formatted() +
//                        " @" + bookingList.get(i).getVenue_name();
//
//                calenderModel = new CalenderModel();
//                calenderModel.setId(bookingList.get(i).getBooking_id());
//                calenderModel.setName(bookingList.get(i).getEvent_name());
//                calenderModel.setDescription(eventDes);
//                arrayList.add(calenderModel);
//
//
//            }
//
//
////            if (dateVal.equalsIgnoreCase(date)
////                    || bookingIDVal.equalsIgnoreCase(bookingID)
////                    || lastNameVal.equalsIgnoreCase(lastName)
////                    || staffVal.equalsIgnoreCase(staff)
////                    || statusVal.equalsIgnoreCase(status)
////
////            ) {
////
////
////                String eventDes = bookingList.get(i).getEvent_date_uk() + "," +
////
////                        bookingList.get(i).getEvent_time_start_formatted() + "-" + bookingList.get(i).getEvent_time_start_formatted() +
////
////                        " @" + bookingList.get(i).getVenue_name();
////
////
////                calenderModel = new CalenderModel();
////                calenderModel.setId(bookingList.get(i).getBooking_id());
////                calenderModel.setName(bookingList.get(i).getEvent_name());
////                calenderModel.setDescription(eventDes);
////                arrayList.add(calenderModel);
////
////
////            }
//
//
//        }


        filterFirstByDate();


        return rootView;
    }


    private void getOtherSearches() {


        if (!bookingID.equalsIgnoreCase("")) {


            for (int i = 0; i < arrayList.size(); i++) {
//

                String dateVal = arrayList.get(i).getDate();
                String bookingIDVal = arrayList.get(i).getId();
                String lastNameVal = arrayList.get(i).getLastName();
                String staffVal = arrayList.get(i).getStaff();
                String statusVal = arrayList.get(i).getStatus();


                if (bookingIDVal.equalsIgnoreCase(bookingID)) {


                    String eventDes = arrayList.get(i).getDateUk() + "," +

                            arrayList.get(i).getEventTimeStart() + "-" + arrayList.get(i).getEventTimeEnd() +

                            " @" + arrayList.get(i).getVenueName();


                    calenderModel = new CalenderModel();
                    calenderModel.setId(arrayList.get(i).getId());
                    calenderModel.setName(arrayList.get(i).getEventName());
                    calenderModel.setDescription(eventDes);
                    calenderModel.setDate(arrayList.get(i).getDate());
                    calenderModel.setLastName(arrayList.get(i).getLastName());
                    calenderModel.setStaff(arrayList.get(i).getStaff());
                    calenderModel.setStatus(arrayList.get(i).getStatus());


                    calenderModel.setEmail(arrayList.get(i).getEmail());
                    calenderModel.setCompanyName(arrayList.get(i).getCompanyName());
                    calenderModel.setCustomerFirstName(arrayList.get(i).getCustomerFirstName());
                    calenderModel.setCustomerLastName(arrayList.get(i).getCustomerLastName());
                    calenderModel.setAddress(arrayList.get(i).getAddress());
                    calenderModel.setVenueName(arrayList.get(i).getVenueName());
                    calenderModel.setVenueAddress(arrayList.get(i).getVenueAddress());
                    calenderModel.setPostcode(arrayList.get(i).getPostcode());
                    calenderModel.setAdditionalNotes(arrayList.get(i).getAdditionalNotes());


                    arrayListTemp.add(calenderModel);


                }


            }


        }
        if (!lastName.equalsIgnoreCase("")) {

            for (int i = 0; i < arrayList.size(); i++) {
//

                String dateVal = arrayList.get(i).getDate();
                String bookingIDVal = arrayList.get(i).getId();
                String lastNameVal = arrayList.get(i).getLastName();
                String staffVal = arrayList.get(i).getStaff();
                String statusVal = arrayList.get(i).getStatus();


                if (lastNameVal.toLowerCase().contains(lastName.toLowerCase())) {


                    String eventDes = arrayList.get(i).getDateUk() + "," +

                            arrayList.get(i).getEventTimeStart() + "-" + arrayList.get(i).getEventTimeEnd() +

                            " @" + arrayList.get(i).getVenueName();


                    calenderModel = new CalenderModel();
                    calenderModel.setId(arrayList.get(i).getId());
                    calenderModel.setName(arrayList.get(i).getEventName());
                    calenderModel.setDescription(eventDes);
                    calenderModel.setDate(arrayList.get(i).getDate());
                    calenderModel.setLastName(arrayList.get(i).getLastName());
                    calenderModel.setStaff(arrayList.get(i).getStaff());
                    calenderModel.setStatus(arrayList.get(i).getStatus());


                    calenderModel.setEmail(arrayList.get(i).getEmail());
                    calenderModel.setCompanyName(arrayList.get(i).getCompanyName());
                    calenderModel.setCustomerFirstName(arrayList.get(i).getCustomerFirstName());
                    calenderModel.setCustomerLastName(arrayList.get(i).getCustomerLastName());
                    calenderModel.setAddress(arrayList.get(i).getAddress());
                    calenderModel.setVenueName(arrayList.get(i).getVenueName());
                    calenderModel.setVenueAddress(arrayList.get(i).getVenueAddress());
                    calenderModel.setPostcode(arrayList.get(i).getPostcode());
                    calenderModel.setAdditionalNotes(arrayList.get(i).getAdditionalNotes());

                    arrayListTemp.add(calenderModel);


                }


            }

        }

        if (!staff.equalsIgnoreCase("")) {

            for (int i = 0; i < arrayList.size(); i++) {
//

                String dateVal = arrayList.get(i).getDate();
                String bookingIDVal = arrayList.get(i).getId();
                String lastNameVal = arrayList.get(i).getLastName();
                String staffVal = arrayList.get(i).getStaff();
                String statusVal = arrayList.get(i).getStatus();


                if (staffVal.toLowerCase().contains("")) {

                    String eventDes = arrayList.get(i).getDateUk() + "," +

                            arrayList.get(i).getEventTimeStart() + "-" + arrayList.get(i).getEventTimeEnd() +

                            " @" + arrayList.get(i).getVenueName();


                    calenderModel = new CalenderModel();
                    calenderModel.setId(arrayList.get(i).getId());
                    calenderModel.setName(arrayList.get(i).getEventName());
                    calenderModel.setDescription(eventDes);
                    calenderModel.setDate(arrayList.get(i).getDate());
                    calenderModel.setLastName(arrayList.get(i).getLastName());
                    calenderModel.setStaff(arrayList.get(i).getStaff());
                    calenderModel.setStatus(arrayList.get(i).getStatus());


                    calenderModel.setEmail(arrayList.get(i).getEmail());
                    calenderModel.setCompanyName(arrayList.get(i).getCompanyName());
                    calenderModel.setCustomerFirstName(arrayList.get(i).getCustomerFirstName());
                    calenderModel.setCustomerLastName(arrayList.get(i).getCustomerLastName());
                    calenderModel.setAddress(arrayList.get(i).getAddress());
                    calenderModel.setVenueName(arrayList.get(i).getVenueName());
                    calenderModel.setVenueAddress(arrayList.get(i).getVenueAddress());
                    calenderModel.setPostcode(arrayList.get(i).getPostcode());
                    calenderModel.setAdditionalNotes(arrayList.get(i).getAdditionalNotes());
                    arrayListTemp.add(calenderModel);
                }
            }
        }
        if (!searchAll.equalsIgnoreCase("")) {
            for (int i = 0; i < arrayList.size(); i++) {
                String dateVal = arrayList.get(i).getDate();
                String bookingIDVal = arrayList.get(i).getId();
                String lastNameVal = arrayList.get(i).getLastName();
                String staffVal = arrayList.get(i).getStaff();
                String statusVal = arrayList.get(i).getStatus();
                String email = arrayList.get(i).getEmail();
                String firstname = arrayList.get(i).getCustomerFirstName();
                String lastName = arrayList.get(i).getCustomerLastName();
                String company = arrayList.get(i).getCompanyName();
                String customerFirstName = arrayList.get(i).getCustomerFirstName();
                String customerLastname = arrayList.get(i).getCustomerLastName();
                String venueName = arrayList.get(i).getVenueName();
                String venueAddress = arrayList.get(i).getVenueAddress();
                String postCode = arrayList.get(i).getPostcode();
                String additionalNotes = arrayList.get(i).getAdditionalNotes();
                if (
                        searchAll.toLowerCase().contains(staffVal.toLowerCase()) ||
                                searchAll.toLowerCase().contains(bookingIDVal.toLowerCase()) ||
                                searchAll.toLowerCase().contains(lastNameVal.toLowerCase()) ||
                                searchAll.toLowerCase().contains(staffVal.toLowerCase()) ||
                                searchAll.toLowerCase().contains(email.toLowerCase()) ||
                                searchAll.toLowerCase().contains(firstname.toLowerCase()) ||
                                searchAll.toLowerCase().contains(lastName.toLowerCase()) ||
                                searchAll.toLowerCase().contains(company.toLowerCase()) ||
                                searchAll.toLowerCase().contains(customerFirstName.toLowerCase()) ||
                                searchAll.toLowerCase().contains(customerLastname.toLowerCase()) ||
                                searchAll.toLowerCase().contains(venueName.toLowerCase()) ||
                                searchAll.toLowerCase().contains(venueAddress.toLowerCase()) ||
                                searchAll.toLowerCase().contains(postCode.toLowerCase()) ||
                                searchAll.toLowerCase().contains(additionalNotes.toLowerCase()) ||
                                searchAll.toLowerCase().contains(staff.toLowerCase())
                ) {


                    String eventDes = arrayList.get(i).getDateUk() + "," +

                            arrayList.get(i).getEventTimeStart() + "-" + arrayList.get(i).getEventTimeEnd() +

                            " @" + arrayList.get(i).getVenueName();


                    calenderModel = new CalenderModel();
                    calenderModel.setId(arrayList.get(i).getId());
                    calenderModel.setName(arrayList.get(i).getEventName());
                    calenderModel.setDescription(eventDes);
                    calenderModel.setDate(arrayList.get(i).getDate());
                    calenderModel.setLastName(arrayList.get(i).getLastName());
                    calenderModel.setStaff(arrayList.get(i).getStaff());
                    calenderModel.setStatus(arrayList.get(i).getStatus());


                    calenderModel.setEmail(arrayList.get(i).getEmail());
                    calenderModel.setCompanyName(arrayList.get(i).getCompanyName());
                    calenderModel.setCustomerFirstName(arrayList.get(i).getCustomerFirstName());
                    calenderModel.setCustomerLastName(arrayList.get(i).getCustomerLastName());
                    calenderModel.setAddress(arrayList.get(i).getAddress());
                    calenderModel.setVenueName(arrayList.get(i).getVenueName());
                    calenderModel.setVenueAddress(arrayList.get(i).getVenueAddress());
                    calenderModel.setPostcode(arrayList.get(i).getPostcode());
                    calenderModel.setAdditionalNotes(arrayList.get(i).getAdditionalNotes());

                    arrayListTemp.add(calenderModel);


                }


            }


        }

        if (!status.equalsIgnoreCase("")) {


            for (int i = 0; i < arrayList.size(); i++) {


                String dateVal = arrayList.get(i).getDate();
                String bookingIDVal = arrayList.get(i).getId();
                String lastNameVal = arrayList.get(i).getLastName();
                String staffVal = arrayList.get(i).getStaff();
                String statusVal = arrayList.get(i).getStatus();
                String email = arrayList.get(i).getEmail();
                String firstname = arrayList.get(i).getCustomerFirstName();
                String lastName = arrayList.get(i).getCustomerLastName();
                String company = arrayList.get(i).getCompanyName();
                String customerFirstName = arrayList.get(i).getCustomerFirstName();
                String customerLastname = arrayList.get(i).getCustomerLastName();
                String venueName = arrayList.get(i).getVenueName();
                String venueAddress = arrayList.get(i).getVenueAddress();
                String postCode = arrayList.get(i).getPostcode();
                String additionalNotes = arrayList.get(i).getAdditionalNotes();
                String statuss = arrayList.get(i).getStatus();


                String statusCompare = "1";

                if (status.equalsIgnoreCase("1")) {
                    statusCompare = "Customer Details Confirmed";
                } else {
                    statusCompare = "Unconfirmed";
                }


                if (statusCompare.toLowerCase().equalsIgnoreCase(statuss)) {


                    String eventDes = arrayList.get(i).getDateUk() + "," +
                            arrayList.get(i).getEventTimeStart() + "-" + arrayList.get(i).getEventTimeEnd() +
                            " @" + arrayList.get(i).getVenueName();

                    calenderModel = new CalenderModel();
                    calenderModel.setId(arrayList.get(i).getId());
                    calenderModel.setName(arrayList.get(i).getEventName());
                    calenderModel.setDescription(eventDes);
                    calenderModel.setDate(arrayList.get(i).getDate());
                    calenderModel.setLastName(arrayList.get(i).getLastName());
                    calenderModel.setStaff(arrayList.get(i).getStaff());
                    calenderModel.setStatus(arrayList.get(i).getStatus());


                    calenderModel.setEmail(arrayList.get(i).getEmail());
                    calenderModel.setCompanyName(arrayList.get(i).getCompanyName());
                    calenderModel.setCustomerFirstName(arrayList.get(i).getCustomerFirstName());
                    calenderModel.setCustomerLastName(arrayList.get(i).getCustomerLastName());
                    calenderModel.setAddress(arrayList.get(i).getAddress());
                    calenderModel.setVenueName(arrayList.get(i).getVenueName());
                    calenderModel.setVenueAddress(arrayList.get(i).getVenueAddress());
                    calenderModel.setPostcode(arrayList.get(i).getPostcode());
                    calenderModel.setAdditionalNotes(arrayList.get(i).getAdditionalNotes());

                    arrayListTemp.add(calenderModel);
                }
            }
        }
        if (firstName.equalsIgnoreCase("")) {

            for (int i = 0; i < arrayList.size(); i++) {

                String dateVal = arrayList.get(i).getDate();
                String bookingIDVal = arrayList.get(i).getId();
                String lastNameVal = arrayList.get(i).getLastName();
                String staffVal = arrayList.get(i).getStaff();
                String statusVal = arrayList.get(i).getStatus();
                String email = arrayList.get(i).getEmail();
                String firstname = arrayList.get(i).getCustomerFirstName();
                String lastName = arrayList.get(i).getCustomerLastName();
                String company = arrayList.get(i).getCompanyName();
                String customerFirstName = arrayList.get(i).getCustomerFirstName();
                String customerLastname = arrayList.get(i).getCustomerLastName();
                String venueName = arrayList.get(i).getVenueName();
                String venueAddress = arrayList.get(i).getVenueAddress();
                String postCode = arrayList.get(i).getPostcode();
                String additionalNotes = arrayList.get(i).getAdditionalNotes();
                String statuss = arrayList.get(i).getStatus();

                String eventDes = arrayList.get(i).getDateUk() + "," +
                        arrayList.get(i).getEventTimeStart() + "-" + arrayList.get(i).getEventTimeEnd() +
                        " @" + arrayList.get(i).getVenueName();

                calenderModel = new CalenderModel();
                calenderModel.setId(arrayList.get(i).getId());
                calenderModel.setName(arrayList.get(i).getEventName());
                calenderModel.setDescription(eventDes);
                calenderModel.setDate(arrayList.get(i).getDate());
                calenderModel.setLastName(arrayList.get(i).getLastName());
                calenderModel.setStaff(arrayList.get(i).getStaff());
                calenderModel.setStatus(arrayList.get(i).getStatus());


                calenderModel.setEmail(arrayList.get(i).getEmail());
                calenderModel.setCompanyName(arrayList.get(i).getCompanyName());
                calenderModel.setCustomerFirstName(arrayList.get(i).getCustomerFirstName());
                calenderModel.setCustomerLastName(arrayList.get(i).getCustomerLastName());
                calenderModel.setAddress(arrayList.get(i).getAddress());
                calenderModel.setVenueName(arrayList.get(i).getVenueName());
                calenderModel.setVenueAddress(arrayList.get(i).getVenueAddress());
                calenderModel.setPostcode(arrayList.get(i).getPostcode());
                calenderModel.setAdditionalNotes(arrayList.get(i).getAdditionalNotes());

                arrayListTemp.add(calenderModel);
            }
        }
        populateRecyclerView(arrayListTemp);
    }

    private void filterFirstByDate() {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = sdf.parse(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < bookingList.size(); i++) {


            String dateVal = bookingList.get(i).getEvent_date_iso();

            try {
                date2 = sdf.parse(dateVal);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (date2.compareTo(date1) > 0) {


                String eventDes = bookingList.get(i).getEvent_date_uk() + "," +

                        bookingList.get(i).getEvent_time_start_formatted() + "-" + bookingList.get(i).getEvent_time_end_formatted() +

                        " @" + bookingList.get(i).getVenue_name();

                String eventName = bookingList.get(i).getEvent_name();

                calenderModel = new CalenderModel();
                calenderModel.setId(bookingList.get(i).getBooking_id());
                calenderModel.setName(eventName);
                calenderModel.setDescription(eventDes);
                calenderModel.setDate(bookingList.get(i).getEvent_date_iso());
                calenderModel.setLastName(bookingList.get(i).getCustomer_last_name());
                calenderModel.setStaff(bookingList.get(i).getStaff_written());
                calenderModel.setStatus(bookingList.get(i).getStatus());
                calenderModel.setEventName(bookingList.get(i).getEvent_name());

                calenderModel.setDateUk(bookingList.get(i).getEvent_date_uk());
                calenderModel.setEventTimeStart(bookingList.get(i).getEvent_time_start_formatted());
                calenderModel.setEventTimeEnd(bookingList.get(i).getEvent_time_end_formatted());

                calenderModel.setEmail(bookingList.get(i).getEmail());
                calenderModel.setCompanyName(bookingList.get(i).getCustomer_company());
                calenderModel.setCustomerFirstName(bookingList.get(i).getCustomer_first_name());
                calenderModel.setCustomerLastName(bookingList.get(i).getCustomer_last_name());
                calenderModel.setAddress(bookingList.get(i).getCustomer_street_address());
                calenderModel.setVenueName(bookingList.get(i).getVenue_name());
                calenderModel.setVenueAddress(bookingList.get(i).getVenue_address());
                calenderModel.setPostcode(bookingList.get(i).getVenue_postcode());
                calenderModel.setAdditionalNotes(bookingList.get(i).getNotes_customer());
                arrayList.add(calenderModel);
                Log.d("DatedAdded", "filterFirstByDate: " + bookingList.get(i).getEvent_date_iso());
            }
        }

        if (!bookingID.equalsIgnoreCase("")) {
            getOtherSearches();
            return;
        }
        if (!lastName.equalsIgnoreCase("")) {
            getOtherSearches();
            return;
        }
        if (!staff.equalsIgnoreCase("")) {
            getOtherSearches();
            return;
        }
        if (!searchAll.equalsIgnoreCase("")) {
            getOtherSearches();
            return;
        }
        if (!status.equalsIgnoreCase("")) {
            getOtherSearches();
            return;
        }
        if (!firstName.equalsIgnoreCase("")) {
           getOtherSearches();
            return;
        }
        populateRecyclerView(arrayList);
    }
    private void populateRecyclerView(ArrayList<CalenderModel> arrayListTemp) {

        displaying_tv.setText("Displaying " + String.valueOf(arrayListTemp.size()) + " results");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mAdapter = new FilterAdapter(activity, arrayListTemp);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (arrayListTemp.size() > 0) {
                            String id = arrayListTemp.get(position).getId();
                            openFragmentDetail(id);
                        }
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
    private void openFragmentDetail(String id) {
        EventDetailFrag eventfrag = new EventDetailFrag();
        Bundle args = new Bundle();
        args.putString("id", id);
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.frame_home_Search, eventfrag);
        eventfrag.setArguments(args);
        FT.commit();
    }
}
