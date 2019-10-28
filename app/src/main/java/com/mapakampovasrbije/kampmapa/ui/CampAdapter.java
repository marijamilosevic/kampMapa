package com.mapakampovasrbije.kampmapa.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        campViewHolder.campAddress.setText(model.getAddress().toString());
        campViewHolder.navigateToCampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                context.startActivity(intent);
            }
        });
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

        @BindView(R.id.navigate_button)
        Button navigateToCampButton;

        public CampViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
