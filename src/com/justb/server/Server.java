package com.justb.server;

import com.justb.eventbus.EventBusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ben on 01/01/15.
 * <p/>
 * JGUILibrary
 */
public class Server extends Thread {

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ServerSocket serverSocket;
    private Socket socket;

    private Logger logger;

    public Server() {
        logger = LoggerFactory.getLogger(Server.class);
    }

    public enum ServerPriority {
        HIGH, BACKGROUND
    }

    public void initializeServer(ServerPriority serverPriority) {
        logger.info("Starting server...");
        try {
            serverSocket = new ServerSocket(6789, 100); // Create a new Server at port 6789 only 100 connections for waiting

            switch (serverPriority) {
                case HIGH:
                    boolean running = true;
                    while(running) {
                        try {
                            waitForConnection();
                            setUpStreams();
                            handleData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            shutDownServer();
                            running = false;
                        }
                    }
                    break;
                case BACKGROUND:
                    this.start();
                    logger.info("Stared Thread... ");
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            waitForConnection();
            setUpStreams();
            handleData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Close the streams and sockets
    private void shutDownServer() {
        logger.info("Closing streams");
        try {
            if (outputStream != null)
                outputStream.close();

            if (inputStream != null)
                inputStream.close();

            if (serverSocket != null)
                serverSocket.close();

            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        logger.info("Waiting for connection...");
        socket = serverSocket.accept();
        logger.info("Connected to " + socket.getInetAddress().getHostName());
    }

    // setup streams to send and recieve data
    private void setUpStreams() throws IOException {
        logger.info("Setting up streams");
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(socket.getInputStream());
        logger.info("Set up streams");
    }

    // Trasmits and recieves the data in the streams
    private void handleData() throws IOException {
        boolean finished = false;
        Object obj;
        logger.info("Waiting for data...");
        do {
            try {
                obj = inputStream.readObject();
                logger.info("Object read");
                EventBusService.publish(obj);
                logger.info("Published Object");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (!finished);
    }

    public void sendObject(Object object) {
        try {
            if (outputStream != null)
                outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        logger.info("Shuting Down Server...");
        logger.info("Stoping thread");
        this.stop();
        shutDownServer();
        logger.info("Server shutdown");
    }

    public boolean isRunning() {
        return isAlive();
    }
}
