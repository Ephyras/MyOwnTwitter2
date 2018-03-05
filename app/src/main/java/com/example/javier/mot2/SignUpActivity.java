package com.example.javier.mot2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    public void onSignUp(View view) {

        EditText editText = findViewById(R.id.uname_field);
        final String username = editText.getText().toString();

        editText = findViewById(R.id.pass_field);
        String password = editText.getText().toString();

        editText = findViewById(R.id.dob_field);
        final String dob = editText.getText().toString();

        final SignUpActivity thisAct = this;

        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, save new user in Freebase
                            String uid = mdatabase.push().getKey();
                            User user = new User();
                            user.setEmail(username);
                            user.setDob(dob);
                            mdatabase.child(uid).setValue(user);
                            Intent intent = new Intent(thisAct, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Exception e = task.getException();
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
