package com.jobesk.boothbook.fragments.calanderfrag;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.jobesk.boothbook.adapters.CalanderAdapter;
import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.broadcastReceivers.MyAlram;
import com.jobesk.boothbook.calendar.HomeCollection;
import com.jobesk.boothbook.calendar.HwAdapter;
import com.jobesk.boothbook.database.DBModelHome;
import com.jobesk.boothbook.database.DatabaseHelper;
import com.jobesk.boothbook.fragments.EventDetailFrag;
import com.jobesk.boothbook.fragments.SearchBookingFrag;
import com.jobesk.boothbook.fragments.calanderfrag.modelsbooking.BookingResponse;
import com.jobesk.boothbook.fragments.calanderfrag.modelsbooking.Bookings;
import com.jobesk.boothbook.models.CalenderModel;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.ApiClient;
import com.jobesk.boothbook.utils.ApiInterface;
import com.jobesk.boothbook.utils.GlobalClass;
import com.jobesk.boothbook.utils.RecyclerItemClickListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalanderFrag extends Fragment implements View.OnClickListener {

    private View rootView;
    private RecyclerView recyclerView;
    private CalenderModel calenderModel;
    private ArrayList<CalenderModel> arrayList = new ArrayList<>();
    private Activity activity;
    private CalanderAdapter mAdapter;
    private ImageView search_icon;
    private LinearLayout search_container;
    private boolean containerVisibility = false;
    private TextView apply_tv;
    private String todaysDate;
    private String TAG = "CalanderFrag";
    //    private ArrayList<Bookings> bookingsArrayList;
    private GregorianCalendar cal_month;
    private GregorianCalendar cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;
    private ImageView next, previous;
    private Bookings bookingmodel;
    private RelativeLayout content_container;
    private DatabaseHelper db;
    private List<DBModelHome> bookingList;
    private TextView more_filters_tv;
    private EditText date_et, booking_id_et, first_name_et, last_name_et, search_all_et;
    private boolean moreEffect = false;
    private TextView greater_than_et;
    private final Calendar myCalendar = Calendar.getInstance();
    private Spinner spinnerStaff, spinnerStatus;
    private String selectedYear, selectedMonth, selectedDay;
    private int statusValue = 1;
    private String staffValueSelected = "";
    private GridView gridview;
    public ArrayList<String> items = new ArrayList<>();
    //location
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private double currentLatitude, currentLongitude;
    private Boolean mRequestingLocationUpdates;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    private GestureDetectorCompat detector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.frag_calander, container, false);
        activity = (MainActivity) rootView.getContext();
        Locale.setDefault(Locale.US);

        init();
        Log.d("fragVisible", "Calander Frag");


        GlobalClass.showLoading(activity);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        todaysDate = sdf.format(new Date());
        detector = new GestureDetectorCompat(getContext(), new MyGestureListener());

        String dateHere = todaysDate;
        String[] items1 = dateHere.split("-");
        selectedYear = items1[0];
        selectedMonth = items1[1];
        selectedDay = items1[2];


        more_filters_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moreEffect) {
                    more_filters_tv.setText(getActivity().getResources().getString(R.string.less_filter));
                    spinnerStaff.setVisibility(View.VISIBLE);
                    spinnerStatus.setVisibility(View.VISIBLE);
                    last_name_et.setVisibility(View.VISIBLE);
                    first_name_et.setVisibility(View.VISIBLE);
                    moreEffect = false;
                } else {
                    more_filters_tv.setText(getActivity().getResources().getString(R.string.more_filters));
                    spinnerStaff.setVisibility(View.GONE);
                    last_name_et.setVisibility(View.GONE);
                    first_name_et.setVisibility(View.GONE);
                    spinnerStatus.setVisibility(View.GONE);
                    moreEffect = true;
                }
            }
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStaff.setAdapter(spinnerAdapter);
        spinnerAdapter.add("Unassigned");
        List<DBModelHome> allBookingsList = db.getAllBookings();
        for (int i = 0; i < allBookingsList.size(); i++) {
            String staff = allBookingsList.get(i).getStaff_written();
            Log.d("staffValue", "onCreateView: " + staff);
            if (!staff.isEmpty()) {
                spinnerAdapter.add(staff);
            }
        }

        spinnerAdapter.notifyDataSetChanged();
        spinnerStaff.setSelection(1);
        spinnerStaff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                staffValueSelected = spinnerStaff.getSelectedItem().toString();
                Log.d("spinnerStaffValue", "onItemSelected: " + spinnerStaff);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        date_et.setText(getActivity().getResources().getString(R.string.date) + " " + todaysDate);
        if (GlobalClass.isOnline(activity)) {
            getAllEvents();
        } else {
            int bookingCount = db.getBookingCount();
            if (bookingCount == 0) {
                Toast.makeText(activity, getActivity().getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            } else {
                bookingList = db.getAllBookings();
                PopulateValues();
            }
        }

//        Unconfirmed
//        Customer Details Confirmed
        search_all_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    booking_id_et.setInputType(InputType.TYPE_NULL);
                    last_name_et.setInputType(InputType.TYPE_NULL);
//                    staff_et.setInputType(InputType.TYPE_NULL);
                } else {
                    booking_id_et.setInputType(InputType.TYPE_CLASS_TEXT);
                    last_name_et.setInputType(InputType.TYPE_CLASS_TEXT);
//                    staff_et.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        first_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    booking_id_et.setInputType(InputType.TYPE_NULL);
                    last_name_et.setInputType(InputType.TYPE_NULL);
                    search_all_et.setInputType(InputType.TYPE_NULL);
                } else {
                    booking_id_et.setInputType(InputType.TYPE_CLASS_TEXT);
                    last_name_et.setInputType(InputType.TYPE_CLASS_TEXT);
                    search_all_et.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        staff_et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (count > 0) {
//                    booking_id_et.setInputType(InputType.TYPE_NULL);
//                    last_name_et.setInputType(InputType.TYPE_NULL);
//                    search_all_et.setInputType(InputType.TYPE_NULL);
//                } else {
//                    booking_id_et.setInputType(InputType.TYPE_CLASS_TEXT);
//                    last_name_et.setInputType(InputType.TYPE_CLASS_TEXT);
//                    search_all_et.setInputType(InputType.TYPE_CLASS_TEXT);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        last_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    booking_id_et.setInputType(InputType.TYPE_NULL);
//                    staff_et.setInputType(InputType.TYPE_NULL);
                    search_all_et.setInputType(InputType.TYPE_NULL);
                } else {
                    booking_id_et.setInputType(InputType.TYPE_CLASS_TEXT);
//                    staff_et.setInputType(InputType.TYPE_CLASS_TEXT);
                    search_all_et.setInputType(InputType.TYPE_CLASS_TEXT);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        booking_id_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    last_name_et.setInputType(InputType.TYPE_NULL);
//                    staff_et.setInputType(InputType.TYPE_NULL);
                    search_all_et.setInputType(InputType.TYPE_NULL);
                } else {
                    last_name_et.setInputType(InputType.TYPE_CLASS_TEXT);
//                    staff_et.setInputType(InputType.TYPE_CLASS_TEXT);
                    search_all_et.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        populateRecyclerView();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                selectedDay = String.valueOf(dayOfMonth);
                selectedMonth = String.valueOf(monthOfYear + 1);
                selectedYear = String.valueOf(year);

                String mydateSelected = selectedYear + "-" + selectedMonth + "-" + selectedDay;
                Log.d(TAG, "onDateSet: " + mydateSelected);
                date_et.setText("Date " + mydateSelected);
            }
        };
        date_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        spinnerStatus.setSelection(1);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusValue = i;
                Log.d("spinnerValue", "onItemSelected: " + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        initializeLocation();
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
//        getDatesArray();

        return rootView;
    }

    private void init() {


        db = new DatabaseHelper(activity);

        gridview = rootView.findViewById(R.id.gv_calendar);


        content_container = rootView.findViewById(R.id.content_container);
        greater_than_et = rootView.findViewById(R.id.greater_than_tv);

        booking_id_et = rootView.findViewById(R.id.booking_id_et);
        last_name_et = rootView.findViewById(R.id.last_name_et);
        first_name_et = rootView.findViewById(R.id.first_name_et);

        search_all_et = rootView.findViewById(R.id.search_all_et);
        next = rootView.findViewById(R.id.Ib_next);
        previous = rootView.findViewById(R.id.ib_prev);

        search_icon = rootView.findViewById(R.id.search_icon);
        search_icon.setOnClickListener(this);
        search_container = rootView.findViewById(R.id.search_container);
        booking_id_et = rootView.findViewById(R.id.booking_id_et);
        last_name_et = rootView.findViewById(R.id.last_name_et);

        search_all_et = rootView.findViewById(R.id.search_all_et);

        greater_than_et = rootView.findViewById(R.id.greater_than_tv);

        date_et = rootView.findViewById(R.id.date_et);
        date_et.setInputType(InputType.TYPE_NULL);

        apply_tv = rootView.findViewById(R.id.apply_tv);
        apply_tv.setOnClickListener(this);
        tv_month = rootView.findViewById(R.id.tv_month);


        spinnerStaff = (Spinner) rootView.findViewById(R.id.spinnerStaff);

        spinnerStatus = (Spinner) rootView.findViewById(R.id.spinner);
        more_filters_tv = rootView.findViewById(R.id.more_filters_tv);
        recyclerView = rootView.findViewById(R.id.recycler_view);

    }

    private void getDatesArray() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i <= 12; i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i);
            Date date = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateOutput = format.format(date);

            calendar.set(Calendar.DATE, 1);
            Date firstDateOfPreviousMonth = calendar.getTime();
            String startDateFormatted = format.format(firstDateOfPreviousMonth);

            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date lastDateOfPreviousMonth = calendar.getTime();
            String endDateFormatted = format.format(lastDateOfPreviousMonth);


            Log.d("dateShowIncrement", dateOutput);

        }

        for (int i = 12; i >= 0; i--) {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -i);
            Date date = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateOutput = format.format(date);
            Log.d("dateShowDecrement", dateOutput + "");

        }

    }

    private void initializeLocation() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        mSettingsClient = LocationServices.getSettingsClient(activity);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                updateLocationUI();
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            currentLatitude = mCurrentLocation.getLatitude();
            currentLongitude = mCurrentLocation.getLongitude();

            Log.d(TAG, "updateLocationlatLogn: lat=" + currentLatitude + "     Longitude:" + currentLongitude);

        }

    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

