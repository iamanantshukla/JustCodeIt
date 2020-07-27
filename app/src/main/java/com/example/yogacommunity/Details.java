package com.example.yogacommunity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.r0adkll.slidr.Slidr;

public class Details extends YouTubeBaseActivity {

    private TextView title;
    private static final String TAG = "Details";
    private TextView description;
    private String link;
    String j = "Apple", k = "Mango";
    YouTubePlayerView mYoutubePlayerView;
    Button playButton;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = findViewById(R.id.title_tv);
        description = findViewById(R.id.description_tv);
        mYoutubePlayerView = findViewById(R.id.video_view);
        playButton = findViewById(R.id.play_button);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String t = extra.getString("EXTRA_TITLE");
            String expT = extra.getString("Exp_Title");
            String d = extra.getString("EXTRA_DESC");
            String expD = extra.getString("Exp_Desc");
            String i = extra.getString("V");

            assert i != null;
            if (i.equals(j)){
                title.setText(t);
                description.setText(d);
                link = extra.getString("EXTRA_LINK");
            }else if (i.equals(k)){
                title.setText(expT);
                description.setText(expD);
                link = extra.getString("Exp_Link");
            }

        }

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onInitializationSuccess: video loading");
                if(link == null) {
                    mYoutubePlayerView.setVisibility(View.GONE);
                    playButton.setVisibility(View.GONE);
                }
                youTubePlayer.loadVideo(link);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onInitializationFailure: video loading failed");
            }
        };

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYoutubePlayerView.initialize(VideoConfig.getApiKey(), mOnInitializedListener);
                playButton.setVisibility(View.GONE);
            }
        });


        Slidr.attach(this);


    }

}