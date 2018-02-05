package com.example.javier.mot2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME = "com.example.javier.USERNAME";
    public static final String EMAIL = "com.example.javier.EMAIL";
    public static final String DOB = "com.example.javier.DOB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        final UserDao dao = AppDatabase.getAppDB(getApplicationContext()).userDao();

        final MainActivity thisAct = this;

        //Taken from https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... params) {
                return dao.getUserByUsername(username);
            }

            @Override
            protected void onPostExecute(User possibleUser) {
                if (possibleUser == null) {
                    showError();

                } else {

                    byte[] salt = possibleUser.getSalt();
                    String storedHash = possibleUser.getHash();

                    String otherHash = Utils.getSecurePassword(password, salt);

                    if (storedHash.equals(otherHash)) {
                        Intent intent = new Intent(thisAct, MessagesActivity.class);
                        intent.putExtra(USERNAME,username);
                        intent.putExtra(EMAIL,possibleUser.getEmail());
                        intent.putExtra(DOB,possibleUser.getDob());
                        startActivity(intent);
                    } else {
                        showError();
                    }
                }
            }
        }.execute();


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
