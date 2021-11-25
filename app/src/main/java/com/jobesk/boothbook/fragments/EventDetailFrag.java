package com.jobesk.boothbook.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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


import com.jobesk.boothbook.database.DBModelHome;
import com.jobesk.boothbook.database.DatabaseHelper;
import com.jobesk.boothbook.R;

public class EventDetailFrag extends Fragment implements View.OnClickListener {
    private View rootView;
    private ImageView backimg, signature_img;
    private String id;
    private TextView time_Tv, venue_name_tv, location_tv, team_tv, packages_tv, msg_admin_tv, msg_client_tv, booking_layout_tv;
    private DBModelHome bookingmodel;
    private DatabaseHelper db;
    private TextView click_here_to_sign_tv;
    private TextView call_tv, email_tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_detail_event, container, false);

        db = new DatabaseHelper(getActivity());
        id = getArguments().getString("id");
        bookingmodel = db.getSingleBooking(Long.valueOf(id));
        backimg = rootView.findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getFragmentManager();
                manager.popBackStackImmediate();

            }
        });

        click_here_to_sign_tv = rootView.findViewById(R.id.click_here_to_sign_tv);
        click_here_to_sign_tv.setOnClickListener(this);
        time_Tv = rootView.findViewById(R.id.time_Tv);
        venue_name_tv = rootView.findViewById(R.id.venue_name_tv);
        location_tv = rootView.findViewById(R.id.location_tv);
        team_tv = rootView.findViewById(R.id.team_tv);
        packages_tv = rootView.findViewById(R.id.packages_tv);
        msg_admin_tv = rootView.findViewById(R.id.msg_admin_tv);
        msg_client_tv = rootView.findViewById(R.id.msg_client_tv);
        booking_layout_tv = rootView.findViewById(R.id.booking_layout_tv);
        signature_img = rootView.findViewById(R.id.signature_img);

        call_tv = rootView.findViewById(R.id.call_tv);
        email_tv = rootView.findViewById(R.id.email_tv);

        String eventDes = bookingmodel.getEvent_date_uk() + "," +
                bookingmodel.getEvent_time_start_formatted() + "-" + bookingmodel.getEvent_time_start_formatted() +
                " @" + bookingmodel.getVenue_name();

        time_Tv.setText(eventDes.trim() + "");
        if (bookingmodel.getEvent_name() != null) {
            venue_name_tv.setText(bookingmodel.getEvent_name().trim() + "");
        }
        if (bookingmodel.getVenue_address() != null) {
            location_tv.setText(bookingmodel.getVenue_address().trim() + "");
        }
        if (bookingmodel.getStaff_written() != null) {
            team_tv.setText(bookingmodel.getStaff_written().trim() + "");
        }
        if (bookingmodel.getPackages() != null) {
            packages_tv.setText(bookingmodel.getPackages().trim() + "");
        }
        if (bookingmodel.getNotes_admin() != null) {
            msg_admin_tv.setText(bookingmodel.getNotes_admin().trim() + "");
        }
        if (bookingmodel.getNotes_customer() != null) {
            msg_client_tv.setText(bookingmodel.getNotes_customer().trim() + "");
        }
        if (bookingmodel.getEvent_name() != null) {
            booking_layout_tv.setText(bookingmodel.getEvent_name().trim() + "");
        }
        if (bookingmodel.getPhone() == null) {
            call_tv.setVisibility(View.GONE);
        }
        if (bookingmodel.getEmail() == null) {
            email_tv.setVisibility(View.GONE);
        }
        call_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookingmodel.getPhone() != null) {
                    dailNumber(bookingmodel.getPhone());
                }
            }
        });
        email_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookingmodel.getEmail() != null) {
                    composeEmail(bookingmodel.getEmail());
                }
            }
        });
        String image = bookingmodel.getSignature_image();
        if (image.equalsIgnoreCase("")) {

        } else {
            signature_img.setVisibility(View.VISIBLE);
            click_here_to_sign_tv.setVisibility(View.GONE);
            image = "data:image/png;base64," + bookingmodel.getSignature_image();
            String base64Image = image.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            signature_img.setImageBitmap(decodedByte);
        }
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("updateSigatureImage"));
        return rootView;
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            doImageWork();
        }
    };
    private void doImageWork() {
        bookingmodel = db.getSingleBooking(Long.valueOf(id));
        String image = bookingmodel.getSignature_image();
        if (image.equalsIgnoreCase("")) {
        } else {
            signature_img.setVisibility(View.VISIBLE);
            click_here_to_sign_tv.setVisibility(View.GONE);
            image = "data:image/png;base64," + bookingmodel.getSignature_image();
            String base64Image = image.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            signature_img.setImageBitmap(decodedByte);
       }
    }
    private void composeEmail(String addresses) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", addresses, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
    private void dailNumber(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_here_to_sign_tv:
                openFragmentDetail();
                break;
        }
    }
    private void openFragmentDetail() {
        SignatureBookFrag eventfrag = new SignatureBookFrag();
        Bundle args = new Bundle();
        args.putString("id", id);
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.frame_detail, eventfrag);
        eventfrag.setArguments(args);
        FT.commit();
    }
}
