package com.computer.socialcampus.ui.gallery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.computer.socialcampus.R;
import com.computer.socialcampus.databinding.FragmentGalleryBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.google.maps.model.TravelMode;


public class GalleryFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private GeoApiContext geoApiContext;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private FragmentGalleryBinding binding;
    private Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        mContext=getActivity();;


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geoApiContext = new GeoApiContext.Builder()
                .apiKey("YOUR_API_KEY")  // Buraya kendi API anahtarınızı ekleyin
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .build();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(snippet)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();

        LatLng markerLatLng = marker.getPosition();
        getDirections(markerLatLng);
        return true;
    }

    private void getDirections(LatLng markerLatLng) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        LatLng startLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                        DirectionsApiRequest directions = DirectionsApi.newRequest(geoApiContext)
                                .origin(new com.google.maps.model.LatLng(startLatLng.latitude, startLatLng.longitude))
                                .destination(new com.google.maps.model.LatLng(markerLatLng.latitude, markerLatLng.longitude))
                                .mode(com.google.maps.model.TravelMode.DRIVING);

                        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {
                            @Override
                            public void onResult(DirectionsResult result) {
                                getActivity().runOnUiThread(() -> {
                                    if (result != null && result.routes != null && result.routes.length > 0) {
                                        showDirections(result.routes);
                                    } else {
                                        Toast.makeText(getActivity(), "Yol tarifi alınamadı", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Throwable e) {
                                getActivity().runOnUiThread(() -> {
                                    Toast.makeText(getActivity(), "Yol tarifi alınamadı", Toast.LENGTH_SHORT).show();
                                });
                            }
                        });

                        directions.awaitIgnoreError();
                    } else {
                        Toast.makeText(getActivity(), "Konum alınamadı", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDirections(com.google.maps.model.DirectionsRoute[] routes) {
        for (int i = 0; i < routes.length; i++) {
            com.google.maps.model.DirectionsRoute route = routes[i];

            if (route != null && route.overviewPolyline != null) {
                List<LatLng> polyline = PolyUtil.decode(route.overviewPolyline.points); // Use PolyUtil to decode the points

                // Add the polyline to the map
                mMap.addPolyline(new PolylineOptions()
                        .color(Color.BLUE)
                        .width(10)
                        .geodesic(true)
                        .addAll(polyline));

                // Move the camera to the starting point of the route
                if (route.legs != null && route.legs.length > 0 && route.legs[0].startLocation != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(route.legs[0].startLocation.lat, route.legs[0].startLocation.lng), 15));
                }
            } else {
                Log.e("showDirections", "Route or overviewPolyline is null at index: " + i);
            }
        }
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Noktaları işaretleme
        LatLng engineeringFaculty = new LatLng(37.832214, 30.528095);
        LatLng rectorate = new LatLng(37.829442, 30.526305);
        LatLng merkeziDerslik = new LatLng(37.829673, 30.523805);
        LatLng archFaculty = new LatLng(37.831977, 30.526959);
        LatLng camcofe = new LatLng(37.829671, 30.525046);
        LatLng starbuks = new LatLng(37.831357, 30.526318);
        LatLng tascoffe = new LatLng(37.829415, 30.528939);
        LatLng doguAmfi = new LatLng(37.827603, 30.531681);
        LatLng batiAmfi = new LatLng(37.827714, 30.528216);
        LatLng ertokus = new LatLng(37.828397, 30.533104);
        LatLng gsfKantin = new LatLng(37.828554, 30.532709);
        LatLng cartoonArt = new LatLng(37.828270, 30.533079);
        LatLng batiYemekhane = new LatLng(37.829652, 30.524565);
        LatLng doguYemekhane = new LatLng(37.827835, 30.533107);



        mMap.addMarker(new MarkerOptions().position(engineeringFaculty).title("Engineering Faculty").snippet("Mühendislik alanında eğitim verir"));
        mMap.addMarker(new MarkerOptions().position(rectorate).title("rektorluk").snippet("rektor burada"));
        mMap.addMarker(new MarkerOptions().position(merkeziDerslik).title("Merkezi Derslik").snippet("Ders"));
        mMap.addMarker(new MarkerOptions().position(archFaculty).title("Mimarlık").snippet("Mimar eğitimi"));
        mMap.addMarker(new MarkerOptions().position(camcofe).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(starbuks).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(tascoffe).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(doguAmfi).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(batiAmfi).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(ertokus).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(gsfKantin).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(cartoonArt).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(batiYemekhane).title("").snippet(""));
        mMap.addMarker(new MarkerOptions().position(doguYemekhane).title("").snippet(""));
        // Diğer noktaları da ekleyebilirsiniz

        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(engineeringFaculty, 15));
    }
}