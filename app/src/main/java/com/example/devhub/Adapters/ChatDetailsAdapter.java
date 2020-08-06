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
import com.example.devhub.Models.Messages;
import com.example.devhub.R;
import com.parse.ParseUser;

import java.util.List;

public class ChatDetailsAdapter extends RecyclerView.Adapter<ChatDetailsAdapter.MessageViewHolder> {

    public static final String TAG = ChatDetailsAdapter.class.getSimpleName();
    public Context context;
    public List<Messages> messages;
    private static final int MESSAGE_OUTGOING = 123;
    private static final int MESSAGE_INCOMING = 321;

    public ChatDetailsAdapter(Context context, List<Messages> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(viewType == MESSAGE_INCOMING){
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false);
            return new IncomingMessageViewHolder(view);
        }else if(viewType == MESSAGE_OUTGOING){
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false);
            return  new OutgoingMessageViewHolder(view);
        }else{
            throw new IllegalArgumentException("Unknown view type");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Messages message = messages.get(position);
        holder.bindMessage(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }



    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSendingUser().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
            return MESSAGE_OUTGOING;
        } else {
            return MESSAGE_INCOMING;
        }
    }

    public abstract class MessageViewHolder extends RecyclerView.ViewHolder {

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bindMessage(Messages message);
    }

    public class IncomingMessageViewHolder extends MessageViewHolder {
        ImageView profilePic;
        TextView chatBody;


        public IncomingMessageViewHolder(View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ivProfileImage);
            chatBody = itemView.findViewById(R.id.chatBody);

        }

        @Override
        public void bindMessage(Messages message) {

            chatBody.setText(message.getChatBody());

            ParseUser user = message.getSendingUser();

            String ImageUrl = "";

            if(user.getBoolean("HasUploadedPic")){
                ImageUrl = user.getParseFile("ProfilePic").getUrl();
            }else{
                ImageUrl = user.getString("githubProfilePic");
            }


            if(!ImageUrl.isEmpty()) {
                Glide.with(context)
                        .load(ImageUrl)
                        .into(profilePic);
            }
        }
    }

    public class OutgoingMessageViewHolder extends MessageViewHolder {
        TextView chatBody;

        public OutgoingMessageViewHolder(View itemView) {
            super(itemView);
            chatBody = itemView.findViewById(R.id.chatBody);
        }

        @Override
        public void bindMessage(Messages message) {
            chatBody.setText(message.getChatBody());
        }
    }

}
