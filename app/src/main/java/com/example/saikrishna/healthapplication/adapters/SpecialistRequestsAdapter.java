package com.example.saikrishna.healthapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.models.PatientReport;

import java.util.List;

public class SpecialistRequestsAdapter extends RecyclerView.Adapter<SpecialistRequestsAdapter.SpecialistRequestsViewHolder> {

    private List<PatientReport> patientReports;

    public SpecialistRequestsAdapter(List<PatientReport> patientReports) {
        this.patientReports = patientReports;
    }

    @Override
    public SpecialistRequestsAdapter.SpecialistRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dataView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_specialist_requests, parent, false);
        return new SpecialistRequestsAdapter.SpecialistRequestsViewHolder(dataView);
    }

    @Override
    public void onBindViewHolder(SpecialistRequestsAdapter.SpecialistRequestsViewHolder holder, int position) {
        PatientReport report = patientReports.get(position);
        String data = report.getPatientData();
        holder.request.setText(data);
    }

    @Override
    public int getItemCount() {
        return patientReports.size();
    }

    public class SpecialistRequestsViewHolder extends RecyclerView.ViewHolder{

        public TextView request;

        public SpecialistRequestsViewHolder(View itemView) {
            super(itemView);
            request = itemView.findViewById(R.id.txtRequest);
        }
    }
}
