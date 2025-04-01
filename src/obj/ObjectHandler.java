package obj;

import Utils.UtilityTool;
import entity.Entity;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ObjectHandler {
    public ArrayList<Obj> objects = new ArrayList<Obj>();
    BufferedImage[][] ALL_ANIMATIONS = new BufferedImage[8][4];
    ObjectDataLoader loader;
    public static BufferedImage animationSheet;
    public ObjectHandler() {
        loadAnimations();
        loader = new ObjectDataLoader();
        loadObjects();
    }
    public void loadAnimations() {
        try{
            animationSheet = UtilityTool.loadImage("props\\pickup_items_animated.png");
            for(int row = 0; row < ALL_ANIMATIONS.length; row++){
                ALL_ANIMATIONS[row] = UtilityTool.cutImagePiece(animationSheet, ALL_ANIMATIONS[row].length, 16, TileManager.TILE_SIZE/16,0,row);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadObjects(){
        //TEMPORARY
        objects = loader.getObjects();
    }
    public Obj colliding(Entity e){
        for(Obj obj : objects){
            if(obj.colliding(e)){
                return obj;
            }
        }
        return null;
    }

    public void update(){
        objects.forEach(Obj::update);
    }
    public void draw(Graphics2D g2){
        objects.forEach((n) -> n.draw(g2));
    }


}
