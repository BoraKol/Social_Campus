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

public class CommentNotificationListener {
    private FirebaseFirestore db;
    private Context context;
    private String postId;

    public CommentNotificationListener(Context context, String postId) {
        this.context = context;
        this.postId = postId;
        db = FirebaseFirestore.getInstance();
    }

    public void startListening() {
        db.collection("posts").document(postId).collection("comments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("CommentNotificationListener", "Error listening for comments", e);
                            return;
                        }

                        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                String commentId = documentChange.getDocument().getId();
                                sendCommentNotification(commentId);
                            }
                        }
                    }
                });
    }

    private void sendCommentNotification(String commentId) {
        // Get the comment text and username
        db.collection("posts").document(postId).collection("comments").document(commentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String commentText = documentSnapshot.getString("text");
                    String username = documentSnapshot.getString("username");

                    // Create a notification
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                    notificationBuilder.setSmallIcon(R.drawable.ic_notification);
                    notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
                    notificationBuilder.setContentTitle("New Comment!");
                    notificationBuilder.setContentText(username + " commented: " + commentText);
                    notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

                    // Show the notification
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(12345, notificationBuilder.build());
                })
                .addOnFailureListener(e -> {
                    Log.w("CommentNotificationListener", "Error getting comment text and username", e);
                });
    }
}
