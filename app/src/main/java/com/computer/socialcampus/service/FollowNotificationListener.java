package com.computer.socialcampus.service;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.computer.socialcampus.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class FollowNotificationListener {
    private FirebaseFirestore db;
    private Context context;

    public FollowNotificationListener(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    public void startListening(String currentUserId) {
        db.collection("users").document(currentUserId).collection("followers")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("FollowNotificationListener", "Error listening for followers", e);
                            return;
                        }

                        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                String newFollowerId = documentChange.getDocument().getId();
                                sendFollowNotification(newFollowerId);
                            }
                        }
                    }
                });
    }

    private void sendFollowNotification(String newFollowerId) {
        // Get the new follower's username
        db.collection("users").document(newFollowerId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String newFollowerUsername = documentSnapshot.getString("username");

                    // Create a notification
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                    notificationBuilder.setSmallIcon(R.drawable.ic_notification);
                    notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification));
                    notificationBuilder.setContentTitle("New Follower!");
                    notificationBuilder.setContentText(newFollowerUsername + " started following you!");
                    notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

                    // Show the notification
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(12345, notificationBuilder.build());
                })
                .addOnFailureListener(e -> {
                    Log.w("FollowNotificationListener", "Error getting new follower's username", e);
                });
    }
}
