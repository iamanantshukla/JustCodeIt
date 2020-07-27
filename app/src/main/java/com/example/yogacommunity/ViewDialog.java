package com.example.yogacommunity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;



public class ViewDialog {

    Activity mActivity;

    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        mActivity = activity;

        Button noBtn = (Button) dialog.findViewById(R.id.noText);
        Button yesBtn = (Button) dialog.findViewById(R.id.yesText);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, Beginner.class);
                mActivity.startActivity(intent);
            }
        });

        dialog.show();

    }
}