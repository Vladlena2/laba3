package com.example.laba3.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laba3.R;
import com.example.laba3.db.ManagerStudent;

public class MainActivity extends AppCompatActivity {

    private ManagerStudent managerStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupDatabase();

        managerStudent.closeDb();
    }

    private void init() {
        managerStudent = new ManagerStudent(this);
    }

    private void setupDatabase() {
        managerStudent.openDb();
        managerStudent.resetAndInsertInitialData();
    }

    public void setupViewButton(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityViewStudents.class);
        startActivity(intent);
    }

    public void setupAddButton(View view) {
        managerStudent.insertToDb("Новиков Андрей Владимирович");
    }

    public void setupUpdateButton(View view) {
        managerStudent.updateLastStudent("Иванов Иван Иванович");
    }
}
