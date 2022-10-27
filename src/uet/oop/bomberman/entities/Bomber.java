package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.misc.Collision;
import uet.oop.bomberman.GameStatus;
import uet.oop.bomberman.misc.Timer;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.graphics.BombLayer;
import uet.oop.bomberman.graphics.ObstacleLayer;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.items.BombItem;
import uet.oop.bomberman.items.FlameItem;
import uet.oop.bomberman.items.SpeedItem;

import java.util.List;

public class Bomber extends Entity {
    private int mainCharacterSpeed = 5;
    private Timer timer;
    private int bombAmount = 1;
    private boolean isDead = false;
    //data for movement.
    private ObstacleLayer obstacleLayer;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        timer = new Timer();
    }

    public void setObstacle(ObstacleLayer obstacleLayer) {
        this.obstacleLayer = obstacleLayer;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    private final int FRAME_STEP = 80;

    private void moveRight() {
        this.x += mainCharacterSpeed;
        this.img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1,
                Sprite.player_right_2, (int) timer.timeElapse(), BombermanGame.FRAME_STEP * 3).getFxImage();

    }

    private void moveLeft() {
        this.x -= mainCharacterSpeed;
        this.img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1,
                Sprite.player_left_2, (int) timer.timeElapse(), BombermanGame.FRAME_STEP * 3).getFxImage();

    }

    private void moveDown() {
        this.y += mainCharacterSpeed;
        this.img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1,
                Sprite.player_down_2, (int) timer.timeElapse(), BombermanGame.FRAME_STEP * 3).getFxImage();

    }

    private void moveUp() {
        this.y -= mainCharacterSpeed;
        this.img = Sprite.player_up.getFxImage();
        this.img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1,
                Sprite.player_up_2, (int) timer.timeElapse(), BombermanGame.FRAME_STEP * 3).getFxImage();

    }

    public void handlePress(KeyEvent event, BombLayer bombLayer) {
        if (!isDead())
            switch (event.getCode()) {
                case RIGHT:
                    if (Collision.bomberCollision(getX() + 5, getY()
                            , obstacleLayer.getUnTravelableList()))
                        moveRight();
                    break;
                case DOWN:
                    if (Collision.bomberCollision(getX(), getY() + 5
                            , obstacleLayer.getUnTravelableList()))
                        moveDown();
                    break;
                case LEFT:
                    if (Collision.bomberCollision(getX() - 5, getY()
                            , obstacleLayer.getUnTravelableList()))
                        moveLeft();
                    break;
                case UP:
                    if (Collision.bomberCollision(getX(), getY() - 5
                            , obstacleLayer.getUnTravelableList()))
                        moveUp();
                    break;

                case Z:
                    int bombX = (getY() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
                    int bombY = (getX() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
                    placeBomb(bombLayer, bombX, bombY);
                    break;
            }
    }

    @Override
    public void update() {
    }

    public void setImg(Image im) {
        this.img = im;
    }

    public void placeBomb(BombLayer bombLayer, int x, int y) {
        if (!bombLayer.hasBomb(x, y) && bombAmount > 0) {
            bombLayer.addBomb(x, y);
            bombAmount--;
        }
    }

    public void restoreABomb() {
        bombAmount++;
    }

    public void addSpeed() {
        mainCharacterSpeed += 2;
    }

    public boolean isDead() {
        return isDead;
    }

    public void dead() {
        this.isDead = true;
    }

    public void collideEnemies(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Enemy) {

                if (Collision.checkCollision(entity.getX(), entity.getY(), x, y))
                    dead();
            }
        }
    }

    public void handleDeadAnimation() {
        this.img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2,
                Sprite.player_dead3, (int) timer.timeElapse(), FRAME_STEP * 6).getFxImage();
    }

    public void pickItem(ObstacleLayer obstacleLayer) {
        for (int i = 0; i < obstacleLayer.getHeight(); i++)
            for (int j = 0; j < obstacleLayer.getWidth(); j++) {
                if (obstacleLayer.getEntityAt(i, j) instanceof BombItem) {
                    BombItem item = (BombItem) obstacleLayer.getEntityAt(i, j);
                    if (Collision.checkCollision(x, y, item.getX(), item.getY())) {
                        obstacleLayer.pickedItem(i, j);
                        restoreABomb();
                    }
                }

                if (obstacleLayer.getEntityAt(i, j) instanceof FlameItem) {
                    FlameItem item = (FlameItem) obstacleLayer.getEntityAt(i, j);
                    if (Collision.checkCollision(x, y, item.getX(), item.getY())) {
                        obstacleLayer.pickedItem(i, j);
                        Bomb.addPower();
                    }
                }

                if (obstacleLayer.getEntityAt(i, j) instanceof SpeedItem) {
                    SpeedItem item = (SpeedItem) obstacleLayer.getEntityAt(i, j);
                    if (Collision.checkCollision(x, y, item.getX(), item.getY())) {
                        obstacleLayer.pickedItem(i, j);
                        addSpeed();
                    }
                }
            }
    }

    public void handleGetInPortal(ObstacleLayer obstacleLayer, BombermanGame game) {
        for (int i = 0; i < obstacleLayer.getHeight(); i++)
            for (int j = 0; j < obstacleLayer.getWidth(); j++)
                if (obstacleLayer.getEntityAt(i, j) instanceof Portal) {
                    Portal portal = (Portal) obstacleLayer.getEntityAt(i, j);
                    if (portal.getY() == Sprite.SCALED_SIZE * i && portal.getX() == Sprite.SCALED_SIZE * j
                            && Collision.checkCollision(x, y, portal.getX(), portal.getY()))
                        if (game.getEnemyCount() == 0) {
                            game.setGameStatus(GameStatus.WIN);
                        }

                }
    }
}
