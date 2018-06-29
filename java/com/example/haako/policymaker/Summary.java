package com.example.haako.policymaker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haako.policymaker.Fragments.CustomFragment;
import com.example.haako.policymaker.JSON.JSONParser;
import com.example.haako.policymaker.JSON.JSONResult;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Summary extends AppCompatActivity {

    private GameLogic gl;
    private ResultManager rm;

    private ProgressBar pb;
    private ProgressDialog pDialog;
    private int score = 10;

    private Boolean showingDetails = false;

    // JSON parser class
    private JSONParser parser = new JSONParser();
    private String LOGIN_URL = "http://10.0.2.2/parser.php";
    private String TAG_SUCCESS = "response";
    private String TAG_MESSAGE = "message";
    private JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setTitle(getString(R.string.summaryhead));
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgressTintList(ColorStateList.valueOf(Color.rgb(41,46,55)));
        pb.setProgress(score);
        pb.setMax(55);

        getScore();
    }

    private void getScore() {
        score = 0;
        score += gl.getCurrentInnovation();
        score += gl.getCurrentFeasibility();
        score += gl.getCurrentPopularity();
        if (gl.getCurrentInnovation() >= gl.getExpectedInnovation()) {
            score += gl.getExpectedInnovation();
        }
        if (gl.getCurrentFeasibility() >= gl.getExpectedFeasibility()) {
            score += gl.getExpectedFeasibility();
        }
        if (gl.getExpectedPopularity() >= gl.getExpectedPopularity()) {
            score += gl.getExpectedPopularity();
        }
        setScore();
    }

    private void setScore() {
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(""+score+"/"+pb.getMax());
        pb.setProgress(score);
        TextView summaryText = (TextView) findViewById(R.id.summaryText);
        if (score < 16) {
            summaryText.setText(getString(R.string.score1));
        }
        else if (score > 15 && score < 26) {
            summaryText.setText(getString(R.string.score2));
        }
        else if (score > 25 && score < 36) {
            summaryText.setText(getString(R.string.score3));
        }
        else if (score > 35 && score < 46) {
            summaryText.setText(getString(R.string.score4));
        }
        else {
            summaryText.setText(getString(R.string.score5));
        }

        object = new JSONResult().generateResult(rm.getTotalTime(), score, gl.getPlayers(), rm.getUsername(), rm.getIntResults(), rm.getTimePerCard(), gl.getSelectedActors(), rm.getStringResults());

    }

    public void detailsBtn_click(View v) {
        if (!showingDetails) {
            showingDetails = true;
            LinearLayout main = (LinearLayout)findViewById(R.id.detailsLayout);
            View view = getLayoutInflater().inflate(R.layout.summary_decisions, main, false);
            TextView decisionOne = (TextView) view.findViewById(R.id.decisionOne);
            TextView decisionTwo = (TextView) view.findViewById(R.id.decisionTwo);
            TextView decisionThree = (TextView) view.findViewById(R.id.decisionThree);
            TextView decisionFour = (TextView) view.findViewById(R.id.decisionFour);
            TextView decisionFive = (TextView) view.findViewById(R.id.decisionFive);
            TextView decisionSix = (TextView) view.findViewById(R.id.decisionSix);
            TextView decisionSeven = (TextView) view.findViewById(R.id.decisionSeven);
            TextView decisionEight = (TextView) view.findViewById(R.id.decisionEight);
            TextView decisionNine = (TextView) view.findViewById(R.id.decisionNine);
            TextView decisionTen = (TextView) view.findViewById(R.id.decisionTen);
            TextView decisionEleven = (TextView) view.findViewById(R.id.decisionEleven);
            if (gl.getPopularElement(rm.getIntResults().get(0)) == 0 ) {
                decisionOne.setText(getString(R.string.decision1a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(0)) == 1 ) {
                decisionOne.setText(getString(R.string.decision1b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(0)) == 2 ) {
                decisionOne.setText(getString(R.string.decision1c));
            }
            if (gl.getPopularElement(rm.getIntResults().get(1)) == 0) {
                decisionTwo.setText(getString(R.string.decision2a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(1)) == 1) {
                decisionTwo.setText(getString(R.string.decision2b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(1)) == 2) {
                decisionTwo.setText(getString(R.string.decision2c));
            }
            decisionThree.setText("");
            for (CustomFragment cf : gl.getSelectedActors()) {
                decisionThree.append("\n"+cf.getDescription());
            }
            if (gl.getPopularElement(rm.getIntResults().get(2)) == 0) {
                decisionFour.setText(getString(R.string.decision4a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(2)) == 1) {
                decisionFour.setText(getString(R.string.decision4b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(2)) == 2) {
                decisionFour.setText(getString(R.string.decision4c));
            }
            if (gl.getPopularElement(rm.getIntResults().get(3)) == 0) {
                decisionFive.setText(getString(R.string.decision5a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(3)) == 1) {
                decisionFive.setText(getString(R.string.decision5b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(3)) == 2) {
                decisionFive.setText(getString(R.string.decision5c));
            }
            if (gl.getPopularElement(rm.getIntResults().get(4)) == 0) {
                decisionSix.setText(getString(R.string.decision6a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(4)) == 1) {
                decisionSix.setText(getString(R.string.decision6b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(4)) == 2) {
                decisionSix.setText(getString(R.string.decision6c));
            }
            if (gl.getPopularElement(rm.getIntResults().get(5)) == 0) {
                decisionSeven.setText(getString(R.string.decision7a));
            }
            else  if (gl.getPopularElement(rm.getIntResults().get(5)) == 1) {
                decisionSeven.setText(getString(R.string.decision7b));
            }
            else  if (gl.getPopularElement(rm.getIntResults().get(5)) == 2) {
                decisionSeven.setText(getString(R.string.decision7c));
            }
            if (gl.getPopularElement(rm.getIntResults().get(6)) == 0) {
                decisionEight.setText(getString(R.string.decision8a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(6)) == 1) {
                decisionEight.setText(getString(R.string.decision8b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(6)) == 2) {
                decisionEight.setText(getString(R.string.decision8c));
            }
            if (gl.getPopularElement(rm.getIntResults().get(7)) == 0) {
                decisionNine.setText(getString(R.string.decision9a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(7)) == 1) {
                decisionNine.setText(getString(R.string.decision9b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(7)) == 2) {
                decisionNine.setText(getString(R.string.decision9c));
            }
            if (gl.getPopularElement(rm.getIntResults().get(8)) == 0) {
                decisionTen.setText(getString(R.string.decision10a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(8)) == 1) {
                decisionTen.setText(getString(R.string.decision10b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(8)) == 2) {
                decisionTen.setText(getString(R.string.decision10c));
            }
            if (gl.getPopularElement(rm.getIntResults().get(9)) == 0) {
                decisionEleven.setText(getString(R.string.decision11a));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(9)) == 1) {
                decisionEleven.setText(getString(R.string.decision11b));
            }
            else if (gl.getPopularElement(rm.getIntResults().get(9)) == 2) {
                decisionEleven.setText(getString(R.string.decision11c));
            }
            main.addView(view);
            Button detailsBtn = (Button) findViewById(R.id.detailsBtn);
            detailsBtn.setVisibility(View.GONE);
            Button exitBtn = (Button) findViewById(R.id.exitBtn);
            exitBtn.setVisibility(View.GONE);
        }
    }

    public void logBtn_click(View v) {
        Intent intent = new Intent(this, Log.class);
        intent.putExtra("GameLogic", gl);
        intent.putExtra("ResultManager", rm);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    public void exitBtn_click(View v) {
        if (rm.getUsername().equals("default_test_user")) {
            Intent intent = new Intent(Summary.this, Welcome.class);
            intent.putExtra("Username", rm.getUsername());
            startActivity(intent);
        }
        else {
            new UploadData().execute(object);
        }
    }

    private class UploadData extends AsyncTask<JSONObject, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Summary.this);
            pDialog.setMessage("Laster opp data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(JSONObject... data) {
            JSONObject object = data[0];
            int success;
            try {
                android.util.Log.d("request!", "starting");
                JSONObject json = parser.makeHttpRequest(LOGIN_URL, "POST", object);
                if (json != null) {
                    android.util.Log.d("Login attempt", json.toString());
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        android.util.Log.d("Successful update!", json.toString());
                        Intent intent = new Intent(Summary.this, Welcome.class);
                        intent.putExtra("Username", rm.getUsername());
                        finish();
                        startActivity(intent);
                        return json.getString(TAG_MESSAGE);
                    }else{
                        return json.getString(TAG_MESSAGE);
                    }
                }
                else {
                    return "Ingen kontakt med server";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();
            if (message != null){
                Toast.makeText(Summary.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
