package com.computer.socialcampus.ui.postShare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.ActivityPostShareBinding;
import com.computer.socialcampus.group.GroupManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PostShareActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;

    private EditText postContentEditText;
    private Button selectImageButton;
    private Button selectVideoButton;
    private Button postButton;
    private Button likeButton;
    private Button commentButton;
    private EditText commentEditText;
    private ImageView selectedImageView;
    private VideoView selectedVideoView;
    private LinearLayout commentsLayout;

    private Uri selectedImageUri;
    private Uri selectedVideoUri;
    private String groupId;
    private boolean isTimeline;
    private FirebaseFirestore db;

    private GroupManager groupManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_share);
        db = FirebaseFirestore.getInstance();

        postContentEditText = findViewById(R.id.post_content);
        selectImageButton = findViewById(R.id.select_image_button);
        selectVideoButton = findViewById(R.id.select_video_button);
        postButton = findViewById(R.id.post_button);
        selectedImageView = findViewById(R.id.selected_image);
        selectedVideoView = findViewById(R.id.selected_video);
        commentsLayout = findViewById(R.id.comments_layout);
        likeButton = findViewById(R.id.like_button);
        commentButton = findViewById(R.id.comment_button);
        commentEditText = findViewById(R.id.comment_edit_text);

        groupManager = new GroupManager();

        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        isTimeline = intent.getBooleanExtra("isTimeline", false);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST);
            }
        });

        selectVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_VIDEO_REQUEST);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePost();
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });

        loadComments();
    }
    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType(requestCode == PICK_IMAGE_REQUEST ? "image/*" : "video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                selectedImageUri = data.getData();
                selectedImageView.setImageURI(selectedImageUri);
                selectedImageView.setVisibility(View.VISIBLE);
                selectedVideoView.setVisibility(View.GONE);
            } else if (requestCode == PICK_VIDEO_REQUEST) {
                selectedVideoUri = data.getData();
                selectedVideoView.setVideoURI(selectedVideoUri);
                selectedVideoView.setVisibility(View.VISIBLE);
                selectedImageView.setVisibility(View.GONE);
            }
        }
    }

    private void createPost() {
        String postContent = postContentEditText.getText().toString().trim();

        if (postContent.isEmpty() && selectedImageUri == null && selectedVideoUri == null) {
            Toast.makeText(this, "Gönderi içeriği veya medya seçmelisiniz", Toast.LENGTH_SHORT).show();
            return;
        }

        Post post = new Post();
        post.setContent(postContent);
        post.setImageUri(selectedImageUri != null ? selectedImageUri.toString() : null);
        post.setVideoUri(selectedVideoUri != null ? selectedVideoUri.toString() : null);

        if (isTimeline) {
            groupManager.createPost(null, post);
        } else {
            groupManager.createPost(groupId, post);
        }

        Toast.makeText(this, "Gönderi paylaşıldı", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void likePost() {
        // Beğeni işlevini eklemek için metot
        String postId = getIntent().getStringExtra("postId");
        groupManager.addLike(postId);
    }

    private void addComment() {
        String commentText = commentEditText.getText().toString().trim();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Yorum metni boş olamaz", Toast.LENGTH_SHORT).show();
            return;
        }

        String postId = getIntent().getStringExtra("postId");
        String username = "kullanici_adi"; // Bu değeri uygun şekilde alınız (örn. mevcut kullanıcı adı)

        groupManager.addComment(postId, commentText, username);
        commentEditText.setText("");
        loadComments();
    }

    private void loadComments() {
        String postId = getIntent().getStringExtra("postId");;
        db.collection("posts").document(postId).collection("comments")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        commentsLayout.removeAllViews();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Comment comment = document.toObject(Comment.class);
                            View commentView = getLayoutInflater().inflate(R.layout.comment_item, null);
                            TextView commentText = commentView.findViewById(R.id.comment_text);
                            TextView commentUsername = commentView.findViewById(R.id.comment_username);
                            commentText.setText(comment.getCommentText());
                            commentUsername.setText(comment.getUsername());
                            commentsLayout.addView(commentView);
                        }
                    } else {
                        // Yorumları yükleme hatası
                        Toast.makeText(this, "Yorumlar yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void addCommentToView(String username, String commentText) {
        View commentView = getLayoutInflater().inflate(R.layout.comment_item, commentsLayout, false);

        TextView usernameTextView = commentView.findViewById(R.id.comment_username);
        TextView commentTextView = commentView.findViewById(R.id.comment_text);

        usernameTextView.setText(username);
        commentTextView.setText(commentText);

        commentsLayout.addView(commentView);
    }

}