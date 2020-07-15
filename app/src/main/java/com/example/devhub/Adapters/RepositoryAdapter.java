package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        return  repos.size();
    }

    /*public void setData(List<UserRepo> userRepos){
        mUserRepos = userRepos;
        notifyDataSetChanged();
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView repoName, repoFullName;
        CardView repoItem;

        ViewHolder(@NonNull View itemView, ItemInteraction itemInteraction) {
            super(itemView);

            repoName = itemView.findViewById(R.id.repo_name);
            repoFullName = itemView.findViewById(R.id.repo_full_name);
            repoItem = itemView.findViewById(R.id.repoItem);

        }

        public void bind(Repositories repo) {


            repoFullName.setText(repo.getFullName());
            repoName.setText(repo.getName());





        }

    }

    public interface ItemInteraction {
        void repoItemClick(UserRepo userRepo);
    }
}
