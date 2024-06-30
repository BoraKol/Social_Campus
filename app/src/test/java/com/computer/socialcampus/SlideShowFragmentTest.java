package com.computer.socialcampus;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.FragmentSlideshowBinding;
import com.computer.socialcampus.ui.slideshow.SlideshowFragment;
import com.computer.socialcampus.ui.slideshow.SlideshowViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
public class SlideShowFragmentTest {

    @Mock
    private Fragment fragmentMock;

    @Mock
    private LayoutInflater inflaterMock;

    @Mock
    private ViewGroup containerMock;

    @Mock
    private Bundle savedInstanceStateMock;

    @Mock
    private SlideshowViewModel slideshowViewModelMock;

    private SlideshowFragment slideshowFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        slideshowFragment = new SlideshowFragment();
        slideshowFragment.binding = mock(FragmentSlideshowBinding.class);
    }

    @Test
    public void testOnCreateView() {
        when(inflaterMock.inflate(R.layout.fragment_slideshow, containerMock, false)).thenReturn(mock(View.class));
        when(slideshowViewModelMock.getText()).thenReturn(mock(LiveData.class));

        slideshowFragment.onCreateView(inflaterMock, containerMock, savedInstanceStateMock);

        verify(slideshowViewModelMock).getText();
    }

    @Test
    public void testOnDestroyView() {
        slideshowFragment.onDestroyView();

        verify(slideshowFragment.binding).unbind();
    }
}
