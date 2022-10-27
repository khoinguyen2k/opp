package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Timer;

import java.io.File;

public class Bomb extends Entity {
    private Timer timer;
    private static int power = 2;
    private MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("res/audio/Explosion.wav").toURI().toString()));

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timer = new Timer();
    }

    public static int getPower() {
        return power;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void update() {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2,
                (int) timer.timeElapse(), BombermanGame.FRAME_STEP * 6).getFxImage();
    }

    public boolean willExplode() {
        return timer.isElapsed(2500);
    }

    public static void addPower() {
        power++;
    }

    public void playSound() {
        mediaPlayer.play();
    }
}
