package com.computer.socialcampus.service;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatNotificationSetting extends NotificationSetting {

    public ChatNotificationSetting() {
        super("chat");
    }

    @Override
    public void saveSetting(String userId) {
        // Implement logic to save chat notification setting in Firebase (use the code provided earlier)
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/chat");
        chatRef.child("enabled").setValue(isEnabled());
    }

    @Override
    public void loadSetting(String userId) {
        // Implement logic to load chat notification setting from Firebase
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/chat");
        chatRef.child("enabled").addListenerForSingleValueEvent(new ValueEventListener() {
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
