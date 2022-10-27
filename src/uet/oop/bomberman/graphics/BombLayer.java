package uet.oop.bomberman.graphics;

import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Bomber;

public class BombLayer extends LayerBoard {
    public BombLayer(DataMap map) {
        super(map);
        entityBoard = new Bomb[height][width];
    }

    public boolean hasBomb(int x, int y) {
        return entityBoard[x][y] != null;
    }

    public void addBomb(int x, int y) {
        entityBoard[x][y] = new Bomb(y, x, Sprite.bomb.getFxImage());
    }

    public void remove(int x, int y) {
        entityBoard[x][y] = null;
    }

    public void handleExploding(Bomber bomberman, ObstacleLayer obstacleLayer, FlameLayer flameLayer) {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (hasBomb(i, j)) {
                    Bomb bomb = (Bomb) entityBoard[i][j];
                    if (bomb.willExplode()) {
                        flameLayer.addFlameChain(new FlameChain(obstacleLayer, i, j));
                        bomb.playSound();
                        remove(i, j);
                        bomberman.restoreABomb();
                    }
                }
    }

    public void update() {
        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++)
                if (hasBomb(h, w))
                    entityBoard[h][w].update();
    }

}
