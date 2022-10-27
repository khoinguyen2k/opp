package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.misc.Timer;
import uet.oop.bomberman.entities.Entity;

public abstract class Enemy extends Entity {
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected Timer timer = null;
    protected boolean isDead = false;
    protected boolean deadAnimated = false;
    protected int speed = 1;

    public void dead() {
        if (timer == null) timer = new Timer();
        deadAnimated = true;
        if (timer.isElapsed(750))
            isDead = true;
    }

    public boolean isDead() {
        return isDead || (deadAnimated && timer.timeElapse() > 740);
    }
}
