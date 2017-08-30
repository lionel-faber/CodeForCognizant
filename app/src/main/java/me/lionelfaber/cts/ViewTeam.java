package me.lionelfaber.cts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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


public class ViewTeam extends Fragment {

    private DatabaseReference mDatabase;
    TextView ln, tm, m1, m2, tt;
    String uid;

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.viewteam, parent, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {

        tt = view.findViewById(R.id.teamname);
        ln = view.findViewById(R.id.leadername);
        tm = view.findViewById(R.id.teammail);
        m1 = view.findViewById(R.id.matename1);
        m2 = view.findViewById(R.id.matename2);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            uid = user.getUid();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child("teams").child(uid);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Team team = dataSnapshot.getValue(Team.class);
                // [START_EXCLUDE]
                ln.setText(team.leader);
                tm.setText(team.email);
                m1.setText(team.tm1);
                m2.setText(team.tm2);
                tt.setText(team.teamname);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);





    }
}
