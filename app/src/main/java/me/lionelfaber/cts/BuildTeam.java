package me.lionelfaber.cts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lionel on 27/8/17.
 */

public class BuildTeam extends Fragment {

    private DatabaseReference mDatabase;
    String name, email, uid, tn, tm1, tm2;
    EditText etn, etm1, etm2;


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.buildteam, parent, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {

        etn = view.findViewById(R.id.team_name);
        etm1 = view.findViewById(R.id.teammate_1);
        etm2 = view.findViewById(R.id.teammate_2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.getDisplayName();
            email = user.getEmail();
            EditText ln = view.findViewById(R.id.leader_name);
            EditText le = view.findViewById(R.id.leader_email);
            ln.setText(name);
            le.setText(email);
        }
        uid = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button maketeam = view.findViewById(R.id.createteam);
        maketeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tn = etn.getText().toString();
                tm1 = etm1.getText().toString();
                tm2= etm2.getText().toString();
                buildTeam(tn, uid, email, name, tm1, tm2);

            }
        });



        }

    private void buildTeam(String teamname, String id, String email, String leader, String tm1, String tm2) {
        Team team = new Team(teamname, id, email, leader, tm1, tm2);

        mDatabase.child("teams").child(id).setValue(team);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, new ViewTeam());
        ft.commit();
    }
}
