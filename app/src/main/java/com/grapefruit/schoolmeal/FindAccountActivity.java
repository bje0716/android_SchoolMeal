package com.grapefruit.schoolmeal;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.grapefruit.schoolmeal.databinding.ActivityFindAccountBinding;
import com.mikepenz.iconics.context.IconicsContextWrapper;

public class FindAccountActivity extends AppCompatActivity {

    private ActivityFindAccountBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_account);
        binding.setActivity(this);
    }

    public void submit(View v) {
        Snackbar snackbar = Snackbar.make(v, "찾고자 하는 계정이 없습니다. 다시 입력하세요.", Snackbar.LENGTH_LONG);
        View sb = snackbar.getView();
        sb.setBackgroundColor(Color.RED);
        snackbar.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
