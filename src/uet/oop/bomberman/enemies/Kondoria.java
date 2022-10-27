package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.misc.Timer;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
    //data for calculate
    private Bomber bomberman;

    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void setEnemyData(Bomber bomber) {
        bomberman = bomber;
    }

    public boolean checkRightIsBetter() {
        return ((x - bomberman.getX() - 5) * (x - bomberman.getX() - 5) + (y - bomberman.getY()) * (y - bomberman.getY())) < ((x - bomberman.getX() + 5) * (x - bomberman.getX() + 5) + (y - bomberman.getY()) * (y - bomberman.getY()));
    }

    public boolean checkUpIsBetter() {
        return ((y - bomberman.getY() - 5) * (y - bomberman.getY() - 5) + (x - bomberman.getX()) * (x - bomberman.getX())) < ((y - bomberman.getY() + 5) * (y - bomberman.getY() + 5) + (x - bomberman.getX()) * (x - bomberman.getX()));
    }

    public void moveRight() {
        this.x += speed;
        this.img = Sprite.kondoria_right1.getFxImage();
    }

    public void moveLeft() {
        this.x -= speed;
        this.img = Sprite.kondoria_left1.getFxImage();
    }

    public void moveDown() {
        this.y += speed;
        this.img = Sprite.kondoria_right2.getFxImage();
    }

    public void moveUp() {
        this.y -= speed;
        this.img = Sprite.kondoria_left2.getFxImage();
    }

    private Timer time = new Timer();

    void move() {
        long elapsed = time.timeElapse() % 1000;
        if (elapsed < 250) {
            if (!checkRightIsBetter())
                moveRight();

        } else if (elapsed >= 250 && elapsed < 500) {
            if (!checkUpIsBetter())
                moveDown();

        } else if (elapsed >= 500 && elapsed < 750) {
            if (checkRightIsBetter())
                moveLeft();

        } else if (elapsed >= 750) {
            if (checkUpIsBetter())
                moveUp();
        }

    }

    @Override
    public void update() {
        if (!isDead && !deadAnimated) move();
        if (deadAnimated) img = Sprite.kondoria_dead.getFxImage();
    }

}
