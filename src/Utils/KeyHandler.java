package Utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyHandler implements KeyListener {
    public static int keyCodePressed = -1;
    public static int keyCodeReleased = -1;
    public static char keyCharPressed = 0;

    public static int[] keyCodeCombo = new int[0];
    public static boolean[] keys = new boolean[256];


    public static int lastPressedKey = 0;
    @Override
    public void keyTyped(KeyEvent e) {
    }
    public int[] removeZeros(int[] flawed){
        int length = flawed.length;
        for(int i = 0; i < flawed.length; i++){
            if(flawed[i] == 0){
                length --;
            }
        }
        int[] unflawed = new int[length];
        int itt = 0;
        for(int i = 0; i < flawed.length; i++){
            if(flawed[i] != 0){
                unflawed[itt++] = flawed[i];
            }
        }
        return unflawed;
    }
    @Override
    public void keyPressed(KeyEvent e) {

        //Static variables for the key held
        keyCodePressed = e.getKeyCode();
        keyCharPressed = e.getKeyChar();
        keyCodeReleased = -1;

        //Check that we aren't adding the same key over and over into the keys held
        if(lastPressedKey != keyCodePressed) {
            keyCodeCombo = Arrays.copyOf(keyCodeCombo, keyCodeCombo.length + 1);

            keyCodeCombo[keyCodeCombo.length-1] = e.getKeyCode();
        }
        lastPressedKey = e.getKeyCode();
        if(e.getKeyCode() < keys.length)
            keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //-1 means no key being pressed
        keyCodePressed = -1;
        keyCodeReleased = e.getKeyCode();
        if(e.getKeyCode() < keys.length)
            keys[e.getKeyCode()] = false;
        //0 means no key being pressed
        keyCharPressed = 0;
        //Reset the last key press because it would make it so if you press the same key it would not work
        lastPressedKey = 0;
        //Check if the key code released is in the array
        for(int i = 0; i < keyCodeCombo.length; i++) {
            if(e.getKeyCode() == keyCodeCombo[i]) {
                int[] temp = Arrays.copyOf(keyCodeCombo, keyCodeCombo.length);
                keyCodeCombo = new int[temp.length - 1];
                int sub = 0;
                for (int j = 0; j < temp.length ; j++) {
                    if(i==j){
                        sub ++;
                    }
                    else
                        keyCodeCombo[j-sub] = temp[j];
                }


                break;
            }
        }
        keyCodeCombo = removeZeros(keyCodeCombo);

    }


}
