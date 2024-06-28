package com.computer.socialcampus.ui.postShare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.ActivityPostShareBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PostShareActivity extends AppCompatActivity {
    ActivityPostShareBinding binding;

    private EditText etPostDescription;
    private Button btnShare;
    private ImageView ivImagePreview; // Add an ImageView to preview the selected image
    private Uri imageUri; // Store the selected image Uri
    private User currentUser;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostShareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        etPostDescription = findViewById(R.id.etPostDescription);
        btnShare = findViewById(R.id.btnSharePost);
        ivImagePreview = findViewById(R.id.ivImagePreview); // Initialize the ImageView

        // Get the current user from the intent or database
        currentUser = getIntent().getParcelableExtra("currentUser");
        // or
        // currentUser = Database.getUserById(userId);

        // Add a button to select an image
        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postContent = etPostDescription.getText().toString();

                // Upload the selected image to Firebase Storage
                if (imageUri != null) {
                    uploadImageToFirebaseStorage(imageUri, new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String imageUrl = task.getResult().toString();

                                // Save the post to Firebase Realtime Database
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                String postId = databaseReference.child("posts").push().getKey(); // Generate a random ID
                                post = new Post(postId, imageUrl, postContent, "", currentUser);
                                databaseReference.child("posts").child(postId).setValue(post);

                                // Show a success toast
                                Toast.makeText(PostShareActivity.this, "Paylaşım başarıyla yapıldı!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PostShareActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(PostShareActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ivImagePreview.setImageURI(imageUri);
        }
    }

    private void uploadImageToFirebaseStorage(Uri imageUri, OnCompleteListener<Uri> onCompleteListener) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child("images/" + UUID.randomUUID().toString());
        UploadTask uploadTask = imageRef.putFile(imageUri);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(onCompleteListener);
    }
}