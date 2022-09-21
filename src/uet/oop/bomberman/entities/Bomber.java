package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import static uet.oop.bomberman.BombermanGame.mainCharacterSpeed;

public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    public void setXY (int x, int y) {
        this.x = x;
        this.y = y;
    }
    public  int getX () {
        return  this.x;
    }
    public  int getY () {
        return this.y;
    }
    public void moveRight() {
        this.x +=mainCharacterSpeed;

    }
    public void moveLeft() {
        this.x -=mainCharacterSpeed;

    }
    public void moveDown() {
        this.y +=mainCharacterSpeed;

    }
    public void moveUp() {
        this.y -=mainCharacterSpeed;

    }

    @Override
    public void update() {

    }
}
