package uet.oop.bomberman;

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
}
