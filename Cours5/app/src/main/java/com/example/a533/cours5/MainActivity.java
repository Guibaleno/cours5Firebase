package com.example.a533.cours5;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a533.cours5.Notification.NotificationService;
import com.example.a533.cours5.Notification.model.MessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListener();
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
    FirebaseUser currentUser = auth.getCurrentUser();
    UdpateUI(currentUser);
    }

    private void StartService()
    {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void UdpateUI(FirebaseUser currentUser)
    {
        if (currentUser == null)
        {
            SendUserToSignUpOrLoginActivity();
        }
        else
        {
            StartService();
        }
    }

    private void SendUserToSignUpOrLoginActivity()
    {
        Intent sendToSignUporLogin = new Intent(this, SignUpLoginActivity.class);
        startActivity(sendToSignUporLogin);
    }

    private void setListener()
    {
        findViewById(R.id.btnSendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage()
    {
        EditText editText = findViewById(R.id.messageContent);
        MessageModel messageModel = new MessageModel(editText.getText().toString(), auth.getCurrentUser().getEmail());
        database.collection("Notification").add(messageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(getApplicationContext(), "messageSent,", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
