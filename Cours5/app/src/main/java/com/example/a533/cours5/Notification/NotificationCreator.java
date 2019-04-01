package com.example.a533.cours5.Notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.a533.cours5.MainActivity;
import com.example.a533.cours5.Notification.model.ImportantMessageModel;
import com.example.a533.cours5.Notification.model.MessageModel;
import com.example.a533.cours5.R;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class NotificationCreator {

    public static Notification createNotificationorMessage(Context context, MessageModel messageModel)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "42")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(messageModel.getSender())
                .setContentText(messageModel.getMessage())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return  builder.build();
    }

    public static Notification createImportantNotificationorMessage(Context context, ImportantMessageModel messageModel)
    {
        Intent snoozeIntent = new Intent(context, MainActivity.class);
        PendingIntent snoozePendingIntent =
                PendingIntent.getActivity(context, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "42")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(messageModel.getSender())
                .setContentText(messageModel.getMessage())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.logo, "Marqué comme lu",
                snoozePendingIntent);
        return  builder.build();
    }
}
