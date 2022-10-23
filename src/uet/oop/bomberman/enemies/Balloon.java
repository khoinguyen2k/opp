package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.*;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.unTravelableList;

public class Balloon extends Entity {
    private Timer timer;
    private boolean isDead = false;
    private boolean deadAnimated = false;

    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timer = new Timer();
    }

    private int speed = 1;

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
            if (Collision.checkCollision(x + 5, y, unTravelableList))
                moveRight();

        } else if (elapsed >= 2000 && elapsed < 4000) {
            if (Collision.checkCollision(x, y + 5, unTravelableList))
                moveDown();

        } else if (elapsed >= 4000 && elapsed < 6000) {
            if (Collision.checkCollision(x - 5, y, unTravelableList))
                moveLeft();

        } else if (elapsed >= 6000) {
            if (Collision.checkCollision(x, y - 5, unTravelableList))
                moveUp();

        }

    }

    public void dead() {
        deadAnimated = true;

        if (timer.timeElapse() % 1000 > 740)
            isDead = true;
    }

    public boolean isDead() {
        return isDead ||(deadAnimated &&timer.timeElapse() % 1000 > 740);
    }

    @Override
    public void update() {
        if (!isDead && !deadAnimated) move();
        if (deadAnimated) img = Sprite.balloom_dead.getFxImage();
    }

}
