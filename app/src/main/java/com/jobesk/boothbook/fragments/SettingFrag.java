package com.jobesk.boothbook.fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jobesk.boothbook.activities.login.LoginActivity;
import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.database.DatabaseHelper;
import com.jobesk.boothbook.utils.GlobalClass;

import static android.content.Context.ACTIVITY_SERVICE;

public class SettingFrag extends Fragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout editLeadFormCon, logout_container;
    private Activity activity;
    private RelativeLayout helpContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_setting, container, false);
        activity = (MainActivity) rootView.getContext();
        Log.d("fragVisible", "Setting Frag");
        logout_container = rootView.findViewById(R.id.logout_container);
        logout_container.setOnClickListener(this);
        editLeadFormCon = rootView.findViewById(R.id.editLeadFormCon);
        editLeadFormCon.setOnClickListener(this);
        helpContainer = rootView.findViewById(R.id.helpContainer);
        helpContainer.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout_container:
                alertForExit();
                break;
            case R.id.editLeadFormCon:
                openFrag();
                break;
            case R.id.helpContainer:
                openFragHelp();
                break;
        }
    }

    private void alertForExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                GlobalClass.clearPref(activity);


                DatabaseHelper databaseHelper = new DatabaseHelper(activity);
                databaseHelper.deleteDatabase();


                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void openFrag() {
        FragEditLeadForm eventfrag = new FragEditLeadForm();
//        Bundle args = new Bundle();
//        args.putString("id", id);
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.frame_setting, eventfrag);
//        eventfrag.setArguments(args);
        FT.commit();
    }

    private void openFragHelp() {
        HelpFrag eventfrag = new HelpFrag();
//        Bundle args = new Bundle();
//        args.putString("id", id);
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.frame_setting, eventfrag);
//        eventfrag.setArguments(args);
        FT.commit();
    }
}
