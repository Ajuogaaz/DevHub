package com.example.devhub.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devhub.Models.Repositories;
import com.example.devhub.R;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepoHolder> {

    private List<Repositories> mUserRepos;
    private ItemInteraction mItemInteraction;

    public RepositoryAdapter(ItemInteraction itemInteraction) {
        mItemInteraction = itemInteraction;
    }

    @NonNull
    @Override
    public RepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.repo_item, parent, false);
        return new RepoHolder(view, mItemInteraction);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoHolder holder, int position) {
        holder.bind(mUserRepos.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserRepos != null ? mUserRepos.size() : 0;
    }

    public void setData(List<UserRepo> userRepos){
        mUserRepos = userRepos;
        notifyDataSetChanged();
    }

    class RepoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemInteraction mItemInteraction;
        TextView repoName, repoFullName;

        RepoHolder(@NonNull View itemView, ItemInteraction itemInteraction) {
            super(itemView);
            mItemInteraction = itemInteraction;
            repoName = itemView.findViewById(R.id.repo_name);
            repoFullName = itemView.findViewById(R.id.repo_full_name);
            itemView.setOnClickListener(this);
        }

        public void bind(UserRepo userRepo) {
            repoFullName.setText(userRepo.getFullName());
            repoName.setText(userRepo.getName());
        }

        @Override
        public void onClick(View v) {
            mItemInteraction.repoItemClick(mUserRepos.get(getAdapterPosition()));
        }
    }

    public interface ItemInteraction {
        void repoItemClick(UserRepo userRepo);
    }
}
