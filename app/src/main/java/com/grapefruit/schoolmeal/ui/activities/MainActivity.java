package com.grapefruit.schoolmeal.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grapefruit.schoolmeal.R;
import com.grapefruit.schoolmeal.adapters.MealAdapter;
import com.grapefruit.schoolmeal.databinding.ActivityMainBinding;
import com.grapefruit.schoolmeal.databinding.HeaderBinding;
import com.grapefruit.schoolmeal.datas.MealData;
import com.grapefruit.schoolmeal.datas.MealRequest;
import com.grapefruit.schoolmeal.networks.MealDataParse;
import com.grapefruit.schoolmeal.utils.Constants;
import com.grapefruit.schoolmeal.utils.PreferenceHelper;
import com.grapefruit.schoolmeal.utils.Util;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private HeaderBinding headerBinding;
    private PreferenceHelper helper;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private MealRequest request;
    private ArrayList<MealData> list = new ArrayList<>();
    private MealAdapter adapter;
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> food = new ArrayList<>();

    private String edu_location;
    private String kraOrgNm;
    private String schulCode;
    private String schulCrseScCode;
    private String schulKndScCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        binding.toolbar.setPadding(0, Util.getStatusBarHeight(this), 0, 0);
        setSupportActionBar(binding.toolbar);

        helper = new PreferenceHelper(this);
        kraOrgNm = helper.getString(Constants.SCHOOL_NAME, null);

        // Permission Check
        PermissionListener listener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.d("permission", "granted");
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        if (helper.getBoolean(Constants.PERMISSION, false)) { // 최초 한번 실행
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("앱 사용권한 안내")
                    .setMessage("'오늘의 급식' 앱을 사용하기 위해서는 급식 데이터를 내부 저장소 공간에 저장하기 위해 저장소 권한이 필요합니다. 원할한 앱 사용을 위해 권한을 허용해주세요.")
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> TedPermission.with(getApplicationContext())
                            .setPermissionListener(listener)
                            .setDeniedMessage("[설정] > [권한]에서 권한을 모두 허용해주세요.")
                            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .check()).show();
            helper.putBoolean(Constants.PERMISSION, true);
        }

        auth = FirebaseAuth.getInstance();
        mAuthListener = (FirebaseAuth firebaseAuth) -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            } else {
                headerBinding.name.setText(user.getDisplayName());
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(headerBinding.profileImg);

                edu_location = helper.getString(Constants.EDU_LOCATION, null);
                kraOrgNm = helper.getString(Constants.SCHOOL_NAME, null);
                if (edu_location == null && kraOrgNm == null) {
                    startActivity(new Intent(this, EduLocationActivity.class));
                    finish();
                }
            }
        };

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, R.string.app_name, R.string.app_name);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.nav.setNavigationItemSelectedListener(this);

        kraOrgNm = getIntent().getStringExtra(Constants.SCHOOL_NAME);

        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        headerBinding = DataBindingUtil.bind(header);
        headerBinding.school.setText(kraOrgNm);
        binding.nav.addHeaderView(headerBinding.getRoot());

        Menu menu = binding.nav.getMenu();
        MenuItem meal = menu.findItem(R.id.nav_meal);
        meal.setIcon(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_food));

        MenuItem weather = menu.findItem(R.id.nav_weather);
        weather.setIcon(new IconicsDrawable(this).icon(WeatherIcons.Icon.wic_day_sunny));

        MenuItem notice = menu.findItem(R.id.nav_notice);
        notice.setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_bell));

        MenuItem settings = menu.findItem(R.id.nav_settings);
        settings.setIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_settings));

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setItemAnimator(new DefaultItemAnimator());
    }

    public void reload(View view) {
        edu_location = helper.getString(Constants.EDU_LOCATION, null);
        kraOrgNm = helper.getString(Constants.SCHOOL_NAME, null);
        String schulCode = helper.getString(Constants.SCHOOL_CODE, null);
        String schulCrseScCode = helper.getString(Constants.SCHOOL_CRSE, null);
        String schulKndScCode = helper.getString(Constants.SCHOOL_KND, null);

        request = new MealRequest().setSchYmd(getToday(System.currentTimeMillis()))
                .setInsttNm(kraOrgNm)
                .setSchMmealScCode("2")
                .setSchulKndScCode(schulKndScCode)
                .setSchulCrseScCode(schulCrseScCode)
                .setSchulCode(schulCode);

        if (Util.isNetwork(this)) {
            Util.getCookie(this, edu_location, cookie -> {
                if (list.size() > 0) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
                new MealDataParse(binding, request, object -> {
                    try {
                        JSONObject resultSVO = object.getJSONObject("resultSVO");
                        JSONArray weekDietList = resultSVO.getJSONArray("weekDietList");

                        JSONObject mealDates = weekDietList.getJSONObject(0);
                        date.add(mealDates.getString("sun"));
                        date.add(mealDates.getString("mon"));
                        date.add(mealDates.getString("tue"));
                        date.add(mealDates.getString("wed"));
                        date.add(mealDates.getString("the"));
                        date.add(mealDates.getString("fri"));
                        date.add(mealDates.getString("sat"));

                        JSONObject mealFoods = weekDietList.getJSONObject(2);
                        food.add(replaceMeal(mealFoods.getString("sun")));
                        food.add(replaceMeal(mealFoods.getString("mon")));
                        food.add(replaceMeal(mealFoods.getString("tue")));
                        food.add(replaceMeal(mealFoods.getString("wed")));
                        food.add(replaceMeal(mealFoods.getString("the")));
                        food.add(replaceMeal(mealFoods.getString("fri")));
                        food.add(replaceMeal(mealFoods.getString("sat")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new MealAdapter(new MealData().setDate(date).setFood(food));
                    binding.recycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }).execute(edu_location, cookie);
            });
        } else {
            Toast.makeText(this, "네트워크 연결 상태를 확인하세요.", Toast.LENGTH_SHORT).show();
        }

    }

    public static String replaceMeal(String meal) {
        return meal.replace("<br />", "\n");
    }

    public String getToday(long time) {
        return new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(time);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        helper = new PreferenceHelper(this);
        edu_location = helper.getString(Constants.EDU_LOCATION, null);
        kraOrgNm = helper.getString(Constants.SCHOOL_NAME, null);
        schulCode = helper.getString(Constants.SCHOOL_CODE, null);
        schulCrseScCode = helper.getString(Constants.SCHOOL_CRSE, null);
        schulKndScCode = helper.getString(Constants.SCHOOL_KND, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

        }
        binding.drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
