package tile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Tile {
    public static double scale;

    BufferedImage image;
    public Rectangle[] colliders = new Rectangle[0];
    public boolean collider;
    public int currentColliderId = 0;
    public Tile(BufferedImage image){
        this.image = image;
    }
    public Tile(Tile tile){
        this.image = tile.image;
        this.currentColliderId = tile.currentColliderId;
        this.colliders = colliderCopy(tile);
        this.collider = tile.collider;
    }
    public Rectangle[] colliderCopy(Tile tile){
        Rectangle[] copy = new Rectangle[tile.colliders.length];
        for(int i = 0; i < tile.colliders.length; i++){
            copy[i] = tile.colliders[i];
        }
        return copy;
    }
    public Tile copy(){
        return new Tile(this);
    }
    //This is meant for only the current tile map not the old tile map

    public void setRectangle(int x, int y, int width, int height, int index){
        System.out.println("New rect: "+y+" "+x+" "+width+" "+height +" at: "+index);
        colliders[index] = new Rectangle(x, y, width , height );
    }


}
