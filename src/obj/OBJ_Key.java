package obj;

import java.awt.image.BufferedImage;

public class OBJ_Key extends Obj{

    public OBJ_Key(BufferedImage[] img){
        super(img);
        ANIMATION_SPEED = 5;
        PICK_UP = true;
        rescale();
    }
}
