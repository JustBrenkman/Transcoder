package com.justb.messages;

import java.io.Serializable;

/**
 * Created by ben on 05/01/15.
 * <p/>
 * JGUILibrary
 */
public class RuntimeMessage implements Serializable {
    private String command;

    public RuntimeMessage(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
