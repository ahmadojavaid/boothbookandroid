package com.jobesk.boothbook.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jobesk.boothbook.database.DBLeads;
import com.jobesk.boothbook.database.DatabaseHelper;
import com.jobesk.boothbook.models.EventTypeModel;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.GlobalClass;
import com.jobesk.boothbook.utils.WebReq;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class AddBusinessActivity extends AppCompatActivity {

    private TextView book_now_tv;
    private AlertDialog alertDialog;
    private EditText firstName_et, last_name_et, company_name_et, email_et, telephone_et, mobile_et, address_et, city_et, postcode_et;
    private EditText event_type_et, event_name_et, additional_notes_et;
    private TextView event_start_time_selection, event_end_time_selection;
    private TextView firstName_tv, last_name_tv, company_name_tv, email_tv, telephone_tv, mobile_tv, address_tv, city_tv, postcode_tv;
    private TextView event_date_tv, event_start_time_tv, event_end_time_tv, event_type_tv, event_name_tv, additional_notes_tv;
    private TextView submit_tv, clear_tv;
    private LinearLayout rootView;
    private Typeface typeface;
    private TextView contact_permission_tv;
    private ImageView businessLogoImg;
    private TextView businessNametv;
    private DatabaseHelper databaseObject;
    private CheckBox checkbox;
    private String checkboxString = "";
    private int checkboxState = 0;
    private ImageView backimg;
    private TextView event_date_selection;
    private String amPm = "";
    private String additionalNotes, eventName, eventEndTime, eventStartTime, eventType, eventDate, postCode,
            city, address, mobile, telephone, email, company, lastName, firstName;
    private String venuName, venuAddress, venuPostCode;
    private String TAG = "AddBusinessActivity";
    private String eventyTypeIDFinal = "";
    private EditText venue_name_et, venue_address_et, venue_postcode_et;
    private TextView venue_name_tv, venue_address_tv, venue_postcode_tv;
    private String bookingLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        databaseObject = new DatabaseHelper(AddBusinessActivity.this);
        rootView = findViewById(R.id.rootView);

        backimg = findViewById(R.id.backimg);
        backimg.setOnClickListener(view -> finish());

        firstName_et = findViewById(R.id.firstName_et);
        last_name_et = findViewById(R.id.last_name_et);
        company_name_et = findViewById(R.id.company_name_et);
        email_et = findViewById(R.id.email_et);
        telephone_et = findViewById(R.id.telephone_et);
        mobile_et = findViewById(R.id.mobile_et);
        address_et = findViewById(R.id.address_et);
        city_et = findViewById(R.id.city_et);
        postcode_et = findViewById(R.id.postcode_et);
        event_date_selection = findViewById(R.id.event_date_selection);
        event_start_time_selection = findViewById(R.id.event_start_time_selection);
        event_end_time_selection = findViewById(R.id.event_end_time_selection);
        event_type_et = findViewById(R.id.event_type_et);
        event_name_et = findViewById(R.id.event_name_et);
//        event_budget_et = findViewById(R.id.event_budget_et);
        additional_notes_et = findViewById(R.id.additional_notes_et);
        venue_name_et = findViewById(R.id.venue_name_et);
        venue_address_et = findViewById(R.id.venue_address_et);
        venue_postcode_et = findViewById(R.id.venue_postcode_et);

        firstName_tv = findViewById(R.id.firstName_tv);
        last_name_tv = findViewById(R.id.last_name_tv);
        company_name_tv = findViewById(R.id.company_name_tv);
        email_tv = findViewById(R.id.email_tv);
        telephone_tv = findViewById(R.id.telephone_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        address_tv = findViewById(R.id.address_tv);
        city_tv = findViewById(R.id.city_tv);
        postcode_tv = findViewById(R.id.postcode_tv);
        event_date_tv = findViewById(R.id.event_date_tv);
        event_start_time_tv = findViewById(R.id.event_start_time_tv);
        event_end_time_tv = findViewById(R.id.event_end_time_tv);
        event_type_tv = findViewById(R.id.event_type_tv);
        event_name_tv = findViewById(R.id.event_name_tv);
//        event_budget_tv = findViewById(R.id.event_budget_tv);
        additional_notes_tv = findViewById(R.id.additional_notes_tv);
        contact_permission_tv = findViewById(R.id.contact_permission_tv);
        venue_name_tv = findViewById(R.id.venue_name_tv);
        venue_address_tv = findViewById(R.id.venue_address_tv);
        venue_postcode_tv = findViewById(R.id.venue_postcode_tv);




        submit_tv = findViewById(R.id.submit_tv);
        clear_tv = findViewById(R.id.clear_tv);

        businessLogoImg = findViewById(R.id.businessLogoImg);
        businessNametv = findViewById(R.id.businessNametv);

        checkbox = findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                checkboxState = 1;
            } else {
                checkboxState = 0;
            }
        });

        String encodedImageString = GlobalClass.getPrefDefault("encodedImageString", "", getApplicationContext());
        if (encodedImageString.equalsIgnoreCase("")) {
            businessLogoImg.setVisibility(View.GONE);
            businessNametv.setVisibility(View.VISIBLE);
        } else {
            businessLogoImg.setVisibility(View.VISIBLE);
            businessNametv.setVisibility(View.GONE);

            byte[] decodedString = Base64.decode(encodedImageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            businessLogoImg.setImageBitmap(decodedByte);
        }


        String additional_notes_display = GlobalClass.getPrefDefault("additional_notes_display", "true", getApplicationContext());
        if (additional_notes_display.equalsIgnoreCase("true")) {
            additional_notes_tv.setVisibility(View.VISIBLE);
            additional_notes_et.setVisibility(View.VISIBLE);
            additionalNotesSetval();
        } else {
            additional_notes_tv.setVisibility(View.GONE);
            additional_notes_et.setVisibility(View.GONE);
        }

        String permission_display = GlobalClass.getPrefDefault("permission_display", "true", getApplicationContext());
        if (permission_display.equalsIgnoreCase("true")) {

            contact_permission_tv.setVisibility(View.VISIBLE);
            checkbox.setVisibility(View.VISIBLE);
            contactPermissionSet();

        } else {

            contact_permission_tv.setVisibility(View.GONE);
            checkbox.setVisibility(View.GONE);

        }


        String firstname_display = GlobalClass.getPrefDefault("firstname_display", "true", getApplicationContext());
        if (firstname_display.equalsIgnoreCase("true")) {
            firstName_tv.setVisibility(View.VISIBLE);
            firstName_et.setVisibility(View.VISIBLE);

            firstNameValueSet();

        } else {
            firstName_tv.setVisibility(View.GONE);
            firstName_et.setVisibility(View.GONE);
        }
        String last_name_display = GlobalClass.getPrefDefault("last_name_display", "true", getApplicationContext());
        if (last_name_display.equalsIgnoreCase("true")) {

            last_name_tv.setVisibility(View.VISIBLE);
            last_name_et.setVisibility(View.VISIBLE);

            lastNameValueSet();


        } else {
            last_name_tv.setVisibility(View.GONE);
            last_name_et.setVisibility(View.GONE);
        }
        String company_display = GlobalClass.getPrefDefault("company_display", "true", getApplicationContext());
        if (company_display.equalsIgnoreCase("true")) {

            company_name_tv.setVisibility(View.VISIBLE);
            company_name_et.setVisibility(View.VISIBLE);

            conpanySet();

        } else {
            company_name_tv.setVisibility(View.GONE);
            company_name_et.setVisibility(View.GONE);
        }
        String email_display = GlobalClass.getPrefDefault("email_display", "true", getApplicationContext());
        if (email_display.equalsIgnoreCase("true")) {

            email_tv.setVisibility(View.VISIBLE);
            email_et.setVisibility(View.VISIBLE);

            emailSet();

        } else {
            email_tv.setVisibility(View.GONE);
            email_et.setVisibility(View.GONE);

        }
        String telephone_display = GlobalClass.getPrefDefault("telephone_display", "true", getApplicationContext());
        if (telephone_display.equalsIgnoreCase("true")) {
            telephone_tv.setVisibility(View.VISIBLE);
            telephone_et.setVisibility(View.VISIBLE);

            telephoneSet();

        } else {
            telephone_tv.setVisibility(View.GONE);
            telephone_et.setVisibility(View.GONE);
        }
        String mobile_display = GlobalClass.getPrefDefault("mobile_display", "true", getApplicationContext());
        if (mobile_display.equalsIgnoreCase("true")) {

            mobile_tv.setVisibility(View.VISIBLE);
            mobile_et.setVisibility(View.VISIBLE);

            mobileNUmberSetter();
        } else {
            mobile_tv.setVisibility(View.GONE);
            mobile_et.setVisibility(View.GONE);
        }
        String address_display = GlobalClass.getPrefDefault("address_display", "true", getApplicationContext());
        if (address_display.equalsIgnoreCase("true")) {

            address_tv.setVisibility(View.VISIBLE);
            address_et.setVisibility(View.VISIBLE);
            addressSet();
        } else {
            address_tv.setVisibility(View.GONE);
            address_et.setVisibility(View.GONE);
        }
        String city_display = GlobalClass.getPrefDefault("city_display", "true", getApplicationContext());
        if (city_display.equalsIgnoreCase("true")) {

            city_tv.setVisibility(View.VISIBLE);
            city_et.setVisibility(View.VISIBLE);

            citySet();

        } else {
            city_tv.setVisibility(View.GONE);
            city_et.setVisibility(View.GONE);
        }
        String postcode_display = GlobalClass.getPrefDefault("postcode_display", "true", getApplicationContext());
        if (postcode_display.equalsIgnoreCase("true")) {

            postcode_tv.setVisibility(View.VISIBLE);
            postcode_et.setVisibility(View.VISIBLE);

            postCodeSet();
        } else {
            postcode_tv.setVisibility(View.GONE);
            postcode_et.setVisibility(View.GONE);
        }
        String event_date_display = GlobalClass.getPrefDefault("event_date_display", "true", getApplicationContext());
        if (event_date_display.equalsIgnoreCase("true")) {

            event_date_tv.setVisibility(View.VISIBLE);
            event_date_selection.setVisibility(View.VISIBLE);

            dateSet();

        } else {
            event_date_tv.setVisibility(View.GONE);
            event_date_selection.setVisibility(View.GONE);
        }


        String event_start_time_display = GlobalClass.getPrefDefault("event_start_time_display", "true", getApplicationContext());
        if (event_start_time_display.equalsIgnoreCase("true")) {
            event_start_time_tv.setVisibility(View.VISIBLE);
            event_start_time_selection.setVisibility(View.VISIBLE);

            starttimeSet();

        } else {
            event_start_time_tv.setVisibility(View.GONE);
            event_start_time_selection.setVisibility(View.GONE);

        }

        String event_end_time_display = GlobalClass.getPrefDefault("event_end_time_display", "true", getApplicationContext());
        if (event_end_time_display.equalsIgnoreCase("true")) {
            event_end_time_tv.setVisibility(View.VISIBLE);
            event_end_time_selection.setVisibility(View.VISIBLE);

            endtimeSet();

        } else {
            event_end_time_tv.setVisibility(View.GONE);
            event_end_time_selection.setVisibility(View.GONE);
        }


        String event_type_display = GlobalClass.getPrefDefault("event_type_display", "true", getApplicationContext());
        if (event_type_display.equalsIgnoreCase("true")) {

            event_type_tv.setVisibility(View.VISIBLE);
            event_type_et.setVisibility(View.VISIBLE);


            typeSet();

        } else {
            event_type_tv.setVisibility(View.GONE);
            event_type_et.setVisibility(View.GONE);
        }
        String event_name_display = GlobalClass.getPrefDefault("event_name_display", "true", getApplicationContext());
        if (event_name_display.equalsIgnoreCase("true")) {
            event_name_tv.setVisibility(View.VISIBLE);
            event_name_et.setVisibility(View.VISIBLE);

            nameSet();
        } else {
            event_name_tv.setVisibility(View.GONE);
            event_name_et.setVisibility(View.GONE);
        }
//        String max_budget_display = GlobalClass.getPrefDefault("max_budget_display", "true", getApplicationContext());
//
//        if (max_budget_display.equalsIgnoreCase("true")) {
//            event_budget_tv.setVisibility(View.GONE);
//            event_budget_et.setVisibility(View.GONE);
//
//            budgetSet();
//        } else {
//            event_budget_tv.setVisibility(View.GONE);
//            event_budget_et.setVisibility(View.GONE);
//        }


        //for venue Name
        String venue_name_display = GlobalClass.getPrefDefault("venue_name_display", "true", getApplicationContext());

        if (venue_name_display.equalsIgnoreCase("true")) {
            venue_name_tv.setVisibility(View.VISIBLE);
            venue_name_et.setVisibility(View.VISIBLE);

            setVenueName();
        } else {
            venue_name_tv.setVisibility(View.GONE);
            venue_name_et.setVisibility(View.GONE);
        }

        //for venue Address
        String venue_address_display = GlobalClass.getPrefDefault("venue_address_display", "true", getApplicationContext());

        if (venue_address_display.equalsIgnoreCase("true")) {
            venue_address_tv.setVisibility(View.VISIBLE);
            venue_address_et.setVisibility(View.VISIBLE);

            setVenueAddress();
        } else {
            venue_address_tv.setVisibility(View.GONE);
            venue_address_et.setVisibility(View.GONE);
        }
        //for venue postcode
        String venue_postcode_display = GlobalClass.getPrefDefault("venue_postcode_display", "true", getApplicationContext());

        if (venue_postcode_display.equalsIgnoreCase("true")) {
            venue_postcode_tv.setVisibility(View.VISIBLE);
            venue_postcode_et.setVisibility(View.VISIBLE);

            setVenuePostCode();
        } else {
            venue_postcode_tv.setVisibility(View.GONE);
            venue_postcode_et.setVisibility(View.GONE);
        }
        String submitText = GlobalClass.getPrefDefault("submit_text_words", getApplicationContext().getResources().getString(R.string.submit), getApplicationContext());
        submit_tv.setText(submitText);

        String formBackgroundColor = GlobalClass.getPref("formBackgroundColor", getApplicationContext());
        if (!formBackgroundColor.equalsIgnoreCase("")) {
            rootView.setBackgroundColor(Integer.parseInt(formBackgroundColor));
        }

        GlobalClass.getPrefDefault("permission_required", "true", getApplicationContext());

        String submitTextColor = GlobalClass.getPref("submitTextColor", getApplicationContext());
        if (!submitTextColor.equalsIgnoreCase("")) {

            submit_tv.setTextColor(Integer.parseInt(submitTextColor));
        }

        String submitBackgroundColor = GlobalClass.getPref("submitBackgroundColor", getApplicationContext());
        if (!submitBackgroundColor.equalsIgnoreCase("")) {

            submit_tv.setBackgroundColor(Integer.parseInt(submitBackgroundColor));
        }
        String allTextColor = GlobalClass.getPref("allTextColor", getApplicationContext());
        if (!allTextColor.equalsIgnoreCase("")) {


            additional_notes_tv.setTextColor(Integer.parseInt(allTextColor));
//            event_budget_tv.setTextColor(Integer.parseInt(allTextColor));
            event_name_tv.setTextColor(Integer.parseInt(allTextColor));
            event_type_tv.setTextColor(Integer.parseInt(allTextColor));

            event_start_time_tv.setTextColor(Integer.parseInt(allTextColor));
            event_end_time_tv.setTextColor(Integer.parseInt(allTextColor));

            event_date_tv.setTextColor(Integer.parseInt(allTextColor));
            postcode_tv.setTextColor(Integer.parseInt(allTextColor));
            city_tv.setTextColor(Integer.parseInt(allTextColor));
            address_tv.setTextColor(Integer.parseInt(allTextColor));
            mobile_tv.setTextColor(Integer.parseInt(allTextColor));

            telephone_tv.setTextColor(Integer.parseInt(allTextColor));
            email_tv.setTextColor(Integer.parseInt(allTextColor));
            company_name_tv.setTextColor(Integer.parseInt(allTextColor));
            last_name_tv.setTextColor(Integer.parseInt(allTextColor));
            firstName_tv.setTextColor(Integer.parseInt(allTextColor));
            contact_permission_tv.setTextColor(Integer.parseInt(allTextColor));

            venue_name_tv.setTextColor(Integer.parseInt(allTextColor));
            venue_address_tv.setTextColor(Integer.parseInt(allTextColor));
            venue_postcode_tv.setTextColor(Integer.parseInt(allTextColor));

        }

        clear_tv.setOnClickListener(view -> {

            additional_notes_et.setText("");
//            event_budget_et.setText("");
            event_name_et.setText("");
            event_type_et.setText("");
            event_start_time_tv.setText("");
            event_end_time_tv.setText("");
            event_date_selection.setText("");
            postcode_et.setText("");
            city_tv.setText("");
            address_et.setText("");
            mobile_et.setText("");
            telephone_et.setText("");
            email_et.setText("");
            company_name_et.setText("");
            last_name_et.setText("");
            firstName_et.setText("");
            venue_name_tv.setText("");
            venue_address_tv.setText("");
            venue_postcode_tv.setText("");


        });


        submit_tv.setOnClickListener(view -> {

//            eventBudget = event_budget_et.getText().toString().trim();
            additionalNotes = additional_notes_et.getText().toString().trim();
            eventName = event_name_et.getText().toString().trim();


            DatabaseHelper dbHelper = new DatabaseHelper(AddBusinessActivity.this);
            List<EventTypeModel> allTypes = dbHelper.getAllTypes();
            if (allTypes.size() > 0) {
                eventType = event_type_et.getText().toString().trim();
            } else {
                eventType = "";
            }




            eventStartTime = event_start_time_selection.getText().toString().trim();
            eventEndTime = event_end_time_selection.getText().toString().trim();
            eventDate = event_date_selection.getText().toString().trim();
            postCode = postcode_et.getText().toString().trim();
            city = city_et.getText().toString().trim();
            address = address_et.getText().toString().trim();
            mobile = mobile_et.getText().toString().trim();
            telephone = telephone_et.getText().toString().trim();
            email = email_et.getText().toString().trim();
            company = company_name_et.getText().toString().trim();
            lastName = last_name_et.getText().toString().trim();
            firstName = firstName_et.getText().toString().trim();

            venuName = venue_name_et.getText().toString().trim();
            venuAddress = venue_address_et.getText().toString().trim();
            venuPostCode = venue_postcode_et.getText().toString().trim();


//            String required_budget = GlobalClass.getPrefDefault("max_budget_required", "true", getApplicationContext());
//            String required_contact_permission = GlobalClass.getPrefDefault("permission_display", "true", getApplicationContext());
            String required_event_name = GlobalClass.getPrefDefault("event_name_required", "true", getApplicationContext());
            String required_event_type = GlobalClass.getPrefDefault("event_type_required", "true", getApplicationContext());
            String required_event_date = GlobalClass.getPrefDefault("event_date_required", "true", getApplicationContext());
            String required_post_code = GlobalClass.getPrefDefault("postcode_required", "true", getApplicationContext());
            String required_city = GlobalClass.getPrefDefault("city_required", "true", getApplicationContext());
            String required_address = GlobalClass.getPrefDefault("address_required", "true", getApplicationContext());
            String required_mobile = GlobalClass.getPrefDefault("mobile_required", "true", getApplicationContext());
            String required_telephone = GlobalClass.getPrefDefault("telephone_required", "true", getApplicationContext());
            String required_email = GlobalClass.getPrefDefault("email_required", "true", getApplicationContext());
            String required_company = GlobalClass.getPrefDefault("company_required", "true", getApplicationContext());
            String required_additional_notes = GlobalClass.getPrefDefault("additional_notes_required", "true", getApplicationContext());
            String required_last_name = GlobalClass.getPrefDefault("last_name_required", "true", getApplicationContext());
            String required_first_name = GlobalClass.getPrefDefault("firstname_required", "true", getApplicationContext());

            String required_start_time = GlobalClass.getPrefDefault("event_start_time_required", "true", getApplicationContext());
            String required_end_time = GlobalClass.getPrefDefault("event_end_time_required", "true", getApplicationContext());

            String required_venue_name = GlobalClass.getPrefDefault("venue_name_required", "true", getApplicationContext());
            String required_venue_address = GlobalClass.getPrefDefault("venue_address_required", "true", getApplicationContext());
            String required_venue_postcodes = GlobalClass.getPrefDefault("venue_postcode_required", "true", getApplicationContext());


            /////////////////////////////////////////////

            String visible_notes = GlobalClass.getPrefDefault("additional_notes_display", "true", getApplicationContext());
            String visible_contact = GlobalClass.getPrefDefault("permission_display", "true", getApplicationContext());
            String visible_book_now = GlobalClass.getPrefDefault("book_now", "true", getApplicationContext());
            String visible_first_name = GlobalClass.getPrefDefault("firstname_display", "true", getApplicationContext());
            String visible_last_name = GlobalClass.getPrefDefault("last_name_display", "true", getApplicationContext());
            String visible_company = GlobalClass.getPrefDefault("company_display", "true", getApplicationContext());
            String visible_email = GlobalClass.getPrefDefault("email_display", "true", getApplicationContext());
            String visible_telephone = GlobalClass.getPrefDefault("telephone_display", "true", getApplicationContext());
            String visible_mobile = GlobalClass.getPrefDefault("mobile_display", "true", getApplicationContext());
            String visible_address = GlobalClass.getPrefDefault("address_display", "true", getApplicationContext());
            String visible_city = GlobalClass.getPrefDefault("city_display", "true", getApplicationContext());
            String visible_postcode = GlobalClass.getPrefDefault("postcode_display", "true", getApplicationContext());
            String visible_date = GlobalClass.getPrefDefault("event_date_display", "true", getApplicationContext());
            String visible_type = GlobalClass.getPrefDefault("event_type_display", "true", getApplicationContext());
            String visible_name = GlobalClass.getPrefDefault("event_name_display", "true", getApplicationContext());
//            String visible_budget = GlobalClass.getPrefDefault("max_budget_display", "true", getApplicationContext());

            String visible_start_time = GlobalClass.getPrefDefault("event_start_time_display", "true", getApplicationContext());
            String visible_end_time = GlobalClass.getPrefDefault("event_end_time_display", "true", getApplicationContext());

            String visible_venue_name = GlobalClass.getPrefDefault("venue_name_display", "true", getApplicationContext());
            String visible_venue_address = GlobalClass.getPrefDefault("venue_address_display", "true", getApplicationContext());
            String visible_venue_postcode = GlobalClass.getPrefDefault("venue_postcode_display", "true", getApplicationContext());


            ///////////////////////////////////////////////


            if (visible_name.equalsIgnoreCase("true")) {

                if (required_event_name.equalsIgnoreCase("true")) {
                    if (eventName.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please Enter Event Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

            }


            if (visible_contact.equalsIgnoreCase("true")) {

//                    if (required_contact_permission.equalsIgnoreCase("true")) {
//                        if (state.equalsIgnoreCase("")) {
//
//                            Toast.makeText(AddBusinessActivity.this, "Please Enter Contact Permission", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }


                if (checkboxState == 0) {

                    checkboxString = "";

                } else {

                    checkboxString = "Yes please, we’d like to hear about offers and services.";

                }

            }

//            if (visible_budget.equalsIgnoreCase("true")) {
//                if (required_budget.equalsIgnoreCase("true")) {
//                    if (eventBudget.equalsIgnoreCase("")) {
//                        Toast.makeText(AddBusinessActivity.this, "Please enter event budget", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//            }

            if (allTypes.size() > 0) {
                if (visible_type.equalsIgnoreCase("true")) {
                    if (required_event_type.equalsIgnoreCase("true")) {
                        if (eventType.equalsIgnoreCase("")) {
                            Toast.makeText(AddBusinessActivity.this, "Please enter event type", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
            if (visible_start_time.equalsIgnoreCase("true")) {
                if (required_start_time.equalsIgnoreCase("true")) {
                    if (eventStartTime.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter event start time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_end_time.equalsIgnoreCase("true")) {
                if (required_end_time.equalsIgnoreCase("true")) {
                    if (eventEndTime.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter even end time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_date.equalsIgnoreCase("true")) {
                if (required_event_date.equalsIgnoreCase("true")) {
                    if (eventDate.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter event date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_postcode.equalsIgnoreCase("true")) {
                if (required_post_code.equalsIgnoreCase("true")) {
                    if (postCode.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter event postcode", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_city.equalsIgnoreCase("true")) {
                if (required_city.equalsIgnoreCase("true")) {
                    if (city.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter City", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_address.equalsIgnoreCase("")) {
                if (required_address.equalsIgnoreCase("true")) {
                    if (address.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter Address", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_mobile.equalsIgnoreCase("true")) {
                if (required_mobile.equalsIgnoreCase("true")) {
                    if (mobile.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_telephone.equalsIgnoreCase("true")) {
                if (required_telephone.equalsIgnoreCase("true")) {
                    if (telephone.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_email.equalsIgnoreCase("true")) {
                if (required_email.equalsIgnoreCase("true")) {
                    if (email.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_company.equalsIgnoreCase("true")) {
                if (required_company.equalsIgnoreCase("true")) {
                    if (company.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter company name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (visible_notes.equalsIgnoreCase("true")) {
                if (required_additional_notes.equalsIgnoreCase("true")) {
                    if (additionalNotes.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter additional notes", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_last_name.equalsIgnoreCase("true")) {
                if (required_last_name.equalsIgnoreCase("true")) {
                    if (lastName.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_first_name.equalsIgnoreCase("true")) {
                if (required_first_name.equalsIgnoreCase("true")) {
                    if (firstName.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_venue_name.equalsIgnoreCase("true")) {
                if (required_venue_name.equalsIgnoreCase("true")) {
                    if (venuName.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter Venue Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_venue_address.equalsIgnoreCase("true")) {
                if (required_venue_address.equalsIgnoreCase("true")) {
                    if (venuAddress.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter Venue Address", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if (visible_venue_postcode.equalsIgnoreCase("true")) {
                if (required_venue_postcodes.equalsIgnoreCase("true")) {
                    if (venuPostCode.equalsIgnoreCase("")) {
                        Toast.makeText(AddBusinessActivity.this, "Please enter Venue Postcode", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }


            GlobalClass.hideKeyboard(AddBusinessActivity.this);

            DBLeads leadModel = new DBLeads();
//                leadModel.setLead_id("");
            leadModel.setFirstName(firstName);
            leadModel.setLastname(lastName);
            leadModel.setCompanyName(company);
            leadModel.setEmail(email);
            leadModel.setTelephone(telephone);
            leadModel.setMobilePhone(mobile);
            leadModel.setAddress(address);
            leadModel.setCity(city);
            leadModel.setPostcode(postCode);
            leadModel.setEventDate(eventDate);
            leadModel.setEventTimeStart(eventStartTime);
            leadModel.setEventTimeEnd(eventEndTime);
            leadModel.setEventType(eventType);
            leadModel.setEventName(eventName);
//            leadModel.setEventBudget(eventBudget);
            leadModel.setContactPermission(checkboxString);
            leadModel.setAdditionalNotes(additionalNotes);
            leadModel.setEventImage("");
            leadModel.setUpdated("0");
            leadModel.setVenueName(venuName);
            leadModel.setVenueAddress(venuAddress);
            leadModel.setVenuPostCode(venuPostCode);
            leadModel.setEventTypeID(eventyTypeIDFinal);

            if (GlobalClass.isOnline(AddBusinessActivity.this)) {
                leadModel.setUploadedStatus("1");
                databaseObject.insertLead(leadModel);
                submitLeads();
                getApplicationContext().sendBroadcast(new Intent("refresh_leads"));
            } else {
                leadModel.setUploadedStatus("0");
                databaseObject.insertLead(leadModel);
                getApplicationContext().sendBroadcast(new Intent("refresh_leads"));
                Toast.makeText(this, "Lead Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        String fontName = GlobalClass.getPrefDefault("fontName", "Open Sans Regular", getApplicationContext());
        switch (fontName) {
            case "Open Sans Regular":
                typeface = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
                setTypeFace(typeface);
                break;
            case "Open Sans Bold":
                typeface = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

                setTypeFace(typeface);
                break;
        }


        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//            String pickerYear = String.valueOf(year);
            String pickerMonth = String.valueOf(monthOfYear + 1);
            String pickerDay = String.valueOf(dayOfMonth);

            if (Integer.parseInt(pickerMonth) < 9) {

                pickerMonth = "0" + pickerMonth;

            }

            if (Integer.parseInt(pickerDay) < 9) {

                pickerDay = "0" + pickerDay;

            }


            String dateHere = year + "-" + pickerMonth + "-" + pickerDay;

            event_date_selection.setText(dateHere);

        };

        event_date_selection.setOnClickListener(v -> {

            new DatePickerDialog(AddBusinessActivity.this, R.style.DialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        event_start_time_selection.setOnClickListener(view -> {

            TimePickerDialog mTimePicker;

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            mTimePicker = new TimePickerDialog(AddBusinessActivity.this, R.style.DialogTheme, (
                    timePicker, selectedHour, selectedMinute) -> {


                String time = selectedHour + ":" + selectedMinute;
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date date1 = null;
                try {
                    date1 = fmt.parse(time);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                String formattedTime = null;
                if (date1 != null) {
                    formattedTime = fmtOut.format(date1);
                }

                event_start_time_selection.setText(formattedTime);
            }, hour, minute, false);//No 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

        event_end_time_selection.setOnClickListener(view -> {

            TimePickerDialog mTimePicker;

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            mTimePicker = new TimePickerDialog(AddBusinessActivity.this, R.style.DialogTheme, (
                    timePicker, selectedHour, selectedMinute) -> {


                String time = selectedHour + ":" + selectedMinute;
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date date12 = null;
                try {
                    date12 = fmt.parse(time);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                String formattedTime = null;
                if (date12 != null) {
                    formattedTime = fmtOut.format(date12);
                }

                event_end_time_selection.setText(formattedTime);
            }, hour, minute, false);//No 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });


        DatabaseHelper dbHelper = new DatabaseHelper(AddBusinessActivity.this);
        List<EventTypeModel> allTypes = dbHelper.getAllTypes();
        if (allTypes.size() > 0) {
        } else {
            event_type_tv.setVisibility(View.GONE);
            event_type_et.setVisibility(View.GONE);
        }
        event_type_et.setOnClickListener(view -> {
            List<String> mAnimals = new ArrayList<>();
            for (int i = 0; i < allTypes.size(); i++) {

                String type = allTypes.get(i).getLabel();

                mAnimals.add(type);
            }
            //Create sequence of items
            final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddBusinessActivity.this);
            dialogBuilder.setTitle(getApplicationContext().getResources().getString(R.string.event_types));
            dialogBuilder.setItems(Animals, (dialog, item) -> {
                String selectedText = Animals[item].toString();
                event_type_et.setText(selectedText);
                for (int i = 0; i < mAnimals.size(); i++) {
                    if (selectedText.equalsIgnoreCase(mAnimals.get(i))) {
                        EventTypeModel enventTypeModel = allTypes.get(i);
                        eventyTypeIDFinal = enventTypeModel.getCateGoryID();
                        Log.d(TAG, "onClickCatID: " + eventyTypeIDFinal);
                    }
                }
            });
            //Create alert dialog object via builder
            AlertDialog alertDialogObject = dialogBuilder.create();
            //Show the dialog
            alertDialogObject.show();
        });

    }
    private void setTypeFace(Typeface typeFace) {

        contact_permission_tv.setTypeface(typeFace);
        additional_notes_tv.setTypeface(typeFace);
//        event_budget_tv.setTypeface(typeFace);
        event_name_tv.setTypeface(typeFace);
        event_type_tv.setTypeface(typeFace);
        event_start_time_tv.setTypeface(typeFace);
        event_end_time_tv.setTypeface(typeFace);
        event_date_tv.setTypeface(typeFace);
        postcode_tv.setTypeface(typeFace);
        city_tv.setTypeface(typeFace);
        address_tv.setTypeface(typeFace);
        mobile_tv.setTypeface(typeFace);
        telephone_tv.setTypeface(typeFace);
        email_tv.setTypeface(typeFace);
        company_name_tv.setTypeface(typeFace);
        last_name_tv.setTypeface(typeFace);
        firstName_tv.setTypeface(typeFace);

        venue_name_tv.setTypeface(typeFace);
        venue_address_tv.setTypeface(typeFace);
        venue_postcode_tv.setTypeface(typeFace);
    }

    private void setVenuePostCode() {


        String required = GlobalClass.getPrefDefault("venue_postcode_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_event_budget = GlobalClass.getPrefDefault("venuePostCode", getApplicationContext().getResources().getString(R.string.venue_postcode_with_star), getApplicationContext());
            venue_postcode_tv.setText(e_event_budget);

        } else {

            String e_event_budget = GlobalClass.getPrefDefault("venuePostCode", getApplicationContext().getResources().getString(R.string.venue_postcode), getApplicationContext());
            venue_postcode_tv.setText(e_event_budget);


        }
    }

    private void setVenueAddress() {


        String required = GlobalClass.getPrefDefault("venue_address_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_event_budget = GlobalClass.getPrefDefault("venueAddress", getApplicationContext().getResources().getString(R.string.venue_address_with_star), getApplicationContext());
            venue_address_tv.setText(e_event_budget);

        } else {

            String e_event_budget = GlobalClass.getPrefDefault("venueAddress", getApplicationContext().getResources().getString(R.string.venue_address), getApplicationContext());
            venue_address_tv.setText(e_event_budget);


        }
    }

    private void setVenueName() {


        String required = GlobalClass.getPrefDefault("venue_name_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_event_budget = GlobalClass.getPrefDefault("venueName", getApplicationContext().getResources().getString(R.string.venu_name_with_star), getApplicationContext());
            venue_name_tv.setText(e_event_budget);

        } else {

            String e_event_budget = GlobalClass.getPrefDefault("venueName", getApplicationContext().getResources().getString(R.string.venue_name), getApplicationContext());
            venue_name_tv.setText(e_event_budget);


        }
    }

//    private void budgetSet() {
//
//
//        String required = GlobalClass.getPrefDefault("max_budget_required", "true", getApplicationContext());
//
//        if (required.equalsIgnoreCase("true")) {
//
//            String e_event_budget = GlobalClass.getPrefDefault("e_event_budget", getApplicationContext().getResources().getString(R.string.max_budget_with_star), getApplicationContext());
//
//            event_budget_tv.setText(e_event_budget);
//
//        } else {
//
//            String e_event_budget = GlobalClass.getPrefDefault("e_event_budget", getApplicationContext().getResources().getString(R.string.max_budget), getApplicationContext());
//
//            event_budget_tv.setText(e_event_budget);
//
//
//        }
//    }

    private void contactPermissionSet() {
        String required = GlobalClass.getPrefDefault("permission_display", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_event_name = GlobalClass.getPrefDefault("contact_permission_text", getApplicationContext().getResources().getString(R.string.contact_permission_with_star), getApplicationContext());
            contact_permission_tv.setText(e_event_name);

        } else {

            String e_event_name = GlobalClass.getPrefDefault("contact_permission_text", getApplicationContext().getResources().getString(R.string.contact_permission), getApplicationContext());
            contact_permission_tv.setText(e_event_name);


        }

    }

    private void nameSet() {
        String required = GlobalClass.getPrefDefault("event_name_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_event_name = GlobalClass.getPrefDefault("e_event_name", getApplicationContext().getResources().getString(R.string.event_name_with_star), getApplicationContext());
            event_name_tv.setText(e_event_name);

        } else {

            String e_event_name = GlobalClass.getPrefDefault("e_event_name", getApplicationContext().getResources().getString(R.string.event_name), getApplicationContext());
            event_name_tv.setText(e_event_name);


        }

    }

    private void typeSet() {


        String required = GlobalClass.getPrefDefault("event_type_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_type = GlobalClass.getPrefDefault("e_type", getApplicationContext().getResources().getString(R.string.event_type_with_star), getApplicationContext());
            event_type_tv.setText(e_type);

        } else {

            String e_type = GlobalClass.getPrefDefault("e_type", getApplicationContext().getResources().getString(R.string.event_type), getApplicationContext());
            event_type_tv.setText(e_type);


        }

    }

    private void starttimeSet() {

        String required = GlobalClass.getPrefDefault("event_start_time_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_time = GlobalClass.getPrefDefault("start_time", getApplicationContext().getResources().getString(R.string.event_start_time_with_star), getApplicationContext());
            event_start_time_tv.setText(e_time);

            event_start_time_tv.setVisibility(View.VISIBLE);
            event_start_time_selection.setVisibility(View.VISIBLE);

        } else {

            String e_time = GlobalClass.getPrefDefault("start_time", getApplicationContext().getResources().getString(R.string.event_start_time), getApplicationContext());
            event_start_time_tv.setText(e_time);

            event_start_time_tv.setVisibility(View.VISIBLE);
            event_start_time_selection.setVisibility(View.VISIBLE);


        }


    }

    private void endtimeSet() {

        String required = GlobalClass.getPrefDefault("event_end_time_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_time = GlobalClass.getPrefDefault("end_time", getApplicationContext().getResources().getString(R.string.event_end_time_with_star), getApplicationContext());
            event_end_time_tv.setText(e_time);

            event_end_time_tv.setVisibility(View.VISIBLE);
            event_end_time_selection.setVisibility(View.VISIBLE);

        } else {

            String e_time = GlobalClass.getPrefDefault("end_time", getApplicationContext().getResources().getString(R.string.event_end_time), getApplicationContext());
            event_end_time_tv.setText(e_time);

            event_end_time_tv.setVisibility(View.VISIBLE);
            event_end_time_tv.setVisibility(View.VISIBLE);


        }


    }

    private void dateSet() {
        String required = GlobalClass.getPrefDefault("event_date_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_date = GlobalClass.getPrefDefault("e_date", getApplicationContext().getResources().getString(R.string.event_date_with_star), getApplicationContext());
            event_date_tv.setText(e_date);

        } else {

            String e_date = GlobalClass.getPrefDefault("e_date", getApplicationContext().getResources().getString(R.string.event_date), getApplicationContext());
            event_date_tv.setText(e_date);


        }


    }

    private void postCodeSet() {


        String required = GlobalClass.getPrefDefault("postcode_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_postcode = GlobalClass.getPrefDefault("e_postcode", getApplicationContext().getResources().getString(R.string.postcode_with_star), getApplicationContext());
            postcode_tv.setText(e_postcode);

        } else {

            String e_postcode = GlobalClass.getPrefDefault("e_postcode", getApplicationContext().getResources().getString(R.string.postcode), getApplicationContext());
            postcode_tv.setText(e_postcode);
        }
    }

    private void citySet() {

        String required = GlobalClass.getPrefDefault("city_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_city = GlobalClass.getPrefDefault("e_city", getApplicationContext().getResources().getString(R.string.city_with_star), getApplicationContext());
            city_tv.setText(e_city);

        } else {

            String e_city = GlobalClass.getPrefDefault("e_city", getApplicationContext().getResources().getString(R.string.city), getApplicationContext());
            city_tv.setText(e_city);


        }

    }

    private void addressSet() {


        String required = GlobalClass.getPrefDefault("address_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {

            String e_address = GlobalClass.getPrefDefault("e_address", getApplicationContext().getResources().getString(R.string.address_with_star), getApplicationContext());
            address_tv.setText(e_address);

        } else {

            String e_address = GlobalClass.getPrefDefault("e_address", getApplicationContext().getResources().getString(R.string.address), getApplicationContext());
            address_tv.setText(e_address);


        }


    }

    private void mobileNUmberSetter() {


        String required = GlobalClass.getPrefDefault("mobile_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {
            String e_mobile = GlobalClass.getPrefDefault("e_mobile", getApplicationContext().getResources().getString(R.string.mobile_phone_with_star), getApplicationContext());
            mobile_tv.setText(e_mobile);
        } else {

            String e_mobile = GlobalClass.getPrefDefault("e_mobile", getApplicationContext().getResources().getString(R.string.mobile_phone), getApplicationContext());
            mobile_tv.setText(e_mobile);


        }


    }

    private void telephoneSet() {


        String required = GlobalClass.getPrefDefault("telephone_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {
            String e_telephone = GlobalClass.getPrefDefault("e_telephone", getApplicationContext().getResources().getString(R.string.telephone_with_star), getApplicationContext());
            telephone_tv.setText(e_telephone);
        } else {

            String e_telephone = GlobalClass.getPrefDefault("e_telephone", getApplicationContext().getResources().getString(R.string.telephone), getApplicationContext());
            telephone_tv.setText(e_telephone);


        }

    }

    private void emailSet() {

        String required = GlobalClass.getPrefDefault("email_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {
            String e_email = GlobalClass.getPrefDefault("e_email", getApplicationContext().getResources().getString(R.string.email_with_star), getApplicationContext());
            email_tv.setText(e_email);
        } else {

            String e_email = GlobalClass.getPrefDefault("e_email", getApplicationContext().getResources().getString(R.string.email), getApplicationContext());
            email_tv.setText(e_email);


        }


    }

    private void conpanySet() {


        String required = GlobalClass.getPrefDefault("company_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {
            String e_company = GlobalClass.getPrefDefault("e_company", getApplicationContext().getResources().getString(R.string.company_name_with_star), getApplicationContext());
            company_name_tv.setText(e_company);
        } else {

            String e_company = GlobalClass.getPrefDefault("e_company", getApplicationContext().getResources().getString(R.string.company_name), getApplicationContext());
            company_name_tv.setText(e_company);


        }

    }

    private void additionalNotesSetval() {
        String required = GlobalClass.getPrefDefault("additional_notes_required", "true", getApplicationContext());

        if (required.equalsIgnoreCase("true")) {
            String e_additional_notes = GlobalClass.getPrefDefault("e_additional_notes", getApplicationContext().getResources().getString(R.string.additional_notes_with_star), getApplicationContext());
            additional_notes_tv.setText(e_additional_notes);
        } else {

            String e_additional_notes = GlobalClass.getPrefDefault("e_additional_notes", getApplicationContext().getResources().getString(R.string.additional_notes), getApplicationContext());
            additional_notes_tv.setText(e_additional_notes);
        }
    }

    private void lastNameValueSet() {

        String required = GlobalClass.getPrefDefault("last_name_required", "true", getApplicationContext());
        if (required.equalsIgnoreCase("true")) {
            String e_last_name = GlobalClass.getPrefDefault("e_last_name", getApplicationContext().getResources().getString(R.string.last_name_with_star), getApplicationContext());
            last_name_tv.setText(e_last_name);
        } else {
            String e_last_name = GlobalClass.getPrefDefault("e_last_name", getApplicationContext().getResources().getString(R.string.last_name), getApplicationContext());
            last_name_tv.setText(e_last_name);
        }
    }

    private void firstNameValueSet() {
        String required = GlobalClass.getPrefDefault("firstname_required", "true", getApplicationContext());
        if (required.equalsIgnoreCase("true")) {
            String e_first_name = GlobalClass.getPrefDefault("e_first_name", getApplicationContext().getResources().getString(R.string.first_name_with_star), getApplicationContext());
            firstName_tv.setText(e_first_name);
        } else {
            String e_first_name = GlobalClass.getPrefDefault("e_first_name", getApplicationContext().getResources().getString(R.string.first_name), getApplicationContext());
            firstName_tv.setText(e_first_name);
        }

    }

    private void showChangeLangDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddBusinessActivity.this);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = null;
        if (inflater != null) {
            dialogView = inflater.inflate(R.layout.custom_business_alert, null);
        }

        dialogBuilder.setView(dialogView);
        String bookNow_text = GlobalClass.getPrefDefault("book_now_text", getApplicationContext().getResources().getString(R.string.book_now), getApplicationContext());
        String thankyouTXT = GlobalClass.getPrefDefault("thankyou_text", getApplicationContext().getResources().getString(R.string.thank_you_default_text), getApplicationContext());
        String bookNowSwitch = GlobalClass.getPrefDefault("book_now", "true", getApplicationContext());

        TextView thankyout_text_tv = null;
        ImageView close_img = null;
        if (dialogView != null) {
            thankyout_text_tv = dialogView.findViewById(R.id.thankyout_text_tv);
            thankyout_text_tv.setText(thankyouTXT);
            book_now_tv = dialogView.findViewById(R.id.book_now_tv);

            close_img = dialogView.findViewById(R.id.close_img);
        }


        if (bookNowSwitch.equalsIgnoreCase("true")) {
            book_now_tv.setVisibility(View.VISIBLE);
            book_now_tv.setText(bookNow_text);

        } else {
            book_now_tv.setVisibility(View.GONE);
        }


        if (close_img != null) {
            close_img.setOnClickListener(v -> {

                alertDialog.dismiss();
                finish();

            });
        }

        book_now_tv.setOnClickListener(view -> {

//            booknowUrlHit();
            Intent intent = new Intent(AddBusinessActivity.this, WebViewActivity.class);
            intent.putExtra("urlVal", bookingLink);
            startActivity(intent);
            finish();
            // to do with book Now block
            alertDialog.dismiss();


        });

        alertDialog = dialogBuilder.create();

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

    }

    private void submitLeads() {

        String key = GlobalClass.getPref("user_client_key", getApplicationContext());
        String secret = GlobalClass.getPref("user_client_secret", getApplicationContext());
        String base_url = GlobalClass.getPref("user_base_url", getApplicationContext());

        String url = base_url + "" + "create/lead?key=" + key + "&secret=" + secret;

        RequestParams mParams = new RequestParams();
        mParams.put("first_name", firstName);
        mParams.put("last_name", lastName);
        mParams.put("company", company);
        mParams.put("email", email);
        mParams.put("telephone", telephone);
        mParams.put("mobile_phone_number", mobile);
        mParams.put("street_address", address);
        mParams.put("city", city);
        mParams.put("postcode", postCode);
        mParams.put("event_date", eventDate);
        mParams.put("event_time_start", eventStartTime);
        mParams.put("event_time_end", eventEndTime);
        mParams.put("event_type", eventyTypeIDFinal);
        mParams.put("event_name", eventName);
        mParams.put("additional_notes", additionalNotes);
        mParams.put("venue_name", venuName);
        mParams.put("venue_address", venuAddress);
        mParams.put("venue_postcode", venuPostCode);
//        mParams.put("field_event_type_target_id", eventyTypeIDFinal);


        WebReq.post(getApplicationContext(), url, mParams, new MyTextHttpResponseHandlerSaveLead());

        Log.d("paramsHere", "saveLocation: " + mParams);


    }


    private class MyTextHttpResponseHandlerSaveLead extends JsonHttpResponseHandler {

        MyTextHttpResponseHandlerSaveLead() {

        }

        @Override
        public void onStart() {
            super.onStart();
            GlobalClass.showLoading(AddBusinessActivity.this);
            Log.d(TAG, "onStart");

        }

        @Override
        public void onFinish() {
            super.onFinish();
            GlobalClass.dismissLoading();

            Log.d(TAG, "onFinish");

        }

        @Override
        public void onFailure(int mStatusCode, Header[] headers, Throwable mThrow, JSONObject e) {
            Log.d(TAG, "OnFailure" + e);
            GlobalClass.dismissLoading();

            try {
                String statusMessage = e.getString("statusMessage");
                Toast.makeText(AddBusinessActivity.this, "" + statusMessage, Toast.LENGTH_SHORT).show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Log.d(TAG, responseString);
            GlobalClass.dismissLoading();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, final JSONObject mResponse) {

            GlobalClass.dismissLoading();
            Log.d(TAG, mResponse.toString() + "Respo");
            if (mResponse.length() != 0) {

                try {
                    String result = mResponse.getString("result");
                    String message = mResponse.getString("message");

                    if (result.equalsIgnoreCase("Success")) {

                        Toast.makeText(AddBusinessActivity.this, message + "", Toast.LENGTH_SHORT).show();

                        bookingLink = mResponse.getString("booking_link");
                        showChangeLangDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


//    private void booknowUrlHit() {
//
//        String key = GlobalClass.getPref("user_client_key", getApplicationContext());
//        String secret = GlobalClass.getPref("user_client_secret", getApplicationContext());
//        String base_url = GlobalClass.getPref("user_base_url", getApplicationContext());
//
//        String url = bookingLink;
//
//        RequestParams mParams = new RequestParams();
//
//        WebReq.get(getApplicationContext(), url, mParams, new MyTextHttpResponseHandlerHitUrl());
//
//        Log.d("paramsHere", "saveLocation: " + mParams);
//
//
//    }
//
//
//    private class MyTextHttpResponseHandlerHitUrl extends JsonHttpResponseHandler {
//
//        MyTextHttpResponseHandlerHitUrl() {
//
//        }
//
//        @Override
//        public void onStart() {
//            super.onStart();
//            GlobalClass.showLoading(AddBusinessActivity.this);
//            Log.d(TAG, "onStart");
//
//        }
//
//        @Override
//        public void onFinish() {
//            super.onFinish();
//            GlobalClass.dismissLoading();
//
//            Log.d(TAG, "onFinish");
//
//        }
//
//        @Override
//        public void onFailure(int mStatusCode, Header[] headers, Throwable mThrow, JSONObject e) {
//            Log.d(TAG, "OnFailure" + e);
//            GlobalClass.dismissLoading();
//
//            try {
//                String statusMessage = e.getString("statusMessage");
//                Toast.makeText(AddBusinessActivity.this, "" + statusMessage, Toast.LENGTH_SHORT).show();
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//            Log.d(TAG, responseString);
//            Toast.makeText(AddBusinessActivity.this, "Booked", Toast.LENGTH_SHORT).show();
//            GlobalClass.dismissLoading();
//            finish();
//        }
//
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, final JSONObject mResponse) {
//
//            GlobalClass.dismissLoading();
//            Log.d(TAG, mResponse.toString() + "Respo");
//            if (mResponse.length() != 0) {
////
////                try {
////                    String result = mResponse.getString("result");
////                    String message = mResponse.getString("message");
////
////                    if (result.equalsIgnoreCase("Success")) {
////
////
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            }
//        }
//    }
//

}
