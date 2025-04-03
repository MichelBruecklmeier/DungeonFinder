package props;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Prop {
    BufferedImage image;
    int type;
    int posX,posY;
    public Prop(BufferedImage image, int type, int posX, int posY) {
        this.image = image;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }
    public void draw(Graphics2D g2){
        g2.drawImage(image,posX,posY,null);
    }
}
