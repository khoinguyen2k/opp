package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.items.Bomb;
import uet.oop.bomberman.items.BombList;

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
        this.img = Sprite.player_right.getFxImage();

    }
    public void moveLeft() {
        this.x -=mainCharacterSpeed;
        this.img = Sprite.player_left.getFxImage();

    }
    public void moveDown() {
        this.y +=mainCharacterSpeed;
        this.img = Sprite.player_down.getFxImage();

    }
    public void moveUp() {
        this.y -=mainCharacterSpeed;
        this.img = Sprite.player_up.getFxImage();

    }

    @Override
    public void update() {

    }
    public void setImg(Image im)
    {
         this.img = im;
    }

    private int bombAmount =1;
    public void placeBomb(BombList bomblist, int x, int y) {
        if (!bomblist.hasBomb(x, y) &&bombAmount >0) {
            bomblist.addBomb(x, y);
            bombAmount--;
        }
    }
    public void restoreABomb() {
        bombAmount++;
    }
}
