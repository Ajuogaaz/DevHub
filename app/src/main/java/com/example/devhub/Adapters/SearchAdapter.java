package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devhub.R;
import com.parse.ParseUser;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    public static final String TAG = SearchAdapter.class.getSimpleName();
    public static final int TYPE_USER = 0;
    public static final int TYPE_EVENT = 1;
    public Context context;
    public List<ParseUser> users;
    public SearchAdapter.onClickListener clickListener;


    public interface onClickListener{
        void onUserClick(int position);
    }

    //constructor
    public SearchAdapter(Context context, List<ParseUser> users, onClickListener clickListener){
        this.context = context;
        this.users = users;
        this.clickListener = clickListener;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParseUser user = users.get(position);
        holder.bind(user);
    }


    @Override
    public int getItemCount() {
        return users.size();
    }


    public void clear() {
        users.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        Context context;
        private ImageView ivProfilePic;
        private TextView tvUsername;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfileImage);
            tvUsername = itemView.findViewById(R.id.tvName);
            tvUsername = itemView.findViewById(R.id.gitHubUserName);

        }

        public void bind(ParseUser user) {
            tvUsername.setText(user.getUsername());
            tvBio.setText(user.getBio());

            //check if the user has a valid profilePic
            ParseFile image = user.getImage();
            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .circleCrop()
                        .into(ivProfilePic);
            } else {
                Glide.with(context)
                        .load(context.getResources().getString(R.string.DEFAULT_PROFILE_PIC))
                        .circleCrop()
                        .into(ivProfilePic);
            }


        }
    }





}
