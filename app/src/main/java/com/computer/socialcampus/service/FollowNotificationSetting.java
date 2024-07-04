package com.computer.socialcampus.service;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FollowNotificationSetting extends NotificationSetting {

    public FollowNotificationSetting() {
        super("follow");
    }

    @Override
    public void saveSetting(String userId) {
        // Implement logic to save follow notification setting in Firebase (use the code provided earlier)
        DatabaseReference followRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/follow");
        followRef.child("enabled").setValue(isEnabled());
    }

    @Override
    public void loadSetting(String userId) {
        // Implement logic to load follow notification setting from Firebase
        DatabaseReference followRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/follow");
        followRef.child("enabled").addListenerForSingleValueEvent(new ValueEventListener() {
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
