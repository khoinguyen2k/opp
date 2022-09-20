package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Balloon extends Entity {
    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void moveRight() {
        this.x +=5;
    }
    public void moveLeft() {
        this.x -=5;
    }
    public void moveDown() {
        this.y +=5;
    }
    public void moveUp() {
        this.y -=5;
    }

    @Override
    public void update() {

    }
}
