package com.example.lab04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn1 = findViewById(R.id.btnMenuCau1);
        btn2 = findViewById(R.id.btnMenuCau2);
        btn3 = findViewById(R.id.btnMenuCau3);
        btn4 = findViewById(R.id.btnMenuCau4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Câu 1: Shared Preferences Login
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Câu 2: Internal Storage
                Intent intent = new Intent(MenuActivity.this, InternalStorageActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Câu 3: External Storage
                Intent intent = new Intent(MenuActivity.this, ExternalStorageActivity.class);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Câu 4: SQLite Management
                Intent intent = new Intent(MenuActivity.this, SQLiteActivity.class);
                startActivity(intent);
            }
        });
    }
}