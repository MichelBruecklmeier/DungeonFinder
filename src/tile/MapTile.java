package tile;

import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapTile extends Tile{
    public static int  ID = 0;
    public int id=ID;
    public MapTile(BufferedImage image){
        super(image);
        ID ++;
    }
    public MapTile(Tile tile){
        super(tile);
        ID ++;
    }
    public void offset(int x, int y){

            if(colliders.length != 0){
                for(int i = 0; i<colliders.length; i++)
                    if(colliders[i] != null)
                        setRectangle(colliders[i].x+x, colliders[i].y+y, colliders[i].width, colliders[i].height,i);
            }

    }
    @Override
    public MapTile copy(){
        return new MapTile(this);
    }

    public void debug(Graphics2D g2){
        for (Rectangle collider : colliders) {
            if (collider != null) {
//                System.out.println(collider.x + " " + collider.y + " " + collider.width + " " + collider.height);
                g2.setColor(Color.red);
                g2.draw(collider);
            }
        }
    }


}
