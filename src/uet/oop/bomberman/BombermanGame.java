package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uet.oop.bomberman.enemies.Balloon;
import uet.oop.bomberman.enemies.Kondoria;
import uet.oop.bomberman.enemies.Minvo;
import uet.oop.bomberman.enemies.Oneal;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.FlameSprite;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.items.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    //remove final to read value
    public static int WIDTH = 20;
    public static int HEIGHT = 15;

    public static final int FRAME_DELAY = 200;
    public static int score = 0;
    private boolean running = true;
    Timer time = new Timer();
    public static int enemyCount = 0;
    private boolean win = false;

    Media media = new Media(new File("res/audio/background_music_game.mp3").toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();

    private List<Entity> items = new ArrayList<>();
    public static long start = System.currentTimeMillis();
    public static Bomber bomberman;

    public static List<Coordination> unTravelableList = new ArrayList<>();
    public static Board board = new Board();
    public static BombList bombList = new BombList(board.getHeight(), board.getWidth());
    private static List<FlameSprite> flameSpriteList = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //createMap chuyen len dau de doc WIDTH, HEIGHT tu file map
        createMap();
        createEntities();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH +100, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("BombermanGame");
        stage.show();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!bomberman.isDead())
                switch (event.getCode()) {
                    case RIGHT:
                        if (Collision.checkCollision(bomberman.getX() +5, bomberman.getY()
                                , unTravelableList))
                            bomberman.moveRight();
                        break;
                    case DOWN:
                        if (Collision.checkCollision(bomberman.getX(), bomberman.getY() +5
                                , unTravelableList))
                            bomberman.moveDown();
                        break;
                    case LEFT:
                        if (Collision.checkCollision(bomberman.getX() -5, bomberman.getY()
                                , unTravelableList))
                            bomberman.moveLeft();
                        break;
                    case UP:
                        if (Collision.checkCollision(bomberman.getX(), bomberman.getY() -5
                                , unTravelableList))
                            bomberman.moveUp();
                        break;

                    case Z:
                        int bombX = (bomberman.getY() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
                        int bombY = (bomberman.getX() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
                        bomberman.placeBomb(bombList, bombX, bombY);
                        break;
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running)
                    render();
                else {
                    renderEndLevel();
                }
                update();
            }
        };
        timer.start();

    }

    private void renderEndLevel() {
        String result =win? "YOU WIN!": "YOU LOSE!";
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFont(Font.font("", FontWeight.BOLD, 100));
        gc.setFill(Color.WHEAT);
        gc.fillText(result, Math.round(canvas.getWidth() / 2) - 200, Math.round(canvas.getHeight() / 2));
        gc.fillText("Your Score: " +score, Math.round(canvas.getWidth() / 2) - 300, Math.round(canvas.getHeight() / 2) + 140);
    }

    public void createMap() {
        HEIGHT = board.getHeight();
        WIDTH = board.getWidth();
    }

    public void createEntities() {
        char[][] data = Board.readMap();
        for (int x = 0; x < data.length; x++) //numRow
            for (int y = 0; y < data[0].length; y++) //numCol
                switch (data[x][y]) {
                    case 'p':
                        bomberman = new Bomber(y, x, Sprite.player_right.getFxImage());
                        entities.add(bomberman);
                        break;
                    case '1':
                        Balloon object = new Balloon(y, x, Sprite.balloom_left1.getFxImage());
                        entities.add(object);
                        enemyCount++;
                        break;
                    case '2':
                        Oneal object4 = new Oneal(y, x, Sprite.oneal_right1.getFxImage());
                        entities.add(object4);
                        enemyCount++;
                        break;
                    case '3':
                        Kondoria object5 = new Kondoria(y, x, Sprite.kondoria_right1.getFxImage());
                        entities.add(object5);
                        enemyCount++;
                        break;
                    case '4':
                        Minvo object6 = new Minvo(y, x, Sprite.minvo_right1.getFxImage());
                        entities.add(object6);
                        enemyCount++;
                        break;
                    case 'b':
                        break;
                    default:
                        break;
                }
    }

    public void update() {
        entities.forEach(Entity::update);
//        if (!entities.contains(bomberman)) running =false;
        bomberman.update();
        handleBomberCollideEnemy();
        handleBomberPickItem();
        bombList.handleExploding(bomberman, board, flameSpriteList);
        flameSpriteList.forEach(f -> f.handleDisapeared());
        flameSpriteList.forEach(f -> f.collideEntity(entities));
        handleChainExplosion();
        handleBomberGetInPortal();
        mediaPlayer.setAutoPlay(true);

    }

    private void handleBomberCollideEnemy() {
        for (Entity entity : entities) {
            if (entity instanceof Balloon ||entity instanceof Oneal
                    ||entity instanceof Kondoria ||entity instanceof Minvo) {

                if (Collision.checkCollision(entity.getX(), entity.getY(), bomberman.getX(), bomberman.getY()))
                    bomberman.dead();
            }
        }
    }

    private void handleBomberGetInPortal() {
//        if (enemyObjects.size() +enemyObjects1.size() ==0)
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                if (board.getEntity(i, j) instanceof Portal) {
                    Portal portal = (Portal) board.getEntity(i, j);
                    if (portal.getY() == Sprite.SCALED_SIZE * i && portal.getX() == Sprite.SCALED_SIZE * j
                            && Collision.checkCollision(bomberman.getX(), bomberman.getY(), portal.getX(), portal.getY()))
                        if (enemyCount == 0) {
                            running = false;
                            win = true;
                        }

                }
    }

    private void handleBomberPickItem() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                if (board.getEntity(i, j) instanceof BombItem) {
                    BombItem item = (BombItem) board.getEntity(i, j);
                    if (Collision.checkCollision(bomberman.getX(), bomberman.getY(), item.getX(), item.getY())) {
                        board.pickedItem(i, j);
                        bomberman.restoreABomb();
                    }
                }

                if (board.getEntity(i, j) instanceof FlameItem) {
                    FlameItem item = (FlameItem) board.getEntity(i, j);
                    if (Collision.checkCollision(bomberman.getX(), bomberman.getY(), item.getX(), item.getY())) {
                        board.pickedItem(i, j);
                        Bomb.addPower();
                    }
                }

                if (board.getEntity(i, j) instanceof SpeedItem) {
                    SpeedItem item = (SpeedItem) board.getEntity(i, j);
                    if (Collision.checkCollision(bomberman.getX(), bomberman.getY(), item.getX(), item.getY())) {
                        board.pickedItem(i, j);
                        Bomber.addSpeed();
                    }
                }
            }
    }

    private void handleChainExplosion() {
        for (int k = 0; k < flameSpriteList.size(); k++) {
            for (int i = 0; i < bombList.getHeight(); i++)
                for (int j = 0; j < bombList.getWidth(); j++)
                    if (bombList.hasBomb(i, j)) {
                        Bomb b = (Bomb) bombList.getEntity(i, j);
                        if (flameSpriteList.get(k).collideBomb(b)) {
                            bombList.remove(i, j);
                            flameSpriteList.add(new FlameSprite(board, i, j));
                        }
                    }
        }
    }

    private Timer deadTimer;

    public void render() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        board.render(gc);
        bombList.render(gc);
        flameSpriteList.forEach(f -> f.render(gc));

        stillObjects.forEach(g -> g.render(gc));
        for (Entity entity : entities)
            if (!(entity instanceof Bomber)) entity.render(gc);
        if (bomberman.isDead()) {
            if (deadTimer == null) deadTimer = new Timer();
            if (!deadTimer.isElapsed(800))
                bomberman.handleDeadAnimation();
            else running = false;
        }
        bomberman.render(gc);

        gc.setFont(Font.font("", FontWeight.BOLD, 15));

        gc.setFill(Color.WHEAT);
        gc.fillText("Score: " + score, Math.round(canvas.getWidth()) - 70, 25);
        gc.fillText("Time: " + time.timeElapse() / 1000, Math.round(canvas.getWidth()) - 70, 60);
    }

}

