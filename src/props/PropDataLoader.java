package props;

import Utils.UtilityTool;
import tile.MapTile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import main.Window;
public class PropDataLoader {

    ArrayList<String> settings = new ArrayList<>();
    MapTile[][] tiles;
    Window window;
    BufferedImage[] images = new BufferedImage[4];
    public PropDataLoader(Window window) {
        this.window = window;
        this.tiles = window.tileManager.currentTiles;
    }
    public void loadImages(){
    }
    public void loadFile(String filename) {
        try{
            File file = UtilityTool.loadFile(filename);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine() && !scanner.nextLine().equals("__Props"));
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                settings.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int[] getPositionData(String element){
        int[] pos = new int[2];
        String[] rawPos = element.split("\\(")[1].split("\\)")[0].split(",");
        pos[0] = Integer.parseInt(rawPos[0]);
        pos[1] = Integer.parseInt(rawPos[1]);
        return pos;
    }
    public void process(){
        for(String setting : settings){
            String[] elements = setting.split("\\{");
            for(String element : elements){
                element = element.replace("}", "");
                String type = element.split("type:\"")[1].split("\"")[0];
                switch (type){
                    case "deadFire" -> deadFire();
                }

            }
        }
    }

    public void deadFire(){

    }


}
