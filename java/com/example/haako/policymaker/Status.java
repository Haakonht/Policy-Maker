package com.example.haako.policymaker;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;

import java.util.Timer;
import java.util.TimerTask;

public class Status extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private int timePerCard = 0;
    //Required libraries
    private GameLogic gl;
    private ResultManager rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        setTitle(getString(R.string.statushead));
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");
        rm.logEntry("ÅPNET SPILLSTATUS - ");
        postResources();
        setProgress();
        setProgressText();
        disableButtons();
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


    private void postResources() {
        TextView politicalCapital = (TextView) findViewById(R.id.status_politicalCapital);
        politicalCapital.setText(getString(R.string.politicalcapital)+gl.getPoliticalCapital());
        TextView knowledge = (TextView) findViewById(R.id.status_knowledge);
        knowledge.setText(getString(R.string.knowledge)+gl.getKnowledge());
        TextView legwork = (TextView) findViewById(R.id.status_legwork);
        legwork.setText(getString(R.string.legwork)+gl.getLegwork());
        TextView network = (TextView) findViewById(R.id.status_network);
        network.setText(getString(R.string.network)+gl.getNetwork());
        TextView influence = (TextView) findViewById(R.id.status_influence);
        influence.setText(getString(R.string.influence)+gl.getInfluence());
        TextView passion = (TextView) findViewById(R.id.status_passion);
        passion.setText(getString(R.string.passion)+gl.getPassion());
    }

    private void setProgressText() {
        TextView innovationLabel = (TextView) findViewById(R.id.innovationLabel);
        TextView feasibilityLabel = (TextView) findViewById(R.id.feasibilityLabel);
        TextView popularityLabel = (TextView) findViewById(R.id.popularityLabel);
        innovationLabel.setText(""+gl.getCurrentInnovation()+"/"+gl.getExpectedInnovation());
        feasibilityLabel.setText(""+gl.getCurrentFeasibility()+"/"+gl.getExpectedFeasibility());
        popularityLabel.setText(""+gl.getCurrentPopularity()+"/"+gl.getExpectedPopularity());
    }

    private void setProgress() {
        ProgressBar ib = (ProgressBar) findViewById(R.id.innovationBar);
        ib.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
        ProgressBar pb = (ProgressBar) findViewById(R.id.popularityBar);
        pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        ProgressBar fb = (ProgressBar) findViewById(R.id.feasibilityBar);
        fb.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        ib.setMax(gl.getExpectedInnovation());
        pb.setMax(gl.getExpectedPopularity());
        fb.setMax(gl.getExpectedFeasibility());
        ib.setProgress(gl.getCurrentInnovation());
        pb.setProgress(gl.getCurrentPopularity());
        fb.setProgress(gl.getCurrentFeasibility());
    }

    private void disableButtons() {
        ImageButton influence = (ImageButton) findViewById(R.id.influenceBtn);
        ImageButton knowledge = (ImageButton) findViewById(R.id.knowledgeBtn);
        ImageButton legwork = (ImageButton) findViewById(R.id.legworkBtn);
        ImageButton network = (ImageButton) findViewById(R.id.networkBtn);
        ImageButton passion = (ImageButton) findViewById(R.id.passionBtn);
        if (gl.getPoliticalCapital() < 1 ) {
            influence.setEnabled(false);
            knowledge.setEnabled(false);
            legwork.setEnabled(false);
            network.setEnabled(false);
            passion.setEnabled(false);
        }
    }

    public void influenceBtn_click(View v) {
        gl.tradeInfluence(rm);
        postResources();
        disableButtons();
    }

    public void knowledgeBtn_click(View v) {
        gl.tradeKnowledge(rm);
        postResources();
        disableButtons();
    }

    public void legworkBtn_click(View v) {
        gl.tradeLegwork(rm);
        postResources();
        disableButtons();
    }

    public void networkBtn_click(View v) {
        gl.tradeNetwork(rm);
        postResources();
        disableButtons();
    }

    public void passionBtn_click(View v) {
        gl.tradePassion(rm);
        postResources();
        disableButtons();
    }

    public void returnBtn_click(View v) {
        timer.cancel();
        rm.logEntry("FERDIG - Varighet: "+timePerCard+"sekunder");
        startActivity(gl.loadCard(this, rm));
    }
}
