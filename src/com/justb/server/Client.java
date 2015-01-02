package com.justb.server;

import com.justb.eventbus.EventBus;
import com.justb.eventbus.EventBusService;
import com.justb.eventbus.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ben on 01/01/15.
 * <p/>
 * JGUILibrary
 */
public class Client extends Thread {
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Object message = "";
    private Socket socket;

    private Logger logger;

    private boolean connected;

    public Client() {
        logger = LoggerFactory.getLogger(Client.class);
    }

    /**
     * connect to a server
     * @param ip = ip address
     * @param port = port of ip address
     * @param priority = priority keep at Server.ServerPriority.BACKGROUND
     */
    public void connectToServer(String ip, int port, Server.ServerPriority priority) throws IOException {
        switch (priority) {
            case HIGH:
                break;
            case BACKGROUND:
                logger.info("Connecting to server");
                socket = new Socket(InetAddress.getByName(ip), port);
                logger.info("Connected to: " + socket.getInetAddress().getHostName());
                setUpStreams();
                this.start();
                break;
            default:
                break;
        }
    }

    private void setUpStreams() throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    private void shutDownClient() throws IOException {
        if (outputStream != null)
            outputStream.close();

        if (inputStream != null)
            inputStream.close();

        if (socket != null)
            socket.close();
    }

    public void shutDown() {
        logger.info("Shutting down client...");
        try {
            shutDownClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Client has shut down.");
    }

    @Override
    public void run() {
        super.run();
        connected = true;
        Object obj;
        do {
            try {
                obj = inputStream.readObject();
                logger.info("Object read");
                EventBusService.publish(obj);
                logger.info("Published Object");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (connected);
    }

    public void sendObject(Object object) {
        try {
            if (outputStream != null)
                outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void handle(String s) {

    }

    public boolean isRunning() {
        return isAlive();
    }
}
