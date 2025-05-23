package props;
import main.Window;
import java.util.ArrayList;

public class PropManager {

    public ArrayList<Prop> props = new ArrayList<>();
    PropDataLoader propDataLoader;
    Window window;
    public PropManager(Window window) {
        this.window = window;
    }

    public void loadProps(){
        propDataLoader = new PropDataLoader(window);
    }


}
