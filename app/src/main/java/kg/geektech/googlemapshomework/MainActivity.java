package kg.geektech.googlemapshomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import kg.geektech.googlemapshomework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityMainBinding binding;
    private GoogleMap gMap;
    private ArrayList<LatLng> markerList;
    private AppDatabase appDatabase;
    private MyDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        markerList = new ArrayList<>();
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my-database")
                .allowMainThreadQueries()
                .build();
        dao = appDatabase.dao();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();

        if (dao.getAll() != null) {
            Log.d("TAG", "ID: " + dao.getAll().getId());
            markerList.addAll(dao.getAll().getListLatLng());
            for (int i = 0; i < markerList.size(); i++) {
                markerOptions.position(markerList.get(i));
                gMap.addMarker(markerOptions);
            }
            getLine();
        }

        gMap.setOnMapClickListener(latLng -> {
            markerOptions.position(latLng);
            gMap.addMarker(markerOptions);
            markerList.add(latLng);
            gMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                            CameraPosition.builder()
                                    .zoom(12f)
                                    .target(latLng)
                                    .tilt(30f)
                                    .build()
                    )
            );
        });

        binding.btnPolyline.setOnClickListener(v -> {
            getLine();
            Model model = new Model();
            model.setListLatLng(markerList);
            model.setId(1);
            dao.insert(model);
        });

        binding.btnClear.setOnClickListener(v -> {
            gMap.clear();
            markerList.clear();
        });
    }

    private void getLine() {
        if (!markerList.isEmpty()) {
            gMap.addPolyline(new PolylineOptions().addAll(markerList));
            gMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                            CameraPosition.builder()
                                    .zoom(9f)
                                    .target(markerList.get(markerList.size() - 1))
                                    .tilt(30f)
                                    .build()
                    )
            );
        }
    }
}