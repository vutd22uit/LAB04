package com.example.lab04;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SQLiteActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText edtMaLop, edtTenLop, edtSiSo;
    Button btnAdd, btnUpdate, btnDelete;
    ListView lvClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        dbHelper = new DatabaseHelper(this);

        edtMaLop = findViewById(R.id.edtMaLop);
        edtTenLop = findViewById(R.id.edtTenLop);
        edtSiSo = findViewById(R.id.edtSiSo);
        btnAdd = findViewById(R.id.btnAddClass);
        btnUpdate = findViewById(R.id.btnUpdateClass);
        btnDelete = findViewById(R.id.btnDeleteClass);
        lvClasses = findViewById(R.id.lvClasses);

        loadClasses();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = edtMaLop.getText().toString();
                String ten = edtTenLop.getText().toString();
                String sisoStr = edtSiSo.getText().toString();

                if (ma.isEmpty() || ten.isEmpty() || sisoStr.isEmpty()) {
                    Toast.makeText(SQLiteActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.addClass(ma, ten, Integer.parseInt(sisoStr))) {
                    Toast.makeText(SQLiteActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadClasses();
                    clearFields();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = edtMaLop.getText().toString();
                String ten = edtTenLop.getText().toString();
                String sisoStr = edtSiSo.getText().toString();

                if (dbHelper.updateClass(ma, ten, Integer.parseInt(sisoStr))) {
                    Toast.makeText(SQLiteActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadClasses();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = edtMaLop.getText().toString();
                if (dbHelper.deleteClass(ma)) {
                    Toast.makeText(SQLiteActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    loadClasses();
                    clearFields();
                }
            }
        });

        lvClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) lvClasses.getItemAtPosition(i);
                // ma_lop ở cột index 1 (do _id là index 0)
                edtMaLop.setText(cursor.getString(1));
                edtTenLop.setText(cursor.getString(2));
                edtSiSo.setText(cursor.getString(3));
            }
        });
    }

    private void loadClasses() {
        Cursor cursor = dbHelper.getAllClasses();
        String[] from = new String[]{DatabaseHelper.COLUMN_CLASS_ID, DatabaseHelper.COLUMN_CLASS_NAME, DatabaseHelper.COLUMN_CLASS_SIZE};
        int[] to = new int[]{R.id.tvMa, R.id.tvTen, R.id.tvSiSo};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_class, cursor, from, to, 0);
        lvClasses.setAdapter(adapter);
    }

    private void clearFields() {
        edtMaLop.setText("");
        edtTenLop.setText("");
        edtSiSo.setText("");
    }
}