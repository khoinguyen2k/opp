package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Entity {
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
        this.y +=2;
    }
    public void moveUp() {
        this.y -=2;
    }

    @Override
    public void update() {

    }
}
