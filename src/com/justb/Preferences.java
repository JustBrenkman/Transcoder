package com.justb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ben on 19/12/14.
 * <p/>
 * JGUILibrary
 */
public class Preferences extends JFrame {
    private JPanel panel;
    private JButton okButton;
    private JTextField textField1;
    private JButton canelButton;

    private transcoder transcoder;

    public Preferences(transcoder t) {
        super("Preferences");

        this.transcoder = t;

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (textField1.getText() != null) {
                    transcoder.changeKeyCharacter(textField1.getText().charAt(0));
                    transcoder.updateLists();
                    setFrameVisible(false);
                }
            }
        });

        canelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setFrameVisible(false);
            }
        });

        setContentPane(panel);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setFrameVisible(boolean b) {
        this.setVisible(b);
    }

    public void setKeyChar(char keyChar) {
        textField1.setText(String.valueOf(keyChar));
    }
}
