package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Timer;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.items.BombList;

public class Bomber extends Entity {
    private static int mainCharacterSpeed = 5;
    private Timer timer;
    private int bombAmount = 1;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        timer =new Timer();
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    private final int FRAME_STEP =80;
    public void moveRight() {
        this.x += mainCharacterSpeed;
        this.img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1,
                Sprite.player_right_2, (int)timer.timeElapse(), FRAME_STEP *3).getFxImage();

    }

    public void moveLeft() {
        this.x -= mainCharacterSpeed;
        this.img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1,
                Sprite.player_left_2, (int)timer.timeElapse(), FRAME_STEP *3).getFxImage();

    }

    public void moveDown() {
        this.y += mainCharacterSpeed;
        this.img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1,
                Sprite.player_down_2, (int)timer.timeElapse(), FRAME_STEP *3).getFxImage();

    }

    public void moveUp() {
        this.y -= mainCharacterSpeed;
        this.img = Sprite.player_up.getFxImage();
        this.img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1,
                Sprite.player_up_2, (int)timer.timeElapse(), FRAME_STEP *3).getFxImage();

    }

    @Override
    public void update() {

    }

    public void setImg(Image im) {
        this.img = im;
    }

    public void placeBomb(BombList bomblist, int x, int y) {
        if (!bomblist.hasBomb(x, y) && bombAmount > 0) {
            bomblist.addBomb(x, y);
            bombAmount--;
        }
    }

    public void restoreABomb() {
        bombAmount++;
    }

    public static void addSpeed() {
        mainCharacterSpeed += 2;
    }
}
