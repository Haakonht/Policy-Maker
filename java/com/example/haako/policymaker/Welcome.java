package com.example.haako.policymaker;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.animation.AlphaAnimation;
        import android.view.animation.Animation;
        import android.widget.TextView;

        import com.example.haako.policymaker.Services.BackgroundSoundService;

        import java.sql.SQLException;

public class Welcome extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("Username");
        setContentView(R.layout.activity_welcome);
        TextView myText = (TextView) findViewById(R.id.welcomeLabel );

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(700); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);

        Intent svc=new Intent(this, BackgroundSoundService.class);
        startService(svc);
    }

    public void welcomeBtn_click(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }
}
