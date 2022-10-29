package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends StillEntity {
    private Entity item;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Entity getItem() {
        return item;
    }

    public void setItem(Entity item) {
        this.item = item;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void update() {
    }
}
