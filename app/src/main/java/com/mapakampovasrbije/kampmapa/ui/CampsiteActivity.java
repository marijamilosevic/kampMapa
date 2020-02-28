package com.mapakampovasrbije.kampmapa.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mapakampovasrbije.kampmapa.CampApp;
import com.mapakampovasrbije.kampmapa.R;
import com.mapakampovasrbije.kampmapa.model.CampsiteModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CampsiteActivity extends AppCompatActivity {

    public static final String EXTRA_CAMPSITE = "extra_campsite";


    @BindView(R.id.camp_description)
    TextView campDescriptionTextView;

    @BindView(R.id.camp_address)
    TextView campAddressTextView;

    @BindView(R.id.camp_web)
    TextView campWebTextView;

    @BindView(R.id.camp_phone)
    TextView campPhoneTextView;

    private CampsiteModel campsiteModel;

    public static Intent createIntent(Context context, CampsiteModel campsiteModel) {
        Intent intent = new Intent(context, CampsiteActivity.class);
        intent.putExtra(EXTRA_CAMPSITE, campsiteModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campsite);

        ButterKnife.bind(this);
        campsiteModel = (CampsiteModel) getIntent().getSerializableExtra(EXTRA_CAMPSITE);
        campAddressTextView.setText("Adresa: " + campsiteModel.getAddress());

        if (TextUtils.isEmpty(campsiteModel.getWebsite())) {
            campWebTextView.setVisibility(View.GONE);
        } else {
            campWebTextView.setText("Sajt: " + campsiteModel.getWebsite());
            campWebTextView.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(campsiteModel.getPhoneNumber())) {
            campPhoneTextView.setVisibility(View.GONE);
        } else {
            campPhoneTextView.setText("Broj telefona: " + campsiteModel.getPhoneNumber());
            campPhoneTextView.setVisibility(View.VISIBLE);
        }
        campDescriptionTextView.setText(campsiteModel.getDescription());
        getSupportActionBar().setTitle(campsiteModel.getName());
    }

    @OnClick(R.id.navigate_button)
    public void navigate() {
        Location currentLocation = CampApp.getInstance().getCurrentLocation();
        if (currentLocation != null) {
            double latFrom = currentLocation.getLatitude();
            double lngFrom = currentLocation.getLongitude();
            double latTo = campsiteModel.getLatitude();
            double lngTo = campsiteModel.getLongitude();
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=" + latFrom + "," + lngFrom + "&daddr=" + latTo + "," + lngTo));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Morate omoguciti pristup lokaciji da biste mogli da koristite navigaciju", Toast.LENGTH_LONG).show();
        }
    }
}
