package obj;

import main.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjectDataLoader {
    ArrayList<String> settings = new ArrayList<String>();
    ArrayList<Obj> objects = new ArrayList<Obj>();
    private int currentIndex = 0;
    Window window;
    public ObjectDataLoader(Window window) {
        this.window = window;
        loadMap();
    }
    public void loadMap() {
        settings.clear();
        objects.clear();
        currentIndex = 0;
        try{

            File map = new File(Paths.get("res\\rooms\\"+ Window.currentRoom).toAbsolutePath().toString());
            Scanner reader = new Scanner(map);
            //Looks only for the __OBJECT_DATA__ because that's all we care about
            while(reader.hasNext())
                if(reader.nextLine().contains("__Objects"))
                    break;

            //Add every line after that
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                System.out.println(line);
                //End when we reach the props data section
                if(line.contains("__Props"))
                    break;

                settings.add(line);
            }
            reader.close();

        }catch(IOException e){
            System.out.println("Failed loading object data");
        }
        process();
    }
    //This will take each individual element of the settings and distribute it to proper methods
    public void process(){
        System.out.println("Settings: "+settings);
        for(int j = 0; j < settings.size(); j++){
            String[] elements = settings.get(j).split("\\{");
            System.out.println("Elements:" + Arrays.toString(elements));

            //To get rid of the end } we use this
            for(int i = 0; i < elements.length; i++){
                elements[i] = elements[i].replace("}","");
            }
            //Now what we can do is treat each setting as a indudual setting instead of the whole line
            for(String element : elements){
                if(!element.isEmpty()) {
                    //all we need to see is the type of the object then hand it off to the method
                    System.out.println(element);
                    String type = element.split("type:\"")[1].split("\",")[0];

                    switch (type) {
                        case "key" -> processKey(element);
                        case "door" -> processDoor(element);
                    }
                    currentIndex++;
                }

            }
        }
        System.out.println("OBJECTS: "+objects);
    }
    private int[] getStartingPos(String setting){
        String pos = setting.split("\\(")[1].split("\\)")[0];
        int x = Integer.parseInt(pos.split(",")[0]);
        int y = Integer.parseInt(pos.split(",")[1]);

        return new int[]{x, y};
    }
    private int getId(String setting){
        return Integer.parseInt(setting.split("id:")[1].split("[,}]")[0]);
    }
    public void processDoor(String setting){
        String sType = setting.split("doorType:")[1].split("[,}]")[0];
        int type = Integer.parseInt(sType);
        int[] pos = getStartingPos(setting);
        if(setting.contains("room:")){
            String room = setting.split("room:\"")[1].split("\"")[0];
            System.out.println(room);
            objects.add(new OBJ_Door(pos[0],pos[1],type,getId(setting),window.tileManager.currentTiles[pos[1]][pos[0]],room));

        }
        else
            objects.add(new OBJ_Door(pos[0],pos[1],type,getId(setting),window.tileManager.currentTiles[pos[1]][pos[0]]));
        if(setting.contains("unlocked:")){
            String unlocked = setting.split("unlocked:")[1].split("[,}]")[0];
            OBJ_Door door = (OBJ_Door) objects.get(currentIndex);
            door.unlocked = unlocked.toLowerCase().contains("true");
        }
    }

    public void processKey(String setting){
        //Because the pos of the setting looks like (x,y) we just split it
        int[] pos = getStartingPos(setting);
        objects.add(new OBJ_Key(pos[0],pos[1],getId(setting)));
    }
    public ArrayList<Obj> getObjects() {
        return objects;
    }
}
