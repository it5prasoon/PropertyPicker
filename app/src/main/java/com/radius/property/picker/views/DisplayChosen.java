package com.radius.property.picker.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.widget.Toast;

import com.radius.property.picker.MainActivity;
import com.radius.property.picker.R;
import com.radius.property.picker.utils.Constant;

public class DisplayChosen extends AppCompatActivity {
    private AppCompatTextView tvNumOfRooms, tvPropertyType, tvOtherFacilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_display);
        initViews();

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);

        final String propertyName = sharedPreferences.getString(Constant.PROPERTY_NAME, "");
        final String otherFacility = getIntent().getStringExtra(Constant.OTHER_FACILITY);
        final String noOfRooms = sharedPreferences.getString(Constant.NO_OF_ROOMS, "");

        tvPropertyType.setText(propertyName);
        tvOtherFacilities.setText(otherFacility);
        tvNumOfRooms.setText(noOfRooms);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final Intent intent = new Intent(DisplayChosen.this, OtherFacilitiesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

    private void initViews() {
        tvNumOfRooms = findViewById(R.id.tvNumberOfRooms);
        tvOtherFacilities = findViewById(R.id.tvOtherFacilties);
        tvPropertyType = findViewById(R.id.tvPropertyType);

        AppCompatButton btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(view -> {
            Toast.makeText(DisplayChosen.this, "Submitted", Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(DisplayChosen.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        });
    }
}
