package com.computer.socialcampus.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.computer.socialcampus.R;

public class PrivacySettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_settings);

        sharedPreferences = getSharedPreferences("privacy_settings", MODE_PRIVATE);

        // Find UI elements (replace with actual IDs from your layout)
        CheckBox profileVisibilityCheckbox = findViewById(R.id.profile_visibility_checkbox);
        Spinner postVisibilitySpinner = findViewById(R.id.post_visibility_spinner);
        CheckBox allowCommentsCheckbox = findViewById(R.id.allow_comments_checkbox);
        CheckBox allowDMsCheckbox = findViewById(R.id.allow_dms_checkbox);
        CheckBox followSuggestionsCheckbox = findViewById(R.id.follow_suggestions_checkbox);

        // Set initial states of settings based on user preferences (load from storage)
        profileVisibilityCheckbox.setChecked(sharedPreferences.getBoolean("profile_visibility", true));
        postVisibilitySpinner.setSelection(sharedPreferences.getInt("post_visibility", 0));
        allowCommentsCheckbox.setChecked(sharedPreferences.getBoolean("allow_comments", true));
        allowDMsCheckbox.setChecked(sharedPreferences.getBoolean("allow_dms", true));
        followSuggestionsCheckbox.setChecked(sharedPreferences.getBoolean("follow_suggestions", true));

        // Set up event listeners for checkboxes and spinners
        profileVisibilityCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("profile_visibility", isChecked).apply();
            }
        });

        postVisibilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferences.edit().putInt("post_visibility", position).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        allowCommentsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("allow_comments", isChecked).apply();
            }
        });

        allowDMsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("allow_dms", isChecked).apply();
            }
        });

        followSuggestionsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("follow_suggestions", isChecked).apply();
            }
        });
    }
}