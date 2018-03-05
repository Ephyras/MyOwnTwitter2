package com.example.javier.mot2;

import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private final List<Message> messageList = new ArrayList<>();
    private ArrayAdapter adapter;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(Utils.DATE_FORMAT);
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Intent intent = getIntent();
        final String username = intent.getStringExtra(MainActivity.USERNAME);
        ((TextView) findViewById(R.id.username)).setText(username);

        final TextView dobField = findViewById(R.id.dob);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        usersRef.orderByChild("email").equalTo(username).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                dobField.setText(user.getDob());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO Handle this
            }
        });


        //Populate the list
        mDatabase = database.getReference("messages");

        final ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new MessageListAdapter(this, messageList);
        listView.setAdapter(adapter);

        mDatabase.orderByChild("publishedAt").limitToFirst(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList.clear();
                Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                while (iter.hasNext()) {
                    messageList.add(iter.next().getValue(Message.class));
                }
                Collections.reverse(messageList);
                listView.invalidateViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO Handle this
            }
        });


    }

    public void submitMessage(View view) {
        EditText editText = findViewById(R.id.message_input);
        final String message = editText.getText().toString();

        TextView textView = findViewById(R.id.username);
        final String username = textView.getText().toString();

        final Message newMessage = new Message();
        newMessage.setAuthor(username);
        newMessage.setMessage(message);
        newMessage.setPublishedAt(new GregorianCalendar().getTimeInMillis());
        mDatabase.child(mDatabase.push().getKey()).setValue(newMessage);

        editText.getText().clear();
    }
}


class MessageListAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private List<Message> messages;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(Utils.DATE_FORMAT);


    public MessageListAdapter(@NonNull Context context, @NonNull List<Message> messages) {
        super(context, R.layout.list_message, messages);
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_message, parent, false);
        TextView messageView = (TextView) rowView.findViewById(R.id.message_field);
        TextView dateView = (TextView) rowView.findViewById(R.id.date_field);
        TextView userView = (TextView) rowView.findViewById(R.id.usersay);

        userView.setText(messages.get(position).getAuthor());
        messageView.setText(messages.get(position).getMessage());
        dateView.setText(dateFormat.format(new Date(messages.get(position).getPublishedAt())));

        return rowView;
    }
}