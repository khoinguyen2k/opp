package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Collision;
import uet.oop.bomberman.Timer;
import uet.oop.bomberman.enemies.Balloon;
import uet.oop.bomberman.enemies.Kondoria;
import uet.oop.bomberman.enemies.Minvo;
import uet.oop.bomberman.enemies.Oneal;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.items.Bomb;

import java.util.ArrayList;
import java.util.List;

public class FlameSprite {
    private List<Flame> flameList = new ArrayList<>();
    private int power;
    private Timer timer;

    public FlameSprite(Board board, int x, int y) {
        timer = new Timer();
        power = Bomb.getPower();
        flameList.add(new Flame(y, x, Sprite.bomb_exploded.getFxImage()));
        for (int i = x - 1; i >= x - power; i--) {
            if (i < 0) break;
            if (board.getEntity(i, y) instanceof Wall) break;
            if (board.getEntity(i, y) instanceof Brick) {
                board.breakBrick(i, y);
                flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
                break;
            }
            flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
        }
        for (int i = x + 1; i <= x + power; i++) {
            if (i >= board.getHeight()) break;
            if (board.getEntity(i, y) instanceof Wall) break;
            if (board.getEntity(i, y) instanceof Brick) {
                board.breakBrick(i, y);
                flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
                break;
            }
            flameList.add(new Flame(y, i, Sprite.explosion_vertical.getFxImage()));
        }
        for (int j = y - 1; j >= y - power; j--) {
            if (j < 0) break;
            if (board.getEntity(x, j) instanceof Wall) break;
            if (board.getEntity(x, j) instanceof Brick) {
                board.breakBrick(x, j);
                flameList.add(new Flame(j, x, Sprite.explosion_horizontal.getFxImage()));
                break;
            }
            flameList.add(new Flame(j, x, Sprite.explosion_horizontal.getFxImage()));
        }
        for (int j = y + 1; j <= y + power; j++) {
            if (j >= board.getWidth()) break;
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
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Bomber) {
                Bomber bomber = (Bomber) entities.get(i);
                for (Flame flame : flameList) {
                    if (Collision.checkCollision(bomber.getX(), bomber.getY(),
                            flame.getX(), flame.getY())) {
                        bomber.dead();
                    }
                }
            }
        }

        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Balloon) {
                Balloon balloon = (Balloon) entities.get(i);
                for (Flame flame : flameList) {
                    if (Collision.checkCollision(balloon.getX(), balloon.getY(),
                            flame.getX(), flame.getY())) {

                        balloon.dead();
                    }
                }
            }
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Balloon) {
                Balloon balloon = (Balloon) entities.get(i);
                if (balloon.isDead()) {
                    entities.remove(i);
                    BombermanGame.score += 100;
                    BombermanGame.enemyCount--;
                }
            }
        }

        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Kondoria) {
                Kondoria kondoria = (Kondoria) entities.get(i);
                for (Flame flame : flameList) {
                    if (Collision.checkCollision(kondoria.getX(), kondoria.getY(),
                            flame.getX(), flame.getY())) {

                        kondoria.dead();
                    }
                }
            }
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Kondoria) {
                Kondoria kondoria = (Kondoria) entities.get(i);
                if (kondoria.isDead()) {
                    entities.remove(i);
                    BombermanGame.score += 300;
                    BombermanGame.enemyCount--;
                }
            }
        }

        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Minvo) {
                Minvo minvo = (Minvo) entities.get(i);
                for (Flame flame : flameList) {
                    if (Collision.checkCollision(minvo.getX(), minvo.getY(),
                            flame.getX(), flame.getY())) {

                        minvo.dead();
                        if (minvo.isDead()) entities.remove(i);
                        BombermanGame.score += 300;
                        BombermanGame.enemyCount--;
                    }
                }
            }
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Minvo) {
                Minvo minvo = (Minvo) entities.get(i);
                if (minvo.isDead()) {
                    entities.remove(i);
                    BombermanGame.score += 300;
                    BombermanGame.enemyCount--;
                }
            }
        }

        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Oneal) {
                Oneal oneal = (Oneal) entities.get(i);
                for (Flame flame : flameList) {
                    if (Collision.checkCollision(oneal.getX(), oneal.getY(),
                            flame.getX(), flame.getY())) {

                        oneal.dead();
                    }
                }
            }
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i) instanceof Oneal) {
                Oneal oneal = (Oneal) entities.get(i);
                if (oneal.isDead()) {
                    entities.remove(i);
                    BombermanGame.score += 200;
                    BombermanGame.enemyCount--;
                }
            }
        }

    }

    public boolean collideBomb(Bomb bomb) {
        for (Flame flame : flameList)
            if (bomb.getX() == flame.getX() && bomb.getY() == flame.getY())
                return true;
        return false;
    }
}
