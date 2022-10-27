package uet.oop.bomberman.graphics;

import uet.oop.bomberman.misc.Coordination;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Portal;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.items.BombItem;
import uet.oop.bomberman.items.FlameItem;
import uet.oop.bomberman.items.SpeedItem;

import java.util.ArrayList;
import java.util.List;

public class ObstacleLayer extends LayerBoard {
    private List<Coordination> unTravelableList = new ArrayList<>();

    public ObstacleLayer(DataMap map) {
        super(map);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char val = map.getAt(i, j);
                switch (val) {
                    case '#':
                        entityBoard[i][j] = new Wall(j, i, Sprite.wall.getFxImage());
                        unTravelableList.add(new Coordination(j, i));
                        break;

                    case '*':
                        entityBoard[i][j] = new Brick(j, i, Sprite.brick.getFxImage());
                        unTravelableList.add(new Coordination(j, i));
                        break;

                    case 'x':
                        entityBoard[i][j] = new Brick(j, i, Sprite.brick.getFxImage());
                        unTravelableList.add(new Coordination(j, i));

                        Brick brick = (Brick) entityBoard[i][j];
                        brick.setItem(new Portal(j, i, Sprite.portal.getFxImage()));
                        break;

                    case 'b':
                        entityBoard[i][j] = new Brick(j, i, Sprite.brick.getFxImage());
                        unTravelableList.add(new Coordination(j, i));

                        Brick brick1 = (Brick) entityBoard[i][j];
                        brick1.setItem(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        break;
                    case 'f':
                        entityBoard[i][j] = new Brick(j, i, Sprite.brick.getFxImage());
                        unTravelableList.add(new Coordination(j, i));

                        Brick brick2 = (Brick) entityBoard[i][j];
                        brick2.setItem(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        break;
                    case 's':
                        entityBoard[i][j] = new Brick(j, i, Sprite.brick.getFxImage());
                        unTravelableList.add(new Coordination(j, i));

                        Brick brick3 = (Brick) entityBoard[i][j];
                        brick3.setItem(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public List<Coordination> getUnTravelableList() {
        return unTravelableList;
    }

    public void pickedItem(int x, int y) {
        entityBoard[x][y] = new Grass(y, x, Sprite.grass.getFxImage());
    }

    public void breakBrick(int x, int y) {
        Brick brick = (Brick) entityBoard[x][y];
        if (brick.getItem() == null) entityBoard[x][y] = new Grass(y, x, Sprite.grass.getFxImage());
        else entityBoard[x][y] = brick.getItem();

        for (int i = unTravelableList.size() - 1; i >= 0; i--) {
            Coordination coord = unTravelableList.get(i);
            if (coord.getX() / Sprite.SCALED_SIZE == y &&
                    coord.getY() / Sprite.SCALED_SIZE == x) {
                unTravelableList.remove(i);
            }
        }
    }
}
