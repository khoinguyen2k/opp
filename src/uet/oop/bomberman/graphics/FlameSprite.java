package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Flame;

import java.util.ArrayList;
import java.util.List;

public class FlameSprite {
    private List<Flame> flameList =new ArrayList<>();
    private int power =1;
    public FlameSprite(Board board, int x, int y) {

    }
    public void render(GraphicsContext gc) {
        flameList.forEach(f -> f.render(gc));
    }
}
