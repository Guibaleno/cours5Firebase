package com.example.a533.cours5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpLoginActivity extends AppCompatActivity {
    Button btnSignup;
    Button btnLogin;
    Button btnLogOut;
    private FirebaseAuth auth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        btnSignup = (Button) findViewById(R.id.btnSignUp);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        auth = FirebaseAuth.getInstance();
        setListener();
    }

    private void setListener()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLogin();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToSignUp();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisconnectUser();
            }
        });
    }

    private void sendToSignUp()
    {
        Intent toSignUp = new Intent(this, SignUpActivity.class);
        startActivity(toSignUp);
    }

    private void sendToLogin()
    {
        Intent toLogin = new Intent(this, MainActivity.class);
        startActivity(toLogin);
    }

    private void DisconnectUser()
    {
        auth.signOut();
        Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_SHORT).show();
    }
}
