package com.example.yogacommunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class StartWorkout extends AppCompatActivity implements View.OnClickListener {

    private TextView title, description, next, pause,timerValue, exerciseNumber;
    private ArrayList<Workout> workoutArrayList;
    private ArrayList<String> titleList, descList, expTitleList, expDescList;
    public int j;
    public String t;
    private static final String TAG = "StartWorkout";
    private static final long START_TIME_IN_MILLIS = 61000;
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
        pause = findViewById(R.id.pause);
        timerValue = findViewById(R.id.timerValue);
        exerciseNumber = findViewById(R.id.exerciseNumber);



        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            t = extra.getString("T");
        }
        Log.d(TAG, "onCreate: Value received = "+t);


        workoutArrayList = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        descList = new ArrayList<>();
        expTitleList = new ArrayList<>();
        expDescList = new ArrayList<>();
        for (int i = 0; i<workoutArrayList.size();i++){
            String title = workoutArrayList.get(i).getTitle();
            String desc = workoutArrayList.get(i).getDescription();
            String expTitle = workoutArrayList.get(i).getTitleExpress();
            String expDesc = workoutArrayList.get(i).getDescriptionExpress();
            titleList.add(title);
            descList.add(desc);
            expTitleList.add(expTitle);
            expDescList.add(expDesc);


        }
        if (t.equals("E")){
        for (j = 0; j<workoutArrayList.size();){

            title.setText(expTitleList.get(j));
            description.setText(expDescList.get(j));
            j++;
            break;
        }}
        else if (t.equals("B"))
        {
            for (j = 0; j<workoutArrayList.size();) {

                title.setText(titleList.get(j));
                description.setText(descList.get(j));
                j++;
                break;
            }
        }


        next.setOnClickListener(this);


        updateExerciseNumber();

        //Timer
        startTimer(mTimeLeftInMillis);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause.getText().equals("Pause")){
                    pause.setText(getResources().getText(R.string.resume));
                    timerPause();
                }  else if (pause.getText().equals("Resume")){
                    pause.setText(getResources().getText(R.string.pause));
                    timerResume();
                }
            }
        });
    }

    private void startTimer(long mTimeLeftInMillis){
        countDownTimer = new CountDownTimer(StartWorkout.this.mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                StartWorkout.this.mTimeLeftInMillis =millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                pause.getBackground().setAlpha(100);
                pause.setClickable(false);
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

    private void timerPause(){
        countDownTimer.cancel();
    }
    private void timerResume(){
        startTimer(mTimeLeftInMillis);
    }


    @Override
    public void onClick(View v) {

        pause.setText(getResources().getText(R.string.pause));
        if(t.equals("B")){
            title.setText(titleList.get(j));
            description.setText(descList.get(j));
        }
        else if (t.equals("E"))
        {
            title.setText(expTitleList.get(j));
            description.setText(expDescList.get(j));
        }

        if(j<workoutArrayList.size()){j++;}
        if(j==workoutArrayList.size()){
            next.setText(getResources().getText(R.string.finish));
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartWorkout.this, Beginner.class);
                    startActivity(intent);
                }
            });
        }
        updateExerciseNumber();
        pause.getBackground().setAlpha(255);
        pause.setClickable(true);
        countDownTimer.start();
    }

    private void updateExerciseNumber() {
        int current = (int) j;
        int total = (int) titleList.size();
        String exerciseNum = String.format(Locale.getDefault(), "%02d/%02d", current, total );
        exerciseNumber.setText(exerciseNum);
    }

    //***************//

    //***************//

    @Override
    public void onBackPressed() {
        ViewDialog alert = new ViewDialog();
        alert.showDialog(StartWorkout.this);

        timerPause();
        if (mTimeLeftInMillis!=0){
            pause.setText(R.string.resume);
        }


    }
}