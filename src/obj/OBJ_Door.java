package obj;

import Utils.UtilityTool;
import entity.Entity;
import main.Window;
import tile.MapTile;
import tile.Tile;
import tile.TileManager;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Door extends Obj implements Interactables {
    Tile doorFrame;
    String room;
    public boolean unlocked;
    public OBJ_Door(int id) {
        super(id);
        isVisible = true;
        this.type = "door";
        ANIMATION_SPEED = 5;
        unlocked = false;
        PICK_UP = true;
    }
    public OBJ_Door(int x, int y, int type, int id, MapTile frame, String room,boolean unlocked) {
        this(x, y, type, id, frame,room);
        this.unlocked = unlocked;
    }
    public OBJ_Door(int x, int y, int type, int id, MapTile frame, String room) {
        this(x,y,type,id,frame);
        this.room = room+".txt";
    }
    public OBJ_Door(int x, int y, int type, int id, MapTile frame){
        this(id);
        doorFrame=frame;
        doorFrame.collider = true;
        row = y;
        col = x;


        setPos(x,y);

        try {
            setImage( new BufferedImage[]{switch(type){
                case 0 -> null;
                case 1 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("res/tiles/tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 0, 33)[0];
                case 2 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("res/tiles/tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 1, 33)[0];
                case 3 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("res/tiles/tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 1, 34)[0];
                case 4 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("res/tiles/tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 12, 33)[0];
                case 5 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("res/tiles/tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 4, 37)[0];
                case 6 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("res/tiles/tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 5, 37)[0];
                case 7 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("res/tiles/tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 5, 38)[0];
                case 8 -> UtilityTool.cutImagePiece(UtilityTool.loadImage("res/tiles/tiles.png"), 1, 16, TileManager.TILE_SIZE / 16, 6, 38)[0];

                default -> null;
            }});



        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ANIMATION[0] == null)
            unlocked = true;
        setCollider(new Rectangle(posX-6,posY-6,collider.width+12,collider.height+12));



        rescale();

    }
    private int openTime = 500;
    private int timeOpen = 0;
    @Override
    public void update(){

        if(ANIMATION[0] != null&&unlocked&&!isVisible) {
            doorFrame.collider = false;
            timeOpen++;
            if (timeOpen > openTime) {
                timeOpen = 0;
                isVisible = true;
            }
        }

    }


    @Override
    public void interact() {
        isVisible = false;
        doorFrame.collider = false;
        unlocked = true;

    }

    @Override
    public Obj pickup() {
        return null;
    }

    @Override
    public Obj onCollide() {
        if(unlocked && room != null){
            System.out.println(room);
            Window.currentRoom = room;
            Window.doRoomChange = true;
        }
        if(unlocked){
            isVisible = false;
            doorFrame.collider = false;
        }
        return this;
    }

    @Override
    public void refresh() {

    }

    @Override
    public String toString(){
        return super.toString() + " unlocked: "+unlocked;
    }
}
