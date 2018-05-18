package com.jovenulip.sbassignment.di;

public enum Injector {
    INSTANCE;

    AppComponent appComponent;

    Injector() {
    }

    public static void initialize() {
        INSTANCE.appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public static AppComponent get() {
        return INSTANCE.appComponent;
    }
}
