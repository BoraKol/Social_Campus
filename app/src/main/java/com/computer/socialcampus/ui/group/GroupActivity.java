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
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_campus, R.id.nav_grups)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_settings){
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.action_profile){
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.action_newGroup){
            Intent i = new Intent(this, GroupActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.action_about){
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.action_postShare){
            Intent i = new Intent(this, PostShareActivity.class);
            startActivity(i);
        }
        return  super.onOptionsItemSelected(item);
    }
}