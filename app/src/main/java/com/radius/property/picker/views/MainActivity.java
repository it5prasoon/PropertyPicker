package com.radius.property.picker.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.*;
import com.radius.property.picker.R;
import com.radius.property.picker.adapter.FaciltyAdapter;
import com.radius.property.picker.databinding.ActivityMainBinding;
import com.radius.property.picker.job.FacilitiesWorkManager;
import com.radius.property.picker.presenter.MainContract;
import com.radius.property.picker.presenter.MainPresenter;
import com.radius.property.picker.room.FacilitiesTable;
import com.radius.property.picker.utils.Constant;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private Context context;
    private Constraints constraints;
    private SharedPreferences sharedPreferences;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, com.radius.property.picker.R.layout.activity_main);

        context = this;
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        binding.tvFacilityTitle.setText("Property Type");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            /**
             * constraint is used to define set of conditions before performing any functions,ex whether
             * internet is required or not etc.
             */
            constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
        }

        /** To make api call once in a day and update the local database */
        final PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                FacilitiesWorkManager.class, 24, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        /**
         * WorkManager performs the operations in background based on the set of constraint define,
         * it performs operation only once or periodically based on the request
         */
        WorkManager.getInstance().
                enqueueUniquePeriodicWork("dbdata", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);


        MainContract.presenter presenter = new MainPresenter(this);
        presenter.requestDataFromDb("Property Type", null, null);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    /**
     * This listener provides arraylist in order to populate the recycler view
     */
    @Override
    public void setDataToRecyclerView(List<FacilitiesTable> noticeArrayList) {

        if (noticeArrayList.size() > 0) {

            FaciltyAdapter faciltyAdapter = new FaciltyAdapter(context, noticeArrayList, recyclerViewOnClick);
            binding.rvFacility.setLayoutManager(new LinearLayoutManager(context));
            binding.rvFacility.setAdapter(faciltyAdapter);
            faciltyAdapter.notifyDataSetChanged();
        } else {
            MainActivity.this.recreate();
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d("ERROR:: ", throwable.getLocalizedMessage());
    }

    MainContract.RecyclerViewOnClick recyclerViewOnClick = new MainContract.RecyclerViewOnClick() {
        @Override
        public void onClick(FacilitiesTable facilityModel) {
            final Intent intent = new Intent(context, RoomsNumberActivity.class);
            intent.putExtra(Constant.FACILITY_ID, facilityModel.getFacilityId());
            intent.putExtra(Constant.OPTION_ID, facilityModel.getOptionId());

            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.PROPERTY_NAME, facilityModel.getOptionName());
            editor.apply();

            startActivity(intent);
            overridePendingTransition(com.radius.property.picker.R.anim.enter, R.anim.exit);
            finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

}
