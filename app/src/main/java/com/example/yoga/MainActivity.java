package com.example.yoga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private ArrayList<Workout> workoutArrayList;
    private ArrayList<String> titleList;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);

        workoutArrayList = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        for (int i=0; i<workoutArrayList.size(); i++){
            String str = workoutArrayList.get(i).getTitle();
            titleList.add(str);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);

      lv.setAdapter((ListAdapter) adapter);

      lv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, Details.class);
        String title = workoutArrayList.get(position).getTitle();
        String description = workoutArrayList.get(position).getDescription();

        intent.putExtra("EXTRA_TITLE", title);
        intent.putExtra("EXTRA_DESC", description);

        startActivity(intent);

    }
}