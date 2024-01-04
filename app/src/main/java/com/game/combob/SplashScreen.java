package com.game.combob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashScreen extends Activity {
    //Internal Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        SplashHandler mHandler = new SplashHandler();
        setContentView(R.layout.splash);
        Message msg = new Message();
        msg.what = 0;
        mHandler.sendMessageDelayed(msg, 3000);
    }
    private class SplashHandler extends Handler {
        public void handleMessage(Message msg)	  {
            // switch to identify the message by its code
            switch (msg.what)
            {
                default:
                case 0:
                    super.handleMessage(msg);
                    Intent intent = new Intent();
                    intent.setClass(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    SplashScreen.this.finish();
            }

        }
    }



}
