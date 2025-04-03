package entity;

import Utils.KeyHandler;
import Utils.UtilityTool;
import main.Window;
import obj.Obj;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import static tile.TileManager.TILE_SIZE;
import static main.Window.deltaTime;
//The player entity loosley is based off the entity parent but is very modified as it's the controlled character
enum Type   {
    IDLE_RIGHT(0,3,15),
    IDLE_LEFT(1,3,15),
    MOVE_RIGHT(2,4,10),
    MOVE_LEFT(3,4,10),
    HURT_RIGHT(4,5,10),
    HURT_LEFT(5,5,10),
    DEATH_RIGHT(6,3,5),
    DEATH_LEFT(7,3,5),
    RESPAWN(8,6,5);
    private final int value;
    private int itterator;
    private final int max;
    public final int speed;
    private static int global_tick = 0;
    public static int current = 3;


    Type(int i, int m, int s) {
        value = i;
        max = m;
        speed = s;
    }

    public static void update(){
        global_tick++;
    }
    public int get() {
        return value;
    }
    public int itt(){
        if(global_tick%speed==0) {
            itterator++;
        }
        if(itterator >= max){
            itterator = 0;
        }
        return itterator;
    }
}

class Inventory{
    public Obj[] inventory = new Obj[10];
    public Inventory(){

    }
    public int getItem(String type){
        for(int i=0;i<inventory.length;i++) {
            if(inventory[i] != null && inventory[i].type.equals(type)) {
                return i;
            }
        }
        return -1;
    }
    public int getItemId(int id){
        for(int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].id == id) {
                return i;
            }
        }
        return -1;
    }
    public boolean add(Obj object){
        for(int i=0;i<inventory.length;i++){
            if(inventory[i]==null){
                inventory[i]=object;
                return true;
            }
        }
        return false;
    }
    public void remove(int slot){
        if(!(slot<0))
            inventory[slot]=null;
    }
}

public class ENT_Player extends Entity {


    Window window;
    Inventory inventory = new Inventory();
    private double fadeOffSpeed = 0.9;
    public ENT_Player(int starting_x, int starting_y, int width, int height, String imagePath, String name, Window window) {

        super(starting_x, starting_y, width, height, imagePath, name);
        this.window = window;
    }

    @Override
    public void draw(Graphics2D g2) {
        Type type = switch(Type.current){
            case 0 -> Type.IDLE_RIGHT;
            case 1 -> Type.IDLE_LEFT;
            case 2 -> Type.MOVE_RIGHT;
            case 3 -> Type.MOVE_LEFT;
            case 4 -> Type.HURT_RIGHT;
            case 5 -> Type.HURT_LEFT;
            case 6 -> Type.DEATH_RIGHT;
            case 7 -> Type.DEATH_LEFT;
            case 8 -> Type.RESPAWN;
            default -> Type.IDLE_RIGHT;

        };
        g2.drawImage(ENTITY_ANIMATION[type.get()][type.itt()], posX, posY, null);
        if(Window.debug)
            debug(g2);
    }
    @Override
    public void debug(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.draw(collisionBox);
    }
    private int[] offset;

    void init() {
        frameSize = 16;
        scale = TileManager.TILE_SIZE/16.;
        speed = 50;
        ENTITY_ANIMATION = new BufferedImage[9][6];
        try {
            for(int i = 0; i < 9; i++) {
                ENTITY_ANIMATION[i] = UtilityTool.cutImagePiece(ENTITY_IMAGE, 5, frameSize, TileManager.TILE_SIZE/16., 0, i);
            }
        } catch (IOException e) {
            System.err.println("Failed to cut image");
        }
        //Set up the collider
        collisionBox = new Rectangle((int)(6*scale),(int)(7 *scale), (int)(6*scale), (int)(8*scale));
        offset = new int[2];
        offset[0] = collisionBox.x;
        offset[1] = collisionBox.y;

    }
    int ticker = 0;
    int itt = 0;
    @Override
    public void update() {
        //The ticker is for the animations works pretty well
        ticker ++;
        if(ticker%10 == 0){
            itt ++;
            if(itt == ENTITY_ANIMATION[0].length){
                itt = 0;
            }
        }
        inputHandler();
        RightCol =  (posX+width)/(TILE_SIZE);
        LeftCol =  (posY)/(TILE_SIZE);
        TopRow =  (posY)/(TILE_SIZE);
        BottomRow =  (posY+height)/(TILE_SIZE);

        collisionBox.x = posX + offset[0];
        collisionBox.y = posY + offset[1];
        Type.update();

        //Check for object collisions
        Obj object = window.objectHandler.colliding(this);
        if(object != null)
            objectCollisionHandler(object);

    }



    boolean coliding = false;
    private void objectCollisionHandler(Obj obj){
        if(obj.type.equals("key")){
            inventory.add(obj.pickup());
            System.out.println(Arrays.toString(inventory.inventory));
        }
        else if((obj.type.equals("door") && inventory.getItem("key") != -1) || obj.id == -1){
            if(inventory.getItemId(obj.id) == inventory.getItem("key") ){
                obj.interact();

                inventory.remove(inventory.getItem("key"));
            }
        } if(obj.type.equals("door")){
            obj.onCollide();
        }
    }
    double xChange = 0;
    double yChange = 0;
    char horzMove = 'r';
    private void inputHandler(){


        //Y movements

        if(KeyHandler.keys[83]){
            direction = 'd';
            yChange = speed * deltaTime;
            Type.current = (horzMove == 'r') ? 2:3;
        }
        else if(KeyHandler.keys[87]){
            direction = 'u';
            yChange = -speed * deltaTime;
            Type.current = (horzMove == 'r') ? 2:3;
        }
        //X movements
        else if(KeyHandler.keys[68]){
            direction = 'r';
            horzMove = 'r';
            xChange = speed * deltaTime;

            Type.current = 2;
        }
        else if(KeyHandler.keys[65]){
            direction = 'l';
            horzMove = 'l';
            xChange = -speed * deltaTime;
            Type.current = 3;
        }
        if(!KeyHandler.keys[83] && !KeyHandler.keys[87] && !KeyHandler.keys[68] && !KeyHandler.keys[65]){
            if(Type.current == 2){
                Type.current = 0;
            }
            if(Type.current == 3){
                Type.current = 1;
            }
            //Fade off speed so its less blocky

        }
        boolean colliding = window.tileManager.colliding(this) || coliding;
        if(Math.abs(yChange) != yChange && yChange >=0)
            yChange = 0;
        else if(Math.abs(yChange) == yChange && yChange <=0)
            yChange = 0;
        else if(yChange !=0) {
            yChange += -(yChange / Math.abs(yChange)) * fadeOffSpeed;
        }
        if(Math.abs(xChange) != xChange && xChange >=0) {
            xChange = 0;
        }
        else if(Math.abs(xChange) == xChange && (int)xChange <=0 ) {
            xChange = 0;
        }
        else if(xChange !=0){
            xChange += -(xChange / Math.abs(xChange)) * fadeOffSpeed;
        }
        if(colliding){
            xChange = 0;
            yChange = 0;
        } else {
            posY += (int) (yChange);
            posX += (int) (xChange);
        }

    }



}
