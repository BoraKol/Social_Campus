package com.computer.socialcampus.helper;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.CardViewHolder>{


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
