package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devhub.Models.jobs;
import com.example.devhub.R;

import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobHolder> {

    private Context mContext;
    private List<jobs> mJobs;
    private JobInteraction mJobInteraction;

    public JobsAdapter(Context context, JobInteraction jobInteraction) {
        mContext = context;
        mJobInteraction = jobInteraction;
    }

    @NonNull
    @Override
    public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_jobs, parent, false);
        return new JobHolder(view, mJobInteraction);
    }

    @Override
    public void onBindViewHolder(@NonNull JobHolder holder, int position) {
        holder.bind(mJobs.get(position));
    }

    @Override
    public int getItemCount() {
        return mJobs != null ? mJobs.size() : 0;
    }

    public void setJobData(List<Job> jobs){
        mJobs = jobs;
        notifyDataSetChanged();
    }

    class JobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        JobInteraction mJobInteraction;
        TextView jobTitle, postedOn, companyName, jobLink, jobLocation, jobType;
        ImageView companyLogo;
        Button btnDetails;

        public JobHolder(@NonNull View itemView, JobInteraction jobInteraction) {
            super(itemView);
            mJobInteraction = jobInteraction;
            jobTitle = itemView.findViewById(R.id.txt_job_title);
            postedOn = itemView.findViewById(R.id.txt_job_post_date);
            companyName = itemView.findViewById(R.id.txt_company_name);
            jobLink = itemView.findViewById(R.id.txt_job_link);
            jobLocation = itemView.findViewById(R.id.txt_job_location);
            jobType = itemView.findViewById(R.id.txt_job_type);
            companyLogo = itemView.findViewById(R.id.company_logo);
            btnDetails = itemView.findViewById(R.id.btn_job_details);

            btnDetails.setOnClickListener(this);
            jobLink.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mJobInteraction.jobClicked(mJobs.get(getAdapterPosition()), view);
        }

        public void bind(Job job) {
            jobTitle.setText(job.getTitle());
            postedOn.setText(job.getCreatedAt());
            companyName.setText(job.getCompany());
            jobLink.setText(job.getJobUrl());
            jobLocation.setText(String.format("Location %s", job.getLocation()));
            jobType.setText(job.getType());

            Glide.with(mContext).load(job.getLogo()).into(companyLogo);
        }
    }

    public interface JobInteraction {

        void jobClicked(Job job, View view);
    }
}
