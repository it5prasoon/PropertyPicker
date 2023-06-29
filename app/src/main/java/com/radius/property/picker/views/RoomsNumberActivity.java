package com.radius.property.picker.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;


import com.radius.property.picker.MainActivity;
import com.radius.property.picker.R;
import com.radius.property.picker.adapter.FaciltyAdapter;
import com.radius.property.picker.utils.Constant;
import com.radius.property.picker.presenter.MainContract;
import com.radius.property.picker.presenter.MainPresenter;
import com.radius.property.picker.room.FacilitiesTable;

import java.util.List;

public class RoomsNumberActivity extends AppCompatActivity implements MainContract.MainView {
    private RecyclerView recyclerView;
    private Context context;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String facilityId = getIntent().getStringExtra(Constant.FACILITY_ID);
        final String optionId = getIntent().getStringExtra(Constant.OPTION_ID);

        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);

        context = this;
        recyclerView = findViewById(R.id.rvFacility);
        AppCompatTextView tvFacilityTitle = findViewById(R.id.tvFacilityTitle);
        tvFacilityTitle.setText("No Of Rooms");

        MainContract.presenter presenter = new MainPresenter(this);
        presenter.requestDataFromDb("Number of Rooms", facilityId, optionId);
    }


    MainContract.RecyclerViewOnClick recyclerViewOnClick = new MainContract.RecyclerViewOnClick() {
        @Override
        public void onClick(FacilitiesTable facilityModel) {
            Intent intent = new Intent(RoomsNumberActivity.this, OtherFacilitiesActivity.class);
            intent.putExtra(Constant.FACILITY_ID, facilityModel.getFacilityId());
            intent.putExtra(Constant.OPTION_ID, facilityModel.getOptionId());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.NO_OF_ROOMS, facilityModel.getOptionName());
            editor.apply();

            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();

        }
    };

    @Override
    public void setDataToRecyclerView(List<FacilitiesTable> noticeArrayList) {
        FaciltyAdapter faciltyAdapter = new FaciltyAdapter(context, noticeArrayList, recyclerViewOnClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(faciltyAdapter);
        faciltyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d("ERROR:: ", throwable.getLocalizedMessage());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RoomsNumberActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

}
