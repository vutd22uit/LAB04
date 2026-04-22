package com.example.lab04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SQLiteLoginActivity extends AppCompatActivity {

    EditText edtUser, edtPass;
    Button btnLogin, btnRegister;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_login);

        dbHelper = new DatabaseHelper(this);
        edtUser = findViewById(R.id.edtSqlUsername);
        edtPass = findViewById(R.id.edtSqlPassword);
        btnLogin = findViewById(R.id.btnSqlLogin);
        btnRegister = findViewById(R.id.btnSqlGoToRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u = edtUser.getText().toString().trim();
                String p = edtPass.getText().toString().trim();
                if (dbHelper.checkUser(u, p)) {
                    Toast.makeText(SQLiteLoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(intentWithClear(SQLiteActivity.class));
                } else {
                    Toast.makeText(SQLiteLoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SQLiteLoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private Intent intentWithClear(Class<?> cls) {
        return new Intent(SQLiteLoginActivity.this, cls);
    }
}