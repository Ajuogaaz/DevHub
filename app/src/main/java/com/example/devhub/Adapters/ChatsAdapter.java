package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devhub.Models.MessageTop;
import com.example.devhub.R;
import com.parse.ParseUser;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder>{

    public static final String TAG = ChatsAdapter.class.getSimpleName();
    public Context context;
    public List<MessageTop> TopMessages;
    public onClickListener clickListener;


    public interface onClickListener{
        void onMessageClick(int position, ParseUser user);
        void onUserClick(int position, ParseUser user);
    }

    //constructor
    public ChatsAdapter(Context context, List<MessageTop> TopMessages, onClickListener clickListener){
        this.context = context;
        this.TopMessages = TopMessages;
        this.clickListener = clickListener;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageTop messagesTop = TopMessages.get(position);
        holder.bind(messagesTop);
    }


    @Override
    public int getItemCount() {
        return TopMessages.size();
    }


    public void clear() {
        TopMessages.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView ivProfilePic;
        private TextView tvUsername, textBody, dated;
        private CardView ParticularPost;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfileImage);
            tvUsername = itemView.findViewById(R.id.tvName);
            textBody = itemView.findViewById(R.id.tvDescription);
            ParticularPost = itemView.findViewById(R.id.otherContainer);
            dated = itemView.findViewById(R.id.tvCreatedAt);
        }

        public void bind(MessageTop messageTop) {
            textBody.setText(messageTop.getTopMessage());
            dated.setText(messageTop.getTime());

            ParseUser user;
            if(messageTop.getUserOne().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){
                user = messageTop.getUserTwo();
            }else{
                user = messageTop.getUserOne();
            }

            tvUsername.setText(user.getString("PreferredName"));

            String ImageUrl = "";

            if(user.getBoolean("HasUploadedPic")){
                ImageUrl = user.getParseFile("ProfilePic").getUrl();
            }else{
                ImageUrl = user.getString("githubProfilePic");
            }


            if(!ImageUrl.isEmpty()) {
                Glide.with(context)
                        .load(ImageUrl)
                        .into(ivProfilePic);
            }
            ivProfilePic.setOnClickListener(view -> clickListener.onUserClick(getAdapterPosition(), user));
            ParticularPost.setOnClickListener(view -> clickListener.onMessageClick(getAdapterPosition(), user));

        }
    }


}
