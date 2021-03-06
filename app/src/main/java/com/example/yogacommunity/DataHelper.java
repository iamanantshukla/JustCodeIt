package com.example.yogacommunity;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DataHelper {

    public static ArrayList<Workout> loadWorkout(Context context) {
        ArrayList<Workout> workouts = new ArrayList<>();
        String json = "";

        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("workouts");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Workout workout = new Workout();
                workout.setTitle(jsonObject.getString("title"));
                workout.setDescription(jsonObject.getString("description"));
                workout.setLink(jsonObject.getString("link"));
                workout.setTitleExpress(jsonObject.getString("titleExpress"));
                workout.setDescriptionExpress(jsonObject.getString("descriptionExpress"));
                workout.setLinkExpress(jsonObject.getString("linkExpress"));

                workouts.add(workout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return workouts;

    }


}