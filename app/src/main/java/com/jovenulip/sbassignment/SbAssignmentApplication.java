package com.jovenulip.sbassignment;

import android.app.Application;

import com.jovenulip.sbassignment.di.Injector;

public class SbAssignmentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Injector.initialize();
    }
}
