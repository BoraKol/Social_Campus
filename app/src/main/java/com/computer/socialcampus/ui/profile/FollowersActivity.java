package com.computer.socialcampus.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.computer.socialcampus.R;
import com.computer.socialcampus.data.model.LoggedInUser;
import com.computer.socialcampus.helper.FollowersAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class FollowersActivity extends AppCompatActivity {

    private RecyclerView followersRecyclerView;
    private FollowersAdapter followersAdapter;
    private List<LoggedInUser> followersList;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        followersRecyclerView = findViewById(R.id.followers_recycler_view);
        followersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        followersList = new ArrayList<>();
        followersAdapter = new FollowersAdapter(followersList);
        followersRecyclerView.setAdapter(followersAdapter);

        userId = getIntent().getStringExtra("userId");

        loadFollowers();
    }

    private void loadFollowers() {
        // Takipçileri veritabanından yükleme işlemi
        // Örnek olarak Firebase Firestore kullanarak yükleyelim
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("followers")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        followersList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            LoggedInUser user = document.toObject(LoggedInUser.class);
                            followersList.add(user);
                        }
                        followersAdapter.notifyDataSetChanged();
                    } else {
                        // Hata işlemi
                    }
                });
    }
}