package com.computer.socialcampus.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.FragmentSlideshowBinding;
import com.computer.socialcampus.group.Group;
import com.computer.socialcampus.group.GroupManager;
import com.computer.socialcampus.ui.home.HomeFragment;
import com.computer.socialcampus.ui.postShare.Post;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {
    private RecyclerView postsRecyclerView;
    private List<Post> postsList;
    private String groupId;
    private GroupManager groupManager;
    private LinearLayout groupListLayout;
    private EditText groupNameInput;
    private EditText groupDescriptionInput;
    private Button createGroupButton;

    private FragmentSlideshowBinding binding;

    public SlideshowFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        groupManager = new GroupManager();
        groupListLayout = view.findViewById(R.id.group_list_layout);
        groupNameInput = view.findViewById(R.id.group_name_input);
        groupDescriptionInput = view.findViewById(R.id.group_description_input);
        createGroupButton = view.findViewById(R.id.create_group_button);

        loadGroups();

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });

/*        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return view;
    }

    private void loadGroups() {
        groupManager.getGroups(new GroupManager.OnGroupsRetrievedListener() {
            @Override
            public void onGroupRetrieved(Group group) {
                addGroupToView(group);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Gruplar yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addGroupToView(Group group) {
        TextView groupTextView = new TextView(getContext());
        groupTextView.setText(group.getGroupName() + "\n" + group.getGroupDescription());
        groupTextView.setPadding(16, 16, 16, 16);
        groupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gruba tıklanınca yapılacak işlemler
                Toast.makeText(getContext(), group.getGroupName() + " seçildi", Toast.LENGTH_SHORT).show();
            }
        });

        groupListLayout.addView(groupTextView);
    }

    private void createGroup() {
        String groupName = groupNameInput.getText().toString().trim();
        String groupDescription = groupDescriptionInput.getText().toString().trim();

        if (groupName.isEmpty() || groupDescription.isEmpty()) {
            Toast.makeText(getContext(), "Grup adı ve açıklaması boş olamaz", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> groupMembers = new ArrayList<>(); // Şu an için boş, daha sonra eklenebilir
        groupManager.createGroup(groupName, groupDescription, groupMembers);
        Toast.makeText(getContext(), "Grup oluşturuldu", Toast.LENGTH_SHORT).show();

        groupNameInput.setText("");
        groupDescriptionInput.setText("");
    }

    private void addGroupToView(Group group) {
        TextView groupTextView = new TextView(getContext());
        groupTextView.setText(group.getGroupName() + "\n" + group.getGroupDescription());
        groupTextView.setPadding(16, 16, 16, 16);
        groupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gruba tıklanınca yapılacak işlemler
                Intent intent = new Intent(getContext(), HomeFragment.class);
                intent.putExtra("groupId", group.getGroupId());
                startActivity(intent);
            }
        });

        groupListLayout.addView(groupTextView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}