package com.mirea.privalov.mireaproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {

    public PlacesAdapter(List<Place> places) {
        this.places = places;
    }

    private List<Place> places = new ArrayList<>();
    private Place selectedPlace;

    public void addPlace(Place place) {
        places.add(place);
        notifyDataSetChanged();
    }

    public void setSelectedPlace(Place place) {
        selectedPlace = place;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = places.get(position);
        holder.bind(place);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView addressTextView;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_name);
            addressTextView = itemView.findViewById(R.id.text_address);
        }

        public void bind(Place place) {
            nameTextView.setText(place.getName());
            addressTextView.setText(place.getAddress());
        }
    }
}