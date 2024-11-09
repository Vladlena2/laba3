package com.example.laba3.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laba3.R;

public class FullNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_notification);

        String title = getIntent().getStringExtra("notification_title");
        String message = getIntent().getStringExtra("notification_message");
        String date = getIntent().getStringExtra("notification_date"); // Получаем дату

        TextView titleTextView = findViewById(R.id.name);
        TextView messageTextView = findViewById(R.id.text);
        TextView dateTextView = findViewById(R.id.date);

        titleTextView.setText(title);
        messageTextView.setText(message);
        dateTextView.setText(date);
    }
}