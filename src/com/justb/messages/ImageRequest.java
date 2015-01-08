package com.justb.messages;

import java.io.Serializable;

/**
 * Created by ben on 05/01/15.
 * <p/>
 * JGUILibrary
 */
public class ImageRequest implements Serializable {
    private String effect;
    private String name;

    public ImageRequest(String filename, String effect) {
        this.name = filename;
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }

}
