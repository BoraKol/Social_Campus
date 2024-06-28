package com.computer.socialcampus.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.ActivityMainChatBinding;
import com.computer.socialcampus.helper.FragmentsAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class MainChatActivity extends AppCompatActivity {

    ActivityMainChatBinding binding;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainChatBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main_chat);

        mAuth = FirebaseAuth.getInstance();

        binding.viewpager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewpager);
    }

    //BURA DOLDURULABİLİR
}