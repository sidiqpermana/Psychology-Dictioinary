package com.psychology.dictioinary.app;

import android.app.Application;

import com.psychology.dictioinary.model.KamusObserver;

/**
 * Created by Sidiq on 16/07/2015.
 */
public class DictionaryApplication extends Application {
    KamusObserver kamusObserver;
    @Override
    public void onCreate() {
        super.onCreate();
        kamusObserver = new KamusObserver();
    }

    public KamusObserver getKamusObserver(){
        return kamusObserver;
    }
}
