package com.example.javier.mot2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        Intent intent = getIntent();
        final String username = intent.getStringExtra(MainActivity.USERNAME);
        ((TextView) findViewById(R.id.username)).setText(username);

        String email = intent.getStringExtra(MainActivity.EMAIL);
        ((TextView) findViewById(R.id.email)).setText(email);

        String dob = intent.getStringExtra(MainActivity.DOB);
        ((TextView) findViewById(R.id.dob)).setText(dob);

        //Populate the list
        final MessagesActivity thisAct = this;
        final ListView listView = (ListView) findViewById(R.id.listView);
        final MessageDao dao = AppDatabase.getAppDB(getApplicationContext()).messageDao();
        new AsyncTask<Void, Void, Message[]>() {
            @Override
            protected Message[] doInBackground(Void... params) {
                return dao.getMessagesByUsername(username);
            }

            @Override
            protected void onPostExecute(Message[] messages) {
                if (messages != null) {
                    listView.setAdapter(new MessageAdapter(thisAct, messages));
                } else {
                    listView.setAdapter(new MessageAdapter(thisAct, new Message[]{}));
                }
            }
        }.execute();
    }

    public void submitMessage(View view) {
        EditText editText = findViewById(R.id.message_input);
        final String message = editText.getText().toString();

        TextView textView = findViewById(R.id.username);
        final String username = textView.getText().toString();

        final Message newMessage = new Message();
        newMessage.setOwner(username);
        newMessage.setMessage(message);
        newMessage.setPublishedAt(new GregorianCalendar().getTime());


        final MessageDao dao = AppDatabase.getAppDB(getApplicationContext()).messageDao();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                dao.insertAll(newMessage);
                return null;
            }

            @Override
            protected void onPostExecute(Void nothing) {
                //Do nothing
            }
        }.execute();


        //Populate the list
        final ListView listView = findViewById(R.id.listView);
        AsyncTask<Void, Void, Message[]> task = new AsyncTask<Void, Void, Message[]>() {
            @Override
            protected Message[] doInBackground(Void... params) {
                return dao.getMessagesByUsername(username);
            }

            @Override
            protected void onPostExecute(Message[] messages) {
                ((MessageAdapter) listView.getAdapter()).setNewData(messages);
            }
        }.execute();

        listView.invalidate();


    }


}


class MessageAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private Message[] objects;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(Utils.DATE_FORMAT);

    public MessageAdapter(@NonNull Context context, @NonNull Message[] objects) {
        super(context, R.layout.list_message, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_message, parent, false);
        TextView messageView = (TextView) rowView.findViewById(R.id.message_field);
        TextView dateView = (TextView) rowView.findViewById(R.id.date_field);
        messageView.setText(objects[position].getMessage());
        dateView.setText(dateFormat.format(objects[position].getPublishedAt()));

        return rowView;
    }

    public void setNewData(Message[] objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }
}