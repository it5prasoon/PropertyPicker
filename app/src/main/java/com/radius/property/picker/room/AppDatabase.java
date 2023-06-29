package com.radius.property.picker.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.radius.property.picker.dagger.AppDatabaseModule;
import com.radius.property.picker.dagger.component.AppComponent;
import com.radius.property.picker.dagger.component.DaggerAppComponent;
import com.radius.property.picker.model.response.Exclusion;

@Database(entities = {Exclusion.class, FacilitiesTable.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mINSTANCE;

    public abstract ExclusionDao userDao();

    public static AppDatabase getInstance(Context context) {
        if (mINSTANCE == null) {
            final AppComponent appComponent = DaggerAppComponent.builder()
                    .appDatabaseModule(new AppDatabaseModule(context))
                    .build();
            mINSTANCE = appComponent.provideAppDatabase();
        }
        return mINSTANCE;
    }

    public static void destroyInstance() {
        mINSTANCE = null;
    }
}
