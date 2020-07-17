package com.azspc.newsline240.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azspc.newsline240.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.posts = posts;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.text.setText(post.getText());
        holder.author.setText(post.getAuthor());
        holder.title.setText(post.getTitle());
        holder.title.setBackgroundColor(post.getColor());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView text, title, author;

        ViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.pt_post);
            title = view.findViewById(R.id.pt_title);
            author = view.findViewById(R.id.pt_author);
        }
    }
}