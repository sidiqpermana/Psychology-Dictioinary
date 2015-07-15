package com.psychology.dictioinary.model;

import java.util.Observable;

/**
 * Created by Sidiq on 16/07/2015.
 */
public class KamusObserver extends Observable {
    public static String NEED_TO_REFRESH = "refresh";

    public void refresh(){
        setChanged();
        notifyObservers(NEED_TO_REFRESH);
    }
}
