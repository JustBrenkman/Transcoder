package com.justb.messages;

import java.io.Serializable;

/**
 * Created by ben on 02/01/15.
 * <p/>
 * JGUILibrary
 */
public class ChangeKeyCharacterEvent implements Serializable {
    private char keyCharacter;

    public ChangeKeyCharacterEvent(char keyCharacter) {
        this.keyCharacter = keyCharacter;
    }

    public char getKeyCharacter() {
        return keyCharacter;
    }
}
