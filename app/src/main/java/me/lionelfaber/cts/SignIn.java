package me.lionelfaber.cts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();



        setContentView(R.layout.activity_sign_in);

        if(bundle != null)
        {
            Toast.makeText(this,bundle.getString("m"),Toast.LENGTH_LONG).show();
        };

        Button s = (Button)findViewById(R.id.signin);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
