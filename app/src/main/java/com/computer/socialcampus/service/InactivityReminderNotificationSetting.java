package com.computer.socialcampus.service;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InactivityReminderNotificationSetting extends NotificationSetting {

    private int interval; // Interval (in days) for inactivity reminders

    public InactivityReminderNotificationSetting() {
        super("inactivity_reminder");
        this.interval = 7; // Default interval of 7 days
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public void saveSetting(String userId) {
        // Implement logic to save inactivity reminder notification setting in Firebase (use the code provided earlier)
        DatabaseReference inactivityRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/inactivity/reminder");
        inactivityRef.child("enabled").setValue(isEnabled());
        inactivityRef.child("interval").setValue(getInterval());
    }

    @Override
    public void loadSetting(String userId) {
        // Implement logic to load inactivity reminder notification setting from Firebase
        DatabaseReference inactivityRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/notifications/inactivity/reminder");
        inactivityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    setEnabled(dataSnapshot.child("enabled").getValue(Boolean.class));
                    if (dataSnapshot.child("interval").exists()) {
                        setInterval(dataSnapshot.child("interval").getValue(Integer.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