//                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }


    private void openFragmentDetail(String id) {

        EventDetailFrag eventfrag = new EventDetailFrag();
        Bundle args = new Bundle();
        args.putString("id", id);
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.containerCalander, eventfrag);
        eventfrag.setArguments(args);
        FT.commit();

    }

    private void openFragmentSearchFilter(String year, String month, String day, String bookingID, String lastName, String staff, String searchAll, String status, String fname) {

        Bundle args = new Bundle();
        args.putString("year", year);
        args.putString("month", month);
        args.putString("day", day);
        args.putString("bookingID", bookingID);
        args.putString("lastName", lastName);
        args.putString("staff", staff);
        args.putString("searchAll", searchAll);
        args.putString("status", status);
        args.putString("firstName", fname);

        SearchBookingFrag eventfrag = new SearchBookingFrag();
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.containerCalander, eventfrag);
        eventfrag.setArguments(args);
        FT.commit();

    }

    private void initializacalander() {

        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(activity, cal_month);
        if (cal_month.get(GregorianCalendar.MONTH) == 0)
            tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        else
            tv_month.setText(android.text.format.DateFormat.format("MMMM", cal_month));

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 1 && cal_month.get(GregorianCalendar.YEAR) == 2000) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(activity, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                } else {
                    setPreviousMonth();
                    refreshCalendar();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 10 && cal_month.get(GregorianCalendar.YEAR) == 3000) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(activity, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                } else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });


        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
