package com.justb.messages;

import java.io.Serializable;

/**
 * Created by ben on 02/01/15.
 * <p/>
 * JGUILibrary
 */
public class ServerStartEvent implements Serializable {
    private int port;

    public ServerStartEvent(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
