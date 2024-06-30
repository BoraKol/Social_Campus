package com.computer.socialcampus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.FragmentGalleryBinding;
import com.computer.socialcampus.ui.gallery.GalleryFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
public class GalleryFragmentTest {
    @Mock
    private Fragment fragmentMock;

    @Mock
    private LayoutInflater inflaterMock;

    @Mock
    private ViewGroup containerMock;

    @Mock
    private Bundle savedInstanceStateMock;

    @Mock
    private GoogleMap googleMapMock;

    @Mock
    private Marker markerMock;

    private GalleryFragment galleryFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        galleryFragment = new GalleryFragment();
        galleryFragment.binding = mock(FragmentGalleryBinding.class);
    }

    @Test
    public void testOnCreateView() {
        when(inflaterMock.inflate(R.layout.fragment_gallery, containerMock, false)).thenReturn(mock(View.class));
        when(googleMapMock.setOnMarkerClickListener(galleryFragment)).thenReturn(null);

        galleryFragment.onCreateView(inflaterMock, containerMock, savedInstanceStateMock);

        verify(googleMapMock).setOnMarkerClickListener(galleryFragment);
    }

    @Test
    public void testOnMarkerClick() {
        when(markerMock.getTitle()).thenReturn("Engineering Faculty");
        when(markerMock.getSnippet()).thenReturn("Mühendislik alanında eğitim verir");

        galleryFragment.onMarkerClick(markerMock);

        // Burada tıklanan noktanın bilgilerini gösterme işlemini test edebilirsiniz
    }

    @Test
    public void testOnMapReady() {
        LatLng engineeringFaculty = new LatLng(37.832214, 30.528095);
        when(googleMapMock.addMarker(new MarkerOptions().position(engineeringFaculty).title("Engineering Faculty").snippet("Mühendislik alanında eğitim verir"))).thenReturn(markerMock);

        galleryFragment.onMapReady(googleMapMock);

        verify(googleMapMock).addMarker(new MarkerOptions().position(engineeringFaculty).title("Engineering Faculty").snippet("Mühendislik alanında eğitim verir"));
    }
}
