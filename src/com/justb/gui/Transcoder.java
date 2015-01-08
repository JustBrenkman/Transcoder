package com.justb.gui;

import com.justb.Coder;
import com.justb.PathManager;
import com.justb.eventbus.EventBusService;
import com.justb.eventbus.EventHandler;
import com.justb.messages.*;
import com.justb.messages.internal.TakeImage;
import com.justb.server.Client;
import com.justb.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.util.*;

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
    private JToolBar toolBar;
    private JButton openToolButton;
    private JComboBox imageEffect;
    private JTextField pictureFileName;
    private JButton takePictureButton;
    private JButton pictureButton;
    private java.util.List<String> listOfInbox = new ArrayList<String>();
    private java.util.List<String> listOfSent = new ArrayList<String>();

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

    private Logger logger;

    private Transcoder instance;

    public Transcoder() {
        super("Transcoder");

        instance = this;

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
//                        client.sendObject(new SecretMessage(keyCharacter, decodedText.getText()));
                    }
                    sendList.addElement(viewMessage.getText().substring(0, viewMessage.getText().length() > 50 ? 50 : viewMessage.getText().length()));
                    listOfSent.add(viewMessage.getText());
                } else {
                    if (server.isRunning()) {
                        server.sendObject(new EncodedMessage(keyCharacter, encondedText.getText()));
                    }
                    if (client.isRunning()) {
                        client.sendObject(new EncodedMessage(keyCharacter, encondedText.getText()));
//                        client.sendObject(new SecretMessage(keyCharacter, decodedText.getText()));
                    }
                    sendList.addElement(encondedText.getText().substring(0, encondedText.getText().length() > 50 ? 50 : encondedText.getText().length()));
                    listOfSent.add(encondedText.getText());
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


        inbox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                JList list = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
//                    encondedText.setText((String) inboxList.getElementAt(index));
                    encondedText.setText((String) listOfInbox.get(index));
                } else if (e.getClickCount() == 3) {
                    int index = list.locationToIndex(e.getPoint());
                }
            }
        });
        sent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                JList list = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
//                    encondedText.setText((String) sendList.getElementAt(index));
                    encondedText.setText((String) listOfSent.get(index));
                } else if (e.getClickCount() == 3) {
                    int index = list.locationToIndex(e.getPoint());
                }
            }
        });
        openToolButton.setBorderPainted(false);

        logger = LoggerFactory.getLogger(Transcoder.class);
//        pictureButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                server.sendObject(new RuntimeMessage("raspistill -ifx cartoon -o still" + System.currentTimeMillis() + ".jpg"));
//                System.out.println("Took a picture");
//            }
//        });
        takePictureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                server.sendObject(new ImageRequest(pictureFileName.getText(), (String) imageEffect.getSelectedItem()));
//                System.out.println("Took a picture");
                PictureRequest pic = new PictureRequest();
                pic.start(instance);
                if (instance == null) {
                    System.out.println("NULL");
                }
            }
        });
    }

    private void createMenu() {
        JMenuBar menuBar;
        JMenu file;
        JMenuItem preferences;
        JMenuItem exit;

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
//                preferencesFrame.setFrameVisible(true);
//                System.out.println("Opening Preferences");
                preferencesFrame.setKeyChar(keyCharacter);
                KeyCharacterDialog.main(new String[]{});
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

        exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shutDown();
            }
        });

        menuBar.add(file);
        file.add(preferences);
        file.addSeparator();
        file.add(startServer);
        file.add(stopServer);
        file.add(connectToServer);
        file.add(disconnect);
        file.addSeparator();
        file.add(exit);

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
        listOfInbox.add(e.message);
        inboxList.addElement(e.message.substring(0, e.message.length() >= 50 ? 50 : e.message.length()));
        logger.info("Key Character: " + e.keyCharater);

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

    @EventHandler
    public void handleKeyCharacterChange(ChangeKeyCharacterEvent event) {
        changeKeyCharacter(event.getKeyCharacter());
        updateLists();
    }

    @EventHandler
    public void handleRuntimeMessage(RuntimeMessage runtimeMessage) {
        try {
            Runtime.getRuntime().exec(runtimeMessage.getCommand());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void handleImageRequest(ImageRequest imageRequest) {
        logger.info("Taking a picture: effect: " + imageRequest.getEffect().toLowerCase() + " Filename: " + imageRequest.getName());
        java.lang.Process p = null;
        try {
            if (imageRequest.getEffect().toLowerCase().equals("none")) {
                p = Runtime.getRuntime().exec("raspistill" + " -o " + PathManager.getLocationPath() + "/imageTransfer.jpg");
            } else {
                p = Runtime.getRuntime().exec("raspistill" + " -ifx " + imageRequest.getEffect().toLowerCase() + " -o "  + PathManager.getLocationPath() + "/imageTransfer.jpg");
            }
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(new File(PathManager.getLocationPath() + "/imageTransfer.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (getServer().isRunning()) {
            getServer().sendObject(new ImageExchange(bufferedImage, imageRequest.getName()));
        } else if (getClient().isRunning()) {
            getClient().sendObject(new ImageExchange(bufferedImage, imageRequest.getName()));
        }
    }

    public void shutDown() {
        client.shutDown();
        server.shutDown();
        dispose();
        System.exit(0);
    }

    public Server getServer() {
        return server;
    }

    public Client getClient() {
        return client;
    }

    @EventHandler
    public void handleImageExchange(ImageExchange exchange) {
        try {
            File file = new File(PathManager.getLocationPath() + "/" + exchange.getName() + ".jpg");
            ImageIO.write(exchange.getImage(), "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void handleTakeImage(TakeImage image) {
            if (getServer() != null) {
                if (getServer().isRunning()) {
                    getServer().sendObject(new ImageRequest(image.getName(), image.getEffect()));
                }
            }
            if (getClient() != null) {
                if (getClient().isRunning()) {
                    getClient().sendObject(new ImageRequest(image.getName(), image.getEffect()));
                }
            }
            System.out.println("Took a picture");
        }
}
