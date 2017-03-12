package com.example.t788340.lscan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Welcome_activity extends Activity {

    private TextView Welcome;
    private Button scan_button;
    private Button view_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);

        this.scan_button = (Button) findViewById(R.id.scan_button);
        this.view_button = (Button) findViewById(R.id.view_button);

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome_activity.this, Abbott.class));
            }
        });



    }
}
