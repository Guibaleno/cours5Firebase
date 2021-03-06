package com.example.a533.cours5.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Switch;

import com.example.a533.cours5.MainActivity;
import com.example.a533.cours5.Notification.model.ImportantMessageModel;
import com.example.a533.cours5.Notification.model.MessageModel;
import com.example.a533.cours5.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.icu.text.UnicodeSet.CASE;

public class NotificationService extends Service{
    public static final String CHANNEL_ID = "NotificationService";
    NotificationManager notificationManager;
    FirebaseFirestore database;
    int idNotification = 2;


    @Override
    public void onCreate() {
        createNotificationChannel();
        database = FirebaseFirestore.getInstance();
        //listenForNotificationMessage();
        listenForImportantNotificationMessage();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createNotificationChannel()
    {
        createNotificationChannelService();
        createNotificationChannelMessage();
    }

    private void createNotificationChannelService()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "42";
            CharSequence channelName = "NotificationMessage";
            String channelDescription = "Notification de message";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription(channelDescription);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationServiceIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationServiceIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "42")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Notification Service")
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        startForeground(1, notification);

        return START_STICKY;
    }

    private void listenForNotificationMessage()
    {
        database.collection("Notification").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for (QueryDocumentSnapshot documentMessage : queryDocumentSnapshots)
                {
                    MessageModel messageModel = documentMessage.toObject(MessageModel.class);
                    sendNotificationForMessage(messageModel);
                }
            }
        });
    }

    private void listenForImportantNotificationMessage()
    {
        database.collection("NotificationImportante").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                QueryDocumentSnapshot documentMessage;
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            ImportantMessageModel messageModel = dc.getDocument().toObject(ImportantMessageModel.class);
                            sendImportantNotificationForMessage(messageModel);
                            break;
                        case MODIFIED:
                            break;
                        case REMOVED:
                            Log.d("AHHHHH", "Removed city: " + dc.getDocument().getData());
                            break;
                    }
                }
            }
        });
    }

    private void sendNotificationForMessage(MessageModel messageModel)
    {
        Notification notification = NotificationCreator.createNotificationorMessage(this,messageModel);
        notificationManager.notify(idNotification, notification);
        idNotification ++;
    }

    private void sendImportantNotificationForMessage(ImportantMessageModel messageModel)
    {
        Notification notification = NotificationCreator.createImportantNotificationorMessage(this,messageModel);
        notificationManager.notify(idNotification, notification);
        idNotification ++;
    }


    private void createNotificationChannelMessage()
    {

    }
}
