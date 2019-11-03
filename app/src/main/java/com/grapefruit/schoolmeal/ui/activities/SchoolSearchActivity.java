package com.grapefruit.schoolmeal.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.grapefruit.schoolmeal.R;
import com.grapefruit.schoolmeal.adapters.SchoolSearchResultAdapter;
import com.grapefruit.schoolmeal.databinding.ActivitySchoolSearchBinding;
import com.grapefruit.schoolmeal.datas.School;
import com.grapefruit.schoolmeal.datas.SchoolRequest;
import com.grapefruit.schoolmeal.networks.SchoolSearchResult;
import com.grapefruit.schoolmeal.utils.Constants;
import com.grapefruit.schoolmeal.utils.PreferenceHelper;
import com.grapefruit.schoolmeal.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SchoolSearchActivity extends AppCompatActivity {

    private ActivitySchoolSearchBinding binding;
    private PreferenceHelper helper;
    private SchoolSearchResultAdapter adapter;
    private ArrayList<School> items = new ArrayList<>();
    private String edu_location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_school_search);
        binding.toolbar.setPadding(0, Util.getStatusBarHeight(this), 0, 0);
        binding.setActivity(this);
        setSupportActionBar(binding.toolbar);
        helper = new PreferenceHelper(this);
        edu_location = helper.getString(Constants.EDU_LOCATION, null);

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setItemAnimator(new DefaultItemAnimator());
    }

    public void submit(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        String search = binding.search.getText().toString();
        SchoolRequest request = new SchoolRequest().setKraOrgNm(search);
        if (search.length() == 0) {
            Toast.makeText(this, "검색할 학교 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
        } else {
            Util.getCookie(this, edu_location, cookie -> {
                if (Util.isNetwork(getApplicationContext())) {
                    if (items.size() > 0) {
                        items.clear();
                        adapter.notifyDataSetChanged();
                    }

                    new SchoolSearchResult(binding, request, object -> {
                        try {
                            JSONObject resultSVO = object.getJSONObject("resultSVO");
                            JSONArray orgDVOList = resultSVO.getJSONArray("orgDVOList");

                            for (int i = 0; i < orgDVOList.length(); i++) {
                                JSONObject j = orgDVOList.getJSONObject(i);
                                JSONObject data = j.getJSONObject("data");

                                String kraOrgNm = data.getString(Constants.SCHOOL_NAME);
                                String zipAdres = data.getString(Constants.SCHOOL_ADDRESS);
                                String schulCode = data.getString("orgCode");
                                String schulKndScCode = data.getString(Constants.SCHOOL_KND);
                                String schulCrseScCode = data.getString(Constants.SCHOOL_CRSE);

                                School school = new School().setKraOrgNm(kraOrgNm)
                                        .setZipAdres(zipAdres)
                                        .setSchulCode(schulCode)
                                        .setSchulKndScCode(schulKndScCode)
                                        .setSchulCrseScCode(schulCrseScCode);

                                items.add(school);

                                adapter = new SchoolSearchResultAdapter(items, this);
                                adapter.notifyDataSetChanged();
                                binding.recycler.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }).execute(edu_location, cookie);
                } else {
                    Toast.makeText(this, "네트워크 연결 상태를 확인하세요.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
