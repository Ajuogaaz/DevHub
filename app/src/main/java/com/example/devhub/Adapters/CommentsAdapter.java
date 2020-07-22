package com.example.devhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devhub.Models.Post;
import com.example.devhub.R;

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

        TextView Title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.tvName);

        }

        public void bind(Post post) {
            Title.setText(post.getUser().getString("PreferredName"));

        }

    }


}
