package com.jamsnap.network;

import com.jamsnap.model.Items;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import roboguice.util.temp.Ln;

public class JamSnapsRequest extends RetrofitSpiceRequest<Items, JamSnap> {

    public JamSnapsRequest() {
        super(Items.class, JamSnap.class);
    }

    @Override
    public Items loadDataFromNetwork() {
        Ln.d("Call web service ");
        return getService().jamsnaps();
    }
}
