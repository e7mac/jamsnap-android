package com.pixaura.ui.feed;

import static com.actionbarsherlock.app.ActionBar.NAVIGATION_MODE_LIST;

import static com.pixaura.RequestCodes.SNAP_CREATE;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.spicelist.okhttp.OkHttpBitmapSpiceManager;
import com.pixaura.R;
import com.pixaura.activity.CameraActivity;
import com.pixaura.adapter.JamSnapsListAdapter;
import com.pixaura.model.Items;
import com.pixaura.network.JamSnapsRequest;
import com.pixaura.ui.BaseSherlockActivityWithRoboSpiceManager;

public class HomeActivity extends BaseSherlockActivityWithRoboSpiceManager implements ActionBar.OnNavigationListener {

    private static final String TAG = "HomeActivity";

    private HomeDropdownListAdapter homeAdapter;

    private ListView listView;
    private View loadingView;

    private JamSnapsListAdapter jamSnapsListAdapter;

    private OkHttpBitmapSpiceManager spiceManagerBinary = new OkHttpBitmapSpiceManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setProgressBarIndeterminateVisibility(false);
        setContentView(R.layout.live_list);

        listView = (ListView) findViewById(R.id.listview_jamsnap);
        loadingView = findViewById(R.id.loading_layout);

        configureActionBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManagerBinary.start(this);
        loadLiveList();
    }

    @Override
    public void onStop() {
        spiceManagerBinary.shouldStop();
        super.onStop();
    }

    private void updateListViewContent(Items result) {
        jamSnapsListAdapter = new JamSnapsListAdapter(this, spiceManagerBinary, result);
        listView.setAdapter(jamSnapsListAdapter);

        loadingView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    private void loadLiveList() {
        setProgressBarIndeterminateVisibility(true);
        JamSnapsRequest request = new JamSnapsRequest();

        getSpiceManager().execute(request, "jamsnap", DurationInMillis.ONE_SECOND * 10, new LiveListListener());
    }

    private class LiveListListener implements RequestListener<Items> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            setProgressBarIndeterminateVisibility(false);
            Toast.makeText(HomeActivity.this, "Impossible to get the list of users", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(Items result) {
            setProgressBarIndeterminateVisibility(false);
            updateListViewContent(result);
        }
    }



    // Rest is from Github:
    private void configureActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(NAVIGATION_MODE_LIST);

        homeAdapter = new HomeDropdownListAdapter(this);
        actionBar.setListNavigationCallbacks(homeAdapter, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu optionMenu) {
        getSupportMenuInflater().inflate(R.menu.home, optionMenu);

        return super.onCreateOptionsMenu(optionMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_create:
                startActivityForResult(new Intent(getBaseContext(), CameraActivity.class), SNAP_CREATE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        homeAdapter.setSelected(itemPosition);
        Log.d("onNavigationItemSelected", "itemPosition: " + Integer.toString(itemPosition) + "itemId: " + Long.toString(itemId));

        return true;
    }
}
