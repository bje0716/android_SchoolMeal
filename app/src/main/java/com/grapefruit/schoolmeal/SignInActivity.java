package com.grapefruit.schoolmeal;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.grapefruit.schoolmeal.databinding.ActivitySigninBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private static final int GALLERY = 0;
    private static final int CAMERA = 1;

    private static final int REQUEST_GALLERY = 100;
    private static final int REQUEST_CAMERA = 200;

    private ActivitySigninBinding binding;
    private PreferenceHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        binding.setActivity(this);

        helper = new PreferenceHelper(this);

        binding.spinner.setItems(getResources().getStringArray(R.array.edu_location));
        binding.spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String location = Util.selectEduLocation(item.toString());
                helper.putString(Constants.EDU_LOCATION, location);
            }
        });
    }

    public void term(View v) {
        new AlertDialog.Builder(this)
                .setTitle("회원약관")
                .setMessage("본 앱은 회원관리를 이러이러한 형식으로 관리할것입니다.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (!binding.check.isChecked()) binding.check.setChecked(true);
                    }
                }).show();
    }

    public void submit(View v) {
        String name = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        String birthday = binding.birthday.getText().toString();

        if (password.length() < 6) {
            Snackbar.make(v, "패스워드 6자리 이상 입력하세요.", Snackbar.LENGTH_SHORT).show();
        }

        if (!binding.check.isChecked()) {
            Snackbar.make(v, "회원약관에 동의해주세요.", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void selectImg(View v) {
        String[] items = {"갤러리에서 사진 가져오기", "사진 찍기"};
        PermissionListener listener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.d(Constants.PERMISSION, "permission granted");

                new AlertDialog.Builder(v.getContext())
                        .setTitle("프로필 사진 선택")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        startActivityForResult(new Intent(Intent.ACTION_PICK).setType(MediaStore.Images.Media.CONTENT_TYPE), REQUEST_GALLERY);
                                        break;
                                    case 1:
                                        break;
                                }
                            }
                        }).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Log.d(Constants.PERMISSION, deniedPermissions.toString());
            }
        };

        TedPermission.with(this)
                .setPermissionListener(listener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setDeniedMessage("[설정] > [권한]에 들어가서 권한을 허용해주세요.")
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
           switch (requestCode) {
               case REQUEST_GALLERY:
                   if (data.getData() != null) binding.photo.setImageURI(data.getData());
                   break;
               case REQUEST_CAMERA:
                   break;
           }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
