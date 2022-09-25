package uet.oop.bomberman.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Timer;
import uet.oop.bomberman.entities.Entity;

public class Bomb extends Entity {
    private Timer timer;
    private static int power =2;
    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timer =new Timer();
    }

    public static int getPower() {
        return power;
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
        return timer.isElapsed(3000);
    }
    public static void addPower() {
        power++;
    }
}
