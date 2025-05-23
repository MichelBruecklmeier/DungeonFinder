package tile;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ColliderLoader {


    ArrayList<String> settings = new ArrayList<String>();
    boolean[] collision = new boolean[45*13];
    public ColliderLoader() {
        loadFile();
    }
    //A simple helper function meant to parse each int from the file stremlining the process just a bit
    private int[] parseArr(String[] str){
        int[] arr = new int[str.length];
        for(int i = 0; i < str.length; i++){
            arr[i] = Integer.parseInt(str[i]);
        }
        return arr;
    }
    //New loader method which stroes the collision data as either the tile is a collider or not
    public void loadFile() {
        try{
            File file = new File(Paths.get("res\\tiles\\collider.set").toAbsolutePath().toString());
            Scanner reader = new Scanner(file);
            //Go thru the file and parse everything
            //Row is so we have an offset so it doesn't overwrite just the first row over and over
            int row = 0;
            while (reader.hasNextLine()) {
                int[] data = parseArr(reader.nextLine().split(","));
                for(int i = 0; i < data.length; i++){
                    //If its 1 in the array then it is a collider
                    collision[(row * data.length) + i] = data[i] == 1;
                }
                row ++;
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.err.println("Failed to load file");
        }
    }


    //I didnt read the tilemap i was using so this whole piece of junk is usellses but might be useful for other projects
    public void loadFileOLD(){
        try{
            File file = new File(Paths.get("res\\tiles\\collider.set").toAbsolutePath().toString());
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                while(line.contains("{") && !line.contains("#")) {
                    String tokens = line.split("\\{")[1].split("\\}")[0];
                    settings.add(tokens);
                    if(line.split("}").length != 1)
                        line = line.split("}")[1];
                    else
                        break;

                }


            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setCollider(Tile[] tiles){

        for(int i = 0; i < tiles.length; i++){
            tiles[i].collider = collision[i];
        }
    }
    public void setTileColliders(Tile[] tiles){
        for(int i = 0; i < settings.size(); i++){
            String setting = settings.get(i);
            //Index of where the new rect should go

            String idLine = settings.get(i).split("id:")[1].split(",")[1];
            //Check for the legacy code version of only having one objectId looking objectId:1,
            int[] id = new int[1];
            if(!settings.get(i).contains("objectId:[")){
                id[0] = Integer.parseInt(setting.split("id:")[1].split(",")[0]);
            } else {
                //We cut the setting objectId:[num,num,num],rect... we cut it by that ], then check the things to the left and count the amount of commas we have
                int length = setting.split("id:")[1].split("],")[0].split(",").length;
                id = new int[length];
                String line = setting.split("id:\\[")[1].split("],")[0];
                String[] tokens = line.split(",");
                for(int j = 0; j < tokens.length; j++){
                    id[j] = Integer.parseInt(tokens[j]);
                }
            }
            //Splits the string into just numbers for the new rect
            String line = setting.split("rect:")[1].split("}")[0];
            for(int z = 0; z<id.length;z++)
                tiles[id[z]].colliders = new Rectangle[line.split("]").length];
            int index = 0;
            while(true) {
                //Split the (,)'s and then parse the ints
                String ls = line.split("\\[")[1].split("]")[0];
                int[] points = {Integer.parseInt(ls.split(",")[0]), Integer.parseInt(ls.split(",")[1]), Integer.parseInt(ls.split(",")[2]), Integer.parseInt(ls.split(",")[3])};
                for(int z = 0; z<id.length;z++)
                    tiles[id[z]].setRectangle(points[0] * (int) Tile.scale, points[1] * (int) Tile.scale, points[2] * (int) Tile.scale, points[3] * (int) Tile.scale, index);
                index++;

                if(line.split("]").length != 1) {
                    line = line.split("]")[1];

                }
                else
                    break;


            }
        }
    }


}
