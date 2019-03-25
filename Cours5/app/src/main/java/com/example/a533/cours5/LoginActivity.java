package com.example.a533.cours5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    private FirebaseAuth auth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        btnLogin = (Button) findViewById(R.id.btnLogin);

        SetListeners();
    }

    private void SetListeners()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectingUser();
            }
        });


    }

    private void ConnectingUser()
    {
        EditText userEmail = findViewById(R.id.txtUsername);
        EditText password = findViewById(R.id.txtPassword);

        //if (!password.getText().toString().equals(passwordConfirmation.getText().toString()))
        //{
        //    Toast.makeText(getApplicationContext(), "MDP pas pareils", Toast.LENGTH_SHORT).show();
        //    return;
        //}
        auth.signInWithEmailAndPassword(userEmail.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    currentUser = auth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "Logged in!", Toast.LENGTH_SHORT).show();
                    UdpateUI(currentUser);
                    sendUserToMainActivity();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to sign up", Toast.LENGTH_SHORT).show();
                    UdpateUI(null);
                }
            }
        });
    }



    private void UdpateUI(FirebaseUser currentUser)
    {
        if (currentUser == null)
        {

        }
    }

    private void sendUserToMainActivity()
    {
        Intent sendToSignUporLogin = new Intent(this, SignUpLoginActivity.class);
        startActivity(sendToSignUporLogin);
    }
}
