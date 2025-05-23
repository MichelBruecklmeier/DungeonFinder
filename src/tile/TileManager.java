package tile;

import Utils.Logger;
import Utils.UtilityTool;
import entity.Entity;
import main.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TileManager {
    //Only able to do a 16:9 ration and will be able to change later if possible
    public static int TILE_COLS = 32;
    public static int TILE_ROWS = 18;
    public static int TILE_WORLD_ROWS = 100;
    public static int TILE_WORLD_COLS = 100;
    public static int TILE_SIZE = Window.SCREEN_HEIGHT / TILE_ROWS;
    public int currentRoomIndex = 0;

    ColliderLoader colliderLoader = new ColliderLoader();
    Tile[] TILES = new Tile[45*13]; //Overall tile size
     int[][] currentTileMap = new int[TILE_ROWS][TILE_COLS];
    public MapTile[][] currentTiles;
    public ArrayList<MapTile[][]> savedCurrentTiles = new ArrayList<>();
    Logger logger  = new Logger("TileManager");
    public TileManager() {
        loadImages();
        Tile.scale = TILE_SIZE/16;
        colliderLoader.setCollider(TILES);
        logger.log("Initilized",3);
    }
    private MapTile[][] copyTileMap(MapTile[][] tileMap) {
        MapTile[][] copy = new MapTile[tileMap.length][tileMap[0].length];
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                copy[i][j] = tileMap[i][j].copy();
            }
        }
        return copy;
    }
    //Method to load images and then create a new tile object to be placed into
    //The tile object is going to be used to hold data for image and colliders and any specail properties
    public void loadImages(){
        try {
            BufferedImage[][] temp = new BufferedImage[45][13];
            BufferedImage img = UtilityTool.loadImage("res/tiles/tiles.png");
            for(int i = 0; i < temp.length; i++){
                temp[i] = UtilityTool.cutImagePiece(img, temp[i].length,16,TILE_SIZE/16.,0,i);
            }
            for(int row = 0; row < temp.length; row++){
                for(int col = 0; col < temp[row].length; col++){
                    TILES[row*temp[0].length+col] = new Tile(temp[row][col]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //The load method is used to load the tilemap of the current room to be loaded these are used as indecies in the TILES[] array
    public void load(String path){
        currentTiles = new MapTile[TILE_ROWS][TILE_COLS];
        try{
            File file = new File(Paths.get("res\\rooms\\"+path).toAbsolutePath().toString());
            Scanner reader = new Scanner(file);
            reader.nextLine();

            for(int row = 0; row < TILE_ROWS; row++){
                String line = reader.nextLine();
                for(int col = 0; col < TILE_COLS; col++){
                    String[] numbers = line.split(",");
                    int number = Integer.parseInt(numbers[col]);
                    currentTileMap[row][col] = number;
                    currentTiles[row][col] = new MapTile(TILES[number].copy());
                    currentTiles[row][col].offset(col*(TILE_SIZE),row*(TILE_SIZE));
                }
            }
            savedCurrentTiles.add(currentTiles);

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } finally {
            logger.log("Loaded TileMap",6);
        }
    }
    //Allows the on level change to make the correct room
    public void setCurrentRoomIndex(int index){
        currentRoomIndex = index;
        currentTiles = savedCurrentTiles.get(currentRoomIndex);
        logger.log(Arrays.toString(currentTiles),3);
    }
    //Draw loops over all tileMap ints then that corrisponds to the indicie in the TILES[] array of the right map
    public void draw(Graphics2D g2){
        int counter = 1;
        for(int row = 0; row < TILE_ROWS; row++){
            for(int col = 0; col < TILE_COLS; col++){
                if(true) {
                    g2.drawImage(currentTiles[row][col].image, col * TILE_SIZE, row * TILE_SIZE, null);
//                    g2.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILES[currentTileMap[row][col]].image.getWidth(), TILES[currentTileMap[row][col]].image.getHeight());
                }
            }
        }
//        for(int row = 0; row<TILE_ROWS; row++){
//            for(int col = 0 ; col < TILE_COLS; col++){
//                g2.setColor(Color.red);
//                currentTiles[row][col].debug(g2);
//
//            }
//        }
    }
    public boolean colliding(Entity ent){

        int speed = 5;
        //Predict the future :mind-blown:
        int yChange = switch(ent.getDirection()){
            case 'u' -> -speed;
            case 'd' -> speed;
            default -> 0;
        };
        int xChange = switch(ent.getDirection()){
            case 'r' -> speed;
            case 'l' -> -speed;
            default -> 0;
        };

        int RightColPos = (ent.getCollider().x+ent.getCollider().width + xChange)/(TILE_SIZE);
        int LeftColPos = (ent.getCollider().x + xChange)/(TILE_SIZE);
        int TopRowPos = (ent.getCollider().y + yChange)/(TILE_SIZE);
        int BottomRowPos = (ent.getCollider().y+ent.getCollider().height + yChange)/(TILE_SIZE);
        if(check(RightColPos,TopRowPos))
            return true;
        if(check(LeftColPos,BottomRowPos))
            return true;
        if(check(RightColPos,BottomRowPos))
            return true;
        return check(LeftColPos, TopRowPos);
    }
    //Smaller name to make it more usefully
    private boolean check(int col, int row){
        return currentTiles[row][col].collider;
    }
    //Old method
    private boolean checkTileMapCollision(int row, int col,Rectangle rect){
        if(currentTiles[row][col].colliders.length!=0) {
            for (int i = 0; i < currentTiles[row][col].colliders.length; i++) {
                if (currentTiles[row][col].colliders[i] != null) {
                    if (currentTiles[row][col].colliders[i].intersects(rect))
                        return true;
                }
            }
        }
        return false;
    }
}
