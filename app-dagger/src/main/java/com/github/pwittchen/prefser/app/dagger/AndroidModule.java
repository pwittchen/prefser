package com.github.pwittchen.prefser.app.dagger;

import android.app.Application;
import android.content.Context;

import com.github.pwittchen.prefser.library.Prefser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = MainActivity.class, library = true)
public class AndroidModule {
    private final Application application;

    public AndroidModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Prefser providePrefser() {
        return new Prefser(application);
    }
}
