package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        void onEventClick(int position);
        void onUserClick(int position);
    }

    //constructor
    public SearchAdapter(Context context, List<Object> objects, onClickListener clickListener){
        this.context = context;
        this.objects = objects;
        this.clickListener = clickListener;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ParseUser user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = objects.get(position);
        if (object instanceof User) {
            return TYPE_USER;
        } else if (object instanceof Event) {
            return TYPE_EVENT;
        }

        throw new IllegalArgumentException("Invalid position " + position);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        private BaseViewHolder(View itemView) {
            super(itemView);
        }
        public abstract void bind(T type);
    }

    public class UserViewHolder extends BaseViewHolder<User> implements View.OnClickListener{
        Context context;
        private ImageView ivProfilePic;
        private TextView tvUsername;
        private TextView tvBio;

        public UserViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvBio = itemView.findViewById(R.id.tvBio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //when clicked go to the profile fragment
            clickListener.onUserClick(getAdapterPosition());

        }

        public void bind(User user) {
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

    public class EventViewHolder extends BaseViewHolder<Event> implements View.OnClickListener {
        private Context context;
        private ImageView ivEventImage;
        private ImageView ivHostProfilePic;
        private TextView tvEventName;
        private TextView tvEventLocation;
        private TextView tvEventDate;
        private TextView tvHostUsername;

        private EventViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ivEventImage = itemView.findViewById(R.id.ivEventImage);
            ivHostProfilePic = itemView.findViewById(R.id.ivHostProfilePic);
            tvEventName = itemView.findViewById(R.id.tvName);
            tvEventLocation = itemView.findViewById(R.id.tvEventLocation);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            tvHostUsername = itemView.findViewById(R.id.tvHostUsername);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(Event event) {
            tvEventName.setText(event.getName());
            try {
                tvEventLocation.setText(Event.getStringFromLocation(event.getLocation(), context, TAG));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvEventDate.setText(event.getDate().toString());
            tvHostUsername.setText(event.getHost().getUsername());

            ParseFile image = event.getImage();
            if (image != null) {
                Glide.with(context).load(event.getImage().getUrl()).into(ivEventImage);
            }

            //check if the user has a valid profilePic
            ParseFile image2 = event.getHost().getParseFile("profileImage");
            if (image2 != null) {
                Glide.with(context)
                        .load(event.getHost().getParseFile("profileImage").getUrl())
                        .circleCrop()
                        .into(ivHostProfilePic);
            } else {
                Glide.with(context)
                        .load(context.getResources().getString(R.string.DEFAULT_PROFILE_PIC))
                        .circleCrop()
                        .into(ivHostProfilePic);
            }
        }

        @Override
        public void onClick(View view) {
            //when clicked go to the map fragment and pull up bottom sheet
            clickListener.onEventClick(getAdapterPosition());

        }
    }

    public void clear() {
        objects.clear();
        notifyDataSetChanged();
    }


}
