package com.example.saikrishna.healthapplication.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.activity.PatientRecordsActivity;

import java.util.List;

public class PatientRecordsAdapter extends RecyclerView.Adapter<PatientRecordsAdapter.PatientReocrdsViewHolder> {

    String names[];

    private List<String> data;

    public PatientRecordsAdapter(List<String> data){
        this.data = data;
    }

    @Override
    public PatientReocrdsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dataView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_patient_records, parent, false);
        return new PatientReocrdsViewHolder(dataView);
    }

    @Override
    public void onBindViewHolder(PatientReocrdsViewHolder holder, int position) {
        String dataString = data.get(position);
        holder.data.setText(dataString);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PatientReocrdsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView data;

        public PatientReocrdsViewHolder(View view)
        {
            super(view);
            data = (TextView) view.findViewById(R.id.txtData);
        }
    }

}
