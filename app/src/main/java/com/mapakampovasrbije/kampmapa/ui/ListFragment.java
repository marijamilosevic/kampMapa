package com.mapakampovasrbije.kampmapa.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mapakampovasrbije.kampmapa.R;
import com.mapakampovasrbije.kampmapa.model.Address;
import com.mapakampovasrbije.kampmapa.model.CampsiteModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    private View rootView;

    @BindView(R.id.list_recycler)
    RecyclerView campListRecycler;

    @BindView(R.id.no_locations_text_view)
    TextView textView;

    private CampAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<CampsiteModel> campList = new ArrayList<>();
        CampsiteModel model = new CampsiteModel(1, "Kamp 1 ", "Teast opis", new Address("Ulica i broj", new LatLng(12, 22)));
        CampsiteModel model2 = new CampsiteModel(1, "Kamp 2 ", "Teast opis", new Address("Ulica i broj", new LatLng(12, 22)));
        CampsiteModel model3 = new CampsiteModel(1, "Kamp 3 ", "Teast opis", new Address("Ulica i broj", new LatLng(12, 22)));
        CampsiteModel model4 = new CampsiteModel(1, "Kamp 4 ", "Teast opis", new Address("Ulica i broj", new LatLng(12, 22)));
        CampsiteModel model5 = new CampsiteModel(1, "Kamp 5 ", "Teast opis", new Address("Ulica i broj", new LatLng(12, 22)));
        CampsiteModel model6 = new CampsiteModel(1, "Kamp 6 ", "Teast opis", new Address("Ulica i broj", new LatLng(12, 22)));

        campList.add(model);
        campList.add(model2);
        campList.add(model3);
        campList.add(model4);
        campList.add(model5);
        campList.add(model6);
        adapter = new CampAdapter(getActivity(), campList);
        campListRecycler.setAdapter(adapter);
        campListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
