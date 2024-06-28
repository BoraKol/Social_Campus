package com.computer.socialcampus.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.FragmentGalleryBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GalleryFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private GoogleMap mMap;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // Tıklanan noktanın bilgilerini göster
        String title = marker.getTitle();
        String snippet = marker.getSnippet();
        // Burada bir AlertDialog veya başka bir yöntemle bilgileri gösterebilirsiniz

        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Noktaları işaretleme
        LatLng engineeringFaculty = new LatLng(37.832214, 30.528095);
        /*LatLng rectorate = new LatLng(37.832214, 30.528095);
        LatLng merkeziDerslik = new LatLng(37.832214, 30.528095);
        LatLng archFaculty = new LatLng(37.832214, 30.528095);
        LatLng camcofe = new LatLng(37.832214, 30.528095);
        LatLng starbuks = new LatLng(37.832214, 30.528095);
        LatLng tascoffe = new LatLng(37.832214, 30.528095);
        LatLng doguAmfi = new LatLng(37.832214, 30.528095);
        LatLng batiAmfi = new LatLng(37.832214, 30.528095);
        LatLng ertokus = new LatLng(37.832214, 30.528095);
        LatLng gsfKantin = new LatLng(37.832214, 30.528095);
        LatLng cartoonArt = new LatLng(37.832214, 30.528095);
        LatLng batıYemekhane = new LatLng(37.832214, 30.528095);
        LatLng doguYemekhane = new LatLng(37.832214, 30.528095);
        LatLng iibf = new LatLng(37.832214, 30.528095);*/



        mMap.addMarker(new MarkerOptions().position(engineeringFaculty).title("Engineering Faculty").snippet("Mühendislik alanında eğitim verir"));

        // Diğer noktaları da ekleyebilirsiniz

        mMap.setOnMarkerClickListener(this);
    }
}