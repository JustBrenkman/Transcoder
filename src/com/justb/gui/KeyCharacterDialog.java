package com.justb.gui;

import com.justb.eventbus.EventBusService;
import com.justb.messages.ChangeKeyCharacterEvent;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class KeyCharacterDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField keyCharacterField;

    public KeyCharacterDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Custom code
//        NumberFormat n = NumberFormat.getNumberInstance();
//        NumberFormatter nn = new NumberFormatter(n);
//        DefaultFormatterFactory dff = new DefaultFormatterFactory(nn);
//
//        keyCharacterField.setFormatterFactory(dff);
    }

    private void onOK() {
// add your code here
        if (formattedTextField1.getText() != null) {
            EventBusService.publish(new ChangeKeyCharacterEvent(formattedTextField1.getText().charAt(0)));
        }
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        KeyCharacterDialog dialog = new KeyCharacterDialog();
        dialog.pack();
        dialog.setVisible(true);
//        System.exit(0);
    }
}
