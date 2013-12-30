package com.pixaura.adapter;

import java.io.File;

import android.content.Context;
import android.view.ViewGroup;

import com.pixaura.model.Item;
import com.pixaura.model.Items;
import com.pixaura.view.ItemView;
import com.octo.android.robospice.request.okhttp.simple.OkHttpBitmapRequest;
import com.octo.android.robospice.spicelist.SpiceListItemView;
import com.octo.android.robospice.spicelist.okhttp.OkHttpBitmapSpiceManager;
import com.octo.android.robospice.spicelist.okhttp.OkHttpSpiceArrayAdapter;

public class JamSnapsListAdapter extends OkHttpSpiceArrayAdapter<Item> {

    public JamSnapsListAdapter(Context context, OkHttpBitmapSpiceManager spiceManagerBitmap, Items items) {
        super(context, spiceManagerBitmap, items.results);
    }

    @Override
    public OkHttpBitmapRequest createRequest(Item item, int imageIndex, int requestImageWidth, int requestImageHeight) {
        File tempFile = new File(getContext().getCacheDir(), "THUMB_IMAGE_TEMP_" + item.user.username);
        String url = item.user.profilePicture;

        return new OkHttpBitmapRequest(url, requestImageWidth, requestImageHeight, tempFile);
    }

    @Override
    public SpiceListItemView<Item> createView(Context context, ViewGroup parent) {
        return new ItemView(getContext());
    }
}
