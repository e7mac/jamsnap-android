package com.pixaura.ui;

import android.app.Activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.octo.android.robospice.SpiceManager;
import com.pixaura.network.JamSnapService;

public abstract class BaseSherlockActivityWithRoboSpiceManager extends SherlockActivity {
    private SpiceManager spiceManager = new SpiceManager(JamSnapService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
