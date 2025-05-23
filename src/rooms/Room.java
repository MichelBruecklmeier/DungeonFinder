package rooms;

import obj.Obj;
import props.Prop;

import java.util.ArrayList;

public abstract class Room {
    public int[][] tileMap;
    public ArrayList<Obj> objects = new ArrayList<>();
    public ArrayList<Prop> props = new ArrayList<>();

    abstract void initialize();
    public void setTileMapFromFile(String path) {

    }

}
