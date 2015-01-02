package com.justb.gui;

import com.justb.eventbus.EventBusService;
import com.justb.messages.ConnectEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ben on 02/01/15.
 * <p/>
 * JGUILibrary
 */
public class Connect extends JFrame {
    private JPanel panel;
    private JButton cancelButton;
    private JButton connectButton;
    private JTextField textField2;
    private JTextField textField1;

    public Connect() {
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EventBusService.publish(new ConnectEvent(textField2.getText(), Integer.parseInt(textField1.getText())));
                setVisible(false);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
