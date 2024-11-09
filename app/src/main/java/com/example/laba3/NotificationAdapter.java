package com.example.laba3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba3.db.ManagerNotification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;
    private Context context;
    private ManagerNotification managerNotification;

    public NotificationAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
        this.managerNotification = new ManagerNotification(context);
        managerNotification.openDb();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.titleTextView.setText(notification.getTitle());
        holder.messageTextView.setText(notification.getMessage());
        holder.dateTextView.setText(notification.getDate());

        // Обработчик нажатия на элемент списка
        holder.itemView.setOnClickListener(v -> {
            // Показать диалог подтверждения удаления
            showDeleteConfirmationDialog(notification, position);
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView messageTextView;
        TextView dateTextView;

        NotificationViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notification_title);
            messageTextView = itemView.findViewById(R.id.notification_message);
            dateTextView = itemView.findViewById(R.id.notification_date);
        }
    }

    private void showDeleteConfirmationDialog(Notification notification, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Удалить уведомление")
                .setMessage("Вы уверены, что хотите удалить это уведомление?")
                .setPositiveButton("Удалить", (dialog, which) -> {
                    managerNotification.deleteNotification(notification.getId()); // Метод удаления из БД
                    removeItem(position); // Метод удаления из списка

                    Intent intent = new Intent(context, NotificationReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    if (alarmManager != null) {
                        alarmManager.cancel(pendingIntent); // Отменяем запланированное уведомление
                        pendingIntent.cancel(); // Отменяем сам PendingIntent
                        Toast.makeText(context, "Запланированное уведомление отменено", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public void removeItem(int position) {
        notificationList.remove(position);
        notifyItemRemoved(position);
    }

}
