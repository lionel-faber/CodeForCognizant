package me.lionelfaber.cts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by lionel on 28/8/17.
 */

public class ProblemStatement  extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.problem, parent, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
