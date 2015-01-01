package com.justb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import com.bulenkov.darcula.ui.*;

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

    private HashMap<Integer, Character> revisedAlphabet = new HashMap<Integer, Character>();
    private HashMap<Character, Integer> revisedAlphabet1 = new HashMap<Character, Integer>();
    private HashMap<Integer, Character> immutableList = new HashMap<Integer, Character>();
    private HashMap<Character, Integer> immutableList1 = new HashMap<Character, Integer>();

    private char keyCharacter = 'p';

    private Preferences preferencesFrame;

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

        createMenu();

        setContentPane(outterPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        preferencesFrame = new Preferences(this);
    }

    private void createMenu() {

        JMenuBar menuBar;
        JMenu file;
        JMenuItem preferences;

        menuBar = new JMenuBar();
        file = new JMenu("File");
        preferences = new JMenuItem("Preferences");

        preferences.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                preferencesFrame.setFrameVisible(true);
                System.out.println("Opening Preferences");
                preferencesFrame.setKeyChar(keyCharacter);
            }
        });

        preferences.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        menuBar.add(file);
        file.add(preferences);

        this.setJMenuBar(menuBar);
    }

    public void changeKeyCharacter(char c) {
        keyCharacter = c;
    }

    public void updateLists() {
        Coder.resetLists(keyCharacter);
    }
}