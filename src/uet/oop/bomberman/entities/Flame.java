package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Timer;

public class Flame extends StillEntity {
    private FlameType type;
    private Timer timer;

    public Flame(int xUnit, int yUnit, FlameType type) {
        super(xUnit, yUnit, Sprite.bomb_exploded.getFxImage());
        switch (type) {
            case CENTER:
                img = Sprite.bomb_exploded.getFxImage();
                break;
            case VERTICAL:
                img =Sprite.explosion_vertical.getFxImage();
                break;
            case HORIZONTAL:
                img =Sprite.explosion_horizontal.getFxImage();
                break;
            case VERTICAL_TOP_LAST:
                img =Sprite.explosion_vertical_top_last.getFxImage();
                break;
            case VERTICAL_DOWN_LAST:
                img =Sprite.explosion_vertical_down_last.getFxImage();
                break;
            case HORIZONTAL_LEFT_LAST:
                img =Sprite.explosion_horizontal_left_last.getFxImage();
                break;
            case HORIZONTAL_RIGHT_LAST:
                img =Sprite.explosion_horizontal_right_last.getFxImage();
                break;
        }
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
            case VERTICAL_TOP_LAST:
                img = Sprite.movingSprite(Sprite.explosion_vertical_top_last
                        , Sprite.explosion_vertical_top_last1
                        , Sprite.explosion_vertical_top_last2
                        , (int) timer.timeElapse(), FRAME_STEP * 3).getFxImage();
                break;
            case VERTICAL_DOWN_LAST:
                img = Sprite.movingSprite(Sprite.explosion_vertical_down_last
                        , Sprite.explosion_vertical_down_last1
                        , Sprite.explosion_vertical_down_last2
                        , (int) timer.timeElapse(), FRAME_STEP * 3).getFxImage();
                break;
            case HORIZONTAL_RIGHT_LAST:
                img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last
                        , Sprite.explosion_horizontal_right_last1
                        , Sprite.explosion_horizontal_right_last2
                        , (int) timer.timeElapse(), FRAME_STEP * 3).getFxImage();
                break;
            case HORIZONTAL_LEFT_LAST:
                img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last
                        , Sprite.explosion_horizontal_left_last1
                        , Sprite.explosion_horizontal_left_last2
                        , (int) timer.timeElapse(), FRAME_STEP * 3).getFxImage();

                break;
            default:
                break;
        }
    }
}
