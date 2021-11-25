package com.jobesk.boothbook.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.jobesk.boothbook.activities.login.LoginActivity;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.GlobalClass;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {


                Log.d("timerSplash", "timerSplash");

                String uID = GlobalClass.getPref("userID", getApplicationContext());

                if (uID.equalsIgnoreCase("")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }


                finish();


            }
        }, 3000);


    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
