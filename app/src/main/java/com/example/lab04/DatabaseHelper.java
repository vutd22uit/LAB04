package com.example.lab04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SchoolManager.db";
    private static final int DATABASE_VERSION = 2;

    // Table Users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Table Classes
    public static final String TABLE_CLASSES = "classes";
    public static final String COLUMN_ID = "_id"; // Required for SimpleCursorAdapter
    public static final String COLUMN_CLASS_ID = "malop";
    public static final String COLUMN_CLASS_NAME = "tenlop";
    public static final String COLUMN_CLASS_SIZE = "siso";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(createUsersTable);

        String createClassesTable = "CREATE TABLE " + TABLE_CLASSES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CLASS_ID + " TEXT UNIQUE,"
                + COLUMN_CLASS_NAME + " TEXT,"
                + COLUMN_CLASS_SIZE + " INTEGER" + ")";
        db.execSQL(createClassesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        onCreate(db);
    }

    // --- User methods ---
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, md5(password));
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String encryptedPassword = md5(password);
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, encryptedPassword}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // --- Class methods ---
    public boolean addClass(String malop, String tenlop, int siso) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_ID, malop);
        values.put(COLUMN_CLASS_NAME, tenlop);
        values.put(COLUMN_CLASS_SIZE, siso);
        long result = db.insert(TABLE_CLASSES, null, values);
        return result != -1;
    }

    public boolean updateClass(String malop, String tenlop, int siso) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_NAME, tenlop);
        values.put(COLUMN_CLASS_SIZE, siso);
        int result = db.update(TABLE_CLASSES, values, COLUMN_CLASS_ID + "=?", new String[]{malop});
        return result > 0;
    }

    public boolean deleteClass(String malop) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CLASSES, COLUMN_CLASS_ID + "=?", new String[]{malop});
        return result > 0;
    }

    public Cursor getAllClasses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CLASSES, null);
    }

    // MD5 Encryption
    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}