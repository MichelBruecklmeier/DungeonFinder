package obj;

import Utils.UtilityTool;
import entity.Entity;
import tile.TileManager;

import java.io.IOException;

public class OBJ_Key extends Obj{

    public OBJ_Key(int x, int y){
        row = y;
        col = x;
        isVisible = true;
        type = "key";
        setPos(x,y);

        try {
            setImage(UtilityTool.cutImagePiece(UtilityTool.loadImage("props\\pickup_items_animated.png"), 4, 16, TileManager.TILE_SIZE / 16, 0, 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ANIMATION_SPEED = 5;
        PICK_UP = true;
        rescale();

    }

    @Override
    public boolean colliding(Entity e) {
        if(isVisible && e.getCollider().intersects(collider)){
            return true;
        }
        return false;
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
}
