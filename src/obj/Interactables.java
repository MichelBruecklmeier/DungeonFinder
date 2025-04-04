package obj;

import entity.Entity;

public interface Interactables {

    public void interact();
    //Pick up method depending on what obj it is
    public Obj pickup();
    //Basic on collide command
    public Obj onCollide();
    //refresh is mainly for the door but incase anything else needs it its good to have
    public void refresh();
}
