package obj;

import main.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjectDataLoader {
    ArrayList<String> settings = new ArrayList<String>();
    public ObjectDataLoader() {
        loadMap();
    }
    public void loadMap() {
        try{
            File map = new File(Paths.get("res\\rooms\\"+ Window.currentRoom).toAbsolutePath().toString());
            Scanner reader = new Scanner(map);
            //Looks only for the __OBJECT_DATA__ because that's all we care about
            while(reader.hasNext() && reader.nextLine().contains("__Objects"));
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                settings.add(line);
            }

        }catch(IOException e){
            System.out.println("Failed loading object data");
        } finally {
            process();
        }
    }

    public void process(){
        for(String s : settings){
            String[] elements = s.split(",");
        }
    }
}
