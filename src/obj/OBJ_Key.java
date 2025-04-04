package obj;

import Utils.UtilityTool;
import entity.Entity;
import tile.TileManager;

import java.io.IOException;

public class OBJ_Key extends Obj{
    public OBJ_Key(int id) {
        super(id);
        ANIMATION_SPEED = 5;
        PICK_UP = true;
        isVisible = true;
        type = "key";
    }
    public OBJ_Key(int x, int y,int id){
        this(id);
        row = y;
        col = x;
        setPos(x,y);

        try {
            setImage(UtilityTool.cutImagePiece(UtilityTool.loadImage("res/props/pickup_items_animated.png"), 4, 16, TileManager.TILE_SIZE / 16, 0, 1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rescale();

    }



    @Override
    public void interact() {

    }

    @Override
    public Obj pickup() {
        isVisible = false;
        return this;
    }

    @Override
    public Obj onCollide() {
        return pickup();
    }

    @Override
    public void refresh() {

    }
}
