package com.radius.property.picker.network;



import com.radius.property.picker.model.response.DataModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/iranjith4/ad-assignment/db")
    Observable<DataModel> getData();
}