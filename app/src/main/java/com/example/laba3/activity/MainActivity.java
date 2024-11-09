package com.example.laba3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.laba3.NotificationReceiver;
import com.example.laba3.R;
import com.example.laba3.db.ManagerNotification;
import com.example.laba3.db.ManagerStudent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ManagerStudent managerStudent;
    private ManagerNotification managerNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel(); // Создаем канал уведомлений
        init();
        setupDatabase();
    }

    private void init() {
        managerStudent = new ManagerStudent(this);
        managerNotification = new ManagerNotification(this);
    }

    private void setupDatabase() {
        managerStudent.openDb();
        managerNotification.openDb();
        managerStudent.resetAndInsertInitialData();
    }

    public void setupViewButton(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityViewStudents.class);
        startActivity(intent);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Установить выбранную дату
                    calendar.set(Calendar.YEAR, selectedYear);
                    calendar.set(Calendar.MONTH, selectedMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    showTimePickerDialog(calendar);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    // Установить выбранное время
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);
                    scheduleNotification(calendar); // Теперь вызываем метод для планирования уведомления
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private String getFormattedDateTime(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    private void scheduleNotification(Calendar calendar) {
        String title = "Напоминание";
        String message = "Посмотрите измененный список студентов";

        // Вставляем уведомление в базу данных
        long id = managerNotification.insertToDb(title, message, getFormattedDateTime(calendar));

        if (id != -1) {
            // Создаем Intent для BroadcastReceiver
            Intent intent = new Intent(this, NotificationReceiver.class);
            intent.putExtra("notification_title", title);
            intent.putExtra("notification_message", message);
            intent.putExtra("notification_date", getFormattedDateTime(calendar));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            // Устанавливаем AlarmManager
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                try {
                    // Используем setExact для установки точного времени срабатывания
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    Toast.makeText(this, "Уведомление запланировано", Toast.LENGTH_SHORT).show();
                } catch (SecurityException e) {
                    // Обрабатываем исключение, если нет разрешения
                    Toast.makeText(this, "Ошибка: нет разрешения на установку точных будильников.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(this, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }


    public void setupAddButton(View view) {
        managerStudent.insertToDb("Новиков Андрей Владимирович");
        showDatePickerDialog();
    }

    public void setupUpdateButton(View view) {
        managerStudent.updateLastStudent("Иванов Иван Иванович");
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("notify_channel", "Напоминания", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setupListNotification(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityListNotification.class);
        startActivity(intent);
    }
}
