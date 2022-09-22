package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Coordination;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Timer;


import java.util.List;

import static uet.oop.bomberman.BombermanGame.unTravelableList;
import uet.oop.bomberman.Coordination;


public class Balloon extends Entity {
    public static boolean checkCollision(int left_a,int top_a,int left_b,int top_b) {
        return (Math.abs((left_a-left_b) )< Sprite.SCALED_SIZE-5 && Math.abs((top_a-top_b) )< Sprite.SCALED_SIZE-5);
    }
    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void moveRight() {
        this.x +=1;
        this.img= Sprite.balloom_right1.getFxImage();
    }
    public void moveLeft() {
        this.x -=1;
        this.img= Sprite.balloom_left1.getFxImage();
    }
    public void moveDown() {
        this.y +=1;
        this.img = Sprite.balloom_right3.getFxImage();
    }
    public void moveUp() {
        this.y -=1;
        this.img = Sprite.balloom_left3.getFxImage();
    }
    private  Timer time = new Timer();
    void move() {
        long elapsed = time.timeElapse() % 8000;
        if (elapsed < 2000) {
            boolean check3 = true;
            for (Coordination i : unTravelableList) {

                if (checkCollision(this.x+5, this.y
                        , i.getX(), i.getY()
                )) {

                    check3 = false;
                }

            }

            if (check3)
                moveRight();

        } else if (elapsed >= 2000 && elapsed < 4000) {

                boolean check3 = true;
                for (Coordination i : unTravelableList) {

                    if (checkCollision(this.x, this.y + 5
                            , i.getX(), i.getY()
                    )) {

                        check3 = false;
                    }

                }

                if (check3)
                    moveDown();
            }
        else if (elapsed >= 4000 && elapsed  < 6000) {
                boolean check3 = true;
                for (Coordination i : unTravelableList) {

                    if (checkCollision(this.x - 5, this.y
                            , i.getX(), i.getY()
                    )) {

                        check3 = false;
                    }

                }

                if (check3)
                    moveLeft();
            }
        else if (elapsed >= 6000 ) {

                boolean check3 = true;
                for (Coordination i : unTravelableList) {

                    if (checkCollision(this.x, this.y - 5
                            , i.getX(), i.getY()
                    )) {

                        check3 = false;
                    }

                }

                if (check3)
                    moveUp();
            }


    }

    @Override
    public void update() {
        move();
    }
}
