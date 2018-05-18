package com.jovenulip.sbassignment.di;

import com.jovenulip.sbassignment.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})

public interface AppComponent {
    void inject(MainPresenter presenter);
}

