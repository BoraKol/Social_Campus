package com.computer.socialcampus.service;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostLikeNotificationSetting extends PostNotificationSetting {

    public PostLikeNotificationSetting(String postId) {
        super("post_like", postId);
    }

    @Override
    public void saveSetting(String userId) {
        // Implement logic to save post like notification setting in Firebase (use the code provided earlier)
        DatabaseReference postLikesRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/post/" + getPostId() + "/likes");
        postLikesRef.child("enabled").setValue(isEnabled());
    }

    @Override
    public void loadSetting(String userId) {
        // Implement logic to load post like notification setting from Firebase
        DatabaseReference postLikesRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/post/" + getPostId() + "/likes");
        postLikesRef.child("enabled").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    setEnabled(dataSnapshot.getValue(Boolean.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
