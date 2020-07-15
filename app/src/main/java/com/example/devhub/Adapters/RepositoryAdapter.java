package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devhub.Models.Repositories;
import com.example.devhub.R;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<Repositories> repos;
    private Context context;
    onClickListener clickListener;

    public interface onClickListener{
        void onItemClicked(int position);
    }


    public RepositoryAdapter(Context context, List<Repositories> repos, onClickListener clickListener) {

        this.context = context;
        this.repos = repos;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Repositories repo = repos.get(position)

        holder.bind(repo);
    }

    @Override
    public int getItemCount() {
        return mUserRepos != null ? mUserRepos.size() : 0;
    }

    public void setData(List<UserRepo> userRepos){
        mUserRepos = userRepos;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemInteraction mItemInteraction;
        TextView repoName, repoFullName;

        ViewHolder(@NonNull View itemView, ItemInteraction itemInteraction) {
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
