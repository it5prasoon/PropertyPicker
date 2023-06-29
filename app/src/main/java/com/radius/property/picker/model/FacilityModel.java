package com.radius.property.picker.model;

import android.content.Context;
import com.radius.property.picker.dagger.AppDatabaseModule;
import com.radius.property.picker.dagger.component.AppComponent;
import com.radius.property.picker.dagger.component.DaggerAppComponent;
import com.radius.property.picker.model.response.DataModel;
import com.radius.property.picker.network.ApiService;
import com.radius.property.picker.network.RetrofitClient;
import com.radius.property.picker.presenter.MainContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FacilityModel implements MainContract.GetFacilityInteractor {
    private MainContract.GetFacilityInteractor.OnFinishedListener onFinishedListener;
    private final ApiService apiService;

    public FacilityModel(Context context) {
        final AppComponent appComponent = DaggerAppComponent.builder()
                .appDatabaseModule(new AppDatabaseModule(context))
                .build();

        final RetrofitClient retrofitClient = appComponent.provideRetrofitClient();
        apiService = retrofitClient.getApiService();
    }

    @Override
    public void getNoticeArrayList(OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
        apiService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
    }

    private void handleResults(DataModel dataModel) {
        onFinishedListener.onFinished(dataModel);
    }

    private void handleError(Throwable throwable) {
        onFinishedListener.onFailure(throwable);
    }

}
