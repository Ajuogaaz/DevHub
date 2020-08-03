package com.example.devhub.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devhub.Activities.DetailsActivity;
import com.example.devhub.Activities.MainActivity;
import com.example.devhub.Activities.ProfileActivity;
import com.example.devhub.Models.Post;
import com.example.devhub.Models.Repositories;
import com.example.devhub.R;
import com.example.devhub.Utils.OnSwipeTouchListener;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private List<Post> posts;
    private Context context;
    onClickListener clickListener;

    public interface onClickListener {
        void onItemClicked(int position);
    }


    public ProfileAdapter(Context context, List<Post> posts, onClickListener clickListener) {

        this.context = context;
        this.posts = posts;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent, false);

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

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Description, datel, numberOfComments, tvNumberofLikes;
        ImageView postImage;
        CardView ParticularPost;
        List likes;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.title);
            postImage = itemView.findViewById(R.id.PostImage);
            Description = itemView.findViewById(R.id.tvDescription);
            datel = itemView.findViewById(R.id.tvCreatedAt);
            ParticularPost = itemView.findViewById(R.id.particularPost);
            numberOfComments = itemView.findViewById(R.id.tvActualComments);
            tvNumberofLikes = itemView.findViewById(R.id.tvActualLikes);

        }

        public void bind(Post post) {
            Title.setText(post.getTopic());
            Description.setText(post.getDescription());
            initLikesandComments(post);

            ParseFile image = post.getImage();

            if (image != null) {
                postImage.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(image.getUrl())
                        .into(postImage);
                Description.setMaxLines(2);
            } else {
                postImage.setVisibility(View.GONE);
                Description.setMaxLines(4);
            }

            ParticularPost.setOnTouchListener(new OnSwipeTouchListener(context) {
                @Override
                public void onSwipeDown() {

                }

                @Override
                public void onSwipeLeft() {


                }

                @Override
                public void onSwipeUp() {

                }

                @Override
                public void onSwipeRight() {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        private void initLikesandComments(Post post) {

            numberOfComments.setText(String.format("%d comments", post.getNumberofComments()));

            likes = new ArrayList<>();

            if (post.getLikes() != null) {
                likes.addAll(post.getLikes());


                tvNumberofLikes.setText(String.format("%d upvotes", likes.size()));

            }


        }
    }
}