package com.jobesk.boothbook.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.GlobalClass;

import java.io.ByteArrayOutputStream;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class FragEditLeadForm extends Fragment {


    private View rootView;
    private Activity activity;
    private ImageView backimg;
    private EditText additional_notes_et_et, contact_permission_et;
    private EditText submit_text_et, thankyou_message_et, book_now_et;
    private EditText firstName_et, last_name_et, company_name_et, email_et, telephone_et, mobile_et, address_et, city_et, postcode_et;
    private EditText event_date_et, event_time_et, event_type_et, event_name_et, max_budget_et;
    private TextView logo_select_tv, save_tv;
    private SwitchCompat switch_additional_notes_display, switch_additional_notes_required, switch_permission_display, switch_permission_required;
    private SwitchCompat switch_book_now, switch_firstname_required, switch_firstname_display, switch_last_name_required, switch_last_name_display;
    private SwitchCompat switch_company_required, switch_company_display, switch_email_required, switch_email_display, switch_telephone_required;
    private SwitchCompat switch_telephone_display, switch_mobile_required, switch_mobile_display, switch_address_required, switch_address_display;
    private SwitchCompat switch_city_required, switch_city_display, switch_postcode_required, switch_postcode_display, switch_event_date_required;
    private SwitchCompat switch_event_date_display, switch_event_time_required, switch_event_time_display, switch_event_type_required;
    private SwitchCompat switch_event_type_display, switch_event_name_required, switch_event_name_display, switch_max_budget_required, switch_max_budget_display;

    private TextView font_text_tv;

    private boolean additional_notes_display = true, additional_notes_required = true;
    private boolean permission_display = true, permission_required = true;
    private boolean book_now = true;
    private boolean firstname_required = true,
            firstname_display = true,

    last_name_required = true,
            last_name_display = true,

    company_required = true,
            company_display = true,

    email_required = true,
            email_display = true,

    telephone_required = true,
            telephone_display = true,

    mobile_required = true,
            mobile_display = true,

    address_required = true,
            address_display = true,

    city_required = true,
            city_display = true,

    postcode_required = true,
            postcode_display = true,

    event_date_required = true,
            event_date_display = true,
            event_time_required = true,
            event_time_display = true,

    event_type_required = true,
            event_type_display = true,

    event_name_required = true,
            event_name_display = true,

    max_budget_required = true,
            max_budget_display = true;

    private TextView form_text_color_tv, form_background_color_tv, submit_text_color_tv, submit_background_color_tv;

    private int allTextColor = 0;
    private int formBackgroundColor = 0;
    private int submitTextColor = 0;
    private int submitBackgroundColor = 0;
    private String fontName;
    private String encodedImageString = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.frag_edit_lead_form, container, false);
        activity = (MainActivity) rootView.getContext();


        backimg = rootView.findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getFragmentManager();
                manager.popBackStackImmediate();


            }
        });


        additional_notes_et_et = rootView.findViewById(R.id.additional_notes_et_et);

        switch_additional_notes_display = rootView.findViewById(R.id.switch_additional_notes_display);
        switch_additional_notes_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    additional_notes_display = true;
                } else {
                    additional_notes_display = false;
                }

            }
        });
        switch_additional_notes_required = rootView.findViewById(R.id.switch_additional_notes_required);
        switch_additional_notes_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    additional_notes_required = true;
                } else {
                    additional_notes_required = false;
                }

            }
        });


        contact_permission_et = rootView.findViewById(R.id.contact_permission_et);


        switch_permission_display = rootView.findViewById(R.id.switch_permission_display);
        switch_permission_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    permission_display = true;
                } else {
                    permission_display = false;
                }
            }
        });
        switch_permission_required = rootView.findViewById(R.id.switch_permission_required);
        switch_permission_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    permission_required = true;
                } else {
                    permission_required = false;
                }
            }
        });

        form_text_color_tv = rootView.findViewById(R.id.form_text_color_tv);

        form_background_color_tv = rootView.findViewById(R.id.form_background_color_tv);
        font_text_tv = rootView.findViewById(R.id.font_text_tv);
        submit_text_et = rootView.findViewById(R.id.submit_text_et);
        submit_text_color_tv = rootView.findViewById(R.id.submit_text_color_tv);
        submit_background_color_tv = rootView.findViewById(R.id.submit_btn_color_tv);
        logo_select_tv = rootView.findViewById(R.id.logo_select_tv);
        thankyou_message_et = rootView.findViewById(R.id.thankyou_message_et);
        switch_book_now = rootView.findViewById(R.id.switch_book_now);

        switch_book_now.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    book_now = true;
                } else {
                    book_now = false;
                }

            }
        });
        book_now_et = rootView.findViewById(R.id.book_now_et);


        switch_firstname_required = rootView.findViewById(R.id.switch_firstname_required);
        switch_firstname_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    firstname_required = true;
                } else {
                    firstname_required = false;
                }
            }
        });
        switch_firstname_display = rootView.findViewById(R.id.switch_firstname_display);
        switch_firstname_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    firstname_display = true;
                } else {
                    firstname_display = false;
                }
            }
        });

        firstName_et = rootView.findViewById(R.id.firstName_et);

        switch_last_name_required = rootView.findViewById(R.id.switch_last_name_required);
        switch_last_name_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    last_name_required = true;
                } else {
                    last_name_required = false;
                }
            }
        });
        switch_last_name_display = rootView.findViewById(R.id.switch_last_name_display);
        switch_last_name_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    last_name_display = true;
                } else {
                    last_name_display = false;
                }
            }
        });
        last_name_et = rootView.findViewById(R.id.last_name_et);


        switch_company_required = rootView.findViewById(R.id.switch_company_required);
        switch_company_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    company_required = true;
                } else {
                    company_required = false;
                }
            }
        });
        switch_company_display = rootView.findViewById(R.id.switch_company_display);
        switch_company_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    company_display = true;
                } else {
                    company_display = false;
                }
            }
        });
        company_name_et = rootView.findViewById(R.id.company_name_et);

        switch_email_required = rootView.findViewById(R.id.switch_email_required);
        switch_email_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    email_required = true;
                } else {
                    email_required = false;
                }
            }
        });
        switch_email_display = rootView.findViewById(R.id.switch_email_display);
        switch_email_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    email_display = true;
                } else {
                    email_display = false;
                }
            }
        });
        email_et = rootView.findViewById(R.id.email_et);


        switch_telephone_required = rootView.findViewById(R.id.switch_telephone_required);
        switch_telephone_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {

                    telephone_required = true;

                } else {
                    telephone_required = false;
                }
            }
        });
        switch_telephone_display = rootView.findViewById(R.id.switch_telephone_display);
        switch_telephone_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    telephone_display = true;
                } else {
                    telephone_display = false;
                }
            }
        });
        telephone_et = rootView.findViewById(R.id.telephone_et);

        switch_mobile_required = rootView.findViewById(R.id.switch_mobile_required);
        switch_mobile_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    mobile_required = true;
                } else {
                    mobile_required = false;
                }
            }
        });
        switch_mobile_display = rootView.findViewById(R.id.switch_mobile_display);
        switch_mobile_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    mobile_display = true;
                } else {
                    mobile_display = false;
                }
            }
        });
        mobile_et = rootView.findViewById(R.id.mobile_et);

        switch_address_required = rootView.findViewById(R.id.switch_address_required);
        switch_address_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    address_required = true;
                } else {
                    address_required = false;
                }
            }
        });
        switch_address_display = rootView.findViewById(R.id.switch_address_display);
        switch_address_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    address_display = true;
                } else {
                    address_display = false;
                }
            }
        });
        address_et = rootView.findViewById(R.id.address_et);


        switch_city_required = rootView.findViewById(R.id.switch_city_required);
        switch_city_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    city_required = true;
                } else {
                    city_required = false;
                }
            }
        });
        switch_city_display = rootView.findViewById(R.id.switch_city_display);
        switch_city_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    city_display = true;
                } else {
                    city_display = false;
                }
            }
        });
        city_et = rootView.findViewById(R.id.city_et);

        switch_postcode_required = rootView.findViewById(R.id.switch_postcode_required);
        switch_postcode_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    postcode_required = true;
                } else {
                    postcode_required = false;
                }
            }
        });
        switch_postcode_display = rootView.findViewById(R.id.switch_postcode_display);
        switch_postcode_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    postcode_display = true;
                } else {
                    postcode_display = false;
                }
            }
        });
        postcode_et = rootView.findViewById(R.id.postcode_et);

        switch_event_date_required = rootView.findViewById(R.id.switch_event_date_required);
        switch_event_date_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    event_date_required = true;
                } else {
                    event_date_required = false;
                }
            }
        });
        switch_event_date_display = rootView.findViewById(R.id.switch_event_date_display);
        switch_event_date_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    event_date_display = true;
                } else {
                    event_date_display = false;
                }
            }
        });
        event_date_et = rootView.findViewById(R.id.event_date_et);

        switch_event_time_required = rootView.findViewById(R.id.switch_event_time_required);
        switch_event_time_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    event_time_required = true;
                } else {
                    event_time_required = false;
                }
            }
        });
        switch_event_time_display = rootView.findViewById(R.id.switch_event_time_display);
        switch_event_time_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    event_time_display = true;
                } else {
                    event_time_display = false;
                }
            }
        });
        event_time_et = rootView.findViewById(R.id.event_time_et);


        switch_event_type_required = rootView.findViewById(R.id.switch_event_type_required);
        switch_event_type_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    event_type_required = true;
                } else {
                    event_type_required = false;
                }
            }
        });
        switch_event_type_display = rootView.findViewById(R.id.switch_event_type_display);
        switch_event_type_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    event_type_display = true;
                } else {
                    event_type_display = false;
                }
            }
        });
        event_type_et = rootView.findViewById(R.id.event_type_et);

        switch_event_name_required = rootView.findViewById(R.id.switch_event_name_required);
        switch_event_name_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    event_name_required = true;
                } else {
                    event_name_required = false;
                }
            }
        });
        switch_event_name_display = rootView.findViewById(R.id.switch_event_name_display);
        switch_event_name_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    event_name_display = true;
                } else {
                    event_name_display = false;
                }
            }
        });
        event_name_et = rootView.findViewById(R.id.event_name_et);

        switch_max_budget_required = rootView.findViewById(R.id.switch_max_budget_required);
        switch_max_budget_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    max_budget_required = true;
                } else {
                    max_budget_required = false;
                }
            }
        });
        switch_max_budget_display = rootView.findViewById(R.id.switch_max_budget_display);
        switch_max_budget_display.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    max_budget_display = true;
                } else {
                    max_budget_display = false;
                }
            }
        });
        max_budget_et = rootView.findViewById(R.id.max_budget_et);

        save_tv = rootView.findViewById(R.id.save_tv);
        save_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String submitText = submit_text_et.getText().toString().trim();
                String thankyouMsg = thankyou_message_et.getText().toString().trim();
                String book_now_text = book_now_et.getText().toString().trim();


                String contactPermission = contact_permission_et.getText().toString().trim();
                String firstName = firstName_et.getText().toString().trim();
                String lastName = last_name_et.getText().toString().trim();
                String companyName = company_name_et.getText().toString().trim();
                String email = email_et.getText().toString().trim();
                String telephone = telephone_et.getText().toString().trim();
                String mobile = mobile_et.getText().toString().trim();
                String address = address_et.getText().toString().trim();
                String city = city_et.getText().toString().trim();
                String postcode = postcode_et.getText().toString().trim();
                String date = event_date_et.getText().toString().trim();
                String time = event_time_et.getText().toString().trim();
                String type = event_type_et.getText().toString().trim();
                String name = event_name_et.getText().toString().trim();
                String budget = max_budget_et.getText().toString().trim();
                String notes = additional_notes_et_et.getText().toString().trim();

                if (allTextColor != 0) {
                    GlobalClass.putPref("allTextColor", String.valueOf(allTextColor), getActivity());

                }
                if (formBackgroundColor != 0) {
                    GlobalClass.putPref("formBackgroundColor", String.valueOf(formBackgroundColor), getActivity());

                }
                if (submitTextColor != 0) {
                    GlobalClass.putPref("submitTextColor", String.valueOf(submitTextColor), getActivity());

                }

                if (submitBackgroundColor != 0) {
                    GlobalClass.putPref("submitBackgroundColor", String.valueOf(submitBackgroundColor), getActivity());

                }


                if (!submitText.equalsIgnoreCase("")) {
                    GlobalClass.putPref("submit_text_words", submitText, getActivity());

                }

                if (!submitText.equalsIgnoreCase("")) {
                    GlobalClass.putPref("thankyou_text", thankyouMsg, getActivity());

                }
                if (!book_now_text.equalsIgnoreCase("")) {
                    GlobalClass.putPref("book_now_text", book_now_text, getActivity());

                }

                if (!contactPermission.equalsIgnoreCase("")) {

                    GlobalClass.putPref("contact_permission_text", contactPermission, getActivity());

                }
                if (!firstName.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_first_name", firstName, getActivity());

                }
                if (!lastName.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_last_name", lastName, getActivity());
                }
                if (!companyName.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_company", companyName, getActivity());
                }
                if (!email.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_email", email, getActivity());
                }
                if (!telephone.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_telephone", telephone, getActivity());
                }
                if (!mobile.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_mobile", mobile, getActivity());
                }
                if (!address.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_address", address, getActivity());
                }
                if (!city.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_city", city, getActivity());
                }
                if (!postcode.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_postcode", postcode, getActivity());
                }
                if (!date.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_date", date, getActivity());
                }
                if (!time.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_time", time, getActivity());
                }
                if (!type.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_type", type, getActivity());
                }
                if (!name.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_event_name", name, getActivity());
                }
                if (!budget.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_event_budget", budget, getActivity());
                }


                if (!notes.equalsIgnoreCase("")) {
                    GlobalClass.putPref("e_additional_notes", notes, getActivity());
                }

                if (!encodedImageString.equalsIgnoreCase("")) {
                    GlobalClass.putPref("encodedImageString", encodedImageString, getActivity());
                }


                GlobalClass.putPref("additional_notes_display", String.valueOf(additional_notes_display), getActivity());
                GlobalClass.putPref("additional_notes_required", String.valueOf(additional_notes_required), getActivity());


                GlobalClass.putPref("permission_display", String.valueOf(permission_display), getActivity());
                GlobalClass.putPref("permission_required", String.valueOf(permission_required), getActivity());


                GlobalClass.putPref("book_now", String.valueOf(book_now), getActivity());

                GlobalClass.putPref("firstname_required", String.valueOf(firstname_required), getActivity());
                GlobalClass.putPref("firstname_display", String.valueOf(firstname_display), getActivity());


                GlobalClass.putPref("last_name_required", String.valueOf(last_name_required), getActivity());
                GlobalClass.putPref("last_name_display", String.valueOf(last_name_display), getActivity());

                GlobalClass.putPref("company_required", String.valueOf(company_required), getActivity());
                GlobalClass.putPref("company_display", String.valueOf(last_name_display), getActivity());

                GlobalClass.putPref("email_required", String.valueOf(email_required), getActivity());
                GlobalClass.putPref("email_display", String.valueOf(email_display), getActivity());

                GlobalClass.putPref("telephone_required", String.valueOf(telephone_required), getActivity());
                GlobalClass.putPref("telephone_display", String.valueOf(telephone_display), getActivity());

                GlobalClass.putPref("mobile_required", String.valueOf(mobile_required), getActivity());
                GlobalClass.putPref("mobile_display", String.valueOf(mobile_display), getActivity());

                GlobalClass.putPref("address_required", String.valueOf(address_required), getActivity());
                GlobalClass.putPref("address_display", String.valueOf(address_display), getActivity());

                GlobalClass.putPref("city_required", String.valueOf(city_required), getActivity());
                GlobalClass.putPref("city_display", String.valueOf(city_display), getActivity());

                GlobalClass.putPref("postcode_required", String.valueOf(postcode_required), getActivity());
                GlobalClass.putPref("postcode_display", String.valueOf(postcode_display), getActivity());

                GlobalClass.putPref("event_date_required", String.valueOf(event_date_required), getActivity());
                GlobalClass.putPref("event_date_display", String.valueOf(event_date_display), getActivity());

                GlobalClass.putPref("event_time_required", String.valueOf(event_time_required), getActivity());
                GlobalClass.putPref("event_time_display", String.valueOf(event_time_display), getActivity());

                GlobalClass.putPref("event_type_required", String.valueOf(event_type_required), getActivity());
                GlobalClass.putPref("event_type_display", String.valueOf(event_type_display), getActivity());


                GlobalClass.putPref("event_name_required", String.valueOf(event_name_required), getActivity());
                GlobalClass.putPref("event_name_display", String.valueOf(event_name_display), getActivity());


                GlobalClass.putPref("max_budget_required", String.valueOf(max_budget_required), getActivity());
                GlobalClass.putPref("max_budget_display", String.valueOf(max_budget_display), getActivity());


                GlobalClass.putPref("leadEdited", "true", getActivity());
                GlobalClass.putPref("fontName", fontName, getActivity());


                FragmentManager manager = getFragmentManager();
                manager.popBackStackImmediate();
                Toast.makeText(activity, "Lead form saved successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        valueSetterEditTexts();


        form_text_color_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPickerText();
            }
        });
        form_background_color_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorBackgroundForm();
            }
        });

        submit_text_color_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submmitTextColor();
            }
        });

        submit_background_color_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submmitBackgroundColor();
            }
        });


        font_text_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fontListDialouge();
            }
        });


        logo_select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, 1213);


            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {


            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute(filePath);


        }


    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {



        @Override
        protected void onPreExecute() {

            GlobalClass.showLoading(activity);

        }

        @Override
        protected String doInBackground(String... params) {

            String filePath = params[0];


            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            encodedImageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.d("imageBitmap", selectedImage + "");

            return encodedImageString;
        }


        @Override
        protected void onPostExecute(String result) {


            GlobalClass.dismissLoading();
        }


    }

    private void fontListDialouge() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
