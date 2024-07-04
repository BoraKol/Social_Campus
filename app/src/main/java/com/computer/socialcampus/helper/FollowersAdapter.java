package com.computer.socialcampus.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.computer.socialcampus.R;
import com.computer.socialcampus.data.model.LoggedInUser;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    private List<LoggedInUser> followersList;

    public FollowersAdapter(List<LoggedInUser> followersList) {
        this.followersList = followersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoggedInUser user = followersList.get(position);
        holder.usernameTextView.setText(user.getDisplayName());
        // Profil resmini ve diğer bilgileri yükleyin
    }

    @Override
    public int getItemCount() {
        return followersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView usernameTextView;
        public ImageView profileImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username_text_view);
            profileImageView = itemView.findViewById(R.id.profile_image_view);
        }
    }
}