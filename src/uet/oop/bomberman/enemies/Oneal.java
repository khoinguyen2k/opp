package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.misc.Collision;
import uet.oop.bomberman.misc.Timer;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.ObstacleLayer;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    //data for calculate
    private Bomber bomberman;
    private ObstacleLayer obstacleLayer;

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void setEnemyData(Bomber bomber, ObstacleLayer obstacleLayer) {
        bomberman = bomber;
        this.obstacleLayer = obstacleLayer;
    }

    private boolean checkRightIsBetter() {
        return ((x - bomberman.getX() - 5) * (x - bomberman.getX() - 5) + (y - bomberman.getY()) * (y - bomberman.getY())) < ((x - bomberman.getX() + 5) * (x - bomberman.getX() + 5) + (y - bomberman.getY()) * (y - bomberman.getY()));
    }

    private boolean checkUpIsBetter() {
        return ((y - bomberman.getY() - 5) * (y - bomberman.getY() - 5) + (x - bomberman.getX()) * (x - bomberman.getX())) < ((y - bomberman.getY() + 5) * (y - bomberman.getY() + 5) + (x - bomberman.getX()) * (x - bomberman.getX()));
    }

    public void moveRight() {
        this.x += speed;
        this.img = Sprite.oneal_right1.getFxImage();
    }

    public void moveLeft() {
        this.x -= speed;
        this.img = Sprite.oneal_left1.getFxImage();
    }

    public void moveDown() {
        this.y += speed;
        this.img = Sprite.oneal_right2.getFxImage();
    }

    public void moveUp() {
        this.y -= speed;
        this.img = Sprite.oneal_left2.getFxImage();
    }

    private Timer time = new Timer();

    void move() {
        long elapsed = time.timeElapse() % 1000;
        if (elapsed < 250) {
            if (Collision.checkCollision(x + 5, y, obstacleLayer.getUnTravelableList()) && !checkRightIsBetter())
                moveRight();

        } else if (elapsed >= 250 && elapsed < 500) {
            if (Collision.checkCollision(x, y + 5, obstacleLayer.getUnTravelableList()) && !checkUpIsBetter())
                moveDown();

        } else if (elapsed >= 500 && elapsed < 750) {
            if (Collision.checkCollision(x - 5, y, obstacleLayer.getUnTravelableList()) && checkRightIsBetter())
                moveLeft();

        } else if (elapsed >= 750) {
            if (Collision.checkCollision(x, y - 5, obstacleLayer.getUnTravelableList()) && checkUpIsBetter())
                moveUp();

        }

    }

    @Override
    public void update() {
        if (!isDead && !deadAnimated) move();
        if (deadAnimated) img = Sprite.oneal_dead.getFxImage();
    }

}
