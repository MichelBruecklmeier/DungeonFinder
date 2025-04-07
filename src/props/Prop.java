package props;

import tile.MapTile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Prop {
    BufferedImage[] images;
    int type;
    int[] posX, posY;
    MapTile[] tiles;
    public Prop(BufferedImage[] image, int type, int[] posX, int[] posY, MapTile[] tiles) {
        this.images = image;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        for(MapTile tile : tiles) {
            tile.collider = true;
        }
    }
    public void draw(Graphics2D g2){
        for(int i =0; i<images.length; i++){
            g2.drawImage(images[i],posX[i],posY[i],null);
        }
    }
}
