package com.example.saikrishna.healthapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.models.PatientReport;

import java.util.List;

public class DoctorRequestsAdapter extends RecyclerView.Adapter<DoctorRequestsAdapter.DoctorRequestsViewHolder> {

    private List<PatientReport> patientReports;

    public DoctorRequestsAdapter(List<PatientReport> patientReports) {
        this.patientReports = patientReports;
    }

    @Override
    public DoctorRequestsAdapter.DoctorRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dataView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_doctor_requests, parent, false);
        return new DoctorRequestsAdapter.DoctorRequestsViewHolder(dataView);
    }

    @Override
    public void onBindViewHolder(DoctorRequestsAdapter.DoctorRequestsViewHolder holder, int position) {
        PatientReport report = patientReports.get(position);
        String data = report.getPatientData();
        holder.request.setText(data);
    }

    @Override
    public int getItemCount() {
        return patientReports.size();
    }

    public class DoctorRequestsViewHolder extends RecyclerView.ViewHolder{

        public TextView request;

        public DoctorRequestsViewHolder(View itemView) {
            super(itemView);
            request = itemView.findViewById(R.id.txtRequest);
        }
    }
}
