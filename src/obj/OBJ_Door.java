package obj;

import Utils.UtilityTool;
import entity.Entity;
import tile.TileManager;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Door extends Obj implements Interactables {

    public OBJ_Door(int x, int y,int type){
        row = y;
        col = x;
        isVisible = true;
        this.type = "door";
        setPos(x,y);
        ANIMATION = new BufferedImage[1];

        try {
            ANIMATION[0] = switch(type){
                case 1 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("tiles\\tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 0, 33)[0];
                case 2 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("tiles\\tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 1, 33)[0];
                case 3 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("tiles\\tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 1, 34)[0];
                case 4 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("tiles\\tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 12, 33)[0];
                case 5 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("tiles\\tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 3, 37)[0];
                case 6 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("tiles\\tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 4, 37)[0];
                case 7 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("tiles\\tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 4, 38)[0];
                case 8 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("tiles\\tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 5, 38)[0];

                default -> null;
            };



        } catch (IOException e) {
            e.printStackTrace();
        }
        ANIMATION_SPEED = 5;
        PICK_UP = true;
        rescale();

    }


    @Override
    public boolean colliding(Entity e) {
        return false;
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
