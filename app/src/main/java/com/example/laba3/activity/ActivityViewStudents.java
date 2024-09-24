package com.example.laba3.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.laba3.R;
import com.example.laba3.db.ManagerStudent;

public class ActivityViewStudents extends AppCompatActivity {

    private ManagerStudent managerStudent;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        init();
        managerStudent.openDb();

        String studentsData = getStudentsData();
        textView.setText(studentsData);

        managerStudent.closeDb();
    }

    private void init() {
        textView = findViewById(R.id.text_view_students);
        managerStudent = new ManagerStudent(this);
    }

    private String getStudentsData() {
        StringBuilder stringBuilder = new StringBuilder();
        Cursor cursor = null;

        try {
            cursor = managerStudent.getAllStudents();
            while (cursor.moveToNext()) {
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("LastName"));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("FirstName"));
                String middleName = cursor.getString(cursor.getColumnIndexOrThrow("SureName"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("TIMESTAMP"));

                stringBuilder.append("ФИО: ").append(lastName).append(" ").append(firstName).append(" ").append(middleName).append("\n");
                stringBuilder.append("Дата добавления: ").append(timestamp).append("\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            stringBuilder.append("Ошибка при получении данных: ").append(e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return stringBuilder.toString();
    }

}
