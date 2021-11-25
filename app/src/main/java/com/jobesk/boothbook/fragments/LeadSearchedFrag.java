package com.jobesk.boothbook.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.jobesk.boothbook.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class LeadSearchedFrag extends Fragment implements View.OnClickListener {


    private View rootView;
    private ImageView search_icon;
    private LinearLayout search_container;
    private boolean showContainer = false;
    private RecyclerView recyclerView;
    private ContactAdapter mAdapter;
    private Activity activity;
    private ArrayList<CalenderModel> arrayListLocal = new ArrayList<>();
    private CalenderModel calenderModel;
    private ImageView back_icon;
    private String id;
    private DatabaseHelper db;
    private DBLeads leadsModel;
    private String keyword = "";
    private List<DBLeads> listOfLeads;

    private TextView count_leads_tv, search_keyword_show_tv;
    RelativeLayout mainContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_lead_searched, container, false);

        activity = (MainActivity) rootView.getContext();

        db = new DatabaseHelper(getActivity());
        keyword = getArguments().getString("keyword");


        count_leads_tv = rootView.findViewById(R.id.count_leads_tv);
        mainContainer = rootView.findViewById(R.id.mainContainer);
        mainContainer.setOnClickListener(this);


        search_keyword_show_tv = rootView.findViewById(R.id.search_keyword_show_tv);
        search_keyword_show_tv.setText(keyword);

        listOfLeads = db.getAllLeads();

        back_icon = rootView.findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getFragmentManager();
                manager.popBackStackImmediate();

            }
        });


        search_icon = rootView.findViewById(R.id.search_icon);
        search_icon.setOnClickListener(this);


        search_container = rootView.findViewById(R.id.search_container);


        if (listOfLeads.size() > 0) {

            if (arrayListLocal.size() > 0) {
                arrayListLocal.clear();

            }
            for (int i = 0; i < listOfLeads.size(); i++) {


                String email = listOfLeads.get(i).getEmail();
                String firstName = listOfLeads.get(i).getFirstName();
                String lastname = listOfLeads.get(i).getLastname();


                if (email.contains(keyword) || firstName.contains(keyword) || lastname.contains(keyword)) {

                    String eventdate = listOfLeads.get(i).getEventDate();
                    int id = listOfLeads.get(i).getId();

                    String secondValue = firstName + " " + lastname + " " + eventdate;


                    calenderModel = new CalenderModel();
                    calenderModel.setId(String.valueOf(id));
                    calenderModel.setName(email);
                    calenderModel.setDescription(secondValue);
                    arrayListLocal.add(calenderModel);


                }


            }

            populaterecyclerView();

        } else {
            count_leads_tv.setText("Displaying 0 Leads");
        }


        populaterecyclerView();

        return rootView;
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
            case R.id.mainContainer:


                break;


        }

    }

    private void populaterecyclerView() {


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new ContactAdapter(activity, arrayListLocal);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        if (arrayListLocal.size() > 0) {

            count_leads_tv.setText("Displaying " + arrayListLocal.size() + " Leads");

        } else {
            count_leads_tv.setText("Displaying 0 Leads");
        }


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        if (arrayListLocal.size() > 0) {

                            String idOfObject = arrayListLocal.get(position).getId();
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

    private void openFrag(String id) {

        LeadDetailFrag eventfrag = new LeadDetailFrag();
        Bundle args = new Bundle();
        args.putString("id", id);
        FragmentManager FM = getChildFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.frame_searched, eventfrag);
        eventfrag.setArguments(args);
        FT.commit();

    }

}
