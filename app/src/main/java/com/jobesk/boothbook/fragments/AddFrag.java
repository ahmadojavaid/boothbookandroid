package com.jobesk.boothbook.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.R;

public class AddFrag extends Fragment {


    private View rootView;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.frag_add, container, false);
        activity = (MainActivity) rootView.getContext();


        Log.d("fragVisible", "Add Frag");


        return rootView;
    }
}
