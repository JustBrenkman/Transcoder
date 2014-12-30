package com.justb;

import javax.swing.*;
import java.util.HashMap;

/**
 * Created by ben on 29/12/14.
 * <p/>
 * JGUILibrary
 */
public class Coder {

    private static HashMap<Integer, Character> revisedAlphabet = new HashMap<Integer, Character>();
    private static HashMap<Character, Integer> revisedAlphabet1 = new HashMap<Character, Integer>();
    private static HashMap<Integer, Character> immutableList = new HashMap<Integer, Character>();
    private static HashMap<Character, Integer> immutableList1 = new HashMap<Character, Integer>();

    private static char keyCharacter = 'p';

    private static Coder instance;

    public Coder(char key) {
        keyCharacter = key;
        prepareLists(keyCharacter);
    }

    public static Coder getInstance() {
        return instance != null ? instance : newCoder();
    }

    public static Coder getInstance(char k) {
        return instance != null ? instance : newCoder(k);
    }

    public static Coder newCoder() {
        instance = new Coder('p');
        return instance;
    }

    public static Coder newCoder(char k) {
        instance = new Coder(k);
        return instance;
    }

//    public static void encode(JTextPane encode, JTextPane to) {
//        encode(encode, to, keyCharacter);
//    }

    public static void encode(JTextArea encode, JTextArea to, char key) {
//        prepareLists(keyCharacter);

        StringBuilder stringBuilder = new StringBuilder(encode.getText());
        StringBuilder result = new StringBuilder();

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
        }

        to.setText(result.toString());
    }

    public static void decode(JTextArea decode, JTextArea to, char key) {
//        prepareLists(keyCharacter);

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

    private static void prepareLists(char keyCharacter) {
        immutableList.put(0, 'a');
        immutableList.put(1, 'b');
        immutableList.put(2, 'c');
        immutableList.put(3, 'd');
        immutableList.put(4, 'e');
        immutableList.put(5, 'f');
        immutableList.put(6, 'g');
        immutableList.put(7, 'h');
        immutableList.put(8, 'i');
        immutableList.put(9, 'j');
        immutableList.put(10, 'k');
        immutableList.put(11, 'l');
        immutableList.put(12, 'm');
        immutableList.put(13, 'n');
        immutableList.put(14, 'o');
        immutableList.put(15, 'p');
        immutableList.put(16, 'q');
        immutableList.put(17, 'r');
        immutableList.put(18, 's');
        immutableList.put(19, 't');
        immutableList.put(20, 'u');
        immutableList.put(21, 'v');
        immutableList.put(22, 'w');
        immutableList.put(23, 'x');
        immutableList.put(24, 'y');
        immutableList.put(25, 'z');

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

        for (int i = immutableList1.get(keyCharacter); i < 26; i++) {
            revisedAlphabet.put(i, immutableList.get(i - immutableList1.get(keyCharacter)));
            revisedAlphabet1.put(revisedAlphabet.get(i), i);
        }

        for (int i = 0; i < immutableList1.get(keyCharacter); i++) {
            revisedAlphabet.put(i, immutableList.get((i + (26 - immutableList1.get(keyCharacter)))));
            revisedAlphabet1.put(revisedAlphabet.get(i), i);
        }
    }

    public static void resetLists(char k) {

        keyCharacter = k;

        for (int i = immutableList1.get(keyCharacter); i < 26; i++) {
            revisedAlphabet.put(i, immutableList.get(i - immutableList1.get(keyCharacter)));
            revisedAlphabet1.put(revisedAlphabet.get(i), i);
        }

        for (int i = 0; i < immutableList1.get(keyCharacter); i++) {
            revisedAlphabet.put(i, immutableList.get((i + (26 - immutableList1.get(keyCharacter)))));
            revisedAlphabet1.put(revisedAlphabet.get(i), i);
        }
    }
}
