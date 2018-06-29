package com.example.haako.policymaker.Cards;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.haako.policymaker.Fragments.CustomFragment;
import com.example.haako.policymaker.Fragments.CustomFragmentAdapter;
import com.example.haako.policymaker.Fragments.DuplicateFragmentAdapter;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;
import com.example.haako.policymaker.R;
import com.example.haako.policymaker.Status;
import com.example.haako.policymaker.Welcome;

import java.util.Timer;
import java.util.TimerTask;

public class ActorCard extends AppCompatActivity {


    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private int timePerCard = 0;
    private GameLogic gl;
    private ResultManager rm;
    private CustomFragmentAdapter pageAdapter;
    private DuplicateFragmentAdapter duplicateAdapter;
    private Activity getActivity;

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
        setContentView(R.layout.activity_actor_card);
        getActivity = this;
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");
        setTitle(gl.getStringByName(this, "head"+gl.getProgress()));
        rm.logEntry(getTitle().toString());
        if (gl.getProgress() == 3) {
            pageAdapter = new CustomFragmentAdapter(getSupportFragmentManager(), getApplicationContext(), gl);
            ViewPager pager=(ViewPager)findViewById(R.id.pager);
            pager.setAdapter(pageAdapter);
            TextView intro = (TextView) findViewById(R.id.intro);
            if (gl.getRequiredPoliticians() == 3) {
                intro.setText("Nå skal dere velge "+gl.getRequiredActors()+" personer som skal sitte i Temautvalget. På grunn av valget dere tok forventes det at minst "+gl.getRequiredPoliticians()+" er politikere");
            }
            else if (gl.getRequiredReps() == 3) {
                intro.setText("Nå skal dere velge "+gl.getRequiredActors()+" personer som skal sitte i Temautvalget. På grunn av valget dere tok forventes det at minst "+gl.getRequiredReps()+" er representanter");
            }
            else {
                intro.setText("Nå skal dere velge "+gl.getRequiredActors()+" personer som skal sitte i Temautvalget. Da dere valgte den håndplukkede gruppen kan dere velge fritt blant alle aktørene.");
            }
        }
        else if (gl.getProgress() == 9) {
            duplicateAdapter = new DuplicateFragmentAdapter(getSupportFragmentManager(),getApplicationContext(), gl);
            ViewPager pager=(ViewPager)findViewById(R.id.pager);
            pager.setAdapter(duplicateAdapter);
            getActivity = this;
            gl.setRequiredActors(7);
            if (gl.getPopularElement(rm.getIntResults().get(1)) == 0) {
                gl.setRequiredPoliticians(4);
            }
            else if (gl.getPopularElement(rm.getIntResults().get(1)) == 1) {
                gl.setRequiredReps(4);
            }
            for (CustomFragment cf : gl.getSelectedActors()) {
                if (cf.getDescription().equals("Formann for BU-utvalget")) {
                    gl.setRequiredActors(8);
                }
            }
            TextView intro = (TextView) findViewById(R.id.intro);
            if (gl.getRequiredPoliticians() == 4) {
                intro.setText("Temautvalget har behov for nye krefter, velg 2 nye aktører der 1 må være politiker.");
            }
            else if (gl.getRequiredReps() == 4) {
                intro.setText("Temautvalget har behov for nye krefter, velg 2 nye aktører der 1 må være representant.");
            }
            else {
                intro.setText("Temautvalget har behov for nye krefter, velg 2 nye aktører.");
            }
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

    public void actorTgl_click(View v) {
        if (gl.getProgress() == 3) {
            ViewPager vp = (ViewPager) findViewById(R.id.pager);
            CustomFragment cf = pageAdapter.getActors().get(vp.getCurrentItem());
            ToggleButton tb = (ToggleButton) cf.getView().findViewById(R.id.actor_selectedBtn);
            if (cf.getSelected()) {
                cf.setSelected(false);
                tb.setTextColor(Color.RED);
            } else {
                cf.setSelected(true);
                tb.setTextColor(Color.GREEN);
            }
            checkActors();
        }
        else if (gl.getProgress() == 9) {
            ViewPager vp = (ViewPager) findViewById(R.id.pager);
            CustomFragment cf = duplicateAdapter.getActors().get(vp.getCurrentItem());
            ToggleButton tb = (ToggleButton) cf.getView().findViewById(R.id.actor_selectedBtn);
            if (cf.getSelected()) {
                cf.setSelected(false);
                tb.setTextColor(Color.RED);
            }
            else {
                cf.setSelected(true);
                tb.setTextColor(Color.GREEN);
            }
            checkActors();
        }
    }

    public void nextBtn_click(View v) {
        int politicians = 0;
        int reps = 0;
        int total = 0;
        if (gl.getProgress() == 3) {
            for (CustomFragment cf : pageAdapter.getActors()) {
                if (cf.getSelected()) {
                    total++;
                    if (cf.getPolitician()) {
                        politicians++;
                    }
                    else if (cf.getRepresentative()) {
                        reps++;
                    }
                }
            }
        }
        else if (gl.getProgress() == 9) {
            for (CustomFragment cf : gl.getSelectedActors()) {
                if (cf.getSelected()) {
                    total++;
                    if (cf.getPolitician()) {
                        politicians++;
                    } else if (cf.getRepresentative()) {
                        reps++;
                    }
                }
            }
            for (CustomFragment cf : duplicateAdapter.getActors()) {
                if (cf.getSelected()) {
                    total++;
                    if (cf.getPolitician()) {
                        politicians++;
                    } else if (cf.getRepresentative()) {
                        reps++;
                    }
                }
            }
        }
        if (total == gl.getRequiredActors()) {
            if (politicians >= gl.getRequiredPoliticians()) {
                if (reps >= gl.getRequiredReps()) {
                    if (gl.getPlayers() > 1 ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setTitle("Gruppeavgjørelse");
                        alertDialogBuilder.setMessage("Dette er en kollektiv avgjørelse, er valgte aktører i samsvar med ønskene til gruppen?");
                        alertDialogBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                for (CustomFragment cf : pageAdapter.getActors()) {
                                    if (cf.getSelected()) {
                                        gl.addActor(cf);
                                        rm.logEntry("VALGT AKTØR - "+cf.getDescription());
                                    }
                                }
                                gl.tradeResources(getApplicationContext(), 0);;
                                gl.setProgress(gl.getProgress()+1);
                                timer.cancel();
                                rm.logEntry("FERDIG - Varighet: "+timePerCard+"sekunder");
                                startActivity(gl.loadCard(getApplicationContext(), rm));
                                finish();
                            }
                        });
                        alertDialogBuilder.setNegativeButton("Nei",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    else {
                        if (gl.getProgress() == 3) {
                            for (CustomFragment cf : pageAdapter.getActors()) {
                                if (cf.getSelected()) {
                                    gl.addActor(cf);
                                    rm.logEntry("VALGT AKTØR - "+cf.getDescription());
                                }
                            }
                        }
                        else if (gl.getProgress() == 9) {
                            for (CustomFragment cf : duplicateAdapter.getActors()) {
                                if (cf.getSelected()) {
                                    gl.addActor(cf);
                                    rm.logEntry("VALGT AKTØR - "+cf.getDescription());
                                }
                            }
                        }
                        gl.tradeResources(getApplicationContext(), 0);
                        timer.cancel();
                        rm.addTimePerCard(timePerCard);
                        rm.logEntry("FERDIG - Varighet: "+timePerCard+"sekunder");
                        gl.setProgress(gl.getProgress() + 1);
                        Intent intent = new Intent(this, CardSelect.class);
                        intent.putExtra("GameLogic", gl);
                        intent.putExtra("ResultManager", rm);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    if (gl.getProgress() == 3) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Du må velge 3 representanter", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if (gl.getProgress() == 9) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Du må velge 1 representant", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
            else {
                if (gl.getProgress() == 3) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Du må velge 3 politikere", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (gl.getProgress() == 9) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Du må velge 1 politiker", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        else {
            if (gl.getProgress() == 3) {
                Toast toast = Toast.makeText(getApplicationContext(), "Du må velge 5 aktører", Toast.LENGTH_SHORT);
                toast.show();
            }
            else if (gl.getProgress() == 9) {
                Toast toast = Toast.makeText(getApplicationContext(), "Du må velge 2 aktører", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void checkActors() {
        if (gl.getProgress() == 3) {
            TextView actors = (TextView) findViewById(R.id.actors);
            actors.requestFocus();
            int politicians = 0;
            int reps = 0;
            int total = 0;
            for (CustomFragment cf : pageAdapter.getActors()) {
                if (cf.getSelected()) {
                    total++;
                    if (cf.getPolitician()) {
                        politicians++;
                    }
                    else if (cf.getRepresentative()) {
                        reps++;
                    }
                }
                if (total != 0) {
                    actors.setText("Du har valgt "+total+" aktører. "+politicians+" politikere, "+reps+" representanter");
                }
                else {
                    actors.setText("");
                }
            }
        }
        else if (gl.getProgress() == 9) {
            TextView actors = (TextView) findViewById(R.id.actors);
            actors.requestFocus();
            int politicians = 0;
            int reps = 0;
            int total = 0;
            for (CustomFragment cf : duplicateAdapter.getActors()) {
                if (cf.getSelected()) {
                    total++;
                    if (cf.getPolitician()) {
                        politicians++;
                    }
                    else if (cf.getRepresentative()) {
                        reps++;
                    }
                }
                if (total != 0) {
                    actors.setText("Du har valgt "+total+" aktører. "+politicians+" politikere, "+reps+" representanter");
                }
                else {
                    actors.setText("");
                }
            }
        }
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
