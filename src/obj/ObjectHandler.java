package obj;

import Utils.UtilityTool;
import main.Window;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjectHandler {
    ArrayList<Obj> objects = new ArrayList<Obj>();
    BufferedImage[][] ALL_ANIMATIONS = new BufferedImage[8][4];
    ObjectDataLoader loader;
    public ObjectHandler() {
        loadAnimations();
        loadObjects();
        loader = new ObjectDataLoader();
    }
    public void loadAnimations() {
        try{
            BufferedImage animationSheet = UtilityTool.loadImage("props\\pickup_items_animated.png");
            for(int row = 0; row < ALL_ANIMATIONS.length; row++){
                ALL_ANIMATIONS[row] = UtilityTool.cutPiece(animationSheet, ALL_ANIMATIONS[row].length, 16, TileManager.TILE_SIZE/16,0,row);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadObjects(){
        //TEMPORARY
        objects.add(new OBJ_Key(ALL_ANIMATIONS[1]));
        objects.get(0).setPos(10,10);
        for(int row = 0; row < objects.size(); row++){
        }
    }


    public void update(){
        objects.forEach(Obj::update);
    }
    public void draw(Graphics2D g2){
        objects.forEach((n) -> n.draw(g2));
    }


}
