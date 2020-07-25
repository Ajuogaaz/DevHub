package com.example.devhub.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.Utils.OnDoubleTapListener;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;
    onClickListener clickListener;
    onTouchListener touchListener;
    public static final int DETAILS_CODE = 200;
    public static final int PROFILE_CODE = 300;
    public static final int LIKE_CODE = 400;
    public static final int COMMENT_CODE = 500;

    public static final int HOME_FRAGMENT_CODE = 21;
    public static final int PROFILE_FRAGMENT_CODE = 22;


    public interface onClickListener{
        void onItemClicked(int position, int replyCode);
    }

    public interface onTouchListener{
        void onItemTouched(int position);
    }

    public TimelineAdapter(Context context, List<Post> posts, onClickListener clickListener, onTouchListener touchListener) {
        this.context = context;
        this.posts = posts;
        this.clickListener = clickListener;
        this.touchListener = touchListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView tvPreferredName;
        private TextView gitHubUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvDate;
        private TextView tvNumberofLikes;
        private ImageView profilePic;
        private TextView tvTopic;
        private  TextView numberOfComments;
        private ImageView comments;
        private TextView commentText;
        public List<String> likes;
        private ImageView ivUpvote;
        private CardView particularPost;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ivProfileImage);
            tvPreferredName = itemView.findViewById(R.id.tvName);
            gitHubUsername = itemView.findViewById(R.id.gitHubUserName);
            tvDate = itemView.findViewById(R.id.tvCreatedAt);
            tvTopic = itemView.findViewById(R.id.title);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.PostImage);
            tvNumberofLikes = itemView.findViewById(R.id.tvActualLikes);
            numberOfComments = itemView.findViewById(R.id.tvActualComments);
            comments = itemView.findViewById(R.id.ivComment);
            commentText = itemView.findViewById(R.id.ivCommentText);
            ivUpvote = itemView.findViewById(R.id.ivUpvote);
            particularPost = itemView.findViewById(R.id.particularPost);


        }

        public void bind(Post post) {

            tvDescription.setText(post.getDescription());
            tvPreferredName.setText(post.getUser().getString("PreferredName"));
            gitHubUsername.setText(String.format("@%s", post.getUser().getString("gitHubUserName")));
            tvDate.setText(post.getTime());
            tvTopic.setText(post.getTopic());

            profilePic.setOnClickListener(view -> clickListener.onItemClicked(getAdapterPosition(), PROFILE_CODE));

            String ImageUrl = "";

            if(post.getUser().getBoolean("HasUploadedPic")){
                ImageUrl = post.getUser().getParseFile("ProfilePic").getUrl();
            }else{
                ImageUrl = post.getUser().getString("githubProfilePic");
            }

            initLikesandComments(post);

            if(!ImageUrl.isEmpty()) {
                Glide.with(context)
                        .load(ImageUrl)
                        .into(profilePic);
            }

            ParseFile image = post.getImage();

            if (image != null){
                ivImage.setVisibility(View.VISIBLE);
                tvDescription.setMaxLines(4);
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }else{
                ivImage.setVisibility(View.GONE);
                tvDescription.setMaxLines(8);
                Log.i("pOSN CB ", "IMAGE NOT EXISTING" );
            }

            ivImage.setOnClickListener(view -> clickListener.onItemClicked(getAdapterPosition(), DETAILS_CODE));
            tvDescription.setOnClickListener(view -> clickListener.onItemClicked(getAdapterPosition(), DETAILS_CODE));

            comments.setOnClickListener(view -> clickListener.onItemClicked(getAdapterPosition(), COMMENT_CODE));
            commentText.setOnClickListener(view -> clickListener.onItemClicked(getAdapterPosition(), COMMENT_CODE));

            ivUpvote.setOnClickListener(view -> clickListener.onItemClicked(getAdapterPosition(), LIKE_CODE));

            particularPost.setOnTouchListener(new OnDoubleTapListener(context) {
                @Override
                public void onDoubleTap(MotionEvent e) {
                    clickListener.onItemClicked(getAdapterPosition(), LIKE_CODE);
                }
            });

        }

        private void initLikesandComments(Post post) {

            numberOfComments.setText(String.format("%d comments", post.getNumberofComments()));

            likes = new ArrayList<>();

            if (post.getLikes() != null){
                likes.addAll(post.getLikes());

                if(currentUserInList(likes)){
                    ivUpvote.setImageResource(R.drawable.ic_upvote_done);
                }else{
                    ivUpvote.setImageResource(R.drawable.ic_upvote);
                }
            }else{
                ivUpvote.setImageResource(R.drawable.ic_upvote_done);
            }

            tvNumberofLikes.setText(String.format("%d upvotes", likes.size()));

        }

        private boolean currentUserInList(List<String> likes) {
            if (likes.size() != 0){
                for(int i = 0; i < likes.size(); i++)
                    if (ParseUser.getCurrentUser().getObjectId().equals(likes.get(i))) {
                        return true;
                    }
                return false;
            }
            return false;
        }
    }



}
