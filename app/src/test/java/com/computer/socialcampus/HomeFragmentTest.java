package com.computer.socialcampus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.FragmentHomeBinding;
import com.computer.socialcampus.ui.home.HomeFragment;
import com.computer.socialcampus.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
public class HomeFragmentTest {

    @Mock
    private Fragment fragmentMock;

    @Mock
    private LayoutInflater inflaterMock;

    @Mock
    private ViewGroup containerMock;

    @Mock
    private Bundle savedInstanceStateMock;

    @Mock
    private HomeViewModel homeViewModelMock;

    private HomeFragment homeFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        homeFragment = new HomeFragment();
        homeFragment.binding = mock(FragmentHomeBinding.class);
    }

    @Test
    public void testOnCreateView() {
        when(inflaterMock.inflate(R.layout.fragment_home, containerMock, false)).thenReturn(mock(View.class));
        when(homeViewModelMock.getText()).thenReturn(mock(LiveData.class));

        homeFragment.onCreateView(inflaterMock, containerMock, savedInstanceStateMock);

        verify(homeViewModelMock).getText();
    }

    @Test
    public void testOnDestroyView() {
        homeFragment.onDestroyView();

        verify(homeFragment.binding).unbind();
    }
}
