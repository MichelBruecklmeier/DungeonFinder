package main;

import java.awt.*;

public class ScreenPrint {
    public static Graphics2D Graphics;


    public ScreenPrint(){
    }
    public void update(Graphics2D g2){
        Graphics = g2;
    }
    public static void printText(String text, Color color, int x, int y, Font font){
        Graphics.setFont(font);
        Graphics.setColor(color);
        int textHeight = (int)Graphics.getFontMetrics().getStringBounds(text, Graphics).getHeight();
        y += textHeight +5;
        Graphics.drawString(text, x, y);

    }


}
