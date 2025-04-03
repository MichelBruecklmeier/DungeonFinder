package props;

import Utils.UtilityTool;
import tile.MapTile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PropDataLoader {

    ArrayList<String> settings = new ArrayList<>();
    MapTile[][] tiles;
    public PropDataLoader(MapTile[][] tiles) {
        this.tiles = tiles;
    }

    public void loadFile(String filename) {
        try{
            File file = UtilityTool.loadFile(filename);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine() && !scanner.nextLine().equals("__Props"));
            while(scanner.hasNextLine()){
                settings.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
