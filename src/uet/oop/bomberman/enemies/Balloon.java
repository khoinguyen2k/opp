package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.misc.Collision;
import uet.oop.bomberman.misc.Timer;
import uet.oop.bomberman.graphics.ObstacleLayer;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    //data for calculate
    private ObstacleLayer obstacleLayer;

    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timer = new Timer();
    }

    public void setEnemyData(ObstacleLayer obstacleLayer) {
        this.obstacleLayer = obstacleLayer;
    }

    public void moveRight() {
        this.x += speed;
        this.img = Sprite.balloom_right1.getFxImage();
    }

    public void moveLeft() {
        this.x -= speed;
        this.img = Sprite.balloom_left1.getFxImage();
    }

    public void moveDown() {
        this.y += speed;
        this.img = Sprite.balloom_right3.getFxImage();
    }

    public void moveUp() {
        this.y -= speed;
        this.img = Sprite.balloom_left3.getFxImage();
    }

    private Timer time = new Timer();

    void move() {
        long elapsed = time.timeElapse() % 8000;
        if (elapsed < 2000) {
            if (Collision.checkCollision(x + 5, y, obstacleLayer.getUnTravelableList()))
                moveRight();

        } else if (elapsed >= 2000 && elapsed < 4000) {
            if (Collision.checkCollision(x, y + 5, obstacleLayer.getUnTravelableList()))
                moveDown();

        } else if (elapsed >= 4000 && elapsed < 6000) {
            if (Collision.checkCollision(x - 5, y, obstacleLayer.getUnTravelableList()))
                moveLeft();

        } else if (elapsed >= 6000) {
            if (Collision.checkCollision(x, y - 5, obstacleLayer.getUnTravelableList()))
                moveUp();

        }

    }

    @Override
    public void update() {
        if (!isDead && !deadAnimated) move();
        if (deadAnimated) img = Sprite.balloom_dead.getFxImage();
    }

}
