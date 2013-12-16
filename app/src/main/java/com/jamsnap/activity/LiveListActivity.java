package com.jamsnap.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.jamsnap.R;
import com.jamsnap.adapter.JamSnapsListAdapter;
import com.jamsnap.model.Items;
import com.jamsnap.network.JamSnapsRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.spicelist.okhttp.OkHttpBitmapSpiceManager;

public class LiveListActivity extends BaseActivity {

    private ListView listView;
    private View loadingView;

    private JamSnapsListAdapter jamSnapsListAdapter;

    private OkHttpBitmapSpiceManager spiceManagerBinary = new OkHttpBitmapSpiceManager();

    // --------------------------------------------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // --------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setProgressBarIndeterminateVisibility(false);
        setContentView(R.layout.live_list);

        listView = (ListView) findViewById(R.id.listview_jamsnap);
        loadingView = findViewById(R.id.loading_layout);
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

    // --------------------------------------------------------------------------------------------
    // PRIVATE
    // --------------------------------------------------------------------------------------------

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

    // --------------------------------------------------------------------------------------------
    // PRIVATE
    // --------------------------------------------------------------------------------------------

    private class LiveListListener implements RequestListener<Items> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            setProgressBarIndeterminateVisibility(false);
            Toast.makeText(LiveListActivity.this, "Impossible to get the list of users", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(Items result) {
            setProgressBarIndeterminateVisibility(false);
            updateListViewContent(result);
        }
    }
}
