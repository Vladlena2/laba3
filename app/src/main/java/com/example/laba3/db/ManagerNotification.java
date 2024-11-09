package com.example.laba3.db;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.laba3.Notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ManagerNotification {
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerNotification(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDb() {
        dbHelper.close();
    }

    public long insertToDb(String name, String text, String dateTime) {
        ContentValues cv = new ContentValues();
        cv.put(ContractNotification.COLUMN_NAME, name);
        cv.put(ContractNotification.COLUMN_TEXT, text);
        cv.put(ContractNotification.COLUMN_TIMESTAMP, dateTime);
        return db.insert(ContractNotification.TABLE_NAME, null, cv);
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();

        Cursor cursor = db.query(ContractNotification.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            // Проверка наличия данных в курсоре
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(ContractNotification.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(ContractNotification.COLUMN_NAME);
                int textIndex = cursor.getColumnIndex(ContractNotification.COLUMN_TEXT);
                int timestampIndex = cursor.getColumnIndex(ContractNotification.COLUMN_TIMESTAMP);

                // Проверка на корректность индексов
                if (idIndex >= 0 && nameIndex >= 0 && textIndex >= 0 && timestampIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String title = cursor.getString(nameIndex);
                    String message = cursor.getString(textIndex);
                    String date = cursor.getString(timestampIndex);

                    Notification notification = new Notification(id, title, message, date);
                    notifications.add(notification);
                }
            }
            cursor.close();
        }

        return notifications;
    }

    public void deleteNotification(int id) {
        String whereClause = ContractNotification.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        db.delete(ContractNotification.TABLE_NAME, whereClause, whereArgs);
    }
}
