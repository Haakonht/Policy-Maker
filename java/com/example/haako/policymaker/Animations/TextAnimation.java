package com.example.haako.policymaker.Animations;

import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by haako on 16.11.2016.
 */
public class TextAnimation {

    private TextView tv;
    private String mText;
    private int mIndex;
    private long mDelay;
    private Timer timer = new Timer();

    private final Handler handler = new Handler();

    public TextAnimation(TextView tv, String text, long millis) {
        this.tv = tv;
        mText = text;
        mIndex = 0;
        mDelay = millis;

        tv.setText("");
        startTimer();
    }

    public void stopAnimation(){
        timer.cancel();
        tv.setText(mText);
    }

    private void startTimer() {
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        tv.setText(mText.subSequence(0, mIndex++));
                        if(mIndex >= mText.length()){
                            timer.cancel();
                        }
                    }
                });
            }
        };
        timer.schedule(timertask, 0, mDelay);
    }

}
