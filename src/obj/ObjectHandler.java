package obj;

import Utils.UtilityTool;
import entity.Entity;
import tile.TileManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import main.Window;
public class ObjectHandler {
    public ArrayList<Obj> objects = new ArrayList<>();
    public int currentRoomIndex = 0;
    public ArrayList<ArrayList<Obj>> roomObjectArchive = new ArrayList<>();
    BufferedImage[][] ALL_ANIMATIONS = new BufferedImage[8][4];
    ObjectDataLoader loader;
    public static BufferedImage animationSheet;
    public ObjectHandler(Window window) {
        loadAnimations();
        loader = new ObjectDataLoader(window);
        loadObjects();
    }
    public void loadMap(){
        System.out.println("LOADING_OBJECT_MAP: "+Window.currentRoom);
        loader.loadMap();
        loadObjects();
    }
    public void loadAnimations() {
        try{
            animationSheet = UtilityTool.loadImage("res/props/pickup_items_animated.png");
            for(int row = 0; row < ALL_ANIMATIONS.length; row++){
                ALL_ANIMATIONS[row] = UtilityTool.cutImagePiece(animationSheet, ALL_ANIMATIONS[row].length, 16, TileManager.TILE_SIZE/16,0,row);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadObjects(){
        objects = loader.getObjects();
        roomObjectArchive.add(objects);

    }
    public Obj colliding(Entity e){
        for(Obj obj : objects){
            if(obj != null){
                if(obj.colliding(e)){
                    return obj;
                }
            }
        }
        return null;
    }

    public void debug(Graphics2D g2){
        objects.forEach( (n) -> g2.draw(n.collider));
    }
    public void update(){
        objects.forEach(Obj::update);


    }
    public void setCurrentRoomIndex(int index){
        currentRoomIndex = index;
        objects = roomObjectArchive.get(currentRoomIndex);
        System.out.println("roomObjectArchive: "+objects);
    }
    public void draw(Graphics2D g2){
        objects.forEach((n) -> n.draw(g2));
        if(Window.debug)
            debug(g2);
    }


}
