package com.example.haako.policymaker.Cards;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.haako.policymaker.Actors.Actor;
import com.example.haako.policymaker.Fragments.CustomFragment;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;
import com.example.haako.policymaker.R;
import com.example.haako.policymaker.Services.BackgroundSoundService;
import com.example.haako.policymaker.Status;
import com.example.haako.policymaker.Welcome;

import java.util.Timer;
import java.util.TimerTask;

public class ChoiceCard extends AppCompatActivity {


    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private int timePerCard = 0;
    //Selected choice array
    private int[] selectedChoices;
    //Required Libraries
    private GameLogic gl;
    private ResultManager rm;
    private int currentVotes = 0;
    //Radio Buttons
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    //TextView
    private TextView tv;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.status_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.status:
                statusBtn_click();
                return true;
            case R.id.menuExit:
                menuExit_click();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_card);
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");
        setTitle(gl.getStringByName(this, "head"+gl.getProgress()));
        rm.logEntry(getTitle().toString());
        selectedChoices = new int[gl.getPlayers()];
        tv = (TextView) findViewById(R.id.intro);
        a = (RadioButton) findViewById(R.id.alternativeA);
        b = (RadioButton) findViewById(R.id.alternativeB);
        c = (RadioButton) findViewById(R.id.alternativeC);
        tv.setText(gl.getStringByName(this, "card"+gl.getProgress()));
        a.setText(gl.getStringByName(this, "card"+gl.getProgress()+"a"));
        b.setText(gl.getStringByName(this, "card"+gl.getProgress()+"b"));
        c.setText(gl.getStringByName(this, "card"+gl.getProgress()+"c"));
        startTimer();
        gl.checkResources(a, b, c);
        setStatus();
        final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.statusScroll);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable(){
            public void run() {
                hsv.scrollTo(120, 0);
            }
        }, 10);
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

    private void setStatus() {
        TextView politicalCapital = (TextView) findViewById(R.id.status_politicalCapital);
        TextView legwork = (TextView) findViewById(R.id.status_legwork);
        TextView knowledge = (TextView) findViewById(R.id.status_knowledge);
        TextView influence = (TextView) findViewById(R.id.status_influence);
        TextView network = (TextView) findViewById(R.id.status_network);
        TextView passion = (TextView) findViewById(R.id.status_passion);
        politicalCapital.setText("x "+gl.getPoliticalCapital());
        legwork.setText("x "+gl.getLegwork());
        knowledge.setText("x "+gl.getKnowledge());
        influence.setText("x "+gl.getInfluence());
        network.setText("x "+gl.getNetwork());
        passion.setText("x "+gl.getPassion());
    }

    public void voteBtn_click(View v) {
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        int radioButtonID = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(radioButtonID);
        int rabID = rg.indexOfChild(radioButton);
        if (rg.getCheckedRadioButtonId() == -1)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Du må velge et alternativ", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            if (currentVotes == gl.getPlayers() - 1) {
                selectedChoices[currentVotes] = rabID;
                int i = gl.getPopularElement(selectedChoices);
                setContentView(R.layout.consequence);
                TextView consequence = (TextView) findViewById(R.id.consequence);
                if (i == 0) {
                    if (gl.getProgress() == 7) {
                        consequence.setVisibility(View.GONE);
                        LinearLayout cardSlot = (LinearLayout) findViewById(R.id.consequence_layout);
                        cardSlot.setVisibility(View.VISIBLE);
                        CustomFragment cf = new Actor(gl.getImageIDByName(this, "actor14"), getString(R.string.actor14head), getString(R.string.actor14desc), getString(R.string.actor14rep), getString(R.string.actor14pol), getString(R.string.actor14value), true);
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.consequence_layout, cf, "MyActivity");
                        fragmentTransaction.commit();
                    }
                    else {
                        consequence.setText(gl.getStringByName(this, "consequence"+gl.getProgress()+"a"));
                    }
                }
                else if (i == 1) {
                    consequence.setText(gl.getStringByName(this, "consequence"+gl.getProgress()+"b"));
                }
                else {
                    consequence.setText(gl.getStringByName(this, "consequence"+gl.getProgress()+"c"));
                }
                setTitle(gl.getStringByName(this, "consequencehead"+gl.getProgress()));
                gl.tradeResources(getApplicationContext(), gl.getPopularElement(selectedChoices));
            }
            else {
                selectedChoices[currentVotes] = rabID;
                currentVotes++;
                TextView votes = (TextView) findViewById(R.id.voters);
                votes.setText(""+currentVotes+"/"+gl.getPlayers());
            }
        }
    }

    public void nextBtn_click(View v) {
        timer.cancel();
        rm.addTimePerCard(timePerCard);
        rm.addArray(selectedChoices);
        rm.logEntry("FERDIG - Varighet: "+timePerCard+"sekunder");
        gl.setProgress(gl.getProgress() + 1);
        if (gl.getProgress() < 15) {
            gl.setMap(true);
        }
        startActivity(gl.loadCard(this, rm));
        finish();
    }

    private void statusBtn_click() {
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
