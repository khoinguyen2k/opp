package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends Entity {
    public int dx = 0 ,dy = 0;

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
        this.x +=5;
        dx = 5;
    }
    public void moveLeft() {
        this.x -=5;
        dx = -5;
    }
    public void moveDown() {
        this.y +=5;
        dy = 5;
    }
    public void moveUp() {
        this.y -=5;
        dy = -5;
    }

    @Override
    public void update() {

    }
}
