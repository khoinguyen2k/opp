package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Timer;
import uet.oop.bomberman.entities.*;


import java.util.ArrayList;
import java.util.List;

public class FlameSprite {
    private List<Flame> flameList =new ArrayList<>();
    private int power =2;
    private Timer timer;
    public int score_balloon = 0;
    public FlameSprite(Board board, int x, int y) {
        timer =new Timer();
        flameList.add(new Flame(y, x, Sprite.bomb_exploded.getFxImage()));
        for (int i =x -1; i >=x -power; i--) {
            if (i <0) break;
            if (board.getEntity(i, y) instanceof Wall) break;
            if (board.getEntity(i, y) instanceof Brick) {
                board.breakBrick(i, y);
                flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
                break;
            }
            flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
        }
        for (int i =x +1; i <=x +power; i++) {
            if (i >=board.getHeight()) break;
            if (board.getEntity(i, y) instanceof Wall) break;
            if (board.getEntity(i, y) instanceof Brick) {
                board.breakBrick(i, y);
                flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
                break;
            }
            flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
        }
        for (int j =y -1; j >=y -power; j--) {
            if (j <0) break;
            if (board.getEntity(x, j) instanceof Wall) break;
            if (board.getEntity(x, j) instanceof Brick) {
                board.breakBrick(x, j);
                flameList.add(new Flame(j, x, Sprite.explosion_horizontal.getFxImage()));
                break;
            }
            flameList.add(new Flame(j, x, Sprite.explosion_horizontal.getFxImage()));
        }
        for (int j =y +1; j <=y +power; j++) {
            if (j >=board.getWidth()) break;
            if (board.getEntity(x, j) instanceof Wall) break;
            if (board.getEntity(x, j) instanceof Brick) {
                board.breakBrick(x, j);
                flameList.add(new Flame(j, x, Sprite.explosion_horizontal.getFxImage()));
                break;
            }
            flameList.add(new Flame(j, x, Sprite.explosion_horizontal.getFxImage()));
        }
    }
    public void render(GraphicsContext gc) {
        flameList.forEach(f -> f.render(gc));
    }
    public void handleDisapeared() {
        if (timer.isElapsed(750))
            flameList.removeAll(flameList);
    }
    public void collideEntity(List<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Bomber) {
                Bomber bomber =(Bomber)entities.get(i);
                for (Flame flame : flameList) {
                    if (BombermanGame.checkCollision(bomber.getX(), bomber.getY(),
                            flame.getX(), flame.getY()))
                        entities.remove(i);
                }
            }
            if (entities.get(i) instanceof Balloon) {
                Balloon balloon =(Balloon) entities.get(i);
                for (Flame flame : flameList) {
                    if (BombermanGame.checkCollision(balloon.getX(), balloon.getY(),
                            flame.getX(), flame.getY()))
                    {
                        entities.remove(i);
                        BombermanGame.score += 100;
                    }

                }
            }
        }
    }
}
