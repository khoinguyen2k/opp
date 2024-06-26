package uet.oop.bomberman.misc;

import uet.oop.bomberman.graphics.Sprite;

public class Coordination {
    public int x;
    public int y;

    public Coordination(int x, int y) {
        this.x = x* Sprite.SCALED_SIZE;
        this.y = y* Sprite.SCALED_SIZE;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordination{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
