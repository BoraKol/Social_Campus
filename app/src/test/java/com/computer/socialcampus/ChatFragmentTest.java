package com.computer.socialcampus;

import static com.google.common.base.CharMatcher.any;
import static com.google.common.base.Verify.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.computer.socialcampus.data.model.LoggedInUser;
import com.computer.socialcampus.databinding.FragmentChatBinding;
import com.computer.socialcampus.helper.UsersAdapter;
import com.computer.socialcampus.ui.chat.ChatFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ChatFragmentTest {
    @Mock
    private Fragment fragmentMock;

    @Mock
    private LayoutInflater inflaterMock;

    @Mock
    private ViewGroup containerMock;

    @Mock
    private Bundle savedInstanceStateMock;

    @Mock
    private FirebaseDatabase databaseMock;

    @Mock
    private RecyclerView recyclerViewMock;

    @Mock
    private UsersAdapter adapterMock;

    @Mock
    private LinearLayoutManager layoutManagerMock;

    @Mock
    private DataSnapshot dataSnapshotMock;

    @Mock
    private FirebaseAuth authMock;

    private ChatFragment chatFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        chatFragment = new ChatFragment();
        chatFragment.binding = mock(FragmentChatBinding.class);
        chatFragment.database = databaseMock;
        chatFragment.list = new ArrayList<>();
        chatFragment.binding.chatRecyclerView = recyclerViewMock;
    }

    @Test
    public void testOnCreateView() {
        when(inflaterMock.inflate(R.layout.fragment_chat, containerMock, false)).thenReturn(mock(View.class));
        when(databaseMock.getReference()).thenReturn(mock(DatabaseReference.class));
        when(dataSnapshotMock.getChildren()).thenReturn(mock(Iterable.class));
        when(dataSnapshotMock.getKey()).thenReturn("userKey");
        when(dataSnapshotMock.getValue(LoggedInUser.class)).thenReturn(mock(LoggedInUser.class));
        when(authMock.getInstance()).thenReturn(mock(FirebaseAuth.class));
        when(authMock.getUid()).thenReturn("currentUserId");

        chatFragment.onCreateView(inflaterMock, containerMock, savedInstanceStateMock);

        verify(recyclerViewMock).setAdapter(adapterMock);
        verify(recyclerViewMock).setLayoutManager(layoutManagerMock);
        verify(databaseMock.getReference().child("Users")).addValueEventListener(any(ValueEventListener.class));
        verify(adapterMock).notifyDataSetChanged();
    }
}
