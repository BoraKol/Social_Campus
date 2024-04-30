package com.computer.socialcampus.message;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
