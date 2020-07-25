package com.example.yogacommunity;

import android.os.Bundle;
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
    private TextView description;
    private String link;
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
        if (extra != null)
        {
            String t = extra.getString("EXTRA_TITLE");
            String d = extra.getString("EXTRA_DESC");
            link = extra.getString("EXTRA_LINK");
            title.setText(t);
            description.setText(d);
        }

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                if(link == null) {
                    mYoutubePlayerView.setVisibility(View.GONE);
                    playButton.setVisibility(View.GONE);
                }
                youTubePlayer.loadVideo(link);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

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

    @Override
    protected void onPause() {
        super.onPause();

    }
}