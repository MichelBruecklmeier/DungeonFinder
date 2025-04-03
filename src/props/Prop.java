package props;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Prop {
    BufferedImage[] images;
    int type;
    int[] posX, posY;
    public Prop(BufferedImage[] image, int type, int[] posX, int[] posY) {
        this.images = image;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }
    public void draw(Graphics2D g2){
        for(int i =0; i<images.length; i++){
            g2.drawImage(images[i],posX[i],posY[i],null);
        }
    }
}
