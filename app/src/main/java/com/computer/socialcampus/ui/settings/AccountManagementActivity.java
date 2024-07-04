package com.computer.socialcampus.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.computer.socialcampus.R;
import com.computer.socialcampus.ui.profile.EditProfileActivity;

public class AccountManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        // Find UI elements
        Button editProfileButton = findViewById(R.id.edit_profile_button);
        Button changePasswordButton = findViewById(R.id.change_password_button);
        Button privacySettingsButton = findViewById(R.id.privacy_settings_button);
        Button notificationSettingsButton = findViewById(R.id.notification_settings_button);
        Button deactivateAccountButton = findViewById(R.id.deactivate_account_button);

        // Set up click listeners for buttons
        editProfileButton.setOnClickListener(view -> {
            Intent editProfileIntent = new Intent(AccountManagementActivity.this, EditProfileActivity.class);
            startActivity(editProfileIntent);
        });

        changePasswordButton.setOnClickListener(view -> {
            Intent changePasswordIntent = new Intent(AccountManagementActivity.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
        });

        privacySettingsButton.setOnClickListener(view -> {
            Intent privacySettingsIntent = new Intent(AccountManagementActivity.this, PrivacySettingsActivity.class);
            startActivity(privacySettingsIntent);
        });

        notificationSettingsButton.setOnClickListener(view -> {
            Intent notificationSettingsIntent = new Intent(AccountManagementActivity.this, NotificationSettingsActivity.class);
            startActivity(notificationSettingsIntent);
        });

        deactivateAccountButton.setOnClickListener(view -> {
            // Handle deactivate account button click (prompt confirmation and handle deactivation)
            // ... (Implement confirmation dialog and deactivation logic)
        });
    }
}