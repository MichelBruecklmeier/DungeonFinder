package main;

import Utils.KeyHandler;
import Utils.UtilityTool;
import entity.ENT_Player;
import entity.Entity;
import obj.ObjectHandler;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Window extends JPanel implements Runnable {

    public static int FPS = 60;
    //Preferred size not actual size
    public static int SCREEN_WIDTH = 1366;
    public static int SCREEN_HEIGHT = 768;

    //Delta time variables
    public static double deltaTime = 0.0f;
    //Keep at/above 10fps
    public final double upperTime = 0.1f;
    //Keep at/below 60fps
    public final double lowerTime = 0.0167f;

    //TODO: make it count and place strings based on how many are in the file system
    public static String[] mapNames = new String[]{"example.txt","room1.txt"};


    public static double RATIO = SCREEN_WIDTH/SCREEN_HEIGHT;
    public static int TICKER = 1;
    public static String currentRoom = "example.txt";

    public static boolean debug = false;
    private String oldRoom = currentRoom;
    ScreenPrint textDisplay = new ScreenPrint();
    Thread thread;
    KeyHandler keyHandler = new KeyHandler();
    public static boolean doRoomChange = false;
    public TileManager tileManager;
    //Weird work around just go with it trust ong ong
    public ObjectHandler objectHandler;
    ArrayList<Entity> Entities = new ArrayList<Entity>();
    public ENT_Player player;
    public Window() {
        //We base the screen dimensions of the previous size to the ones we are going to assign
        this.setPreferredSize(new Dimension(TileManager.TILE_SIZE*TileManager.TILE_COLS,TileManager.TILE_SIZE*TileManager.TILE_ROWS));
        this.setBackground(Color.black);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        init();
    }
    public void loadPlayerStart(){

    }
    public int[] loadPlayerStartPos(){
        int x = 0;
        int y = 0;
        try {
            String[] str = new Scanner(UtilityTool.loadFile("res\\rooms\\"+currentRoom)).nextLine().split(",");
            x = Integer.parseInt(str[0]);
            y = Integer.parseInt(str[1]);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new int[]{x,y};
    }
    public void init(){
        tileManager = new TileManager();
        int[] pos = loadPlayerStartPos();

        player = new ENT_Player(pos[0]*TileManager.TILE_SIZE,pos[1]*TileManager.TILE_SIZE,10,10,"characters/main/elf.png","Player",this);
        Entities.add(player);
        tileManager.load(currentRoom);
        objectHandler = new ObjectHandler(this);
    }

    public void changeRoom(String room){
        currentRoom = room;
        tileManager.load(currentRoom);
        objectHandler.loadMap();
        int[] pos = loadPlayerStartPos();
        player.setPos(pos[0]*TileManager.TILE_SIZE,pos[1]*TileManager.TILE_SIZE);
    }

    public void startThread(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        //Divides 1 second by FPS
        double drawInterval =  1000000000. /FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        double lastTime = System.currentTimeMillis();
        while(thread!=null) {
            double currentTime = System.currentTimeMillis();

            deltaTime = (float)(currentTime-lastTime);
            if(deltaTime < lowerTime)
                deltaTime = lowerTime;
            else if(deltaTime > upperTime)
                deltaTime = upperTime;

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
            lastTime = currentTime;
        }

    }


    public void update(){
        if(doRoomChange){
            doRoomChange = false;
            oldRoom = currentRoom;
            System.out.println("Triggered");
            changeRoom(currentRoom);
        }
        Entities.forEach(Entity::update);
        objectHandler.update();
        TICKER++;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawRect(0, 0, 10, 10);
        textDisplay.update(g2);
        tileManager.draw(g2);
        objectHandler.draw(g2);
        Entities.forEach((n) -> n.draw(g2));

        g2.dispose();
    }



}
