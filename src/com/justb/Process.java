package com.justb;

import javax.swing.*;

/**
 * Created by ben on 26/12/14.
 * <p/>
 * JGUILibrary
 */
public class Process extends JFrame {
    private JPanel pane;
    private JProgressBar progressBar1;

    public Process() {
        super("Progress");

        setContentPane(pane);
        pack();

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void setValueOfProgress(int i) {
        progressBar1.setValue(i);
    }

    public void setFrameVisible(boolean b) {
        this.setVisible(b);
    }
}
