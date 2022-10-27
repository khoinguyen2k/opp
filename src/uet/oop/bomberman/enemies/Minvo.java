package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.misc.Collision;
import uet.oop.bomberman.misc.Timer;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.BombLayer;
import uet.oop.bomberman.graphics.ObstacleLayer;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    //data for calculate
    private Board board;
    private ObstacleLayer obstacleLayer;
    private BombLayer bombLayer;
    private Bomber bomberman;

    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timer = new Timer();
    }

    public void setEnemyData(Bomber bomber, BombLayer bombLayer, ObstacleLayer obstacleLayer, Board board) {
        bomberman = bomber;
        this.bombLayer = bombLayer;
        this.obstacleLayer = obstacleLayer;
        this.board = board;
    }

    private boolean checkRightIsBetter() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {

                if (bombLayer.hasBomb(i, j)) {

                    return ((x - i - 5) * (x - i - 5)
                            + (y - j) * (y - j))
                            > ((x - i + 5) * (x - i + 5)
                            + (y - j) * (y - j));

                }
            }
        }

        return ((x - bomberman.getX() - 5) * (x - bomberman.getX() - 5) + (y - bomberman.getY()) * (y - bomberman.getY())) < ((x - bomberman.getX() + 5) * (x - bomberman.getX() + 5) + (y - bomberman.getY()) * (y - bomberman.getY()));
    }

    private boolean checkUpIsBetter() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (bombLayer.hasBomb(i, j)) {
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
        if (deadAnimated) img = Sprite.minvo_dead.getFxImage();
    }

}
