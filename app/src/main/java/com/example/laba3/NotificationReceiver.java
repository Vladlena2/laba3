package com.example.laba3;

import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.laba3.activity.FullNotificationActivity;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("notification_title");
        String message = intent.getStringExtra("notification_message");

        // Создаем уведомление
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)  // Иконка для уведомления
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH); // Повышенный приоритет для тестирования

        // Настройка действия при нажатии на уведомление
        Intent notificationIntent = new Intent(context, FullNotificationActivity.class);
        notificationIntent.putExtra("notification_title", title);
        notificationIntent.putExtra("notification_message", message);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        builder.setContentIntent(pendingIntent); // Открытие активности при нажатии

        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        } else {
            Toast.makeText(context, "Ошибка: NotificationManager не найден", Toast.LENGTH_SHORT).show();
        }
    }
}
