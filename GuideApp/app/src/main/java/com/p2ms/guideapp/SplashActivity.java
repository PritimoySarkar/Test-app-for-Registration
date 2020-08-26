package com.p2ms.guideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.p2ms.guideapp.keys.StaticData;
import com.p2ms.guideapp.session.LocalSessionStore;

import java.util.concurrent.Delayed;

public class SplashActivity extends AppCompatActivity {

    LocalSessionStore sessionStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionStore =new LocalSessionStore(SplashActivity.this);

        new Handler(Looper.myLooper())
                .postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                String currUser = sessionStore.getData(StaticData.USER_ID);
                                //Code For navigation
                                Log.d("Splash Activity", "Outside if else");
                                if (currUser.isEmpty()) {
                                    Log.d("Splash Activity", "getting into if part to return to Home Activity");
                                    startActivity(
                                            new Intent(
                                                    SplashActivity.this,
                                                    SignInActivity.class
                                            )
                                    );
                                } else {
                                    Log.d("Splash Activity", "getting into else part to return to Home Activity");
                                    startActivity(
                                            new Intent(SplashActivity.this,
                                                    HomeActivity.class
                                            )
                                    );
                                }
                                SplashActivity.this.finish();
                            }
                        },2000);
    }
}