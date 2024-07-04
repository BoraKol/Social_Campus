package com.computer.socialcampus.service;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostCommentNotificationSetting extends PostNotificationSetting {

    public PostCommentNotificationSetting(String postId) {
        super("post_comment", postId);
    }

    @Override
    public void saveSetting(String userId) {
        // Implement logic to save post comment notification setting in Firebase (use the code provided earlier)
        DatabaseReference postCommentsRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/post/" + getPostId() + "/comments");
        postCommentsRef.child("enabled").setValue(isEnabled());
    }

    @Override
    public void loadSetting(String userId) {
        // Implement logic to load post comment notification setting from Firebase
        DatabaseReference postCommentsRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/post/" + getPostId() + "/comments");
        postCommentsRef.child("enabled").addListenerForSingleValueEvent(new ValueEventListener() {
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
