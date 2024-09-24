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
        dbHelper.close();
    }

    // Метод для удаления всех записей и добавления 5 новых
    public void resetAndInsertInitialData() {

        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM " + ContractStudent.TABLE_NAME); // Удаляем все записи

            String[] students = {
                    "Иванов Иван Иванович",
                    "Петров Петр Петрович",
                    "Сидоров Сидор Сидорович",
                    "Кузнецов Алексей Александрович",
                    "Смирнова Анна Сергеевна"
            };

            for (String student : students) {
                insertToDb(student);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // Метод для вставки данных в таблицу
    public void insertToDb(String name) {
        ContentValues cv = new ContentValues();
        cv.put(ContractStudent.COLUMN_NAME, name);
        db.insert(ContractStudent.TABLE_NAME, null, cv);
    }

    // Метод для получения всех записей
    public Cursor getAllStudents() {
        return db.rawQuery("SELECT * FROM " + ContractStudent.TABLE_NAME, null);
    }

    // Метод для обновления последней записи
    public void updateLastStudent(String newName) {
        ContentValues cv = new ContentValues();
        cv.put(ContractStudent.COLUMN_NAME, newName);

        db.update(ContractStudent.TABLE_NAME, cv,
                ContractStudent.COLUMN_ID + " = (SELECT MAX(" + ContractStudent.COLUMN_ID + ") FROM " + ContractStudent.TABLE_NAME + ")",
                null);
    }
}
