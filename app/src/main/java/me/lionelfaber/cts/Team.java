package me.lionelfaber.cts;

/**
 * Created by lionel on 28/8/17.
 */

public class Team {

    public String teamname, id, email, leader, tm1, tm2;

    public Team() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Team(String teamname, String id, String email, String leader, String tm1, String tm2) {
        this.teamname = teamname;
        this.id = id;
        this.email = email;
        this.leader = leader;
        this.tm1 = tm1;
        this.tm2 = tm2;
    }


}

