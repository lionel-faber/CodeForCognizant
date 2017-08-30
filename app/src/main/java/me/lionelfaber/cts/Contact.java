package me.lionelfaber.cts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by lionel on 28/8/17.
 */

public class Contact  extends Fragment {

    EditText input;
    FloatingActionButton fab;
    FirebaseUser user;
    ListView listOfMessages;
    private FirebaseListAdapter<ChatMessage> adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.contact, parent, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        fab = view.findViewById(R.id.fab);
        input = view.findViewById(R.id.input);
        listOfMessages = view.findViewById(R.id.list_of_messages);
        user = FirebaseAuth.getInstance().getCurrentUser();

        adapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("chats");
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(user.getDisplayName())) {

                        }
                        else
                        {
                            rootRef.child(user.getDisplayName()).setValue(new Chat(user.getUid()));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        Toast.makeText(getActivity(), "Failed to load post.",
                                Toast.LENGTH_SHORT).show();
                        // [END_EXCLUDE]
                    }
                });

                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(user.getDisplayName())
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),user.getDisplayName())
                        );

                // Clear the input
                input.setText("");
            }
        });


    }
}
