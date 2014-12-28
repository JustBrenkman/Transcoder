package com.justb;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by ben on 18/12/14.
 * <p/>
 * JGUILibrary
 */
public class transcoder extends JFrame {
    private JTextPane encodedText;
    private JTextPane decodedText;
    private JButton decodeButton;
    private JButton encodeButton;
    private JPanel pane;
    private JLabel encodedLabel;
    private JLabel decodedLabel;
    private JScrollPane jscrolldecode;
    private JScrollPane jscrollencode;

    // Menu
    private Preferences preferencesFrame;
    private Process progress;

    private HashMap<Integer, Character> revisedAlphabet = new HashMap<Integer, Character>();
    private HashMap<Character, Integer> revisedAlphabet1 = new HashMap<Character, Integer>();
    private HashMap<Integer, Character> immutableList = new HashMap<Integer, Character>();
    private HashMap<Character, Integer> immutableList1 = new HashMap<Character, Integer>();

    private char keyCharacter = 'p';


    public transcoder() {
        super("Transcoder");

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                encode(decodedText, encodedText);
            }
        });
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                decode(encodedText, decodedText);
            }
        });

        encodeButton.setBackground(new Color(60, 63, 65));
        encodeButton.setForeground(new Color(187,187,187));

        decodeButton.setBackground(new Color(60, 63, 65));
        decodeButton.setForeground(new Color(187,187,187));

        encodedText.setBackground(new Color(60, 63, 65));
        encodedText.setForeground(new Color(187,187,187));

        decodedText.setBackground(new Color(60, 63, 65));
        decodedText.setForeground(new Color(187,187,187));

        pane.setBackground(new Color(47, 50, 52));
        pane.setForeground(new Color(187, 187, 187));

        encodedLabel.setBackground(new Color(60, 63, 65));
        encodedLabel.setForeground(new Color(187,187,187));

        decodedLabel.setBackground(new Color(60, 63, 65));
        decodedLabel.setForeground(new Color(187,187,187));

        jscrolldecode.setBackground(new Color(60, 63, 65));
        jscrolldecode.setForeground(new Color(187,187,187));

        jscrollencode.setBackground(new Color(60, 63, 65));
        jscrollencode.setForeground(new Color(187,187,187));

        createMenu();

        setContentPane(pane);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        setUpLists(immutableList, revisedAlphabet, keyCharacter);

        preferencesFrame = new Preferences(this);
        progress = new Process();
    }

    public void encode(JTextPane encode, JTextPane to) {
        StringBuilder stringBuilder = new StringBuilder(encode.getText());
        StringBuilder result = new StringBuilder();

        progress.setValueOfProgress(0);
        progress.setFrameVisible(true);

        for (int i = 0; i < stringBuilder.length(); i++) {
            if (immutableList1.get(stringBuilder.charAt(i)) == null) {
                if (immutableList1.get( String.valueOf(stringBuilder.charAt(i)).toLowerCase().charAt(0)) != null) {
                    result.append(immutableList.get(revisedAlphabet1.get(String.valueOf(stringBuilder.charAt(i)).toLowerCase().charAt(0))).toString().toUpperCase());
                } else {
                    result.append(stringBuilder.charAt(i));
                }
            } else {
                result.append(immutableList.get(revisedAlphabet1.get(stringBuilder.charAt(i))));
            }

            progress.setValueOfProgress(result.length() / stringBuilder.length() * 100);
        }

        to.setText(result.toString());
        progress.setFrameVisible(false);
        progress.setValueOfProgress(0);
    }

    public void decode(JTextPane decode, JTextPane to) {
        StringBuilder stringBuilder = new StringBuilder(decode.getText());
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < stringBuilder.length(); i++) {
            if (immutableList1.get(stringBuilder.charAt(i)) == null) {
                if (immutableList1.get(String.valueOf(stringBuilder.charAt(i)).toLowerCase().charAt(0)) != null) {
                    result.append(revisedAlphabet.get(immutableList1.get(String.valueOf(stringBuilder.charAt(i)).toLowerCase().charAt(0))).toString().toUpperCase());
                } else {
                    result.append(stringBuilder.charAt(i));
                }
            } else {
                result.append(revisedAlphabet.get(immutableList1.get(stringBuilder.charAt(i))));
            }
        }

        to.setText(result.toString());
    }

    public void setUpLists(HashMap<Integer, Character> immutableAlphabet, HashMap<Integer, Character> revisedList, Character character) {
        immutableAlphabet.put(0, 'a');
        immutableAlphabet.put(1, 'b');
        immutableAlphabet.put(2, 'c');
        immutableAlphabet.put(3, 'd');
        immutableAlphabet.put(4, 'e');
        immutableAlphabet.put(5, 'f');
        immutableAlphabet.put(6, 'g');
        immutableAlphabet.put(7, 'h');
        immutableAlphabet.put(8, 'i');
        immutableAlphabet.put(9, 'j');
        immutableAlphabet.put(10, 'k');
        immutableAlphabet.put(11, 'l');
        immutableAlphabet.put(12, 'm');
        immutableAlphabet.put(13, 'n');
        immutableAlphabet.put(14, 'o');
        immutableAlphabet.put(15, 'p');
        immutableAlphabet.put(16, 'q');
        immutableAlphabet.put(17, 'r');
        immutableAlphabet.put(18, 's');
        immutableAlphabet.put(19, 't');
        immutableAlphabet.put(20, 'u');
        immutableAlphabet.put(21, 'v');
        immutableAlphabet.put(22, 'w');
        immutableAlphabet.put(23, 'x');
        immutableAlphabet.put(24, 'y');
        immutableAlphabet.put(25, 'z');

        immutableList1.put('a', 0);
        immutableList1.put('b', 1);
        immutableList1.put('c', 2);
        immutableList1.put('d', 3);
        immutableList1.put('e', 4);
        immutableList1.put('f', 5);
        immutableList1.put('g', 6);
        immutableList1.put('h', 7);
        immutableList1.put('i', 8);
        immutableList1.put('j', 9);
        immutableList1.put('k', 10);
        immutableList1.put('l', 11);
        immutableList1.put('m', 12);
        immutableList1.put('n', 13);
        immutableList1.put('o', 14);
        immutableList1.put('p', 15);
        immutableList1.put('q', 16);
        immutableList1.put('r', 17);
        immutableList1.put('s', 18);
        immutableList1.put('t', 19);
        immutableList1.put('u', 20);
        immutableList1.put('v', 21);
        immutableList1.put('w', 22);
        immutableList1.put('x', 23);
        immutableList1.put('y', 24);
        immutableList1.put('z', 25);

        for (int i = immutableList1.get(character); i < 26; i++) {
            revisedList.put(i, immutableAlphabet.get(i - immutableList1.get(character)));
            revisedAlphabet1.put(revisedList.get(i), i);
        }

        for (int i = 0; i < immutableList1.get(character); i++) {
            revisedList.put(i, immutableAlphabet.get((i + (26 - immutableList1.get(character)))));
            revisedAlphabet1.put(revisedList.get(i), i);
        }

        // Print out results

//        for (int i = 0; i < immutableAlphabet.size(); i++) {
//            System.out.println(immutableAlphabet.get(i));
//        }
//
//        System.out.println();
//
//        System.out.println(immutableList1.get(character));
//
//        for (int i = 0; i < 26; i++) {
//            System.out.println(i + ": " + revisedList.get(i));
//        }
//
//        for (int i = 0; i < 26; i++) {
//            System.out.println(immutableAlphabet.get(i) + ": " + revisedAlphabet1.get(immutableAlphabet.get(i)));
//        }
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

        menuBar.add(file);
        file.add(preferences);

        this.setJMenuBar(menuBar);
    }

    public void changeKeyCharacter(char c) {
        keyCharacter = c;
    }

    public void updateLists() {
        setUpLists(immutableList, revisedAlphabet, keyCharacter);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new GTKLookAndFeel());
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            System.out.println("Unable to supply secified look and feel");
        }
    }
}
