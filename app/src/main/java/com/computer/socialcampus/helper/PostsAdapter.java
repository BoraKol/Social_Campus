package com.computer.socialcampus.helper;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.computer.socialcampus.ui.postShare.Post;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.CardViewHolder>{
    public PostsAdapter(List<Post> postsList) {
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
