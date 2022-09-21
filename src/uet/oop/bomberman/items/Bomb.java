package uet.oop.bomberman.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

class Timer {
    private long start;
    private long end;
    public Timer() {
        start =System.currentTimeMillis();
        end =start;
    }
    public boolean isElapsed(long step) {
        if (end -start >step) {
            start =end;
            return true;
        } else {
            end =System.currentTimeMillis();
            return false;
        }
    }
}

public class Bomb extends Entity {
    private Timer timer;
    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timer =new Timer();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void update() {

    }
    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public boolean willExplode() {
        return timer.isElapsed(2000);
    }
}
