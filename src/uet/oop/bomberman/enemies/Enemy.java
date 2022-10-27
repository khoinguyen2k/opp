package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.misc.Timer;
import uet.oop.bomberman.entities.Entity;

public abstract class Enemy extends Entity {
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected Timer timer;
    protected boolean isDead = false;
    protected boolean deadAnimated = false;
    protected int speed = 1;

    public void dead() {
        deadAnimated = true;
        if (timer.timeElapse() % 1000 > 740)
            isDead = true;
    }

    public boolean isDead() {
        return isDead || (deadAnimated && timer.timeElapse() % 1000 > 740);
    }
}
