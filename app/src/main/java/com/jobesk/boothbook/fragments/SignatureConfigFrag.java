package com.jobesk.boothbook.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.GlobalClass;

public class SignatureConfigFrag extends Fragment {


    private View rootView;
    private Activity activity;
    private ImageView backimg;
    private TextView save_tv;

    private EditText signature_title_et, signer_name_title_et,
            signer_name_place_holder_et, signer_title_et, signer_placeholder_et, submit_label_et;
    private SwitchCompat switch_display_signer_name, switch_signer_title;

    private boolean nameField = true, titleField = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.frag_config_signature, container, false);
        activity = (MainActivity) rootView.getContext();


        switch_display_signer_name = rootView.findViewById(R.id.switch_display_signer_name);
        switch_signer_title = rootView.findViewById(R.id.switch_signer_title);


        signature_title_et = rootView.findViewById(R.id.signature_title_et);
        signer_name_title_et = rootView.findViewById(R.id.signer_name_title_et);

        signer_name_place_holder_et = rootView.findViewById(R.id.signer_name_place_holder_et);

        signer_title_et = rootView.findViewById(R.id.signer_title_et);
        signer_placeholder_et = rootView.findViewById(R.id.signer_placeholder_et);
        submit_label_et = rootView.findViewById(R.id.submit_label_et);

        save_tv = rootView.findViewById(R.id.save_tv);


        backimg = rootView.findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager manager = getFragmentManager();
                manager.popBackStackImmediate();

            }
        });


        save_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String signature_title = signature_title_et.getText().toString().trim();
                String signer_name_title = signer_name_title_et.getText().toString().trim();
                String signer_name_placeholder = signer_name_place_holder_et.getText().toString().trim();
                String signer_title = signer_title_et.getText().toString().trim();
                String signer_placeholder = signer_placeholder_et.getText().toString().trim();
                String submit_label = submit_label_et.getText().toString().trim();


                if (!signature_title.equalsIgnoreCase("")) {
                    GlobalClass.putPref("signature_title", signature_title, getActivity());
                }

                if (!signer_name_title.equalsIgnoreCase("")) {

                    GlobalClass.putPref("signer_name_title", signer_name_title, getActivity());

                }

                if (!signer_name_placeholder.equalsIgnoreCase("")) {

                    GlobalClass.putPref("signer_name_placeholder", signer_name_placeholder, getActivity());

                }


                if (!signer_title.equalsIgnoreCase("")) {

                    GlobalClass.putPref("signer_title", signer_title, getActivity());

                }

                if (!signer_placeholder.equalsIgnoreCase("")) {

                    GlobalClass.putPref("signer_placeholder", signer_placeholder, getActivity());

                }

                if (!submit_label.equalsIgnoreCase("")) {

                    GlobalClass.putPref("submit_label", submit_label, getActivity());

                }

                GlobalClass.putPref("check_signer_name_field", String.valueOf(nameField), getActivity());
                GlobalClass.putPref("check_signer_title_field", String.valueOf(titleField), getActivity());


                Intent intent = new Intent("alterValuesSignature");
                intent.putExtra("extra", "alterValuesSignature");
                activity.sendBroadcast(intent);

                backimg.callOnClick();


            }
        });



        switch_display_signer_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                nameField = b;


            }
        });
        switch_signer_title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                titleField = b;


            }
        });

        valueSetters();
        return rootView;
    }


    private void valueSetters() {


        signature_title_et.setHint(GlobalClass.getPrefDefault("signature_title", "Signature", getActivity()));
        signer_name_title_et.setHint(GlobalClass.getPrefDefault("signer_name_title", "Signer Name", getActivity()));
        signer_name_place_holder_et.setHint(GlobalClass.getPrefDefault("signer_name_placeholder", "JOE BLOGGS", getActivity()));
        signer_title_et.setHint(GlobalClass.getPrefDefault("signer_title", "Signer Title", getActivity()));
        signer_placeholder_et.setHint(GlobalClass.getPrefDefault("signer_placeholder", "Mr", getActivity()));


        submit_label_et.setHint(GlobalClass.getPrefDefault("submit_label", "Submit", getActivity()));


        String check_signer_name_field = GlobalClass.getPrefDefault("check_signer_name_field", "true", getActivity());
        String check_signer_title_field = GlobalClass.getPrefDefault("check_signer_title_field", "true", getActivity());


        if (check_signer_name_field.equalsIgnoreCase("true")) {
            switch_display_signer_name.setChecked(true);
        } else {
            switch_display_signer_name.setChecked(false);
        }


        if (check_signer_title_field.equalsIgnoreCase("true")) {

            switch_signer_title.setChecked(true);
        } else {
            switch_signer_title.setChecked(false);
        }


    }

}
