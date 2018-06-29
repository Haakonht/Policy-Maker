package com.example.haako.policymaker.Cards;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.haako.policymaker.Actors.ActorStatus;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;
import com.example.haako.policymaker.R;
import com.example.haako.policymaker.Status;
import com.example.haako.policymaker.Welcome;

import java.util.Timer;
import java.util.TimerTask;

public class AnswerCard extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private int timePerCard = 0;
    private GameLogic gl;
    private ResultManager rm;
    private TextView intro, questionText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.extended_status_menu, menu);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_card);
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");
        intro = (TextView) findViewById(R.id.intro);
        questionText = (TextView) findViewById(R.id.questionText);
        setTitle(gl.getStringByName(this, "head"+gl.getProgress()));
        intro.setText(gl.getStringByName(this, "card"+gl.getProgress()+"a"));
        questionText.setText(gl.getStringByName(this, "card"+gl.getProgress()+"b"));
        rm.logEntry(getTitle().toString());
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

    public void answerBtn_click(View v) {
        setContentView(R.layout.reflection_answer);
        setTitle(gl.getStringByName(this, "consequencehead"+gl.getProgress()));
    }

    public void nextBtn_click(View v) {
        timer.cancel();
        rm.addTimePerCard(timePerCard);
        rm.logEntry("LEVERT SVAR - ");
        rm.logEntry("FERDIG - Varighet: "+timePerCard+"sekunder");
        EditText answerText = (EditText) findViewById(R.id.answerText);
        rm.addString(answerText.getText().toString());
        gl.setProgress(gl.getProgress()+1);
        Intent intent = new Intent(this, CardSelect.class);
        intent.putExtra("GameLogic", gl);
        intent.putExtra("ResultManager", rm);
        startActivity(intent);
    }

    private void repBtn_click() {
        Intent intent = new Intent(this, ActorStatus.class);
        intent.putExtra("GameLogic", gl);
        intent.putExtra("ResultManager", rm);
        startActivity(intent);
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
