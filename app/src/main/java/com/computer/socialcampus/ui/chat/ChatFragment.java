package com.computer.socialcampus.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.computer.socialcampus.R;
import com.computer.socialcampus.data.model.LoggedInUser;
import com.computer.socialcampus.databinding.FragmentChatBinding;
import com.computer.socialcampus.helper.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }

    FragmentChatBinding binding;
    ArrayList<LoggedInUser> list = new ArrayList<>(  );
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentChatBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();

        UsersAdapter adapter = new UsersAdapter(list,getContext());
        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    LoggedInUser users = dataSnapshot.getValue( LoggedInUser.class );
                    users.setUserId(dataSnapshot.getKey());

                    if (!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users);
                    }

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}