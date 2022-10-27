package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
