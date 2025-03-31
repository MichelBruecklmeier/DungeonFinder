package tile;

import Utils.UtilityTool;
import entity.Entity;
import main.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class TileManager {
    //Only able to do a 16:9 ration and will be able to change later if possible
    public static int TILE_COLS = 32;
    public static int TILE_ROWS = 18;
    public static int TILE_WORLD_ROWS = 100;
    public static int TILE_WORLD_COLS = 100;
    public static int TILE_SIZE = Window.SCREEN_HEIGHT / TILE_ROWS;
    ColliderLoader colliderLoader = new ColliderLoader();
    Tile[] TILES = new Tile[45*13]; //Overall tile size
     int[][] currentTileMap = new int[TILE_ROWS][TILE_COLS];
    public MapTile[][] currentTiles = new MapTile[TILE_ROWS][TILE_COLS];
    public TileManager() {
        loadImages();
        Tile.scale = TILE_SIZE/16;
        colliderLoader.setCollider(TILES);
    }
    //Method to load images and then create a new tile object to be placed into
    //The tile object is going to be used to hold data for image and colliders and any specail properties
    public void loadImages(){
        try {
            BufferedImage[][] temp = new BufferedImage[45][13];
            BufferedImage img = UtilityTool.loadImage("TILES/TILES.png");
            for(int i = 0; i < temp.length; i++){
                temp[i] = UtilityTool.cutPiece(img, temp[i].length,16,TILE_SIZE/16.,0,i);
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
        try{
            File file = new File(Paths.get("res\\rooms\\"+path).toAbsolutePath().toString());
            Scanner reader = new Scanner(file);


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

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
//        for(int[] x: currentTileMap){
//            for(int y: x){
//                System.out.print(y+" ");
//            }
//            System.out.println();
//        }
    }
    //Draw loops over all tileMap ints then that corrisponds to the indicie in the TILES[] array of the right map
    public void draw(Graphics2D g2){
        int counter = 1;
        for(int row = 0; row < TILE_ROWS; row++){
            for(int col = 0; col < TILE_COLS; col++){
                if(currentTileMap[row][col] != -1) {
                    g2.drawImage(TILES[currentTileMap[row][col]].image, col * TILE_SIZE, row * TILE_SIZE, null);
                    g2.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILES[currentTileMap[row][col]].image.getWidth(), TILES[currentTileMap[row][col]].image.getHeight());
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
    //Smaller name to make it more usefull
    private boolean check(int col, int row){
//        System.out.println(row +" "+col+" " + currentTiles[row][col].collider);
        return currentTiles[row][col].collider;
    }
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
