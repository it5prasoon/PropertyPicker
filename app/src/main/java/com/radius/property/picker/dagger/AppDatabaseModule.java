package com.radius.property.picker.dagger;

import android.content.Context;
import androidx.room.Room;
import com.radius.property.picker.room.AppDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class AppDatabaseModule {
    private final Context context;

    public AppDatabaseModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    public AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "demo-database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
