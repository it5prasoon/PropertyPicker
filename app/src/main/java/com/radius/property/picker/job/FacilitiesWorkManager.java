package com.radius.property.picker.job;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;


import com.radius.property.picker.dagger.AppDatabaseModule;
import com.radius.property.picker.dagger.component.DaggerAppComponent;
import com.radius.property.picker.network.RetrofitClient;
import com.radius.property.picker.dagger.component.AppComponent;
import com.radius.property.picker.model.response.DataModel;
import com.radius.property.picker.model.response.Exclusion;
import com.radius.property.picker.model.response.Facility;
import com.radius.property.picker.model.response.Option;
import com.radius.property.picker.network.ApiService;
import com.radius.property.picker.room.AppDatabase;
import com.radius.property.picker.room.FacilitiesTable;

import java.util.ArrayList;
import java.util.List;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class FacilitiesWorkManager extends Worker {
    private final AppDatabase db;
    private Result result;
    private final ApiService apiService;

    public FacilitiesWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = AppDatabase.getInstance(context);
        final AppComponent appComponent = DaggerAppComponent.builder()
                .appDatabaseModule(new AppDatabaseModule(context))
                .build();
        final RetrofitClient retrofitClient = appComponent.provideRetrofitClient();
        apiService = retrofitClient.getApiService();
    }

    @NonNull
    @Override
    public Result doWork() {
        apiService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
        return result;
    }

    private void handleError(Throwable throwable) {
        Log.d("WORK MANAGER ERROR:: ", throwable.getLocalizedMessage());
        result = Result.retry();
    }

    private void handleResults(DataModel dataModel) {
        db.userDao().deleteExclusionTable();
        db.userDao().deleteFacilitiesTable();
        final List<List<Exclusion>> exclusionList = new ArrayList<>(dataModel.getExclusions());

        final Exclusion exclusion = new Exclusion();
        for (int i = 0; i < exclusionList.size(); i++) {
            exclusion.setFacilityId(exclusionList.get(i).get(0).getFacilityId());
            exclusion.setOptionsId(exclusionList.get(i).get(0).getOptionsId());
            exclusion.setExclusionFacilityId(exclusionList.get(i).get(1).getFacilityId());
            exclusion.setExclusionOptionsId(exclusionList.get(i).get(1).getOptionsId());
            db.userDao().insertAll(exclusion);
        }

        final FacilitiesTable facilitiesTable = new FacilitiesTable();
        for (final Facility facility : dataModel.getFacilities()) {
            facilitiesTable.setName(facility.getName());
            facilitiesTable.setFacilityId(facility.getFacilityId());
            for (Option option : facility.getOptions()) {
                facilitiesTable.setOptionIconName(option.getIcon());
                facilitiesTable.setOptionId(option.getId());
                facilitiesTable.setOptionName(option.getName());
                db.userDao().insertFacilities(facilitiesTable);
            }
        }
        result = Result.success();
    }
}
