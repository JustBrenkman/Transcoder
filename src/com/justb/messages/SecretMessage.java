package com.justb.messages;

import java.io.Serializable;

/**
 * Created by ben on 02/01/15.
 * <p/>
 * JGUILibrary
 */
public class SecretMessage implements Serializable {
    private char key;
    private String message;

    public SecretMessage(char key, String message) {
        this.key = key;
        this.message = message;
    }

    public char getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}
