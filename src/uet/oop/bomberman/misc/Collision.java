package uet.oop.bomberman.misc;

import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Collision {
    public static boolean checkCollision(int left_a, int top_a, int left_b, int top_b) {
        return (Math.abs((left_a - left_b)) < Sprite.SCALED_SIZE - 5
                && Math.abs((top_a - top_b)) < Sprite.SCALED_SIZE - 5);
    }

    public static boolean checkCollision(int expectedX, int expectedY,
                                         List<Coordination> coordinationList) {
        for (Coordination i : coordinationList) {
            if (Collision.checkCollision(expectedX, expectedY, i.getX(), i.getY()))
                return false;
        }
        return true;
    }

    //bomber's real size is (12x16) x2.
    public static boolean bomberCollision(int left_a, int top_a, int left_b, int top_b) {
        int bomberHeight = 16 * 2;
        int bomberWidth = 12 * 2;
        int xOffset = 4;
        int upOffset = 8;
        int downOffset = 4;

        return left_a < left_b + Sprite.SCALED_SIZE - xOffset &&
                left_a + bomberWidth - xOffset > left_b &&
                top_a < top_b + Sprite.SCALED_SIZE - upOffset &&
                top_a + bomberHeight - downOffset > top_b;
    }

    public static boolean bomberCollision(int expectedX, int expectedY,
                                          List<Coordination> coordinationList) {
        for (Coordination i : coordinationList) {
            if (Collision.bomberCollision(expectedX, expectedY, i.getX(), i.getY()))
                return false;
        }
        return true;
    }
}
