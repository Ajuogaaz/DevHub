package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devhub.Models.Comments;
import com.example.devhub.R;

import java.util.List;
import java.util.Objects;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{

    private List<Comments> comments;
    private Context context;
    onClickListener clickListener;

    public interface onClickListener {
        void onItemClicked(int position);
    }


    public CommentsAdapter(Context context, List<Comments> comments, onClickListener clickListener) {

        this.context = context;
        this.comments = comments;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comments comment = comments.get(position);

        holder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void clear(){
        comments.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView PreferredName, gitHubUserName, date, body;
        ImageView ProfilePic;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            PreferredName = itemView.findViewById(R.id.tvName);
            ProfilePic = itemView.findViewById(R.id.ivProfileImage);
            gitHubUserName = itemView.findViewById(R.id.gitHubUserName);
            date = itemView.findViewById(R.id.tvCreatedAt);
            body = itemView.findViewById(R.id.tvDescription);

        }

        public void bind(Comments comment) {

            String ImageUrl;

            if(comment.getUser().getBoolean("HasUploadedPic")) {
                ImageUrl = Objects.requireNonNull(comment.getUser().getParseFile("ProfilePic")).getUrl();
            } else{
                ImageUrl = comment.getUser().getString("githubProfilePic");
            }

            if (ImageUrl != null && !ImageUrl.isEmpty()) {
                Glide.with(context)
                        .load(ImageUrl)
                        .into(ProfilePic);
            }

            PreferredName.setText(comment.getUser().getString("PreferredName"));
            gitHubUserName.setText(comment.getUser().getString("gitHubUserName"));
            date.setText(comment.getTime());
            body.setText(comment.getDescription());


            body.setOnClickListener(view1 -> {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            });


        }

    }


}
