package com.example.devhub.Adapters;

import android.content.Context;
import android.util.Log;
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
import com.parse.ParseFile;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;
    onClickListener clickListener;
    public static final int DETAILS_CODE = 200;
    public static final int PROFILE_CODE = 300;
    public static final int LIKE_CODE = 400;

    public static final int HOME_FRAGMENT_CODE = 21;
    public static final int PROFILE_FRAGMENT_CODE = 22;

    public interface onClickListener{
        void onItemClicked(int position, int replyCode);
    }

    public TimelineAdapter(Context context, List<Post> posts, onClickListener clickListener) {
        this.context = context;
        this.posts = posts;
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
        private TextView tvUserNameDescription;
        private TextView tvDate;
        private TextView tvNumberofLikes;
        private ImageView profilePic;
        private ImageView likeIcon;
        private TextView tvTopic;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ivProfileImage);
            tvPreferredName = itemView.findViewById(R.id.tvName);
            gitHubUsername = itemView.findViewById(R.id.gitHubUserName);
            tvDate = itemView.findViewById(R.id.tvCreatedAt);
            tvTopic = itemView.findViewById(R.id.title);

            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivPostImage);
            tvUserNameDescription = itemView.findViewById(R.id.tvUserNameDescription);

            tvNumberofLikes = itemView.findViewById(R.id.NumberofActualLikes);

            likeIcon = itemView.findViewById(R.id.ivLike);




        }

        public void bind(final Post post) {

            tvDescription.setText(post.getDescription());
            tvPreferredName.setText(post.getUser().getString("PreferredName"));
            gitHubUsername.setText(String.format("@%s", post.getUser().getString("gitHubUserName")));
            tvDate.setText(post.getTime());
            tvTopic.setText(post.getTopic());

            tvUserNameDescription.setText(post.getUser().getUsername());

            tvNumberofLikes.setText("200");


            profilePic.setOnClickListener(view -> clickListener.onItemClicked(getAdapterPosition(), PROFILE_CODE));

            likeIcon.setOnClickListener(view -> {
                clickListener.onItemClicked(getAdapterPosition(), LIKE_CODE);
                likeIcon.setImageResource(R.drawable.ufi_heart_active);
                tvNumberofLikes.setText("200");
            });



            String profilepic = post.getUser().getParseFile("ProfilePic").getUrl();

            Glide.with(context)
                    .load(profilepic)
                    .into(profilePic);

            ParseFile image = post.getImage();

            if (image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }else{
                Log.i("pOSN CB ", "IMAGE NOT EXISTING" );
            }

            ivImage.setOnClickListener(view -> clickListener.onItemClicked(getAdapterPosition(), DETAILS_CODE));

        }
    }


}
