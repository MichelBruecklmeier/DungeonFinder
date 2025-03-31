package obj;

import Utils.UtilityTool;
import tile.TileManager;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Key extends Obj{

    public OBJ_Key(int x, int y){
        setPos(x,y);
        try {
            setImage(UtilityTool.cutPiece(UtilityTool.loadImage("props\\pickup_items_animated.png"), 4, 16, TileManager.TILE_SIZE / 16, 0, 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ANIMATION_SPEED = 5;
        PICK_UP = true;
        rescale();
    }

    @Override
    public boolean colliding() {
        return true;
    }

    @Override
    public void interact() {

    }

    @Override
    public Obj pickup() {
        return null;
    }

    @Override
    public Obj onCollide() {
        return null;
    }
}
