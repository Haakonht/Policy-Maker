package com.example.haako.policymaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by haako on 09.11.2016.
 */
public class Splash extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

}
