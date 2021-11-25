package com.jobesk.boothbook.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jobesk.boothbook.adapters.ContactAdapter;
import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.database.DBLeads;
import com.jobesk.boothbook.database.DatabaseHelper;
import com.jobesk.boothbook.models.CalenderModel;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.GlobalClass;
import com.jobesk.boothbook.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class LeadShowFrag extends Fragment implements View.OnClickListener {


    private View rootView;
    private ImageView search_icon;
    private LinearLayout search_container;
    private boolean showContainer = false;
    private RecyclerView recyclerView;
    private ContactAdapter mAdapter;
    private Activity activity;
    private ArrayList<CalenderModel> arrayList = new ArrayList<>();

    private CalenderModel calenderModel;
    private TextView apply_tv;
    private DatabaseHelper db;
    private List<DBLeads> arrayListLeads;
    private EditText search_et;
    private RelativeLayout mainContainer;
    private TextView text_display_all;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_contact, container, false);


        activity = (MainActivity) rootView.getContext();
        Log.d("fragVisible", "LeadShow Frag");

        db = new DatabaseHelper(activity);

        mainContainer = rootView.findViewById(R.id.mainContainer);
        mainContainer.setOnClickListener(this);

        search_icon = rootView.findViewById(R.id.search_icon);
        search_icon.setOnClickListener(this);


        search_et = rootView.findViewById(R.id.search_et);

        apply_tv = rootView.findViewById(R.id.apply_tv);
        apply_tv.setOnClickListener(this);


        search_container = rootView.findViewById(R.id.search_container);
        text_display_all = rootView.findViewById(R.id.text_display_all);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


        arrayListLeads = db.getAllLeads();

        if (arrayListLeads.size() > 0) {


            text_display_all.setText(activity.getResources().getString(R.string.displaying_all_leads));
            if (arrayList.size() > 0) {
                arrayList.clear();

            }
            for (int i = 0; i < arrayListLeads.size(); i++) {

                String email = arrayListLeads.get(i).getEmail();
                String firstName = arrayListLeads.get(i).getFirstName();
                String lastname = arrayListLeads.get(i).getLastname();
                String eventdate = arrayListLeads.get(i).getEventDate();
                int id = arrayListLeads.get(i).getId();

                String secondValue = firstName + " " + lastname + " " + eventdate;


                calenderModel = new CalenderModel();
                calenderModel.setId(String.valueOf(id));
                calenderModel.setName(email);
                calenderModel.setDescription(secondValue);
                arrayList.add(calenderModel);

            }

            populaterecyclerView();

        }else {

            text_display_all.setText(activity.getResources().getString(R.string.submit_lead_text));
        }

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.search_icon:

                if (showContainer == false) {

                    search_container.setVisibility(View.VISIBLE);
                    showContainer = true;

                } else {

                    search_container.setVisibility(View.GONE);
                    showContainer = false;
                }

                break;

            case R.id.apply_tv:


                String searchValue = search_et.getText().toString().trim();

                if (searchValue.equalsIgnoreCase("")) {

                    Toast.makeText(activity, "Please enter something to search", Toast.LENGTH_SHORT).show();

                    return;
                }


                GlobalClass.hideKeyboard(activity);
                search_et.clearFocus();
                openFragSearch(searchValue);


//                Intent intent = new Intent("open_frag_detail");
//                intent.putExtra("extra", "open_frag_search_lead");
//                activity.sendBroadcast(intent);


                break;


        }

    }


    private void openFragSearch(String keyword) {

        LeadSearchedFrag eventfrag = new LeadSearchedFrag();
        Bundle args = new Bundle();
        args.putString("keyword", keyword);
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.frame_leads, eventfrag);
        eventfrag.setArguments(args);
        FT.commit();

    }

    private void openFrag(String id) {

        LeadDetailFrag eventfrag = new LeadDetailFrag();
        Bundle args = new Bundle();
        args.putString("id", id);
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.frame_leads, eventfrag);
        eventfrag.setArguments(args);
        FT.commit();

    }


    private void populaterecyclerView() {


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new ContactAdapter(activity, arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        if (arrayList.size() > 0) {

                            String idOfObject = arrayList.get(position).getId();
                            openFrag(idOfObject);

                        }

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }
}
