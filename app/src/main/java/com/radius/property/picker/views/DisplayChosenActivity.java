package com.radius.property.picker.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.radius.property.picker.MainActivity;
import com.radius.property.picker.R;
import com.radius.property.picker.databinding.ActivityChooseDisplayBinding;
import com.radius.property.picker.utils.Constant;

public class DisplayChosenActivity extends AppCompatActivity {
    private ActivityChooseDisplayBinding activityChooseDisplayBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChooseDisplayBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_display);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);

        final String propertyName = sharedPreferences.getString(Constant.PROPERTY_NAME, "");
        final String otherFacility = getIntent().getStringExtra(Constant.OTHER_FACILITY);
        final String noOfRooms = sharedPreferences.getString(Constant.NO_OF_ROOMS, "");

        activityChooseDisplayBinding.tvPropertyType.setText(propertyName);
        activityChooseDisplayBinding.tvOtherFacilties.setText(otherFacility);
        activityChooseDisplayBinding.tvNumberOfRooms.setText(noOfRooms);
        handleSubmit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final Intent intent = new Intent(DisplayChosenActivity.this, OtherFacilitiesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

    private void handleSubmit() {
        activityChooseDisplayBinding.btnSubmit.setOnClickListener(view -> {
            Toast.makeText(DisplayChosenActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(DisplayChosenActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        });
    }
}
