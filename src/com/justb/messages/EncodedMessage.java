package com.justb.messages;

import java.io.Serializable;

/**
 * Created by ben on 01/01/15.
 * <p/>
 * JGUILibrary
 */
public class EncodedMessage implements Serializable {
    public char keyCharater;
    public String message;

    public EncodedMessage(char key, String mess) {
        message = mess;
        keyCharater = key;
    }
}
