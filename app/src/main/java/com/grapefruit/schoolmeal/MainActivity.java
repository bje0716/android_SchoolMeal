package com.grapefruit.schoolmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.grapefruit.schoolmeal.databinding.ActivityMainBinding;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Glide.with(this)
                .load("https://story-img.kakaocdn.net/dn/cfRsj/hyCNW6Hos9/kCi4z6Q2ae5qkFUramEoQK/img_l.jpg?width=420&height=420&avg=%23898784")
                .into(binding.profileImg);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 E요일", Locale.KOREA);
        binding.date.setText(sdf.format(System.currentTimeMillis()));

        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
