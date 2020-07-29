package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    public interface onClickListener {
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

        View view = LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Repositories repo = repos.get(position);

        holder.bind(repo);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void clear(){
        repos.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView repoName, repoDescription, repoLanguage, repoSize, repoStars, repoForks;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            repoName = itemView.findViewById(R.id.repo_name);
            repoDescription = itemView.findViewById(R.id.repo_description);
            repoLanguage = itemView.findViewById(R.id.language);
            repoSize = itemView.findViewById(R.id.tvActualSize);
            repoForks = itemView.findViewById(R.id.tvActualForks);
            repoStars = itemView.findViewById(R.id.tvActualStars);


        }

        public void bind(Repositories repo) {


            repoName.setText(repo.getName());
            if(repo.getDescription() != null){
                repoDescription.setText(repo.getDescription());
            }else{
                repoDescription.setText("");
            }
            if(repo.getLanguage() != null){
                repoLanguage.setText(repo.getLanguage());
            }else{
                repoLanguage.setText("");
            }

            repoSize.setText(String.format("%d size", repo.getSizeOfRepo()));
            repoForks.setText(String.format("%d forks", repo.getForks()));
            repoStars.setText(String.format("%d stars", repo.getStars()));

            repoName.setOnClickListener(view -> {
                clickListener.onItemClicked(getAdapterPosition());

            });
            repoDescription.setOnClickListener(view -> {
                clickListener.onItemClicked(getAdapterPosition());

            });

        }

    }
}