//                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, activity);
                setListAccordingToDate(selectedGridDate);
            }

        });
        gridview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });

        setListAccordingToDate(todaysDate);
        content_container.setVisibility(View.VISIBLE);
        GlobalClass.dismissLoading();

    }

    private void setNextMonth() {

        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    private void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    private void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        if (cal_month.get(GregorianCalendar.MONTH) == 0)
            tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        else
            tv_month.setText(android.text.format.DateFormat.format("MMMM", cal_month));
        // tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }

    private void setListAccordingToDate(String selectedGridDate) {
        if (arrayList.size() > 0) {
            arrayList.clear();
        }
        for (int i = 0; i < bookingList.size(); i++) {
            String eventDate = bookingList.get(i).getEvent_date_iso();
            if (eventDate.equalsIgnoreCase(selectedGridDate)) {
                DBModelHome bookingmodel = bookingList.get(i);
                Log.d("foundEvent", "onItemClick: found:" + bookingList.get(i));
                String eventDes = bookingmodel.getEvent_date_uk() + "," +
                        bookingmodel.getEvent_time_start_formatted() + "-" + bookingmodel.getEvent_time_start_formatted() +
                        " @" + bookingmodel.getVenue_name();
                calenderModel = new CalenderModel();
                calenderModel.setId(bookingList.get(i).getBooking_id());
            String val=    bookingList.get(i).getEvent_name();
                calenderModel.setName(val);
                calenderModel.setDescription(eventDes);
                arrayList.add(calenderModel);
            }
        }
        if (arrayList.size() > 0) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void populateRecyclerView() {
        mAdapter = new CalanderAdapter(activity, arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (arrayList.size() > 0) {
                            String id = arrayList.get(position).getId();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_icon:
                if (!containerVisibility) {
                    containerVisibility = true;
                    search_container.setVisibility(View.VISIBLE);
                } else {
                    containerVisibility = false;
                    search_container.setVisibility(View.GONE);
                }
                break;
            case R.id.apply_tv:

                String bookingID = booking_id_et.getText().toString().trim();
                String lastName = last_name_et.getText().toString().trim();
                String staff = "";
                String searchAll = search_all_et.getText().toString().trim();
                String firstName = first_name_et.getText().toString().trim();
                String status = String.valueOf(statusValue);


                if (todaysDate.equalsIgnoreCase("") &&
                        bookingID.equalsIgnoreCase("") &&
                        lastName.equalsIgnoreCase("") &&
                        staff.equalsIgnoreCase("") &&
                        searchAll.equalsIgnoreCase("") &&
                        status.equalsIgnoreCase("")
                ) {

                    Toast.makeText(activity, Objects.requireNonNull(getActivity()).getResources().getString(R.string.enter_values_to_search), Toast.LENGTH_SHORT).show();

                } else {

                    //Date entered
                    if (!todaysDate.equalsIgnoreCase("") &&
                            bookingID.equalsIgnoreCase("") &&
                            lastName.equalsIgnoreCase("") &&
                            staff.equalsIgnoreCase("") &&
                            searchAll.equalsIgnoreCase("") &&
                            status.equalsIgnoreCase("") &&
                            firstName.equalsIgnoreCase("")


                    ) {
                        Toast.makeText(activity, "Date entered", Toast.LENGTH_SHORT).show();

                    }
                    //Booking ID Entered
                    if (todaysDate.equalsIgnoreCase("") &&
                            !bookingID.equalsIgnoreCase("") &&
                            lastName.equalsIgnoreCase("") &&
                            staff.equalsIgnoreCase("") &&
                            searchAll.equalsIgnoreCase("") &&
                            status.equalsIgnoreCase("") &&
                            firstName.equalsIgnoreCase("")

                    ) {
                        Toast.makeText(activity, "ID entered", Toast.LENGTH_SHORT).show();

                    }


                    //last name Entered
                    if (todaysDate.equalsIgnoreCase("") &&
                            bookingID.equalsIgnoreCase("") &&
                            !lastName.equalsIgnoreCase("") &&
                            staff.equalsIgnoreCase("") &&
                            searchAll.equalsIgnoreCase("") &&
                            status.equalsIgnoreCase("") &&
                            firstName.equalsIgnoreCase("")

                    ) {
                        Toast.makeText(activity, "last name entered", Toast.LENGTH_SHORT).show();
                    }

                    //search all  Entered
                    if (todaysDate.equalsIgnoreCase("") &&
                            bookingID.equalsIgnoreCase("") &&
                            lastName.equalsIgnoreCase("") &&
                            staff.equalsIgnoreCase("") &&
                            !searchAll.equalsIgnoreCase("") &&
                            status.equalsIgnoreCase("") &&
                            firstName.equalsIgnoreCase("")

                    ) {
                        Toast.makeText(activity, "search all entered", Toast.LENGTH_SHORT).show();

                    }

                    //Status   Entered
                    if (todaysDate.equalsIgnoreCase("") &&
                            bookingID.equalsIgnoreCase("") &&
                            lastName.equalsIgnoreCase("") &&
                            staff.equalsIgnoreCase("") &&
                            searchAll.equalsIgnoreCase("") &&
                            !status.equalsIgnoreCase("") &&
                            firstName.equalsIgnoreCase("")

                    ) {
                        Toast.makeText(activity, "status entered", Toast.LENGTH_SHORT).show();

                    }

                    //FirstName   Entered
                    if (firstName.equalsIgnoreCase("") &&
                            bookingID.equalsIgnoreCase("") &&
                            lastName.equalsIgnoreCase("") &&
                            staff.equalsIgnoreCase("") &&
                            searchAll.equalsIgnoreCase("") &&
                            status.equalsIgnoreCase("") &&
                            !firstName.equalsIgnoreCase("")

                    ) {
                        Toast.makeText(activity, "status entered", Toast.LENGTH_SHORT).show();

                    }


                    openFragmentSearchFilter(selectedYear, selectedMonth, selectedDay, bookingID, lastName, "Unassigned", searchAll, status, firstName);
                }


                break;


            default:
//                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

    }

    private void getAllEvents() {

        String key = GlobalClass.getPref("user_client_key", Objects.requireNonNull(getActivity()));
        String secret = GlobalClass.getPref("user_client_secret", getActivity());
        String base_url = GlobalClass.getPref("user_base_url", getActivity());

        ApiInterface apiInterface = ApiClient.getApiClientWithoutUrl(base_url).create(ApiInterface.class);
//        Call<BookingResponse> eventCall = apiInterface.MyBookings(key, secret, "full", "2020-03-01", "2020-03-30");
        Call<BookingResponse> eventCall = apiInterface.MyBookings(key, secret, "full");


        String urlHit = eventCall.request().url().toString();
        Log.d("urlHit", urlHit);

        eventCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {


                Log.d("ResponseRetro", "response 33: " + new Gson().toJson(response.body()));

                if (response.isSuccessful()) {
                    BookingResponse bookingResponse = response.body();

                    assert bookingResponse != null;
                    Map<String, Bookings> bookingList = bookingResponse.getBookings();
                    if (bookingList != null) {

                    } else {
                        Toast.makeText(activity, "No Booking Available", Toast.LENGTH_SHORT).show();
                        GlobalClass.dismissLoading();
                        return;
                    }

                    ArrayList<Bookings> bookingsArrayList = new ArrayList<Bookings>(bookingList.values());

////                    GlobalClass.bookingList = bookingsArrayList;
                    int bookingCount = db.getBookingCount();
                    if (bookingCount == 0) {
                        InsertingInDataBase(bookingsArrayList);
                    } else {
                        UpdatingInDataBase(bookingsArrayList);
                    }

                } else {
                    ResponseBody error = response.errorBody();
                    GlobalClass.dismissLoading();
                    Toast.makeText(activity, error + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, error + "");
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(activity, t + "", Toast.LENGTH_SHORT).show();
                GlobalClass.dismissLoading();
                Log.d(TAG, t + "");
            }
        });
    }

    private void InsertingInDataBase(ArrayList<Bookings> bookingsArrayList) {

        for (int i = 0; i < bookingsArrayList.size(); i++) {

            bookingmodel = bookingsArrayList.get(i);
            String bookingID = String.valueOf(bookingmodel.getId());
            String created = bookingmodel.getCreated();
            String createdISO = bookingmodel.getCreated_iso();
            String changed = bookingmodel.getChanged();
            String changedISO = bookingmodel.getChanged_iso();
            String status = bookingmodel.getStatus();
            String email = bookingmodel.getEmail();
            String phone = bookingmodel.getPhone();
            String packages = bookingmodel.getPackages();
            String signatureRequired = bookingmodel.getSignature_required();
            String signatureImage = "";

            String customerFirstName = bookingmodel.getCustomer().getFirst_name();
            String customerLastName = bookingmodel.getCustomer().getLast_name();
            String customerCompany = bookingmodel.getCustomer().getCompany();
            String customerStreetAddress = bookingmodel.getCustomer().getCustomer_street_address();
            String customerCity = bookingmodel.getCustomer().getCustomer_city();
            String customerCountry = bookingmodel.getCustomer().getCustomer_country();
            String customerPostCode = bookingmodel.getCustomer().getCustomer_postcode();

            String staff = "";

            if (String.valueOf(bookingmodel.getStaff()).equalsIgnoreCase("null")) {
                staff = "";
            } else {
                if (bookingmodel.getStaff() != null) {
                    staff = bookingmodel.getStaff().getWritten();
                }
            }

            String eventName = bookingmodel.getEventName();
            String eventTimeStart = bookingmodel.getEvent().getEvent_time_start_formatted();
            String eventTimeEND = bookingmodel.getEvent().getEvent_time_end_formatted();
            String eventDateUK = bookingmodel.getEvent().getEvent_date_uk();
            String eventDateUS = bookingmodel.getEvent().getEvent_date_us();
            String eventDateISO = bookingmodel.getEvent().getEvent_date_iso();


            String venueName = bookingmodel.getVenue().getVenue_name();
            String venueAddress = bookingmodel.getVenue().getVenue_address();
            String venueCode = bookingmodel.getVenue().getVenue_postcode();
            String venueCountry = bookingmodel.getVenue().getBusiness_country();

            String prices = bookingmodel.getPrice().getTotal();

            String notesAdmin = bookingmodel.getNotes().getNotes_admin();
            String notesCustomer = bookingmodel.getNotes().getNotes_customer();


            items.add(created);

            DBModelHome model = new DBModelHome();
            model.setBooking_id(bookingID);
            model.setCreated(created);
            model.setCreated_iso(createdISO);
            model.setChanged(changed);
            model.setChanged_iso(changedISO);
            model.setStatus(status);
            model.setEmail(email);
            model.setPhone(phone);
            model.setPackages(packages);
            model.setSignature_required(signatureRequired);
            model.setSignature_image(signatureImage);
            model.setCustomer_first_name(customerFirstName);
            model.setCustomer_last_name(customerLastName);
            model.setCustomer_company(customerCompany);
            model.setCustomer_street_address(customerStreetAddress);
            model.setCustomer_city(customerCity);
            model.setCustomer_country(customerCountry);
            model.setCustomer_postcode(customerPostCode);
            model.setStaff_written(staff);
            model.setEvent_name(eventName);
            model.setEvent_time_start_formatted(eventTimeStart);
            model.setEvent_time_end_formatted(eventTimeEND);
            model.setEvent_date_uk(eventDateUK);
            model.setEvent_date_us(eventDateUS);
            model.setEvent_date_iso(eventDateISO);
            model.setVenue_name(venueName);
            model.setVenue_address(venueAddress);
            model.setVenue_postcode(venueCode);
            model.setVenue_business_country(venueCountry);
            model.setPrice_total(prices);
            model.setNotes_customer(notesAdmin);
            model.setNotes_admin(notesCustomer);
            db.insertBooking(model);

        }
        bookingList = db.getAllBookings();
        PopulateValues();
    }

    private void UpdatingInDataBase(ArrayList<Bookings> bookingsArrayList) {

        Log.d("eventCount: ", bookingsArrayList.size() + "");
        for (int i = 0; i < bookingsArrayList.size(); i++) {

            bookingmodel = bookingsArrayList.get(i);
            String bookingID = String.valueOf(bookingmodel.getId());
            String created = bookingmodel.getCreated();
            String createdISO = bookingmodel.getCreated_iso();
            String changed = bookingmodel.getChanged();
            String changedISO = bookingmodel.getChanged_iso();
            String status = bookingmodel.getStatus();
            String email = bookingmodel.getEmail();
            String phone = bookingmodel.getPhone();
            String packages = bookingmodel.getPackages();
            String signatureRequired = bookingmodel.getSignature_required();
            String signatureImage = "";

            String customerFirstName = bookingmodel.getCustomer().getFirst_name();
            String customerLastName = bookingmodel.getCustomer().getLast_name();
            String customerCompany = bookingmodel.getCustomer().getCompany();
            String customerStreetAddress = bookingmodel.getCustomer().getCustomer_street_address();
            String customerCity = bookingmodel.getCustomer().getCustomer_city();
            String customerCountry = bookingmodel.getCustomer().getCustomer_country();
            String customerPostCode = bookingmodel.getCustomer().getCustomer_postcode();
            String staff = "";

            if (String.valueOf(bookingmodel.getStaff()).equalsIgnoreCase("null")) {
                staff = "";
            } else {
                if (bookingmodel.getStaff() != null) {
                    staff = bookingmodel.getStaff().getWritten();
                }
            }

            String eventName = bookingmodel.getEventName();
            String eventTimeStart = bookingmodel.getEvent().getEvent_time_start_formatted();
            String eventTimeEND = bookingmodel.getEvent().getEvent_time_end_formatted();
            String eventDateUK = bookingmodel.getEvent().getEvent_date_uk();
            String eventDateUS = bookingmodel.getEvent().getEvent_date_us();
            String eventDateISO = bookingmodel.getEvent().getEvent_date_iso();


            String venueName = bookingmodel.getVenue().getVenue_name();
            String venueAddress = bookingmodel.getVenue().getVenue_address();
            String venueCode = bookingmodel.getVenue().getVenue_postcode();
            String venueCountry = bookingmodel.getVenue().getBusiness_country();

            String prices = bookingmodel.getPrice().getTotal();

            String notesAdmin = bookingmodel.getNotes().getNotes_admin();
            String notesCustomer = bookingmodel.getNotes().getNotes_customer();


            DBModelHome model = new DBModelHome();
            model.setBooking_id(bookingID);
            model.setCreated(created);
            model.setCreated_iso(createdISO);
            model.setChanged(changed);
            model.setChanged_iso(changedISO);
            model.setStatus(status);
            model.setEmail(email);
            model.setPhone(phone);
            model.setPackages(packages);
            model.setSignature_required(signatureRequired);
            model.setSignature_image(signatureImage);
            model.setCustomer_first_name(customerFirstName);
            model.setCustomer_last_name(customerLastName);
            model.setCustomer_company(customerCompany);
            model.setCustomer_street_address(customerStreetAddress);
            model.setCustomer_city(customerCity);
            model.setCustomer_country(customerCountry);
            model.setCustomer_postcode(customerPostCode);
            model.setStaff_written(staff);
            model.setEvent_name(eventName);
            model.setEvent_time_start_formatted(eventTimeStart);
            model.setEvent_time_end_formatted(eventTimeEND);
            model.setEvent_date_uk(eventDateUK);
            model.setEvent_date_us(eventDateUS);
            model.setEvent_date_iso(eventDateISO);
            model.setVenue_name(venueName);
            model.setVenue_address(venueAddress);
            model.setVenue_postcode(venueCode);
            model.setVenue_business_country(venueCountry);
            model.setPrice_total(prices);
            model.setNotes_customer(notesAdmin);
            model.setNotes_admin(notesCustomer);
            db.updateBooking(model);
        }
//        int no = db.getBookingCount();
        bookingList = db.getAllBookings();
        PopulateValues();
    }



    private void PopulateValues() {

        HomeCollection.date_collection_arr = new ArrayList<HomeCollection>();

        for (int i = 0; i < bookingList.size(); i++) {


            String dateEvent = bookingList.get(i).getEvent_date_iso();

            String[] separated = dateEvent.split("-");
            String year = separated[0];  // month (Aug 26 2019)
            String day = separated[1];  //Day
            String month = separated[2]; //year


            Log.d("Dates", "Dates: " + dateEvent);

            HomeCollection.date_collection_arr.add(
                    new HomeCollection(dateEvent,
                            "Diwali",
                            "Holiday",
                            "this is holiday",
                            "1"));
        }


        initializacalander();
        computeTodayEvents();

    }


    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }


    private void computeTodayEvents() {


        for (int i = 0; i < bookingList.size(); i++) {

            String dateEvent = bookingList.get(i).getEvent_date_iso();

            if (dateEvent.equals(todaysDate)) {

//        Log.d("DatesMatched", "Dates: " + dateEvent);


//        String dateEvent = "2020-3-3";
//        String[] separated = dateEvent.split("-");
//        String year = separated[0];  // month
//        String day = separated[1];  //Day
//        String month = separated[2]; //year

                String eventStartTime = bookingList.get(i).getEvent_time_start_formatted();
                String eventEndTime = bookingList.get(i).getEvent_time_start_formatted();
                String bookingID = bookingList.get(i).getBooking_id();

//        String eventStartTime = "5:49pm";
//        String eventEndTime = "5:51pm";

                eventStartTime = GlobalClass.changeDateFormat("hh:mma", "HH:mm", eventStartTime);
                eventEndTime = GlobalClass.changeDateFormat("hh:mma", "HH:mm", eventEndTime);

                // For start time
                String[] separatedTimeStart = eventStartTime.split(":");
                String hoursStart = separatedTimeStart[0];  // Hours
                String minsStart = separatedTimeStart[1];  //mins
                String minsStartFormatted = minsStart.replaceAll("[^\\d.]", "");

                // For end time
                String[] separatedTimeEnd = eventEndTime.split(":");
                String hoursEnd = separatedTimeEnd[0];  // Hours
                String minsEnd = separatedTimeEnd[1];  //mins
                String minsEndFormatted = minsEnd.replaceAll("[^\\d.]", "");


                Calendar calendar = Calendar.getInstance();


                //trigger at start time
                startTimeAlarmCompute(calendar, Integer.valueOf(hoursStart), Integer.valueOf(minsStartFormatted), bookingID);
                EndTimeAlarmCompute(calendar, Integer.valueOf(hoursEnd), Integer.valueOf(minsEndFormatted), bookingID);
//        halfTimeAlarmCompute(calendar, Integer.valueOf(hoursStart), Integer.valueOf(minsStartFormatted));

                //trigger at end time
//        setAlarm(calendar.getTimeInMillis(), 2);

                //trigger at half hour
//        calendar.add(Calendar.HOUR, 1);
//        setAlarm(calendar.getTimeInMillis(), 3);

            }


        }


    }

    private void startTimeAlarmCompute(Calendar calendar, int hours, int mins, String bookingID) {


        if (android.os.Build.VERSION.SDK_INT >= 23) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    hours, mins, 0);
        } else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    hours, mins, 0);
        }


        int requestCode = hours + mins;
        //trigger at start time
        setAlarm(calendar.getTimeInMillis(), requestCode, "start", bookingID);

    }

    private void EndTimeAlarmCompute(Calendar calendar, int hours, int mins, String bookingID) {


        if (android.os.Build.VERSION.SDK_INT >= 23) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    hours, mins, 0);
        } else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    hours, mins, 0);
        }

        int requestCode = hours + mins;
        //trigger at start time
        setAlarm(calendar.getTimeInMillis(), requestCode, "end", bookingID);

    }

    private void halfTimeAlarmCompute(Calendar calendar, int hours, int mins, String bookingID) {


        if (android.os.Build.VERSION.SDK_INT >= 23) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    hours, mins, 0);
        } else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    hours, mins, 0);
        }

        int requestCode = hours + mins;
        //trigger at start time
        setAlarm(calendar.getTimeInMillis(), requestCode, "half", bookingID);

    }

    private void setAlarm(long time, int requrestCodeAlarm, String message, String bookingID) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(activity, MyAlram.class);
        i.putExtra("status", message);
        i.putExtra("bookingID", bookingID);
        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(activity, requrestCodeAlarm, i, 0);
        //setting the repeating alarm that will be fired every day
        am.set(AlarmManager.RTC_WAKEUP, time, pi);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            float diffY = event2.getY() - event1.getY();
            float diffX = event2.getX() - event1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
            }
            return true;
        }
    }

    private void onSwipeLeft() {
        if (cal_month.get(GregorianCalendar.MONTH) == 10 && cal_month.get(GregorianCalendar.YEAR) == 3000) {
            //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
            Toast.makeText(activity, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
        } else {
            setNextMonth();
            refreshCalendar();
        }
    }

    private void onSwipeRight() {
        if (cal_month.get(GregorianCalendar.MONTH) == 1 && cal_month.get(GregorianCalendar.YEAR) == 2000) {
            //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
            Toast.makeText(activity, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
        } else {
            setPreviousMonth();
            refreshCalendar();
        }
    }

    private void onSwipeTop() {

    }

    private void onSwipeBottom() {

    }
}