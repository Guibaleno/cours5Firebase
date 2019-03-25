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

public class SignUpActivity extends AppCompatActivity {
    Button btnSignUp;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        SetListeners();
    }

    private void SetListeners()
    {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigningUpUser();
            }
        });
    }

    private void SigningUpUser()
    {
        EditText userEmail = findViewById(R.id.txtUsername);
        EditText password = findViewById(R.id.txtPassword);
        EditText passwordConfirmation = findViewById(R.id.txtPasswordConfirmation);

        //if (!password.getText().toString().equals(passwordConfirmation.getText().toString()))
        //{
        //    Toast.makeText(getApplicationContext(), "MDP pas pareils", Toast.LENGTH_SHORT).show();
        //    return;
        //}
        auth.createUserWithEmailAndPassword(userEmail.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    sendUserToMainActivity();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to sign up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToMainActivity()
    {
        Intent sendToSignUporLogin = new Intent(this, SignUpLoginActivity.class);
        startActivity(sendToSignUporLogin);
    }

}
