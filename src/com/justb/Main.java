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

        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.setLookAndFeel(new GTKLookAndFeel());
            UIManager.setLookAndFeel(new DarculaLaf());
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

        Transcoder transcoder = new Transcoder();
    }
}
