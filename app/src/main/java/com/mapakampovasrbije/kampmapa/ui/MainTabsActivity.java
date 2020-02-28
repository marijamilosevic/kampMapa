package com.mapakampovasrbije.kampmapa.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mapakampovasrbije.kampmapa.R;
import com.mapakampovasrbije.kampmapa.database.DatabaseHelper;
import com.mapakampovasrbije.kampmapa.model.CampsiteModel;

import java.util.ArrayList;

public class MainTabsActivity extends Activity {
    public Fragment currentFragment;
    ArrayList<CampsiteModel> campsiteModelArrayList;
    private FragmentManager fragmentManager;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDataFromDB();
        initUI();
    }

    private void loadDataFromDB() {

        mDBHelper = new DatabaseHelper(this);
        try {
            mDb = mDBHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }


        campsiteModelArrayList = new ArrayList<>();

        Cursor cursor = mDb.query("campsites", null, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            campsiteModelArrayList.add(CampsiteModel.fromCursor(cursor));
            while (cursor.moveToNext()) {
                CampsiteModel model = CampsiteModel.fromCursor(cursor);
                campsiteModelArrayList.add(model);
            }
        }
    }

    private void initUI() {
        fragmentManager = getFragmentManager();
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.list:
                    currentFragment = new ListFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("list", campsiteModelArrayList);
                    currentFragment.setArguments(args);
                    break;
                case R.id.map:
                    currentFragment = new CampMapFragment();
                    Bundle argsMap = new Bundle();
                    argsMap.putSerializable("list", campsiteModelArrayList);
                    currentFragment.setArguments(argsMap);
                    break;
                default:
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, currentFragment).commit();
            return true;
        });
        currentFragment = new CampMapFragment();
        Bundle argsMap = new Bundle();
        argsMap.putSerializable("list", campsiteModelArrayList);
        currentFragment.setArguments(argsMap);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, currentFragment).commit();
    }
}
