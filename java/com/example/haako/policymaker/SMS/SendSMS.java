package com.example.haako.policymaker.SMS;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.haako.policymaker.Cards.CardSelect;
import com.example.haako.policymaker.Cards.ChoiceCard;
import com.example.haako.policymaker.Libraries.GameLogic;
import com.example.haako.policymaker.Libraries.ResultManager;

/**
 * Created by haako on 16.11.2016.
 */
public class SendSMS extends AppCompatActivity {

    private GameLogic gl;
    private ResultManager rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rm = (ResultManager) getIntent().getSerializableExtra("ResultManager");
        gl = (GameLogic) getIntent().getSerializableExtra("GameLogic");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String message = getIntent().getStringExtra("message");

        String notificationText = message;
        if (notificationText.length() > 40) {
            notificationText = notificationText.substring(0, 40);
        }

        String defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(this);

        ContentValues values = new ContentValues();

        values.put(Telephony.Sms.ADDRESS, phoneNumber);
        values.put(Telephony.Sms.DATE, System.currentTimeMillis());
        values.put(Telephony.Sms.BODY, message);

        final String myPackageName = getPackageName();
        if (!Telephony.Sms.getDefaultSmsPackage(getApplicationContext()).equals(myPackageName)) {

            //Change the default sms app to my app
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
            startActivityForResult(intent, 1);
        }

        //Insert the message
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getContentResolver().insert(Telephony.Sms.Sent.CONTENT_URI, values);
        }
        else {
            getContentResolver().insert(Uri.parse("content://sms/sent"), values);
        }
        Intent send = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        send.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, defaultSmsApp);
        startActivity(send);
        notification(notificationText);
        startActivity(gl.loadCard(this, rm));
    }

    private void notification(String notificationText) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.sym_action_email)
                        .setContentTitle("SMS Mottatt")
                        .setContentText(notificationText);

        String defaultApplication = Settings.Secure.getString(getContentResolver(), "sms_default_application");
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(defaultApplication);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(contentIntent);

        int mNotificationId = 001;
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
