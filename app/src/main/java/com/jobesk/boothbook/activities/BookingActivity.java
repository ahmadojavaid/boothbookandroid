package com.jobesk.boothbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.jobesk.boothbook.R;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
//
//        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

//        Calendar min = Calendar.getInstance();
//        min.add(Calendar.MONTH, -2);
//
//        Calendar max = Calendar.getInstance();
//        max.add(Calendar.MONTH, 2);
//
//        calendarView.setMinimumDate(min);
//        calendarView.setMaximumDate(max);


        // setting disabled days

//        List<Calendar> calendars = new ArrayList<>();
//
//        Calendar firstDisabled = DateUtils.getCalendar();
//        firstDisabled.add(Calendar.DATE, 1);
//
//        Calendar secondDisabled = DateUtils.getCalendar();
//        secondDisabled.add(Calendar.DATE, 2);
//
//        Calendar thirdDisabled = DateUtils.getCalendar();
//        thirdDisabled.add(Calendar.DATE, 3);
//
//        calendars.add(firstDisabled);
//        calendars.add(secondDisabled);
//        calendars.add(thirdDisabled);
//
//
//        calendarView.setDisabledDays(calendars);
//
    }

    private void populateRecyclerView() {

//        RecyclerView recyclerView;
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//
//        mAdapter = new CalanderAdapter(activity, arrayList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);

    }

}
