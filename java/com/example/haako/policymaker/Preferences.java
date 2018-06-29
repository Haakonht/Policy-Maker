package com.example.haako.policymaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;

public class Preferences extends AppCompatActivity {

    private GameLogic gl;
    private ResultManager rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");
        setTitle(getString(R.string.prefhead));
    }


}
