package com.jobesk.boothbook.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jobesk.boothbook.models.CalenderModel;
import com.jobesk.boothbook.R;

import java.util.ArrayList;

public class CalanderAdapter extends RecyclerView.Adapter<CalanderAdapter.MyViewHolder> {

    private ArrayList<CalenderModel> arrayList;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView title_tv;
        private TextView description_tv;

        public MyViewHolder(View view) {
            super(view);

            title_tv = (TextView) view.findViewById(R.id.title_tv);
            description_tv = (TextView) view.findViewById(R.id.description_tv);

        }
    }


    public CalanderAdapter(Activity activity, ArrayList<CalenderModel> arrayList) {

        this.arrayList = arrayList;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_calander, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CalenderModel model = arrayList.get(position);

        holder.title_tv.setText(model.getName());
        holder.description_tv.setText(model.getDescription());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }


    @Override
    public int getItemCount() {

        return arrayList.size();

    }
}