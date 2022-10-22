package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Collision;
import uet.oop.bomberman.Timer;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.items.Bomb;

import static uet.oop.bomberman.BombermanGame.bombList;
import static uet.oop.bomberman.BombermanGame.board;
import static uet.oop.bomberman.BombermanGame.unTravelableList;
import static uet.oop.bomberman.BombermanGame.bomberman;

public class Minvo extends Entity {
    private Timer timer;
    private boolean isDead = false;
    private boolean deadAnimated = false;

    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timer = new Timer();
    }

    private int speed = 1;


    private boolean checkRightisBetter() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {

                if (bombList.hasBomb(i, j)) {

                    return ((x - i - 5) * (x - i - 5)
                            + (y - j) * (y - j))
                            > ((x - i + 5) * (x - i + 5)
                            + (y - j) * (y - j));

                }
            }
        }

        return ((x - bomberman.getX() - 5) * (x - bomberman.getX() - 5) + (y - bomberman.getY()) * (y - bomberman.getY())) < ((x - bomberman.getX() + 5) * (x - bomberman.getX() + 5) + (y - bomberman.getY()) * (y - bomberman.getY()));
    }

    private boolean checkUpisBetter() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (bombList.hasBomb(i, j)) {
                    return ((y - j - 5) * (y - j - 5)
                            + (x - i) * (x - i))
                            > ((y - j + 5) * (y - j + 5)
                            + (x - i) * (x - i));

                }
            }
        }
        return ((y - bomberman.getY() - 5) * (y - bomberman.getY() - 5) + (x - bomberman.getX()) * (x - bomberman.getX())) < ((y - bomberman.getY() + 5) * (y - bomberman.getY() + 5) + (x - bomberman.getX()) * (x - bomberman.getX()));
    }

    public void moveRight() {
        this.x += speed;
        this.img = Sprite.minvo_right1.getFxImage();
    }

    public void moveLeft() {
        this.x -= speed;
        this.img = Sprite.minvo_left1.getFxImage();
    }

    public void moveDown() {
        this.y += speed;
        this.img = Sprite.minvo_right2.getFxImage();
    }

    public void moveUp() {
        this.y -= speed;
        this.img = Sprite.minvo_left2.getFxImage();
    }

    private Timer time = new Timer();

    void move() {
        long elapsed = time.timeElapse() % 1000;
        if (elapsed < 250) {
            if (Collision.checkCollision(x + 5, y, unTravelableList) && !checkRightisBetter())
                moveRight();

        } else if (elapsed >= 250 && elapsed < 500) {
            if (Collision.checkCollision(x, y + 5, unTravelableList) && !checkUpisBetter())
                moveDown();

        } else if (elapsed >= 500 && elapsed < 750) {
            if (Collision.checkCollision(x - 5, y, unTravelableList) && checkRightisBetter())
                moveLeft();

        } else if (elapsed >= 750) {
            if (Collision.checkCollision(x, y - 5, unTravelableList) && checkUpisBetter())
                moveUp();

        }


    }

    public void dead() {
        deadAnimated = true;
        BombermanGame.enemyCount--;
        if (timer.timeElapse() % 1000 > 900)
            isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public void update() {
        if (!isDead && !deadAnimated) move();
        if (deadAnimated) img = Sprite.minvo_dead.getFxImage();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


}
