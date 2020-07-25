package com.example.yogacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

public class Beginner extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    private ListView lv;
    private ArrayList<Workout> workoutArrayList;
    private ArrayList<String> titleList;
    private Adapter adapter;
    private TextView startExercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
        lv = findViewById(R.id.lv);
        startExercise = findViewById(R.id.startExercise);

        workoutArrayList = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        for (int i = 0; i < workoutArrayList.size(); i++) {
            String str = workoutArrayList.get(i).getTitle();
            titleList.add(str);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the current item from ListView
                View view = super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 225;
                view.setLayoutParams(params);

                view.setBackgroundResource(R.drawable.rounded_button);

                return view;
            }
        };

        lv.setAdapter((ListAdapter) adapter);

        lv.setOnItemClickListener(this);
        startExercise.setOnClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Beginner.this, Details.class);
        String title = workoutArrayList.get(position).getTitle();
        String description = workoutArrayList.get(position).getDescription();
        String link = workoutArrayList.get(position).getLink();

        intent.putExtra("EXTRA_TITLE", title);
        intent.putExtra("EXTRA_DESC", description);
        intent.putExtra("EXTRA_LINK", link);

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, StartWorkout.class);
        startActivity(intent);
    }
}