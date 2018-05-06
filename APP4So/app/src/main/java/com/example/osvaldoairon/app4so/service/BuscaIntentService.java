package com.example.osvaldoairon.app4so.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class BuscaIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public BuscaIntentService() {
        super("search-thread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
