package uet.oop.bomberman.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpeedItem extends Item {
    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void update() {
    }

}
