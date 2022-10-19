package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Coordination;
import uet.oop.bomberman.Timer;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.unTravelableList;
import static uet.oop.bomberman.BombermanGame.bomberman;

public class Oneal extends Entity {
    private Timer timer;
    private boolean isDead = false;
    private boolean deadAnimated = false;
    public static boolean checkCollision(int left_a,int top_a,int left_b,int top_b) {
        return (Math.abs((left_a-left_b) )< Sprite.SCALED_SIZE-5 && Math.abs((top_a-top_b) )< Sprite.SCALED_SIZE-5);
    }
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timer = new Timer();
    }

    private int speed = 1;
    public  boolean checkRightisBetter () {
        return ((x - bomberman.getX()-5)*(x - bomberman.getX()-5) + (y - bomberman.getY())*(y - bomberman.getY())) < ((x - bomberman.getX()+5)*(x - bomberman.getX()+5) + (y - bomberman.getY())*(y - bomberman.getY()));
    }
    public  boolean checkUpisBetter () {
        return ((y - bomberman.getY()-5)*(y - bomberman.getY()-5) + (x - bomberman.getX())*(x - bomberman.getX())) < ((y - bomberman.getY()+5)*(y - bomberman.getY()+5) + (x - bomberman.getX())*(x - bomberman.getX()));
    }

    public void moveRight() {
        this.x +=speed;
        this.img= Sprite.oneal_right1.getFxImage();
    }
    public void moveLeft() {
        this.x -=speed;
        this.img= Sprite.oneal_left1.getFxImage();
    }
    public void moveDown() {
        this.y +=speed;
        this.img = Sprite.oneal_right2.getFxImage();
    }
    public void moveUp() {
        this.y -=speed;
        this.img = Sprite.oneal_left2.getFxImage();
    }
    private Timer time = new Timer();
    void move() {
        long elapsed = time.timeElapse() % 1000;
        if (elapsed < 250) {
            boolean check3 = true;
            for (Coordination i : unTravelableList) {

                if (checkCollision(this.x+5, this.y
                        , i.getX(), i.getY()
                )) {
                    check3 = false;
                }

            }

            if (check3 && !checkRightisBetter())
                moveRight();

        } else if (elapsed >= 250 && elapsed < 500) {

            boolean check3 = true;
            for (Coordination i : unTravelableList) {

                if (checkCollision(this.x, this.y + 5
                        , i.getX(), i.getY()
                )) {
                    check3 = false;
                }

            }

            if (check3 && !checkUpisBetter())

                moveDown();
        }
        else if (elapsed >= 500 && elapsed  < 750) {
            boolean check3 = true;
            for (Coordination i : unTravelableList) {

                if (checkCollision(this.x - 5, this.y
                        , i.getX(), i.getY()
                )) {
                    check3 = false;
                }

            }

            if (check3 && checkRightisBetter())

                moveLeft();
        }
        else if (elapsed >= 750 ) {

            boolean check3 = true;
            for (Coordination i : unTravelableList) {

                if (checkCollision(this.x, this.y - 5
                        , i.getX(), i.getY()
                )) {
                    check3 = false;
                }

            }

            if (check3 && checkUpisBetter())
                moveUp();
        }


    }

    public void dead() {
        deadAnimated = true;
        if (timer.timeElapse() %1000 >720)
            isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public void update() {
        if (!isDead && !deadAnimated) move();
        if (deadAnimated) img = Sprite.oneal_dead.getFxImage();
    }
    public  int getX () {
        return this.x;
    }
    public  int getY () {
        return this.y;
    }
}
