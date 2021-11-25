package com.jobesk.boothbook.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.R;

public class HelpFrag extends Fragment {


    private View rootView;
    private Activity activity;
    private ImageView backimg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.frag_help, container, false);

        activity = (MainActivity) rootView.getContext();


        backimg = rootView.findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getFragmentManager();
                manager.popBackStackImmediate();

            }
        });


        return rootView;
    }

}
