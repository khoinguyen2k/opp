package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Timer;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Flame;
import uet.oop.bomberman.entities.Wall;

import java.util.ArrayList;
import java.util.List;

public class FlameSprite {
    private List<Flame> flameList =new ArrayList<>();
    private int power =2;
    private Timer timer;
    public FlameSprite(Board board, int x, int y) {
        timer =new Timer();
        flameList.add(new Flame(x, y, Sprite.bomb_exploded.getFxImage()));
        for (int i =x -1; i >=x -power; i--) {
            if (i <0) break;
            if (board.getEntity(i, y) instanceof Wall) break;
            if (board.getEntity(i, y) instanceof Brick) break;
            flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
        }
        for (int i =x +1; i <=x +power; i++) {
            if (i >=board.getHeight()) break;
            if (board.getEntity(i, y) instanceof Wall) break;
            if (board.getEntity(i, y) instanceof Brick) break;
            flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
        }
        for (int j =y -1; j >=y -power; j--) {
            if (j <0) break;
            if (board.getEntity(x, j) instanceof Wall) break;
            if (board.getEntity(x, j) instanceof Brick) break;
            flameList.add(new Flame(j, x, Sprite.explosion_horizontal.getFxImage()));
        }
        for (int j =y +1; j <=y +power; j++) {
            if (j >=board.getWidth()) break;
            if (board.getEntity(x, j) instanceof Wall) break;
            if (board.getEntity(x, j) instanceof Brick) break;
            flameList.add(new Flame(j, x, Sprite.explosion_horizontal.getFxImage()));
        }
    }
    public void render(GraphicsContext gc) {
        flameList.forEach(f -> f.render(gc));
    }
    public void handleDisapeared() {
        if (timer.isElapsed(1000))
            flameList.removeAll(flameList);
    }
}
