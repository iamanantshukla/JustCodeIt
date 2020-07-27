package com.example.yogacommunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

public class Beginner extends AppCompatActivity {


    private ListView lv;
    private static final String TAG = "Beginner";
    private ArrayList<Workout> workoutArrayList;
    private ArrayList<String> titleList, expressList;
    private Adapter adapter, adapterExpress;
    private TextView startExercise, express, tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
        lv = findViewById(R.id.lv);
        startExercise = findViewById(R.id.startExercise);
        express = findViewById(R.id.weightLoss_tv);
        tv = findViewById(R.id.tv);


        workoutArrayList = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        for (int i = 0; i < workoutArrayList.size(); i++) {
            String str = workoutArrayList.get(i).getTitle();
            titleList.add(str);
        }
        expressList = new ArrayList<>();
        for (int i =0; i < workoutArrayList.size(); i++){
            String str = workoutArrayList.get(i).getTitleExpress();
            expressList.add(str);
        }


        adapter = new ArrayAdapter<String>(this, R.layout.item, titleList) {
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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Beginner.this, Details.class);
                String title = workoutArrayList.get(position).getTitle();
                String description = workoutArrayList.get(position).getDescription();
                String link = workoutArrayList.get(position).getLink();
                String i = "Apple";
                intent.putExtra("EXTRA_TITLE", title);
                intent.putExtra("EXTRA_DESC", description);
                intent.putExtra("EXTRA_LINK", link);
                intent.putExtra("V", i);
                startActivity(intent);
            }
        });

        startExercise.getBackground().setAlpha(100);
        startExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Beginner.this, "Select one workout", Toast.LENGTH_SHORT).show();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setTextColor(ContextCompat.getColor(Beginner.this, R.color.bg));
                express.setTextColor(ContextCompat.getColor(Beginner.this, R.color.grey_unselected));

                startExercise.getBackground().setAlpha(255);
                final String t = "B";
                startExercise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Beginner.this, StartWorkout.class);
                        Log.d(TAG, "onClick: t = "+t);
                        intent.putExtra("T",t);
                        startActivity(intent);
                    }
                });

                //**************//
                adapter = new ArrayAdapter<String>(Beginner.this, R.layout.item, titleList) {
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
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Beginner.this, Details.class);
                        String title = workoutArrayList.get(position).getTitle();
                        String description = workoutArrayList.get(position).getDescription();
                        String link = workoutArrayList.get(position).getLink();
                        String i = "Apple";


                        intent.putExtra("EXTRA_TITLE", title);
                        intent.putExtra("EXTRA_DESC", description);
                        intent.putExtra("EXTRA_LINK", link);
                        intent.putExtra("V", i);
                        startActivity(intent);
                    }
                });



            }
        });

        express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setTextColor(ContextCompat.getColor(Beginner.this, R.color.grey_unselected));
                express.setTextColor(ContextCompat.getColor(Beginner.this, R.color.bg));

                startExercise.getBackground().setAlpha(255);

                final String t = "E";
                startExercise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Beginner.this, StartWorkout.class);
                        Log.d(TAG, "onClick: t = "+t);
                        intent.putExtra("T",t);
                        startActivity(intent);
                    }
                });


                adapterExpress = new ArrayAdapter<String>(Beginner.this, R.layout.item, expressList) {
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

                lv.setAdapter((ListAdapter) adapterExpress);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Beginner.this, Details.class);
                        String expTitle = workoutArrayList.get(position).getTitleExpress();
                        String expDescription = workoutArrayList.get(position).getDescriptionExpress();
                        String expLink = workoutArrayList.get(position).getLinkExpress();
                        String i = "Mango";


                        intent.putExtra("Exp_Title", expTitle);
                        intent.putExtra("Exp_Desc", expDescription);
                        intent.putExtra("Exp_Link", expLink);
                        intent.putExtra("V", i);

                        startActivity(intent);

                    }
                });



            }
        });


    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Beginner.this, Details.class);
        String title = workoutArrayList.get(position).getTitle();
        String description = workoutArrayList.get(position).getDescription();
        String link = workoutArrayList.get(position).getLink();
        String i = "Apple";
        intent.putExtra("EXTRA_TITLE", title);
        intent.putExtra("EXTRA_DESC", description);
        intent.putExtra("EXTRA_LINK", link);
        intent.putExtra("V", i);
        startActivity(intent);
    }*/

    /*@Override
    public void onClick(View v) {
        Intent intent = new Intent(this, StartWorkout.class);
        startActivity(intent);
    }*/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,AppHomePage.class);
        startActivity(intent);
    }
}