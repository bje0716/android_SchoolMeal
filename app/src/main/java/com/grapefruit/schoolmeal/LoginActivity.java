package com.grapefruit.schoolmeal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.grapefruit.schoolmeal.databinding.ActivityLoginBinding;
import com.mikepenz.iconics.context.IconicsContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);
    }

    public void submit(View v) {
        Snackbar.make(v, "기능 미구현", Snackbar.LENGTH_SHORT).show();
    }

    public void signIn(View v) {
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void gso(View v) {

    }

    public void fb(View v) {

    }

    public void findAccount(View v) {
        startActivity(new Intent(this, FindAccountActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
