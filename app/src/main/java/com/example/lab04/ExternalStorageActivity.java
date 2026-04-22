package com.example.lab04;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class ExternalStorageActivity extends AppCompatActivity {

    EditText edtExternalData;
    Button btnSaveExternal, btnReadExternal;
    TextView txtExternalContent;
    String fileName = "external.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        edtExternalData = findViewById(R.id.edtExternalData);
        btnSaveExternal = findViewById(R.id.btnSaveExternal);
        btnReadExternal = findViewById(R.id.btnReadExternal);
        txtExternalContent = findViewById(R.id.txtExternalContent);

        btnSaveExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExternalStorageWritable()) {
                    String data = edtExternalData.getText().toString();
                    saveToExternalStorage(data);
                    edtExternalData.setText("");
                } else {
                    Toast.makeText(ExternalStorageActivity.this, "External Storage không khả dụng để ghi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReadExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExternalStorageReadable()) {
                    String data = readFromExternalStorage();
                    txtExternalContent.setText(data);
                } else {
                    Toast.makeText(ExternalStorageActivity.this, "External Storage không khả dụng để đọc", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private void saveToExternalStorage(String data) {
        File file = new File(getExternalFilesDir(null), fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
            Toast.makeText(this, "Dữ liệu đã lưu vào External Storage", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi lưu file", Toast.LENGTH_SHORT).show();
        }
    }

    private String readFromExternalStorage() {
        File file = new File(getExternalFilesDir(null), fileName);
        if (!file.exists()) {
            return "Không có dữ liệu trong file external.txt";
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            fis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file";
        }
    }
}