package com.computer.socialcampus.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.computer.socialcampus.R;
import com.computer.socialcampus.service.ChatNotificationSetting;
import com.computer.socialcampus.service.FollowNotificationSetting;
import com.computer.socialcampus.service.InactivityReminderNotificationSetting;
import com.computer.socialcampus.service.PostCommentNotificationSetting;
import com.computer.socialcampus.service.PostLikeNotificationSetting;
import com.computer.socialcampus.service.PostNotificationSetting;

public class NotificationSettingsActivity extends AppCompatActivity {

    private String userId; // Current user's ID

    private ChatNotificationSetting chatNotificationSetting;
    private FollowNotificationSetting followNotificationSetting;
    private PostCommentNotificationSetting postCommentNotificationSetting; // Replace with actual post notification setting class
    private PostLikeNotificationSetting postLikeNotificationSetting; // Replace with actual post notification setting class
    private InactivityReminderNotificationSetting inactivityReminderNotificationSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        userId = getIntent().getStringExtra("userId"); // Get the current user's ID

        // Initialize notification setting objects
        chatNotificationSetting = new ChatNotificationSetting();
        followNotificationSetting = new FollowNotificationSetting();
        postCommentNotificationSetting = new PostCommentNotificationSetting("postId"); // Replace with actual post ID
        postLikeNotificationSetting = new PostLikeNotificationSetting("postId"); // Replace with actual post ID
        inactivityReminderNotificationSetting = new InactivityReminderNotificationSetting();

        // Load initial notification settings from Firebase
        loadNotificationSettings();

        // Find UI elements
        Switch chatNotificationSwitch = findViewById(R.id.chat_notification_switch);
        Switch followNotificationSwitch = findViewById(R.id.follow_notification_switch);
        Switch postCommentNotificationSwitch = findViewById(R.id.post_comment_notification_switch);
        Switch postLikeNotificationSwitch = findViewById(R.id.post_like_notification_switch);
        Switch inactivityReminderSwitch = findViewById(R.id.inactivity_reminder_switch);
        Button saveButton = findViewById(R.id.save_button);

        // Set initial switch states based on loaded settings
        chatNotificationSwitch.setChecked(chatNotificationSetting.isEnabled());
        followNotificationSwitch.setChecked(followNotificationSetting.isEnabled());
        postCommentNotificationSwitch.setChecked(postCommentNotificationSetting.isEnabled());
        postLikeNotificationSwitch.setChecked(postLikeNotificationSetting.isEnabled());
        inactivityReminderSwitch.setChecked(inactivityReminderNotificationSetting.isEnabled());

        // Set up click listeners for switches
        chatNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            chatNotificationSetting.setEnabled(isChecked);
            chatNotificationSetting.saveSetting(userId);
        });

        followNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            followNotificationSetting.setEnabled(isChecked);
            followNotificationSetting.saveSetting(userId);
        });

        postCommentNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            postCommentNotificationSetting.setEnabled(isChecked);
            postCommentNotificationSetting.saveSetting(userId);
        });

        postLikeNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            postLikeNotificationSetting.setEnabled(isChecked);
            postLikeNotificationSetting.saveSetting(userId);
        });

        inactivityReminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            inactivityReminderNotificationSetting.setEnabled(isChecked);
            inactivityReminderNotificationSetting.saveSetting(userId);
        });

        // Set up click listener for the save button
        saveButton.setOnClickListener(view -> {
            // Save all updated notification settings to Firebase
            chatNotificationSetting.saveSetting(userId);
            followNotificationSetting.saveSetting(userId);
            postCommentNotificationSetting.saveSetting(userId);
            postLikeNotificationSetting.saveSetting(userId);
            inactivityReminderNotificationSetting.saveSetting(userId);

            // Show a toast or snackbar to indicate successful saving
            Toast.makeText(NotificationSettingsActivity.this, "Settings saved successfully", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadNotificationSettings() {
        chatNotificationSetting.loadSetting(userId);
        followNotificationSetting.loadSetting(userId);
        postCommentNotificationSetting.loadSetting(userId);
        postLikeNotificationSetting.loadSetting(userId);
        inactivityReminderNotificationSetting.loadSetting(userId);
    }

}