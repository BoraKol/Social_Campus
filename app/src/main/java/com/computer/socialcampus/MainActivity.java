package com.computer.socialcampus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.computer.socialcampus.ui.chat.ChatActivity;
import com.computer.socialcampus.ui.group.GroupActivity;
import com.computer.socialcampus.ui.postShare.PostShareActivity;
import com.computer.socialcampus.ui.profile.AboutActivity;
import com.computer.socialcampus.ui.profile.ProfileActivity;
import com.computer.socialcampus.ui.settings.SettingsActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.computer.socialcampus.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start messaging with people you know now", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}