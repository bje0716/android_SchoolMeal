package com.grapefruit.schoolmeal.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.grapefruit.schoolmeal.R;
import com.grapefruit.schoolmeal.databinding.ActivitySignInBinding;
import com.grapefruit.schoolmeal.utils.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        binding.setActivity(this);
        binding.toolbar.setPadding(0, Util.getStatusBarHeight(this), 0, 0);
        setSupportActionBar(binding.toolbar);
    }

    public void submit(View view) {
        String name = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();

        if (name.length() <= 0 && email.length() <= 0 && password.length() <= 0) {
            Toast.makeText(this, "빈 칸을 빠짐없이 전부 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(() -> {
                try {
                    URL url = new URL("https://bje0716.synology.me/meal/JoinAction.php");
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");

                    String parameter = "name=" + name + "&email=" + email + "&password=" + password;
                    OutputStreamWriter os = new OutputStreamWriter(http.getOutputStream(), StandardCharsets.UTF_8);
                    PrintWriter writer = new PrintWriter(os);
                    writer.write(parameter);
                    writer.flush();

                    InputStreamReader is = new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(is);
                    StringBuilder builder = new StringBuilder();
                    String str;

                    while ((str = reader.readLine()) != null) {
                        builder.append(str);
                    }

                    String result = builder.toString();
                    Log.d("result", result);

                    runOnUiThread(() -> {
                        if (result.equals("ok")) {
                            Toast.makeText(SignInActivity.this, name + "님 가입을 축하드립니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (result.equals("failed")) {
                            Toast.makeText(SignInActivity.this, "가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
