package me.lionelfaber.cts;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by lionel on 27/8/17.
 */

public class HomeFragment extends Fragment {

    TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond;
    Handler handler;
    Runnable runnable;
    String uid;
    Button team;
    Boolean teamExists;
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.home_fragment, parent, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {

        txtTimerDay = view.findViewById(R.id.days);
        txtTimerHour = view.findViewById(R.id.hours);
        txtTimerMinute = view.findViewById(R.id.minutes);
        txtTimerSecond = view.findViewById(R.id.seconds);
        team = view.findViewById(R.id.team);
        countDownStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            uid = user.getUid();
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("teams");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(uid)) {
                    team.setText("View my team");
                    teamExists = true;
                }
                else
                {
                    team.setText("Build a team");
                    teamExists = false;

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

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamExists)
                {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.mainframe, new ViewTeam());
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else
                {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.mainframe, new BuildTeam());
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });


    }
    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    Date futureDate = dateFormat.parse("2017-09-30");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText(""
                                + String.format("%02d", minutes));
                        txtTimerSecond.setText(""
                                + String.format("%02d", seconds));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }
}
