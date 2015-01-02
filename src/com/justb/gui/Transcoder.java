package com.justb.gui;

import com.justb.Coder;
import com.justb.PathManager;
import com.justb.eventbus.EventBusService;
import com.justb.eventbus.EventHandler;
import com.justb.messages.EncodedMessage;
import com.justb.server.Client;
import com.justb.server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ben on 29/12/14.
 * <p/>
 * JGUILibrary
 */
public class Transcoder extends JFrame {
    private JPanel panel;
    private JPanel panelInner;
    private JTextArea encondedText;
    private JTextArea decodedText;
    private JProgressBar progressBar1;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTabbedPane tabs;
    private JPanel outterPanel;
    private JButton sendButton;
    private JTabbedPane sentTab;
    private JList sent;
    private JList inbox;
    private DefaultListModel inboxList;
    private JTextArea viewMessage;
    private JPanel inboxTab;
    private JPanel currentMessageTab;

    private HashMap<Integer, Character> revisedAlphabet = new HashMap<Integer, Character>();
    private HashMap<Character, Integer> revisedAlphabet1 = new HashMap<Character, Integer>();
    private HashMap<Integer, Character> immutableList = new HashMap<Integer, Character>();
    private HashMap<Character, Integer> immutableList1 = new HashMap<Character, Integer>();

    private char keyCharacter = 'p';

    private Preferences preferencesFrame;

    private PathManager pathManager;

    private Server server;
    private Client client;

    public Transcoder() {
        super("Transcoder");

        Coder.getInstance();

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Coder.encode(decodedText, encondedText, keyCharacter);
            }
        });
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Coder.decode(encondedText, decodedText, keyCharacter);
            }
        });

        pathManager = PathManager.getInstance();
        PathManager.initialize();

        createMenu();

        setContentPane(outterPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        preferencesFrame = new Preferences(this);

        server = new Server();
//        server.initializeServer(Server.ServerPriority.BACKGROUND);
        client = new Client();

        EventBusService.subscribe(this);
        sentTab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (sentTab.getTitleAt(sentTab.getSelectedIndex()).equals("Message")) {
                    viewMessage.setText(encondedText.getText());
                }
            }
        });

        inboxList = new DefaultListModel();

        inbox.setModel(inboxList);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (server.isRunning()) {
                    server.sendObject(new EncodedMessage(keyCharacter, decodedText.getText()));
                }
                if (client.isRunning()) {
                    client.sendObject(new EncodedMessage(keyCharacter, decodedText.getText()));
                }
            }
        });
    }

    private void createMenu() {
        JMenuBar menuBar;
        JMenu file;
        JMenuItem preferences;
        final JMenuItem startServer;
        final JMenuItem stopServer;
        final JMenuItem connectToServer;
        final JMenuItem disconnect;

        menuBar = new JMenuBar();
        file = new JMenu("File");

        // Item Initialization
        preferences = new JMenuItem("Preferences");
        startServer = new JMenuItem("Start Server");
        stopServer = new JMenuItem("Stop Server");
        connectToServer = new JMenuItem("Connect...");
        disconnect = new JMenuItem("Disconnect");

        ImageIcon icon = new ImageIcon();
        try {
            icon = new ImageIcon(ImageIO.read(new File(PathManager.getLocationPath() + "/resources/images/preferences_16.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        preferences.setIcon(icon);
        preferences.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                preferencesFrame.setFrameVisible(true);
                System.out.println("Opening Preferences");
                preferencesFrame.setKeyChar(keyCharacter);
            }
        });

        preferences.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        // Start server initialization
        startServer.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                server.initializeServer(Server.ServerPriority.BACKGROUND);
                stopServer.setEnabled(true);
                startServer.setEnabled(false);
            }
        });

        // Stop server initialization
        stopServer.setAccelerator(KeyStroke.getKeyStroke('K', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        stopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                server.shutDown();
                stopServer.setEnabled(false);
                startServer.setEnabled(true);
            }
        });
        stopServer.setEnabled(false);

        // Connect to Server initialization
        connectToServer.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        connectToServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    client.connectToServer("127.0.0.1", 6789, Server.ServerPriority.BACKGROUND);
                    disconnect.setEnabled(true);
                    connectToServer.setEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Disconnect from the server
        disconnect.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.shutDown();
                disconnect.setEnabled(false);
                connectToServer.setEnabled(true);
            }
        });

        disconnect.setEnabled(false);

        menuBar.add(file);
        file.add(preferences);
        file.addSeparator();
        file.add(startServer);
        file.add(stopServer);
        file.add(connectToServer);
        file.add(disconnect);

        this.setJMenuBar(menuBar);
    }

    public void changeKeyCharacter(char c) {
        keyCharacter = c;
    }

    public void updateLists() {
        Coder.resetLists(keyCharacter);
    }

    @EventHandler
    public void handler(EncodedMessage e) {
        System.out.println(e.message);
//        inboxList.addElement("Hello");
        inboxList.addElement(e.message.substring(0, e.message.length() >= 50 ? 50 : e.message.length()));
    }
}
