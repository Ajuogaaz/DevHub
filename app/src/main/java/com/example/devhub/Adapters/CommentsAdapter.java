package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.parse.ParseUser;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{

    private List<Post> posts;
    private Context context;
    onClickListener clickListener;

    public interface onClickListener {
        void onItemClicked(int position);
    }


    public CommentsAdapter(Context context, List<Post> posts, onClickListener clickListener) {

        this.context = context;
        this.posts = posts;
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

        Post post = posts.get(position);

        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView PreferredName, gitHubUserName, date;
        ImageView ProfilePic;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            PreferredName = itemView.findViewById(R.id.tvName);
            ProfilePic = itemView.findViewById(R.id.ivProfileImage);
            gitHubUserName = itemView.findViewById(R.id.gitHubUserName);
            date = itemView.findViewById(R.id.tvCreatedAt);

        }

        public void bind(Post post) {

            String ImageUrl = "";

            if(post.getUser().getBoolean("HasUploadedPic")){
                ImageUrl = post.getUser().getParseFile("ProfilePic").getUrl();
            }else{
                ImageUrl = post.getUser().getString("githubProfilePic");
            }

            if (!ImageUrl.isEmpty()) {
                Glide.with(context)
                        .load(ImageUrl)
                        .into(ProfilePic);
            }

            PreferredName.setText(post.getUser().getString("PreferredName"));
            gitHubUserName.setText(post.getUser().getString("gitHubUserName"));
            date.setText(post.getTime());

        }

    }


}
