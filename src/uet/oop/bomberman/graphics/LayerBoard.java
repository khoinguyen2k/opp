package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;

public abstract class LayerBoard {
    protected Entity[][] entityBoard;
    protected int height;
    protected int width;

    public LayerBoard(DataMap map) {
        height = map.getHeight();
        width = map.getWidth();
        entityBoard = new Entity[height][width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Entity getEntityAt(int x, int y) {
        return entityBoard[x][y];
    }

    public void render(GraphicsContext gc) {
        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++) {
                if (entityBoard[h][w] != null)
                    entityBoard[h][w].render(gc);
            }
    }
}
