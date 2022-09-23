package uet.oop.bomberman.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class BombItem extends Entity {
    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
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
}