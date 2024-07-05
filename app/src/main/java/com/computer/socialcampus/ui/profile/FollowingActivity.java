package com.computer.socialcampus.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.computer.socialcampus.R;
import com.computer.socialcampus.data.model.LoggedInUser;
import com.computer.socialcampus.helper.FollowingAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class FollowingActivity extends AppCompatActivity {

    private RecyclerView followingRecyclerView;
    private FollowingAdapter followingAdapter;
    private List<LoggedInUser> followingList;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        followingRecyclerView = findViewById(R.id.following_recycler_view);
        followingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        followingList = new ArrayList<>();
        followingAdapter = new FollowingAdapter(followingList);
        followingRecyclerView.setAdapter(followingAdapter);

        userId = getIntent().getStringExtra("userId");

        loadFollowing();
    }

    private void loadFollowing() {
        // Takip edilenleri veritabanından yükleme işlemi
        // Örnek olarak Firebase Firestore kullanarak yükleyelim
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("following")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        followingList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            LoggedInUser user = document.toObject(LoggedInUser.class);
                            followingList.add(user);
                        }
                        followingAdapter.notifyDataSetChanged();
                    } else {
                        // Hata işlemi
                    }
                });
    }
}