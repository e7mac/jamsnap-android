package com.pixaura.network;

import com.pixaura.model.Items;

import retrofit.http.GET;

public interface JamSnap {
    @GET("/api/v1/jamsnaps")
    Items jamsnaps();
}