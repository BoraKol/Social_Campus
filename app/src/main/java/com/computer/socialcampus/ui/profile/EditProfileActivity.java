package com.computer.socialcampus.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.computer.socialcampus.R;

public class EditProfileActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText bioEditText;
    private ImageView profileImageView;
    private Button saveButton;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        usernameEditText = findViewById(R.id.username_edit_text);
        bioEditText = findViewById(R.id.bio_edit_text);
        profileImageView = findViewById(R.id.profile_image_view);
        saveButton = findViewById(R.id.save_button);

        userId = getIntent().getStringExtra("userId");

        loadProfile();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Profil resmi seçme işlemi
            }
        });
    }

    private void loadProfile() {
        // Kullanıcı profil bilgilerini yükleme işlemi
    }

    private void saveProfile() {
        String username = usernameEditText.getText().toString().trim();
        String bio = bioEditText.getText().toString().trim();

        if (username.isEmpty() || bio.isEmpty()) {
            Toast.makeText(this, "Kullanıcı adı ve bio boş olamaz", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kullanıcı profil bilgilerini kaydetme işlemi
    }
}