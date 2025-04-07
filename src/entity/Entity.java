package entity;

import Utils.UtilityTool;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static tile.TileManager.TILE_SIZE;


public abstract class  Entity {

    protected static int id;
    protected String name;
    protected int posX;
    protected int posY;
    public int RightCol, LeftCol;
    public int TopRow, BottomRow;
    protected int width;
    protected int height;
    protected int frameSize;
    protected double scale;
    protected int speed;
    protected char direction = 'r';
    protected Rectangle collisionBox;
    public BufferedImage ENTITY_IMAGE;
    public BufferedImage[][] ENTITY_ANIMATION;

    public Entity(int starting_x, int starting_y, int width, int height, String imagePath, String name) {
        posX = starting_x;
        posY = starting_y;
        RightCol =  (posX+width)/(TILE_SIZE);
        LeftCol =  (posY)/(TILE_SIZE);
        TopRow =  (posY)/(TILE_SIZE);
        BottomRow =  (posY+height)/(TILE_SIZE);
        this.width = width;
        this.height = height;
        this.name = name;
        id++;
        try{
            ENTITY_IMAGE = UtilityTool.loadImage("res\\"+imagePath);
        } catch (IOException e) {
            System.err.println("Failed to load image " + imagePath + ": " + e.getMessage()+ " |ID: "+id);
        }

        init();
    }
    public char getDirection(){
        return direction;
    }

    abstract public void draw(Graphics2D g2);

    public abstract void update();
    abstract public void debug(Graphics2D g2);
    abstract void init();

    //Dont know if i like the getter setter methods
    public Rectangle getCollider(){return collisionBox;}
    public int getX(){
        return posX;
    }
    public int getY(){
        return posY;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public void setPosX(int x){
        posX = x;
    }
    public void setPosY(int y){
        posY = y;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPos(int x, int y){
        posX = x;
        posY = y;
    }
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }


}
