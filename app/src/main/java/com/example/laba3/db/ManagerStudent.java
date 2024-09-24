package com.example.laba3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ManagerStudent {
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerStudent(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDb() {
            db.close();
    }

    public void resetAndInsertInitialData() {
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM " + ContractStudent.TABLE_NAME);

            String[][] students = {
                    {"Иванов", "Иван", "Иванович"},
                    {"Петров", "Петр", "Петрович"},
                    {"Сидоров", "Сидор", "Сидорович"},
                    {"Кузнецов", "Алексей", "Александрович"},
                    {"Смирнова", "Анна", "Сергеевна"}
            };

            for (String[] student : students) {
                insertToDb(student[0], student[1], student[2]);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void insertToDb(String lastName, String firstName, String middleName) {
        ContentValues cv = new ContentValues();
        cv.put(ContractStudent.COLUMN_LAST_NAME, lastName);
        cv.put(ContractStudent.COLUMN_FIRST_NAME, firstName);
        cv.put(ContractStudent.COLUMN_MIDDLE_NAME, middleName);
        cv.put(ContractStudent.COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(ContractStudent.TABLE_NAME, null, cv);
    }

    public Cursor getAllStudents() {
        return db.rawQuery("SELECT * FROM " + ContractStudent.TABLE_NAME, null);
    }

    public void updateLastStudent(String lastName, String firstName, String middleName) {
        ContentValues cv = new ContentValues();
        cv.put(ContractStudent.COLUMN_LAST_NAME, lastName);
        cv.put(ContractStudent.COLUMN_FIRST_NAME, firstName);
        cv.put(ContractStudent.COLUMN_MIDDLE_NAME, middleName);

        db.update(ContractStudent.TABLE_NAME, cv,
                ContractStudent.COLUMN_ID + " = (SELECT MAX(" + ContractStudent.COLUMN_ID + ") FROM " + ContractStudent.TABLE_NAME + ")",
                null);
    }
}
