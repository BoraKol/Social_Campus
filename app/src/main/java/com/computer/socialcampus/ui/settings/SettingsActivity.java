package com.computer.socialcampus.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.computer.socialcampus.R;
import com.computer.socialcampus.ui.profile.EditProfileActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.profile_settings_button).setOnClickListener(v -> openProfileSettings());
        findViewById(R.id.notification_settings_button).setOnClickListener(v -> openNotificationSettings());
        findViewById(R.id.privacy_settings_button).setOnClickListener(v -> openPrivacySettings());
//        findViewById(R.id.language_settings_button).setOnClickListener(v -> openLanguageSettings());
        findViewById(R.id.account_management_button).setOnClickListener(v -> openAccountManagement());
    }

    private void openProfileSettings() {
        Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void openNotificationSettings() {
        Intent intent = new Intent(SettingsActivity.this, NotificationSettingsActivity.class);
        startActivity(intent);
    }

    private void openPrivacySettings() {
        Intent intent = new Intent(SettingsActivity.this, PrivacySettingsActivity.class);
        startActivity(intent);
    }

/*    private void openLanguageSettings() {
        Intent intent = new Intent(SettingsActivity.this, LanguageSettingsActivity.class);
        startActivity(intent);
    }*/

    private void openAccountManagement() {
        Intent intent = new Intent(SettingsActivity.this, AccountManagementActivity.class);
        startActivity(intent);
    }
}