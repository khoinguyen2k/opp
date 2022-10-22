package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordination;
import uet.oop.bomberman.Timer;
import uet.oop.bomberman.graphics.Sprite;
import static uet.oop.bomberman.BombermanGame.bomberman;

public class Kondoria extends Entity{

        private Timer timer;
        private boolean isDead = false;
        private boolean deadAnimated = false;

        public Kondoria(int xUnit, int yUnit, Image img) {
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
            this.img= Sprite.kondoria_right1.getFxImage();
        }
        public void moveLeft() {
            this.x -=speed;
            this.img= Sprite.kondoria_left1.getFxImage();
        }
        public void moveDown() {
            this.y +=speed;
            this.img = Sprite.kondoria_right2.getFxImage();
        }
        public void moveUp() {
            this.y -=speed;
            this.img = Sprite.kondoria_left2.getFxImage();
        }
        private Timer time = new Timer();
        void move() {
            long elapsed = time.timeElapse() % 1000;
            if (elapsed < 250) {



                if (!checkRightisBetter())
                    moveRight();

            } else if (elapsed >= 250 && elapsed < 500) {



                if ( !checkUpisBetter())

                    moveDown();
            }
            else if (elapsed >= 500 && elapsed  < 750) {


                if (checkRightisBetter())

                    moveLeft();
            }
            else if (elapsed >= 750 ) {



                if (checkUpisBetter())
                    moveUp();
            }


        }

        public void dead() {
            deadAnimated = true;
            BombermanGame.enemyCount --;
            if (timer.timeElapse() %1000 >720)
                isDead = true;
        }

        public boolean isDead() {
            return isDead;
        }

        @Override
        public void update() {
            if (!isDead && !deadAnimated) move();
            if (deadAnimated) img = Sprite.kondoria_dead.getFxImage();
        }
        public  int getX () {
            return this.x;
        }
        public  int getY () {
            return this.y;
        }


}
