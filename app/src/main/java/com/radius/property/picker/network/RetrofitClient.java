package com.radius.property.picker.network;

import javax.inject.Inject;

public class RetrofitClient {
    private final ApiService apiService;

    @Inject
    public RetrofitClient(ApiService apiService) {
        this.apiService = apiService;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
