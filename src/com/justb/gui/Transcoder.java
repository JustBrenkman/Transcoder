package com.justb.gui;

import com.justb.Coder;
import com.justb.PathManager;
import com.justb.eventbus.EventBusService;
import com.justb.eventbus.EventHandler;
import com.justb.messages.ConnectEvent;
import com.justb.messages.EncodedMessage;
import com.justb.messages.ServerStartEvent;
import com.justb.server.Client;
import com.justb.server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private DefaultListModel sendList;
    private JTextArea viewMessage;
    private JPanel inboxTab;
    private JPanel currentMessageTab;

    private final JMenuItem connectToServer = new JMenuItem("Connect...");
    private final JMenuItem disconnect = new JMenuItem("Disconnect");
    private final JMenuItem startServer = new JMenuItem("Start Server");
    private final JMenuItem stopServer = new JMenuItem("Stop Server");

    private HashMap<Integer, Character> revisedAlphabet = new HashMap<Integer, Character>();
    private HashMap<Character, Integer> revisedAlphabet1 = new HashMap<Character, Integer>();
    private HashMap<Integer, Character> immutableList = new HashMap<Integer, Character>();
    private HashMap<Character, Integer> immutableList1 = new HashMap<Character, Integer>();

    private char keyCharacter = 'p';

    private Preferences preferencesFrame;
    private Connect connectFrame;

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
        connectFrame = new Connect();

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
        sendList = new DefaultListModel();

        inbox.setModel(inboxList);
        sent.setModel(sendList);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (sentTab.getTitleAt(sentTab.getSelectedIndex()).equals("Message")) {
                    if (server.isRunning()) {
                        server.sendObject(new EncodedMessage(keyCharacter, viewMessage.getText()));
                    }
                    if (client.isRunning()) {
                        client.sendObject(new EncodedMessage(keyCharacter, viewMessage.getText()));
                    }
                    sendList.addElement(viewMessage.getText().substring(0, viewMessage.getText().length() > 50 ? 50 : viewMessage.getText().length()));
                } else {
                    if (server.isRunning()) {
                        server.sendObject(new EncodedMessage(keyCharacter, encondedText.getText()));
                    }
                    if (client.isRunning()) {
                        client.sendObject(new EncodedMessage(keyCharacter, encondedText.getText()));
                    }
                    sendList.addElement(encondedText.getText().substring(0, encondedText.getText().length() > 50 ? 50 : encondedText.getText().length()));
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                server.shutDown();
                client.shutDown();
            }
        });
    }

    private void createMenu() {
        JMenuBar menuBar;
        JMenu file;
        JMenuItem preferences;


        menuBar = new JMenuBar();
        file = new JMenu("File");

        // Item Initialization
        preferences = new JMenuItem("Preferences");

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
                ServerStart.main(new String[]{});
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
//                connectFrame.setVisible(true);
                ConnectDialog.main(new String[]{});
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

    @EventHandler
    public void handleConnect(ConnectEvent event) {
        try {
            client.connectToServer(event.getIpAddress(), event.getPortNumber(), Server.ServerPriority.BACKGROUND);
            disconnect.setEnabled(true);
            connectToServer.setEnabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void handleServerStart(ServerStartEvent serverStartEvent) {
        server.initializeServer(serverStartEvent.getPort(), 100, Server.ServerPriority.BACKGROUND);
        stopServer.setEnabled(true);
        startServer.setEnabled(false);
    }
}
