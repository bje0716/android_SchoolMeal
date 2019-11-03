package com.grapefruit.schoolmeal.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.grapefruit.schoolmeal.adapters.EduLocationAdapter;
import com.grapefruit.schoolmeal.R;
import com.grapefruit.schoolmeal.utils.Util;
import com.grapefruit.schoolmeal.databinding.ActivityEduLocationBinding;
import com.grapefruit.schoolmeal.utils.PreferenceHelper;

public class EduLocationActivity extends AppCompatActivity {

    private static final String EDU_LOCATION = "edu_location";

    private ActivityEduLocationBinding binding;
    private EduLocationAdapter adapter;
    private PreferenceHelper helper;

    private String[] edu_locations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edu_location);
        binding.toolbar.setPadding(0, Util.getStatusBarHeight(this), 0,0);
        setSupportActionBar(binding.toolbar);

        helper = new PreferenceHelper(this);

        edu_locations = getResources().getStringArray(R.array.edu_location);
        adapter = new EduLocationAdapter(edu_locations);

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setItemAnimator(new DefaultItemAnimator());
        binding.recycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edu_location_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edu_location_submit:
                helper.putString(EDU_LOCATION, adapter.getLocation());
                startActivity(new Intent(this, SchoolSearchActivity.class));
                finish();
                break;
        }
        return true;
    }
}
