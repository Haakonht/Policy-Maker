package com.example.haako.policymaker;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haako.policymaker.JSON.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Activity implements OnClickListener{

    private CheckBox testBox;
    private EditText user, pass;
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    private JSONParser parser = new JSONParser();
    private String LOGIN_URL = "http://10.0.2.2/login.php";
    private String TAG_SUCCESS = "response";
    private String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        testBox = (CheckBox)findViewById(R.id.testBox);
        Button bLogin = (Button)findViewById(R.id.login);
        bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (testBox.isChecked()) {
            Intent intent = new Intent(Login.this,Welcome.class);
            intent.putExtra("Username", "default_test_user");
            startActivity(intent);
        }
        else {
            JSONObject credentials = new JSONObject();
            try {
                credentials.put("username", user.getText().toString());
                credentials.put("password", pass.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            switch (v.getId()) {
                case R.id.login:
                    new AttemptLogin().execute(credentials);
                default:
                    break;
            }
        }
    }

    private class AttemptLogin extends AsyncTask<JSONObject, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Prøver å logge deg inn...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(JSONObject... data) {
            JSONObject object = data[0];
            int success;
            try {
                Log.d("request!", "starting");
                JSONObject json = parser.makeHttpRequest(LOGIN_URL, "POST", object);
                if (json != null) {
                    Log.d("Login attempt", json.toString());
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", json.toString());
                        Intent intent = new Intent(Login.this,Welcome.class);
                        intent.putExtra("Username", object.get("username").toString());
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
                Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}