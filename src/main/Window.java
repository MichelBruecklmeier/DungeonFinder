package main;

import Utils.KeyHandler;
import entity.ENT_Player;
import entity.Entity;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Window extends JPanel implements Runnable {

    public static int FPS = 60;
    public static int SCREEN_WIDTH = 1366;
    public static int SCREEN_HEIGHT = 768;



    public static double RATIO = SCREEN_WIDTH/SCREEN_HEIGHT;

    ScreenPrint textDisplay = new ScreenPrint();
    Thread thread;
    KeyHandler keyHandler = new KeyHandler();
    public TileManager tileManager = new TileManager();
    ArrayList<Entity> Entities = new ArrayList<Entity>();
    public Window() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        init();
    }
    public void init(){
        Entities.add(new ENT_Player(SCREEN_WIDTH/2,SCREEN_HEIGHT/2,10,10,"characters/main/elf.png","Player",this));
        tileManager.load("example.txt");
    }

    public void startThread(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        //Divides 1 second by FPS
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(thread!=null) {




            repaint(); //paintComponent() method
            update();

            //Sleeps the fps
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000; //Converts nano -> milli
                if(remainingTime <0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void update(){
        Entities.forEach((n) -> n.update());

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawRect(0, 0, 10, 10);
        textDisplay.update(g2);
        tileManager.draw(g2);
        Entities.forEach((n) -> n.draw(g2));

        g2.dispose();
    }



}
