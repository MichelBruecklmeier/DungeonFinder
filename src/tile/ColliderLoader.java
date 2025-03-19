package tile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ColliderLoader {


    ArrayList<String> settings = new ArrayList<String>();
    public ColliderLoader() {
        loadFile();
    }

    public void loadFile(){
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
                System.out.println(Arrays.toString(settings.toArray()));

            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setTileColliders(Tile[] tiles){
        for(int i = 0; i < settings.size(); i++){
            String setting = settings.get(i);
            //Index of where the new rect should go

            String idLine = settings.get(i).split("id:")[1].split(",")[1];
            //Check for the legacy code version of only having one id looking id:1,
            int[] id = new int[1];
            if(!settings.get(i).contains("id:[")){
                id[0] = Integer.parseInt(setting.split("id:")[1].split(",")[0]);
            } else {
                //We cut the setting id:[num,num,num],rect... we cut it by that ], then check the things to the left and count the amount of commas we have
                int length = setting.split("id:")[1].split("],")[0].split(",").length;
                id = new int[length];
                String line = setting.split("id:\\[")[1].split("],")[0];
                String[] tokens = line.split(",");
                System.out.println(line +" - "+Arrays.toString(tokens));
                System.out.println(length);
                for(int j = 0; j < tokens.length; j++){
                    id[j] = Integer.parseInt(tokens[j]);
                }
            }
            //Splits the string into just numbers for the new rect
            //TODO: Handle multiple settings for colliders
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
