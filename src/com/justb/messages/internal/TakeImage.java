package com.justb.messages.internal;

import java.io.Serializable;

/**
 * Created by ben on 06/01/15.
 * <p/>
 * JGUILibrary
 */
public class TakeImage implements Serializable {
    private String effect;
    private String name;

    public TakeImage(String name, String effect) {
        this.effect = effect;
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }
}
