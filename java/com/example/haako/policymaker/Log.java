package com.example.haako.policymaker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haako.policymaker.JSON.JSONParser;
import com.example.haako.policymaker.JSON.JSONResult;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Log extends AppCompatActivity {

    private ResultManager rm;
    private GameLogic gl;

    private int score;
    private ProgressDialog pDialog;

    // JSON parser class
    private JSONParser parser = new JSONParser();
    private String LOGIN_URL = "http://10.0.2.2/parser.php";
    private String TAG_SUCCESS = "response";
    private String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        score = getIntent().getIntExtra("score", 0);
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");

        TextView logText = (TextView) findViewById(R.id.logText);
        for (String s : rm.getLog()) {
            logText.append(s+"\n");
        }
    }

    public void exitLogBtn_click(View v) {
        if (rm.getUsername().equals("default_test_user")) {
            Intent intent = new Intent(Log.this, Welcome.class);
            intent.putExtra("Username", rm.getUsername());
            startActivity(intent);
        }
        else {
            JSONObject object = new JSONResult().generateResult(rm.getTotalTime(), score, gl.getPlayers(), rm.getUsername(), rm.getIntResults(), rm.getTimePerCard(), gl.getSelectedActors(), rm.getStringResults());
            new UploadData().execute(object);
        }
    }

    private class UploadData extends AsyncTask<JSONObject, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Log.this);
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
                        Intent intent = new Intent(Log.this, Welcome.class);
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
                Toast.makeText(Log.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
