package com.jamsnap.network;

import com.jamsnap.model.Items;

import retrofit.http.GET;

public interface JamSnap {
    @GET("/api/v1/jamsnaps")
    Items jamsnaps();
}