package obj;

import entity.Entity;

public interface Interactables {
    public boolean colliding(Entity e);
    public void interact();
    public Obj pickup();
    public Obj onCollide();
}
