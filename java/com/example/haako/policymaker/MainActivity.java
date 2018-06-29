package com.example.haako.policymaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haako.policymaker.Animations.TextAnimation;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;
import com.example.haako.policymaker.SMS.SendSMS;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ResultManager rm = new ResultManager();
    private GameLogic gl = new GameLogic();
    private ImageView logo, img1, img2;
    private TextView text1, text2, text3, text4;
    private LinearLayout buttonDock;
    private String txt1, txt2;
    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private int elapsedTime = 0, timeTaken = 0, timeTaken1 = 0;
    private ScrollView sv;
    protected PowerManager.WakeLock mWakeLock;

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preferences_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.preferencesBtn:
                prefBtn_click();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getIntent().getStringExtra("Username").equals("")) {
            rm.setUsername(getIntent().getStringExtra("Username"));
        }
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
        getApplication().setTheme(R.style.MapTheme);
        setContentView(R.layout.activity_main);
        setTitle("INTRODUKSJON");
        logo = (ImageView) findViewById(R.id.intro_logo);
        img1 = (ImageView) findViewById(R.id.intro_img);
        text1 = (TextView) findViewById(R.id.intro_text);
        img2 = (ImageView) findViewById(R.id.intro_img1);
        text2 = (TextView) findViewById(R.id.intro_text1);
        text3 = (TextView) findViewById(R.id.intro_text3);
        text4 = (TextView) findViewById(R.id.intro_text4);
        sv = (ScrollView) findViewById(R.id.intro_scroll);
        buttonDock = (LinearLayout) findViewById(R.id.intro_buttons);
        txt1 = text1.getText().toString();
        txt2 = text2.getText().toString();
        text1.setText("");
        text2.setText("");
        startTimer();
        img1.setAlpha(0.0f);
        img1.setVisibility(View.VISIBLE);
        img1.animate().alpha(1).setDuration(1000);
        timeTaken = (txt1.length() * 30) / 100;
        timeTaken1 = (txt2.length() * 30) / 100;
    }
    private void startTimer() {
        final TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if(elapsedTime == 10){
                            text1.setVisibility(View.VISIBLE);
                            new TextAnimation(text1, txt1, 30);
                        }
                        if(elapsedTime == (30 + timeTaken)){
                            img2.setAlpha(0.0f);
                            img2.setVisibility(View.VISIBLE);
                            img2.animate().alpha(1).setDuration(2000);
                        }
                        if(elapsedTime == (50 + timeTaken)){
                            text2.setVisibility(View.VISIBLE);
                            new TextAnimation(text2, txt2, 30);
                        }
                        if(elapsedTime == (70 + timeTaken1 + timeTaken)){
                            text1.animate().alpha(0.0f).setDuration(500);
                            text2.animate().alpha(0.0f).setDuration(500);
                            img2.animate().alpha(0.0f).setDuration(500);
                        }
                        if(elapsedTime == (75 + timeTaken1 + timeTaken)){
                            text1.setVisibility(View.GONE);
                            text2.setVisibility(View.GONE);
                            img2.setVisibility(View.GONE);
                            img1.setVisibility(View.GONE);
                            logo.setVisibility(View.VISIBLE);
                            logo.setAlpha(0.0f);
                            logo.animate().alpha(1.0f).setDuration(2000);
                        }
                        if(elapsedTime == (90 + timeTaken + timeTaken1)){
                            logo.animate().alpha(0.0f).setDuration(3000);
                        }
                        if(elapsedTime == (130 + timeTaken + timeTaken1)){
                            img2.setVisibility(View.GONE);
                            img1.setVisibility(View.GONE);
                            text1.setVisibility(View.GONE);
                            text2.setVisibility(View.GONE);
                            logo.setVisibility(View.GONE);
                            text3.setVisibility(View.VISIBLE);
                            text4.setVisibility(View.VISIBLE);
                            text3.setAlpha(0.0f);
                            text4.setAlpha(0.0f);
                            text4.animate().alpha(1.0f).setDuration(500);
                            text3.animate().alpha(1.0f).setDuration(500);
                            buttonDock.setVisibility(View.VISIBLE);
                            buttonDock.animate().alpha(1.0f).setDuration(500);
                            getApplication().setTheme(R.style.AppTheme);
                            timer.cancel();
                        }
                        elapsedTime++;
                        sv.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        };
        timer.schedule(timertask, 0, 100);
    }

    public void skipBtn_Click(View v){
        if(elapsedTime < (130 + timeTaken + timeTaken1)){
            timer.cancel();
            img2.setVisibility(View.VISIBLE);
            img1.setVisibility(View.VISIBLE);
            text1.setVisibility(View.GONE);
            text2.setVisibility(View.GONE);
            logo.setVisibility(View.GONE);
            text3.setVisibility(View.VISIBLE);
            text4.setVisibility(View.VISIBLE);
            text3.setAlpha(0.0f);
            text4.setAlpha(0.0f);
            text4.animate().alpha(1.0f).setDuration(500);
            text3.animate().alpha(1.0f).setDuration(500);
            buttonDock.setVisibility(View.VISIBLE);
            buttonDock.animate().alpha(1.0f).setDuration(500);
            getApplication().setTheme(R.style.AppTheme);
        }
    }

    public void startGame_click(View v) {
        CheckBox cb = (CheckBox) findViewById(R.id.menuCheckbox);
        if (cb.isChecked()) {
            EditText et = (EditText) findViewById(R.id.playerNo);
            if (!et.getText().equals("") || !et.getText().toString().equals("0")) {
                mWakeLock.release();
                gl.setPlayers(Integer.parseInt(et.getText().toString()));
                Intent intent = new Intent(this, SendSMS.class);
                intent.putExtra("GameLogic", gl);
                intent.putExtra("ResultManager", rm);
                intent.putExtra("message", "Velkommen til Policy Maker");
                intent.putExtra("phoneNumber", "Policy Maker Teamet");
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Fyll inn antall spillere", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Dere må bekrefte at reglene er lest", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void prefBtn_click() {
        Intent intent = new Intent(this, Preferences.class);
        intent.putExtra("GameLogic", gl);
        intent.putExtra("ResultManager", rm);
        startActivity(intent);
    }
}
