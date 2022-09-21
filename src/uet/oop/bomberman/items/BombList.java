package uet.oop.bomberman.items;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.Sprite;

public class BombList extends Board {
    public BombList(int x, int y) {
        playGround =new Bomb[x][y];
    }
    public boolean hasBomb(int x, int y) {
        return playGround[x][y] !=null;
    }
    public void addBomb(int x, int y) {
        playGround[x][y] =new Bomb(y, x, Sprite.bomb.getFxImage());
    }
    public void handleExploding(Bomber bomberman) {
        for (int i =0; i <height; i++)
            for (int j =0; j <width; j++)
                if (hasBomb(i, j)) {
                    System.out.println(i +" " +j);
                    Bomb b =(Bomb) playGround[i][j];
                    if (b.willExplode()) {
                        playGround[i][j] =null;
                        bomberman.restoreABomb();
                    }
                }
    }
}
