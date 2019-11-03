package com.grapefruit.schoolmeal.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.grapefruit.schoolmeal.R;
import com.grapefruit.schoolmeal.databinding.ActivityLoginBinding;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);
    }

    public void submit(View view) {
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();

        if (email.length() <= 0 && password.length() <= 0) {
            Toast.makeText(this, "빈 칸을 빠짐없이 채워주세요.", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(() -> {
                try {
                    URL url = new URL("https://bje0716.synology.me/meal/LoginAction.php");
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");

                    String parameter = "email=" + email + "&password=" + password;
                    OutputStreamWriter os = new OutputStreamWriter(http.getOutputStream(), "utf-8");
                    PrintWriter writer = new PrintWriter(os);
                    writer.write(parameter);
                    writer.flush();

                    InputStreamReader is = new InputStreamReader(http.getInputStream(), "utf-8");
                    BufferedReader reader = new BufferedReader(is);
                    StringBuilder builder = new StringBuilder();
                    String str;

                    while ((str = reader.readLine()) != null) {
                        builder.append(str);
                    }

                    String result = builder.toString();
                    runOnUiThread(() -> {
                        if (result.equals("ok")) {
                            Toast.makeText(LoginActivity.this, email + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                        } else if (result.equals("failed")) {
                            Toast.makeText(LoginActivity.this, "로그인 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void signUp(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }
}
