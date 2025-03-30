package obj;

import java.awt.*;
import java.awt.image.BufferedImage;

import Utils.UtilityTool;
import main.Window;
import tile.TileManager;

public class Obj {
    BufferedImage[] ANIMATION;
    Rectangle collider;
    int posX, posY;
    int ANIMATION_SPEED;
    int currentIndex;
    //Have some boolean values to define what the object behaviour has
    boolean  PICK_UP;
    public Obj(BufferedImage[] animation) {
        ANIMATION = animation;
    }
    public void rescale(){

            for(int i = 0; i<ANIMATION.length; i++){
                ANIMATION[i] = UtilityTool.scaleImage(ANIMATION[i],(int)(ANIMATION[i].getWidth() * TileManager.TILE_SIZE/32),(int)(ANIMATION[i].getHeight() * TileManager.TILE_SIZE/32));
            }

    }
    public void setCollider(Rectangle collider) {
        this.collider = collider;
    }

    public void setPos(int x, int y) {
        posX = x*TileManager.TILE_SIZE;
        posY = y*TileManager.TILE_SIZE;
    }
    public void update(){

    }
    public boolean colliding(){
        return false;
    }
    public void draw(Graphics2D g2){
        if(Window.TICKER % ANIMATION_SPEED == 0){
            currentIndex ++;
        }
        if(currentIndex == ANIMATION.length){
            currentIndex = 0;
        }
        g2.drawImage(ANIMATION[currentIndex], posX, posY, null);
    }


}