//        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select Font");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Open Sans Regular");
        arrayAdapter.add("Open Sans Bold");


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fontName = arrayAdapter.getItem(which);
                Log.d("fontName", "onClick: " + fontName);
                font_text_tv.setText(fontName);

            }
        });
        builderSingle.show();


    }


    private void colorPickerText() {


        ColorPickerDialogBuilder
                .with(activity)
                .setTitle("Choose color")

                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                        changeBackgroundColor(selectedColor);

                        Log.d("selectedColor", selectedColor + "");

                        allTextColor = selectedColor;
                        form_text_color_tv.setText(String.valueOf(selectedColor));


                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();

    }

    private void colorBackgroundForm() {


        ColorPickerDialogBuilder
                .with(activity)
                .setTitle("Choose color")

                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                        changeBackgroundColor(selectedColor);

                        Log.d("selectedColor", selectedColor + "");

                        formBackgroundColor = selectedColor;

                        form_background_color_tv.setText(String.valueOf(selectedColor));

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();

    }

    private void submmitTextColor() {


        ColorPickerDialogBuilder
                .with(activity)
                .setTitle("Choose color")

                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                        changeBackgroundColor(selectedColor);

                        Log.d("selectedColor", selectedColor + "");

                        submitTextColor = selectedColor;
                        submit_text_color_tv.setText(String.valueOf(selectedColor));

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();

    }

    private void submmitBackgroundColor() {


        ColorPickerDialogBuilder
                .with(activity)
                .setTitle("Choose color")

                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                        changeBackgroundColor(selectedColor);

                        Log.d("selectedColor", selectedColor + "");
                        submitBackgroundColor = selectedColor;
                        submit_background_color_tv.setText(String.valueOf(selectedColor));

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();

    }


    private void valueSetterEditTexts() {


        String leadEdited = GlobalClass.getPref("leadEdited", getActivity());
        if (leadEdited.equalsIgnoreCase("true")) {


//            company_name_et.setText(e_company);
//            email_et.setText(e_email);
//            telephone_et.setText(e_telephone);
//            mobile_et.setText(e_mobile);
//            address_et.setText(e_address);
//            city_et.setText(e_city);
//            postcode_et.setText(e_postcode);
//            event_date_et.setText(e_date);
//            event_time_et.setText(e_time);
//            event_type_et.setText(e_type);
//            event_name_et.setText(e_event_name);
//            max_budget_et.setText(e_event_budget);
//            additional_notes_et_et.setText(e_additional_notes);


            String allTextColor = GlobalClass.getPref("allTextColor", getActivity());
            if (!allTextColor.equalsIgnoreCase("")) {

                form_text_color_tv.setText(allTextColor);

            }

            String formBackgroundColor = GlobalClass.getPref("formBackgroundColor", getActivity());
            if (!formBackgroundColor.equalsIgnoreCase("")) {
                form_background_color_tv.setText(formBackgroundColor);
            }

            String submitText = GlobalClass.getPrefDefault("submit_text_words", getActivity().getResources().getString(R.string.submit), getActivity());

            submit_text_et.setText(submitText);


            String submitTextColor = GlobalClass.getPref("submitTextColor", getActivity());
            if (!submitTextColor.equalsIgnoreCase("")) {

                submit_text_color_tv.setText(submitTextColor);
            }

            String submitBackgroundColor = GlobalClass.getPref("submitBackgroundColor", getActivity());
            if (!submitBackgroundColor.equalsIgnoreCase("")) {

                submit_background_color_tv.setText(submitBackgroundColor);
            }


        }


        String e_first_name = GlobalClass.getPrefDefault("e_first_name", getActivity().getResources().getString(R.string.first_name_with_star), getActivity());
        String e_last_name = GlobalClass.getPrefDefault("e_last_name", getActivity().getResources().getString(R.string.last_name_with_star), getActivity());
        String e_company = GlobalClass.getPrefDefault("e_company", getActivity().getResources().getString(R.string.company_name_with_star), getActivity());
        String e_email = GlobalClass.getPrefDefault("e_email", getActivity().getResources().getString(R.string.email_with_star), getActivity());
        String e_telephone = GlobalClass.getPrefDefault("e_telephone", getActivity().getResources().getString(R.string.telephone_with_star), getActivity());
        String e_mobile = GlobalClass.getPrefDefault("e_mobile", getActivity().getResources().getString(R.string.mobile_phone_with_star), getActivity());
        String e_address = GlobalClass.getPrefDefault("e_address", getActivity().getResources().getString(R.string.address_with_star), getActivity());
        String e_city = GlobalClass.getPrefDefault("e_city", getActivity().getResources().getString(R.string.city_with_star), getActivity());
        String e_postcode = GlobalClass.getPrefDefault("e_postcode", getActivity().getResources().getString(R.string.postcode_with_star), getActivity());
        String e_date = GlobalClass.getPrefDefault("e_date", getActivity().getResources().getString(R.string.event_date_with_star), getActivity());
        String e_time = GlobalClass.getPrefDefault("e_time", getActivity().getResources().getString(R.string.event_time_with_star), getActivity());
        String e_type = GlobalClass.getPrefDefault("e_type", getActivity().getResources().getString(R.string.event_type_with_star), getActivity());
        String e_event_name = GlobalClass.getPrefDefault("e_event_name", getActivity().getResources().getString(R.string.event_name_with_star), getActivity());
        String e_event_budget = GlobalClass.getPrefDefault("e_event_budget", getActivity().getResources().getString(R.string.max_budget_with_star), getActivity());
        String e_additional_notes = GlobalClass.getPrefDefault("e_additional_notes", getActivity().getResources().getString(R.string.additional_notes_with_star), getActivity());
        String contactPermission = GlobalClass.getPrefDefault("contact_permission_text", getActivity().getResources().getString(R.string.contact_permission_with_star), getActivity());


        String bookNow_text = GlobalClass.getPrefDefault("book_now_text", getActivity().getResources().getString(R.string.book_now), getActivity());
        String thankyouTXT = GlobalClass.getPrefDefault("thankyou_text", getActivity().getResources().getString(R.string.thank_you_default_text), getActivity());


        book_now_et.setText(bookNow_text);
        thankyou_message_et.setText(thankyouTXT);


        if (contactPermission.equalsIgnoreCase(getActivity().getResources().getString(R.string.contact_permission_with_star))) {
            contact_permission_et.setHint(getActivity().getResources().getString(R.string.contact_permission));
        } else {
            contact_permission_et.setText(contactPermission);
        }


        if (e_additional_notes.equalsIgnoreCase(getActivity().getResources().getString(R.string.additional_notes_with_star))) {
            additional_notes_et_et.setHint(getActivity().getResources().getString(R.string.additional_notes));
        } else {
            additional_notes_et_et.setText(e_additional_notes);
        }

        if (e_event_budget.equalsIgnoreCase(getActivity().getResources().getString(R.string.max_budget_with_star))) {
            max_budget_et.setHint(getActivity().getResources().getString(R.string.max_budget));
        } else {
            max_budget_et.setText(e_event_budget);
        }


        if (e_event_name.equalsIgnoreCase(getActivity().getResources().getString(R.string.event_name_with_star))) {
            event_name_et.setHint(getActivity().getResources().getString(R.string.event_name));
        } else {
            event_name_et.setText(e_event_name);
        }


        if (e_type.equalsIgnoreCase(getActivity().getResources().getString(R.string.event_type_with_star))) {
            event_type_et.setHint(getActivity().getResources().getString(R.string.event_type));
        } else {
            event_type_et.setText(e_type);
        }


        if (e_time.equalsIgnoreCase(getActivity().getResources().getString(R.string.event_time_with_star))) {
            event_time_et.setHint(getActivity().getResources().getString(R.string.event_time));
        } else {
            event_time_et.setText(e_time);
        }


        if (e_date.equalsIgnoreCase(getActivity().getResources().getString(R.string.event_date_with_star))) {
            event_date_et.setHint(getActivity().getResources().getString(R.string.postcode));
        } else {
            event_date_et.setText(e_date);
        }


        if (e_postcode.equalsIgnoreCase(getActivity().getResources().getString(R.string.postcode_with_star))) {
            postcode_et.setHint(getActivity().getResources().getString(R.string.postcode));

        } else {
            postcode_et.setText(e_postcode);
        }

        if (e_city.equalsIgnoreCase(getActivity().getResources().getString(R.string.city_with_star))) {
            city_et.setHint(getActivity().getResources().getString(R.string.city));

        } else {
            city_et.setText(e_city);
        }

        if (e_address.equalsIgnoreCase(getActivity().getResources().getString(R.string.address_with_star))) {
            address_et.setHint(getActivity().getResources().getString(R.string.address));

        } else {
            address_et.setText(e_address);
        }


        if (e_mobile.equalsIgnoreCase(getActivity().getResources().getString(R.string.mobile_phone_with_star))) {
            mobile_et.setHint(getActivity().getResources().getString(R.string.mobile_phone));

        } else {
            mobile_et.setText(e_mobile);
        }

        if (e_telephone.equalsIgnoreCase(getActivity().getResources().getString(R.string.telephone_with_star))) {
            telephone_et.setHint(getActivity().getResources().getString(R.string.telephone));
        } else {
            telephone_et.setText(e_telephone);

        }


        if (e_email.equalsIgnoreCase(getActivity().getResources().getString(R.string.email_with_star))) {
            email_et.setHint(getActivity().getResources().getString(R.string.email));

        } else {
            email_et.setText(e_email);
        }

        if (e_company.equalsIgnoreCase(getActivity().getResources().getString(R.string.company_name_with_star))) {
            company_name_et.setHint(getActivity().getResources().getString(R.string.company_name));

        } else {
            company_name_et.setText(e_company);
        }


        if (e_last_name.equalsIgnoreCase(getActivity().getResources().getString(R.string.last_name_with_star))) {
            last_name_et.setHint(getActivity().getResources().getString(R.string.last_name));

        } else {
            last_name_et.setText(e_last_name);
        }

        if (e_first_name.equalsIgnoreCase(getActivity().getResources().getString(R.string.first_name_with_star))) {
            firstName_et.setHint(getActivity().getResources().getString(R.string.first_name));

        } else {
            firstName_et.setText(e_first_name);
        }


        String additional_notes_display = GlobalClass.getPref("additional_notes_display", getActivity());
        if (additional_notes_display.equalsIgnoreCase("true") || additional_notes_display.equalsIgnoreCase("")) {
            switch_additional_notes_display.setChecked(true);
        } else {
            switch_additional_notes_display.setChecked(false);
        }


        String additional_notes_required = GlobalClass.getPref("additional_notes_required", getActivity());
        if (additional_notes_required.equalsIgnoreCase("true") || additional_notes_required.equalsIgnoreCase("")) {
            switch_additional_notes_required.setChecked(true);
        } else {
            switch_additional_notes_required.setChecked(false);
        }


        String permission_display = GlobalClass.getPref("permission_display", getActivity());
        if (permission_display.equalsIgnoreCase("true") || permission_display.equalsIgnoreCase("")) {
            switch_permission_display.setChecked(true);
        } else {
            switch_permission_display.setChecked(false);
        }

        String permission_required = GlobalClass.getPref("permission_required", getActivity());
        if (permission_required.equalsIgnoreCase("true") || permission_required.equalsIgnoreCase("")) {
            switch_permission_required.setChecked(true);
        } else {
            switch_permission_required.setChecked(false);
        }

        String book_now = GlobalClass.getPref("book_now", getActivity());
        if (book_now.equalsIgnoreCase("true") || book_now.equalsIgnoreCase("")) {
            switch_book_now.setChecked(true);
        } else {
            switch_book_now.setChecked(false);
        }

        String firstname_required = GlobalClass.getPref("firstname_required", getActivity());
        if (firstname_required.equalsIgnoreCase("true") || firstname_required.equalsIgnoreCase("")) {
            switch_firstname_required.setChecked(true);
        } else {
            switch_firstname_required.setChecked(false);
        }

        String firstname_display = GlobalClass.getPref("firstname_display", getActivity());
        if (firstname_display.equalsIgnoreCase("true") || firstname_display.equalsIgnoreCase("")) {
            switch_firstname_display.setChecked(true);
        } else {
            switch_firstname_display.setChecked(false);
        }

        String last_name_required = GlobalClass.getPref("last_name_required", getActivity());
        if (last_name_required.equalsIgnoreCase("true") || last_name_required.equalsIgnoreCase("")) {
            switch_last_name_required.setChecked(true);
        } else {
            switch_last_name_required.setChecked(false);
        }

        String last_name_display = GlobalClass.getPref("last_name_display", getActivity());
        if (last_name_display.equalsIgnoreCase("true") || last_name_display.equalsIgnoreCase("")) {
            switch_last_name_display.setChecked(true);
        } else {
            switch_last_name_display.setChecked(false);
        }

        String company_required = GlobalClass.getPref("company_required", getActivity());
        if (company_required.equalsIgnoreCase("true") || company_required.equalsIgnoreCase("")) {
            switch_company_required.setChecked(true);
        } else {
            switch_company_required.setChecked(false);
        }

        String company_display = GlobalClass.getPref("company_display", getActivity());
        if (company_display.equalsIgnoreCase("true") || company_display.equalsIgnoreCase("")) {
            switch_company_display.setChecked(true);
        } else {
            switch_company_display.setChecked(false);
        }

        String email_required = GlobalClass.getPref("email_required", getActivity());
        if (email_required.equalsIgnoreCase("true") || email_required.equalsIgnoreCase("")) {
            switch_email_required.setChecked(true);
        } else {
            switch_email_required.setChecked(false);
        }

        String email_display = GlobalClass.getPref("email_display", getActivity());
        if (email_display.equalsIgnoreCase("true") || email_display.equalsIgnoreCase("")) {
            switch_email_display.setChecked(true);
        } else {
            switch_email_display.setChecked(false);
        }

        String telephone_required = GlobalClass.getPref("telephone_required", getActivity());
        if (telephone_required.equalsIgnoreCase("true") || telephone_required.equalsIgnoreCase("")) {
            switch_telephone_required.setChecked(true);
        } else {
            switch_telephone_required.setChecked(false);
        }

        String telephone_display = GlobalClass.getPref("telephone_display", getActivity());
        if (telephone_display.equalsIgnoreCase("true") || telephone_display.equalsIgnoreCase("")) {
            switch_telephone_display.setChecked(true);
        } else {
            switch_telephone_display.setChecked(false);
        }

        String mobile_required = GlobalClass.getPref("mobile_required", getActivity());
        if (mobile_required.equalsIgnoreCase("true") || mobile_required.equalsIgnoreCase("")) {
            switch_mobile_required.setChecked(true);
        } else {
            switch_mobile_required.setChecked(false);
        }

        String mobile_display = GlobalClass.getPref("mobile_display", getActivity());
        if (mobile_display.equalsIgnoreCase("true") || mobile_display.equalsIgnoreCase("")) {
            switch_mobile_display.setChecked(true);
        } else {
            switch_mobile_display.setChecked(false);
        }


        String address_required = GlobalClass.getPref("address_required", getActivity());
        if (address_required.equalsIgnoreCase("true") || address_required.equalsIgnoreCase("")) {
            switch_address_required.setChecked(true);
        } else {
            switch_address_required.setChecked(false);
        }

        String address_display = GlobalClass.getPref("address_display", getActivity());
        if (address_display.equalsIgnoreCase("true") || address_display.equalsIgnoreCase("")) {
            switch_address_display.setChecked(true);
        } else {
            switch_address_display.setChecked(false);
        }

        String city_required = GlobalClass.getPref("city_required", getActivity());
        if (city_required.equalsIgnoreCase("true") || city_required.equalsIgnoreCase("")) {
            switch_city_required.setChecked(true);
        } else {

            switch_city_required.setChecked(false);

        }

        String city_display = GlobalClass.getPref("city_display", getActivity());
        if (city_display.equalsIgnoreCase("true") || city_display.equalsIgnoreCase("")) {
            switch_city_display.setChecked(true);
        } else {
            switch_city_display.setChecked(false);
        }

        String postcode_required = GlobalClass.getPref("postcode_required", getActivity());
        if (postcode_required.equalsIgnoreCase("true") || postcode_required.equalsIgnoreCase("")) {
            switch_postcode_required.setChecked(true);
        } else {
            switch_postcode_required.setChecked(false);
        }

        String postcode_display = GlobalClass.getPref("postcode_display", getActivity());
        if (postcode_display.equalsIgnoreCase("true") || postcode_display.equalsIgnoreCase("")) {
            switch_postcode_display.setChecked(true);
        } else {
            switch_postcode_display.setChecked(false);
        }

        String event_date_required = GlobalClass.getPref("event_date_required", getActivity());
        if (event_date_required.equalsIgnoreCase("true") || event_date_required.equalsIgnoreCase("")) {
            switch_event_date_required.setChecked(true);
        } else {
            switch_event_date_required.setChecked(false);
        }

        String event_date_display = GlobalClass.getPref("event_date_display", getActivity());
        if (event_date_display.equalsIgnoreCase("true") || event_date_display.equalsIgnoreCase("")) {
            switch_event_date_display.setChecked(true);
        } else {
            switch_event_date_display.setChecked(false);
        }

        String event_time_required = GlobalClass.getPref("event_time_required", getActivity());
        if (event_time_required.equalsIgnoreCase("true") || event_time_required.equalsIgnoreCase("")) {
            switch_event_time_required.setChecked(true);
        } else {
            switch_event_time_required.setChecked(false);
        }

        String event_time_display = GlobalClass.getPref("event_time_display", getActivity());
        if (event_time_display.equalsIgnoreCase("true") || event_time_display.equalsIgnoreCase("")) {
            switch_event_time_display.setChecked(true);
        } else {
            switch_event_time_display.setChecked(false);
        }

        String event_type_required = GlobalClass.getPref("event_type_required", getActivity());
        if (event_type_required.equalsIgnoreCase("true") || event_type_required.equalsIgnoreCase("")) {
            switch_event_type_required.setChecked(true);
        } else {
            switch_event_type_required.setChecked(false);
        }


        String event_type_display = GlobalClass.getPref("event_type_display", getActivity());
        if (event_type_display.equalsIgnoreCase("true") || event_type_display.equalsIgnoreCase("")) {
            switch_event_type_display.setChecked(true);
        } else {
            switch_event_type_display.setChecked(false);
        }

        String event_name_required = GlobalClass.getPref("event_name_required", getActivity());
        if (event_name_required.equalsIgnoreCase("true") || event_name_required.equalsIgnoreCase("")) {
            switch_event_name_required.setChecked(true);
        } else {
            switch_event_name_required.setChecked(false);
        }

        String event_name_display = GlobalClass.getPref("event_name_display", getActivity());
        if (event_name_display.equalsIgnoreCase("true") || event_name_display.equalsIgnoreCase("")) {
            switch_event_name_display.setChecked(true);
        } else {
            switch_event_name_display.setChecked(false);
        }

        String max_budget_required = GlobalClass.getPref("max_budget_required", getActivity());
        if (max_budget_required.equalsIgnoreCase("true") || max_budget_required.equalsIgnoreCase("")) {
            switch_max_budget_required.setChecked(true);
        } else {
            switch_max_budget_required.setChecked(false);
        }

        String max_budget_display = GlobalClass.getPref("max_budget_display", getActivity());
        if (max_budget_display.equalsIgnoreCase("true") || max_budget_display.equalsIgnoreCase("")) {
            switch_max_budget_display.setChecked(true);
        } else {
            switch_max_budget_display.setChecked(false);
        }


        String bookNowSwitch = GlobalClass.getPref("book_now", getActivity());
        if (bookNowSwitch.equalsIgnoreCase("true") || bookNowSwitch.equalsIgnoreCase("")) {
            switch_book_now.setChecked(true);
        } else {
            switch_book_now.setChecked(false);
        }

        String fontName = GlobalClass.getPrefDefault("fontName", "Open Sans Regular", getActivity());
        font_text_tv.setText(fontName);


    }
}
