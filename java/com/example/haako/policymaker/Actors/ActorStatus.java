package com.example.haako.policymaker.Actors;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.example.haako.policymaker.Fragments.CustomFragment;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.R;
import com.example.haako.policymaker.Libraries.ResultManager;

import java.util.Timer;
import java.util.TimerTask;

public class ActorStatus extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private int timePerCard = 0;
    private GameLogic gl;
    private ResultManager rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors_status);
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");
        rm.logEntry("VIEWING SELECTED ACTORS - ");
        setTitle(getString(R.string.actorhead));
        LinearLayout main = (LinearLayout)findViewById(R.id.actor_status);
        for (CustomFragment cf : gl.getSelectedActors()) {
            View view = getLayoutInflater().inflate(cf.getID(), main, false);
            ToggleButton tb = (ToggleButton) view.findViewById(R.id.actor_selectedBtn);
            tb.setVisibility(View.GONE);
            main.addView(view);
        }
        startTimer();
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
    
    public void returnBtn_click(View v) {
        timer.cancel();
        rm.logEntry("FERDIG - Varighet: "+timePerCard+"sekunder");
        startActivity(gl.loadCard(this, rm));
    }
}
