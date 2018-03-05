package com.example.javier.mot2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME = "com.example.javier.USERNAME";
    public static final String EMAIL = "com.example.javier.EMAIL";
    public static final String DOB = "com.example.javier.DOB";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }


    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void logIn(View view) {

        EditText editText = findViewById(R.id.user_field);
        final String username = editText.getText().toString();

        editText = findViewById(R.id.pass_field);
        final String password = editText.getText().toString();

        final MainActivity thisAct = this;

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(thisAct, MessagesActivity.class);
                            intent.putExtra(USERNAME,username);
                            intent.putExtra(EMAIL,"pending");
                            intent.putExtra(DOB,"pending");
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            showError();
                        }

                        // ...
                    }
                });

    }

    private void showError() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Error")
                .setMessage("Invalid Username or Password")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Whatever...
                    }
                }).show();
    }
}
