package com.example.javier.mot2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.security.SecureRandom;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onSignUp(View view) {

        EditText editText = findViewById(R.id.uname_field);
        String username = editText.getText().toString();

        editText = findViewById(R.id.pass_field);
        String password = editText.getText().toString();

        editText = findViewById(R.id.email_field);
        String email = editText.getText().toString();

        editText = findViewById(R.id.dob_field);
        String dob = editText.getText().toString();

        createUser(username, password, email, dob);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void createUser(String username, String password, String email, String dob) {

        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[20];
        random.nextBytes(salt);
        String securePass = Utils.getSecurePassword(password, salt);

        final User newUser = new User();
        newUser.setUsername(username);
        newUser.setSalt(salt);
        newUser.setHash(securePass);
        newUser.setEmail(email);
        newUser.setDob(dob);

        final UserDao dao = AppDatabase.getAppDB(getApplicationContext()).userDao();

        //Taken from https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                dao.insertAll(newUser);
                return null;
            }
        }.execute();


    }


}
