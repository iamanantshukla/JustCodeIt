package com.example.yogacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class StartWorkout extends AppCompatActivity implements View.OnClickListener {

    private TextView title, description, next,timerValue, exerciseNumber;
    private ArrayList<Workout> workoutArrayList;
    private ArrayList<String> titleList, descList;
    public int j;

    private static final long START_TIME_IN_MILLIS = 6000;
    private CountDownTimer countDownTimer;
    private Boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        title = findViewById(R.id.title_tv);
        description = findViewById(R.id.description_tv);
        next = findViewById(R.id.next);
        timerValue = findViewById(R.id.timerValue);
        exerciseNumber = findViewById(R.id.exerciseNumber);

        workoutArrayList = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        descList = new ArrayList<>();
        for (int i = 0; i<workoutArrayList.size();i++){
            String title = workoutArrayList.get(i).getTitle();
            String desc = workoutArrayList.get(i).getDescription();
            titleList.add(title);
            descList.add(desc);

        }
        for (j = 0; j<titleList.size();){

            title.setText(titleList.get(j));
            description.setText(descList.get(j));
            j++;
            break;
        }
        next.setOnClickListener(this);

        updateExerciseNumber();

        //Timer
        startTimer();
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis =millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {

            }
        }.start();
        mTimerRunning = true;
    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis/1000)/60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;
        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes ,seconds);
        timerValue.setText(timeLeft);
    }


    @Override
    public void onClick(View v) {
        countDownTimer.start();
        title.setText(titleList.get(j));
        description.setText(descList.get(j));
        if(j<titleList.size()){j++;}
        if(j==titleList.size()){
            next.setText("FINISH");
        }
        updateExerciseNumber();

    }

    private void updateExerciseNumber() {
        int current = (int) j;
        int total = (int) titleList.size();
        String exerciseNum = String.format(Locale.getDefault(), "%02d/%02d", current, total );
        exerciseNumber.setText(exerciseNum);
    }
}