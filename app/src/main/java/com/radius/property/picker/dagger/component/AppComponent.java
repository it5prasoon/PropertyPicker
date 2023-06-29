package com.radius.property.picker.dagger.component;

import com.radius.property.picker.dagger.AppDatabaseModule;
import com.radius.property.picker.network.RetrofitClient;
import com.radius.property.picker.dagger.RetrofitModule;
import com.radius.property.picker.room.AppDatabase;
import dagger.Component;

import javax.inject.Singleton;


@Singleton
@Component(modules = {RetrofitModule.class, AppDatabaseModule.class})
public interface AppComponent {
    RetrofitClient provideRetrofitClient();

    AppDatabase provideAppDatabase();

}
