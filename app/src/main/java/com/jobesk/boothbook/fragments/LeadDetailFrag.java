package com.jobesk.boothbook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jobesk.boothbook.database.DBLeads;
import com.jobesk.boothbook.database.DatabaseHelper;
import com.jobesk.boothbook.R;

public class LeadDetailFrag extends Fragment {


    private View rootView;
    private ImageView backimg;
    private TextView email_tv, name_tv, date_tv, address_tv, additional_notes_tv, contact_permission_tv;
    private DatabaseHelper db;
    private String id;
    private DBLeads leadModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_lead_detail, container, false);

        backimg = rootView.findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                if (manager != null) {
                    manager.popBackStackImmediate();
                }
            }
        });

        db = new DatabaseHelper(getActivity());
        if (getArguments() != null) {
            id = getArguments().getString("id");
        }

        if (id != null) {
            leadModel = db.getSingleLead(Long.parseLong(id));
        }
        email_tv = rootView.findViewById(R.id.email_tv);
        name_tv = rootView.findViewById(R.id.name_tv);
        date_tv = rootView.findViewById(R.id.date_tv);
        address_tv = rootView.findViewById(R.id.address_tv);
        additional_notes_tv = rootView.findViewById(R.id.additional_notes_tv);
        contact_permission_tv = rootView.findViewById(R.id.contact_permission_tv);

        String email = leadModel.getEmail();

        email_tv.setText(email);
        String nameValue = leadModel.getFirstName() + " "
                + leadModel.getLastname() + " ,"
                + leadModel.getCompanyName() + ", "
                + leadModel.getMobilePhone() + ", "
                + leadModel.getTelephone();
        name_tv.setText(nameValue);
        String dateValue = leadModel.getEventDate() + ", "
                + leadModel.getEventTimeStart() + ", "
                + leadModel.getEventTimeEnd() + ", "
                + leadModel.getEventType() + ", "
                + leadModel.getEventName() + ", "
                + leadModel.getEventBudget();
        date_tv.setText(dateValue);
        String addressValue = leadModel.getAddress() + ", "
                + leadModel.getCity() + ", "
                + leadModel.getPostcode();
        address_tv.setText(addressValue);
        String additionalNotes = leadModel.getAdditionalNotes();
        additional_notes_tv.setText(additionalNotes);
        String contactPermission = leadModel.getContactPermission();
        contact_permission_tv.setText(contactPermission);

        return rootView;
    }
}
