package com.example.laba3.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba3.Notification;
import com.example.laba3.NotificationAdapter;
import com.example.laba3.R;
import com.example.laba3.db.ManagerNotification;

import java.util.List;

public class ActivityListNotification extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;
    private ManagerNotification managerNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notification);

        recyclerView = findViewById(R.id.list_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        managerNotification = new ManagerNotification(this);
        managerNotification.openDb();

        notificationList = managerNotification.getAllNotifications();

        notificationAdapter = new NotificationAdapter(notificationList, this);
        recyclerView.setAdapter(notificationAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerNotification.closeDb();
    }
}
