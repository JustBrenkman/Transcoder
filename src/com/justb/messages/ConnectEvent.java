package com.justb.messages;

import java.io.Serializable;

/**
 * Created by ben on 02/01/15.
 * <p/>
 * JGUILibrary
 */
public class ConnectEvent implements Serializable {
    private String ipAddress;
    private int portNumber;

    public ConnectEvent(String ipAddress, int portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
