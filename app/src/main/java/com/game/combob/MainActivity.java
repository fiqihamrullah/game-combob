package com.game.combob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    ImageButton imgBtnOpen,imgVBtnOptions,imgVBtnExit;
    MediaPlayer mMediaPlayer;
    boolean suaraaktif;
    GameDatabase gd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgBtnOpen =(ImageButton) findViewById(R.id.imgBtnPlay);
        imgBtnOpen.setOnClickListener(clickToPlay);

        imgVBtnOptions =(ImageButton) findViewById(R.id.imgVBtnOption);
        imgVBtnOptions.setOnClickListener(clickToOptions);

        imgVBtnExit =(ImageButton) findViewById(R.id.imgVBtnExit);
        imgVBtnExit.setOnClickListener(clickToExit);

        gd = new GameDatabase(getApplicationContext());
        gd.CreateTableSetting();
        suaraaktif = gd.isSuaraAktif();
        if (suaraaktif)
        {
            mMediaPlayer = MediaPlayer.create(this, R.raw.op_sanji1);
        }


    }

    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        if (suaraaktif)
        {
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }
        super.onStart();
    }



    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        if (suaraaktif)
        {
            mMediaPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        suaraaktif = gd.isSuaraAktif();
        if (suaraaktif)
        {
            mMediaPlayer.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (suaraaktif)
        {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        super.onDestroy();
    }


    private OnClickListener clickToExit = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            MainActivity.this.finish();
        }
    };

    private OnClickListener clickToPlay = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            startActivity(new Intent(getApplicationContext(), PlayActivity.class));
        }
    };

    private OnClickListener clickToOptions = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            startActivity(new Intent(getApplicationContext(), ViewPilihanActivity.class));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
