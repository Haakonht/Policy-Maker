package com.example.haako.policymaker.SMS;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by haako on 16.11.2016.
 */
public class HeadlessSmsSendService extends IntentService {
    public HeadlessSmsSendService() {
        super(HeadlessSmsSendService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
