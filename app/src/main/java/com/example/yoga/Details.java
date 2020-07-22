package com.example.yoga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    private TextView title;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = findViewById(R.id.title_tv);
        description = findViewById(R.id.description_tv);

        Bundle extra = getIntent().getExtras();
        if (extra != null)
        {
            String t = extra.getString("EXTRA_TITLE");
            String d = extra.getString("EXTRA_DESC");
            title.setText(t);
            description.setText(d);
        }


    }
}