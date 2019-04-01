package com.example.a533.cours5.Notification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.a533.cours5.Notification.model.MessageModel;
import com.example.a533.cours5.R;

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

}
