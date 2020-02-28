package com.mapakampovasrbije.kampmapa.ui;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mapakampovasrbije.kampmapa.R;
import com.mapakampovasrbije.kampmapa.model.CampsiteModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    @BindView(R.id.list_recycler)
    RecyclerView campListRecycler;

    @BindView(R.id.no_locations_text_view)
    TextView noLocationsTextView;

    private View rootView;
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
        ArrayList<CampsiteModel> campList = (ArrayList<CampsiteModel>) getArguments().getSerializable("list");
        adapter = new CampAdapter(getActivity(), campList);
        campListRecycler.setAdapter(adapter);
        campListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (campList != null && !campList.isEmpty()) {
            noLocationsTextView.setVisibility(View.GONE);
        }else {
            noLocationsTextView.setVisibility(View.VISIBLE);
        }

    }
}
