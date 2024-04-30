package com.computer.socialcampus.ui.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.ActivityMainBinding;
import com.computer.socialcampus.ui.postShare.PostShareActivity;
import com.computer.socialcampus.ui.profile.AboutActivity;
import com.computer.socialcampus.ui.profile.ProfileActivity;
import com.computer.socialcampus.ui.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

public class GroupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


    }


}