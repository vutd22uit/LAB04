package com.example.lab04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    CheckBox cbRemember;
    Button btnConfirm, btnOpenSettings;
    LinearLayout mainLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo views
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnOpenSettings = findViewById(R.id.btnOpenSettings);
        mainLayout = findViewById(R.id.main_layout);

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        // Lấy lại giá trị đã lưu nếu có
        edtUsername.setText(sharedPreferences.getString("username", ""));
        edtPassword.setText(sharedPreferences.getString("password", ""));
        cbRemember.setChecked(sharedPreferences.getBoolean("checked", false));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // Kiểm tra đăng nhập
                if (username.equals("minhtien") && password.equals("1234")) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (cbRemember.isChecked()) {
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.putBoolean("checked", true);
                    } else {
                        editor.remove("username");
                        editor.remove("password");
                        editor.remove("checked");
                    }
                    editor.apply();
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật màu nền dựa trên Preference
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRed = pref.getBoolean("color_checked", false);
        if (isRed) {
            mainLayout.setBackgroundColor(Color.RED);
        } else {
            mainLayout.setBackgroundColor(Color.CYAN);
        }
    }
}