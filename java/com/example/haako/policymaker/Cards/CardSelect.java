package com.example.haako.policymaker.Cards;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.haako.policymaker.Actors.ActorStatus;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Animations.ProgressBarAnimation;
import com.example.haako.policymaker.R;
import com.example.haako.policymaker.Libraries.ResultManager;
import com.example.haako.policymaker.Status;
import com.example.haako.policymaker.Welcome;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by haako on 16.11.2016.
 */
public class CardSelect extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private int timePerCard = 0;
    private GameLogic gl;
    private ResultManager rm;
    private int[] buttons = { R.id.cardSelect1, R.id.cardSelect2, R.id.cardSelect3, R.id.cardSelect4, R.id.cardSelect5, R.id.cardSelect6, R.id.cardSelect7, R.id.cardSelect8, R.id.cardSelect9, R.id.cardSelect10, R.id.cardSelect11, R.id.cardSelect12, R.id.cardSelect13, R.id.cardSelect14  };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.extended_status_menu, menu);
        if (gl.getProgress() < 4) {
            MenuItem mi = menu.findItem(R.id.representanter);
            mi.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.status:
                statusBtn_click();
                return true;
            case R.id.representanter:
                repBtn_click();
                return true;
            case R.id.menuExit:
                menuExit_click();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_card_select);
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");
        setTitle(getString(R.string.levelSelect_name));
        init();
        findLevel();
        startTimer();
    }

    private void init() {
        ProgressBar levelSelect = (ProgressBar) findViewById(R.id.levelProgress);
        levelSelect.setRotation(180);
        levelSelect.setIndeterminateTintList(ColorStateList.valueOf(Color.GREEN));
        levelSelect.setProgress((gl.getProgress() - 2) * 100);
        final ScrollView scroll = (ScrollView) findViewById(R.id.selectorScroll);
        scroll.post(new Runnable() {
            @Override
            public void run() {
                // Get the button.
                View button = findViewById(buttons[gl.getProgress() - 1]);

                // Locate the button.
                int x, y;
                x = button.getLeft();
                y = button.getTop() - 1200;

                // Scroll to the button.
                scroll.scrollTo(x, y);
            }
        });
        if (gl.getProgress() != 1) {
            ProgressBarAnimation anim = new ProgressBarAnimation(levelSelect, (gl.getProgress() - 2) * 100, (gl.getProgress() - 1) * 100);
            anim.setDuration(2000);
            levelSelect.startAnimation(anim);
        }
    }

    private void startTimer() {
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        rm.setTotalTime(rm.getTotalTime() + 1);
                        timePerCard++;
                    }
                });
            }
        };
        timer.schedule(timertask, 0, 1000);
    }


    private void findLevel() {
        int i = gl.getProgress();
        for (int r = 0; r < buttons.length; r++) {
            Button b = (Button) findViewById(buttons[r]);
            b.setEnabled(false);
            if (i == r + 1) {
                b.setTextColor(Color.rgb(255, 205, 0));
                b.setEnabled(true);
            }
            if (i > r + 1) {
                b.setTextColor(Color.GREEN);
            }
        }
    }

    public void enterLevelBtn_click(View v) {
        timer.cancel();
        rm.logEntry("FERDIG - Varighet: "+timePerCard+"sekunder");
        startActivity(gl.loadCard(this, rm));
    }

    private void repBtn_click() {
        gl.setMap(true);
        Intent intent = new Intent(this, ActorStatus.class);
        intent.putExtra("GameLogic", gl);
        intent.putExtra("ResultManager", rm);
        startActivity(intent);
    }

    private void statusBtn_click() {
        gl.setMap(true);
        Intent intent = new Intent(this, Status.class);
        intent.putExtra("GameLogic", gl);
        intent.putExtra("ResultManager", rm);
        startActivity(intent);
    }

    private void menuExit_click() {
        Intent intent = new Intent(this, Welcome.class);
        intent.putExtra("Username", rm.getUsername());
        startActivity(intent);
    }
}
