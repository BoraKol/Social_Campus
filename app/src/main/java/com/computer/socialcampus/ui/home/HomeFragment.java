package com.computer.socialcampus.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.FragmentHomeBinding;
import com.computer.socialcampus.group.GroupManager;
import com.computer.socialcampus.helper.PostsAdapter;
import com.computer.socialcampus.ui.postShare.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_GROUP_ID = "groupId";
    private static final String ARG_IS_TIMELINE = "isTimeline";

    private String groupId;
    private boolean isTimeline;
    private GroupManager groupManager;
    private LinearLayout postListLayout;
    private RecyclerView postsRecyclerView;
    private List<Post> postsList;

    public HomeFragment(){

    }

    public static HomeFragment newInstance(String groupId, boolean isTimeline) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, groupId);
        args.putBoolean(ARG_IS_TIMELINE, isTimeline);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_GROUP_ID);
            isTimeline = getArguments().getBoolean(ARG_IS_TIMELINE);
        }
        groupManager = new GroupManager();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        postsRecyclerView = view.findViewById(R.id.recyclerViewPosts);

        loadPosts();

        // Retrieve the post list from arguments (passed from MainActivity)
        if (getArguments() != null) {
            postsList = getArguments().<Post>getParcelableArrayList("postsList");
        }

        // Set up the RecyclerView adapter
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postsRecyclerView.setAdapter(new PostsAdapter(postsList));

        return view;
    }

    private void loadPosts() {
        if (isTimeline) {
            loadTimelinePosts();
        } else {
            loadGroupPosts();
        }
    }

    private void loadGroupPosts() {
        groupManager.getPosts(groupId, new GroupManager.OnPostsRetrievedListener() {
            @Override
            public void onPostRetrieved(Post post) {
                addPostToView(post);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Gönderiler yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTimelinePosts() {
        groupManager.getAllPosts(new GroupManager.OnPostsRetrievedListener() {
            @Override
            public void onPostRetrieved(Post post) {
                addPostToView(post);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Gönderiler yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPostToView(Post post) {
        TextView postTextView = new TextView(getContext());
        postTextView.setText(post.getContent());
        postTextView.setPadding(16, 16, 16, 16);

        postListLayout.addView(postTextView);
    }
}