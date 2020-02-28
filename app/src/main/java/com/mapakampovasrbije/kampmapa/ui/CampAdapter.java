package com.mapakampovasrbije.kampmapa.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mapakampovasrbije.kampmapa.CampApp;
import com.mapakampovasrbije.kampmapa.R;
import com.mapakampovasrbije.kampmapa.model.CampsiteModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampAdapter extends RecyclerView.Adapter<CampAdapter.CampViewHolder> {

    List<CampsiteModel> campList;

    Context context;

    public CampAdapter(Context context, List<CampsiteModel> campList) {
        this.campList = campList;
        this.context = context;
    }

    @NonNull
    @Override
    public CampViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_campsite, viewGroup, false);
        return new CampViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CampViewHolder campViewHolder, int i) {
        CampsiteModel model = campList.get(i);
        campViewHolder.campName.setText(model.getName());
        campViewHolder.campAddress.setText(model.getAddress());
        campViewHolder.campDescription.setText(model.getDescription());
        campViewHolder.navigateToCampButton.setOnClickListener(view -> navigateTo(model));
        campViewHolder.itemView.setOnClickListener(v -> {
            Intent campIntent = CampsiteActivity.createIntent(context, model);
            context.startActivity(campIntent);
        });
    }

    private void navigateTo(CampsiteModel campsiteModel) {
        Location currentLocation = CampApp.getInstance().getCurrentLocation();
        if (currentLocation != null) {
            double latFrom = currentLocation.getLatitude();
            double lngFrom = currentLocation.getLongitude();

            double latTo = campsiteModel.getLatitude();
            double lngTo = campsiteModel.getLongitude();
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=" + latFrom + "," + lngFrom + "&daddr=" + latTo + "," + lngTo));
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Morate omoguciti pristup lokaciji da biste mogli da koristite navigaciju", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return campList != null ? campList.size() : 0;
    }

    static class CampViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.camp_name)
        TextView campName;

        @BindView(R.id.camp_address)
        TextView campAddress;

        @BindView(R.id.camp_description)
        TextView campDescription;

        @BindView(R.id.navigate_button)
        TextView navigateToCampButton;

        public CampViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
