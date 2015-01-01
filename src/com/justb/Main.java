package com.justb;

import javax.swing.*;

import com.bulenkov.darcula.DarculaLaf;

/**
 * Created by ben on 18/12/14.
 * <p/>
 * JGUILibrary
 */
public class Main {

    public static void main(String args[]) {

        PathManager.getInstance();
        PathManager.initialize();

        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            System.out.println("Unable to supply secified look and feel");
        }

        Transcoder transcoder = new Transcoder();
    }

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }
}
