package com.computer.socialcampus.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.computer.socialcampus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView bioTextView;
    private ImageView profileImageView;
    private Button editProfileButton;
    private Button followersButton;
    private Button followingButton;
    private Button followButton;
    private Button unfollowButton;

    private String userId;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameTextView = findViewById(R.id.username_text_view);
        bioTextView = findViewById(R.id.bio_text_view);
        profileImageView = findViewById(R.id.profile_image_view);
        editProfileButton = findViewById(R.id.edit_profile_button);
        followersButton = findViewById(R.id.followers_button);
        followingButton = findViewById(R.id.following_button);
        followButton = findViewById(R.id.follow_button);
        unfollowButton = findViewById(R.id.unfollow_button);

        userId = getIntent().getStringExtra("userId");
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadProfile();

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        followersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFollowers();
            }
        });

        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFollowing();
            }
        });

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followUser(currentUserId, userId);
                followButton.setVisibility(View.GONE);
                unfollowButton.setVisibility(View.VISIBLE);
            }
        });

        unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfollowUser(currentUserId, userId);
                followButton.setVisibility(View.VISIBLE);
                unfollowButton.setVisibility(View.GONE);
            }
        });

        checkFollowingStatus();
    }
    private void checkFollowingStatus() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(currentUserId).collection("following").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        followButton.setVisibility(View.GONE);
                        unfollowButton.setVisibility(View.VISIBLE);
                    } else {
                        followButton.setVisibility(View.VISIBLE);
                        unfollowButton.setVisibility(View.GONE);
                    }
                });
    }

    private void followUser(String currentUserId, String targetUserId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Current user follows target user
        db.collection("users").document(currentUserId).collection("following").document(targetUserId)
                .set(new HashMap<>())
                .addOnSuccessListener(aVoid -> {
                    // Following successful
                    Toast.makeText(ProfileActivity.this, "Kullanıcı takip edildi", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Following failed
                    Toast.makeText(ProfileActivity.this, "Takip etme başarısız", Toast.LENGTH_SHORT).show();
                });

        // Target user gains a new follower
        db.collection("users").document(targetUserId).collection("followers").document(currentUserId)
                .set(new HashMap<>())
                .addOnSuccessListener(aVoid -> {
                    // Follower added successfully
                })
                .addOnFailureListener(e -> {
                    // Adding follower failed
                });
    }

    private void unfollowUser(String currentUserId, String targetUserId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Current user unfollows target user
        db.collection("users").document(currentUserId).collection("following").document(targetUserId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Unfollowing successful
                    Toast.makeText(ProfileActivity.this, "Kullanıcı takibi bırakıldı", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Unfollowing failed
                    Toast.makeText(ProfileActivity.this, "Takibi bırakma başarısız", Toast.LENGTH_SHORT).show();
                });

        // Target user loses a follower
        db.collection("users").document(targetUserId).collection("followers").document(currentUserId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Follower removed successfully
                })
                .addOnFailureListener(e -> {
                    // Removing follower failed
                });
    }


    private void loadProfile() {
        // Kullanıcı profil bilgilerini yükleme işlemi
        // Bu kısımda veritabanından kullanıcı bilgilerini çekip görüntüleyebilirsiniz
    }

    private void editProfile() {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    private void showFollowers() {
        Intent intent = new Intent(ProfileActivity.this, FollowersActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    private void showFollowing() {
        Intent intent = new Intent(ProfileActivity.this, FollowingActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}