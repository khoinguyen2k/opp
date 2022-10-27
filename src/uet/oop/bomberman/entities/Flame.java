package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Timer;

public class Flame extends Entity {
    private FlameType type;
    private Timer timer;

    public Flame(int xUnit, int yUnit, FlameType type) {
        super(xUnit, yUnit, Sprite.bomb_exploded.getFxImage());
        this.type = type;
        timer = new Timer();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    private int FRAME_STEP =200;

    @Override
    public void update() {
        switch (type) {
            case CENTER:
                img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1
                        , Sprite.bomb_exploded2, (int) timer.timeElapse()
                        , FRAME_STEP * 3).getFxImage();
                break;
            case VERTICAL:
                img = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1
                        , Sprite.explosion_vertical2, (int) timer.timeElapse()
                        , FRAME_STEP * 3).getFxImage();
                break;
            case HORIZONTAL:
                img = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1
                        , Sprite.explosion_horizontal2, (int) timer.timeElapse()
                        , FRAME_STEP * 3).getFxImage();
                break;
            default:
                break;
        }
    }
}
