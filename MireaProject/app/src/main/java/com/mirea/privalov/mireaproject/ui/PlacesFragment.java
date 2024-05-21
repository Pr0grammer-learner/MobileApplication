package com.mirea.privalov.mireaproject.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.privalov.mireaproject.PlacesAdapter;
import com.mirea.privalov.mireaproject.Place;
import com.mirea.privalov.mireaproject.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class PlacesFragment extends Fragment {

    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_places, container, false);

        // Initialize the map
        Configuration.getInstance().load(getActivity(), getActivity().getSharedPreferences("osmdroid", 0));
        mapView = rootView.findViewById(R.id.map_view);
        mapView.getController().setCenter(new GeoPoint(55.751244, 37.618423));
        mapView.getController().setZoom(10.0);
        mapView.setMultiTouchControls(true);

        // Enable zoom controls and multi-touch controls
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        addMapMarkers();

        return rootView;
    }

    // Метод для получения информации о месте по заголовку маркера
    private Place getPlaceByMarkerTitle(String title) {
        List<Place> places = getPlaces(); // Получаем список всех мест

        // Проходимся по всем местам и ищем место с заданным заголовком
        for (Place place : places) {
            if (place.getName().equals(title)) { // Если заголовок места совпадает с заданным заголовком маркера
                return place; // Возвращаем найденное место
            }
        }

        return null; // Если место не найдено, возвращаем null
    }

    private List<Place> getPlaces() {
        List<Place> places = new ArrayList<>();
        // Add your places (e.g., fitness clubs) here
        places.add(new Place("Fitness Club 1", "Address 1", "Качалка Головач Лены", 55.7539, 37.6208));
        places.add(new Place("Fitness Club 2", "Address 2", "Фитнесс Юлии Высоцкой", 55.7558, 37.6176));
        places.add(new Place("Fitness Club 3", "Address 3", "Тенис Паши Техника", 55.7544, 37.6232));
        return places;
    }

    private void addMapMarkers() {
        List<Place> places = getPlaces();
        for (Place place : places) {
            Marker marker = new Marker(mapView);
            marker.setPosition(new GeoPoint(place.getLatitude(), place.getLongitude()));
            marker.setTitle(place.getName() + " " + place.getAddress() + " " + place.getDescription());
            mapView.getOverlays().add(marker);
            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    // Получаем информацию о месте из заголовка маркера
                    String title = marker.getTitle();
                    // Отображаем информацию о месте в виде всплывающего сообщения
                    Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
        mapView.invalidate();
    }

    private void zoomToSpecificPoint(double latitude, double longitude, float zoomLevel) {
        MapController mapController = (MapController) mapView.getController();
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        mapController.setCenter(startPoint);
        mapController.setZoom(zoomLevel);
    }
}