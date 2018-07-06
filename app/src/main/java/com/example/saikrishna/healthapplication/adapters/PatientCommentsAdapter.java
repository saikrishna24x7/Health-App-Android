package com.example.saikrishna.healthapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saikrishna.healthapplication.R;

import java.util.List;

public class PatientCommentsAdapter extends RecyclerView.Adapter<PatientCommentsAdapter.PatientCommentsViewHolder> {

    private List<String> comments;

    public PatientCommentsAdapter(List<String> comments) {
        this.comments = comments;
    }

    @Override
    public PatientCommentsAdapter.PatientCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dataView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_patient_comments, parent, false);
        return new PatientCommentsAdapter.PatientCommentsViewHolder(dataView);
    }

    @Override
    public void onBindViewHolder(PatientCommentsAdapter.PatientCommentsViewHolder holder, int position) {
        String dataString = comments.get(position);
        holder.comment.setText(dataString);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class PatientCommentsViewHolder extends RecyclerView.ViewHolder{
        TextView comment;

        public PatientCommentsViewHolder(View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.txtComment);
        }
    }

}
