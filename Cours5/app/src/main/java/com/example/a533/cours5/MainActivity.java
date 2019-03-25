package com.example.a533.cours5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        SendUserToSignUpOrLoginActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
    FirebaseUser currentUser = auth.getCurrentUser();
    UdpateUI(currentUser);
    }

    private void UdpateUI(FirebaseUser currentUser)
    {
        if (currentUser == null)
        {

        }
    }

    private void SendUserToSignUpOrLoginActivity()
    {
        Intent sendToSignUporLogin = new Intent(this, SignUpLoginActivity.class);
        startActivity(sendToSignUporLogin);
    }
}
