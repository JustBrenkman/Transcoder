package com.justb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setContentPane(pane);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public void encode(JTextPane encode, JTextPane to) {
        to.setText(encode.getText());
    }

    public void decode(JTextPane decode, JTextPane to) {
        to.setText(decode.getText());
    }
}
