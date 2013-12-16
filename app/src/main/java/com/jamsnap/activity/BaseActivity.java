package com.jamsnap.activity;

import android.app.Activity;

import com.jamsnap.network.JamSnapService;
import com.octo.android.robospice.SpiceManager;

/**
 * This class is the base class of all activities.
 * This class offers all subclasses an easy access to a {@link com.octo.android.robospice.SpiceManager}
 * that is linked to the {@link android.app.Activity} lifecycle.
 */
public abstract class BaseActivity extends Activity {
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
