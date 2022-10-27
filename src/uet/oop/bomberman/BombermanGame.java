package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uet.oop.bomberman.enemies.Balloon;
import uet.oop.bomberman.enemies.Kondoria;
import uet.oop.bomberman.enemies.Minvo;
import uet.oop.bomberman.enemies.Oneal;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.misc.Button;
import uet.oop.bomberman.misc.Menu;
import uet.oop.bomberman.misc.Timer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    //remove final to read value
    public static int WIDTH = 20;
    public static int HEIGHT = 15;

    public static final int FRAME_STEP = 80;
    private int score = 0;
    private GameStatus gameStatus = GameStatus.MENU;
    private Timer time = new Timer();
    private int enemyCount = 0;

    private Media media = new Media(new File("res/audio/background_music_game.mp3").toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(media);

    private GraphicsContext gc;
    private Canvas canvas;
    private Menu startMenu, exitMenu;

    private List<Entity> entities = new ArrayList<>();
    private Bomber bomberman;

    private String path = "res/levels/Level1.txt";
    private DataMap dataMap;

    private Board board;
    private ObstacleLayer obstacleLayer;
    private BombLayer bombLayer;
    private FlameLayer flameLayer;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {

        createMap();
        createEntities();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH + 100, Sprite.SCALED_SIZE * HEIGHT);
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

        createMenu();
        scene.setOnMouseMoved((event) -> {
            if (gameStatus ==GameStatus.MENU)
            startMenu.handleMouseMoved(event);
            else if (gameStatus ==GameStatus.WIN ||gameStatus ==GameStatus.LOSE)
                exitMenu.handleMouseMoved(event);
        });
        scene.setOnMouseClicked(event -> {
            if (gameStatus ==GameStatus.MENU)
            startMenu.handleMouseClicked(event, this);
            else if (gameStatus ==GameStatus.WIN ||gameStatus ==GameStatus.LOSE)
                exitMenu.handleMouseClicked(event, this);
        });

        scene.setOnKeyPressed(event -> {
            if (gameStatus == GameStatus.RUNNING)
                bomberman.handlePress(event, bombLayer);
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

    }

    private void renderEndLevel() {
        String result = (gameStatus == GameStatus.WIN ? "YOU WIN!" : "YOU LOSE!");
        gc.setFill(Color.BLACK);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFont(Font.font("", FontWeight.BOLD, 100));
        gc.setFill(Color.WHEAT);
        gc.fillText(result, Math.round(canvas.getWidth() / 2) - 500, Math.round(canvas.getHeight() / 2) -80);
        gc.fillText("Your Score: " + score, Math.round(canvas.getWidth() / 2) - 500, Math.round(canvas.getHeight() / 2) + 40);
    }

    public void createMap() {
        dataMap = new DataMap(path);
        HEIGHT = dataMap.getHeight();
        WIDTH = dataMap.getWidth();
        board = new Board(dataMap);
        obstacleLayer = new ObstacleLayer(dataMap);
        bombLayer = new BombLayer(dataMap);
        flameLayer = new FlameLayer(dataMap);
    }

    public void createEntities() {
        for (int x = 0; x < dataMap.getHeight(); x++) //numRow
            for (int y = 0; y < dataMap.getWidth(); y++) //numCol
            {
                char val = dataMap.getAt(x, y);
                switch (val) {
                    case 'p':
                        bomberman = new Bomber(y, x, Sprite.player_right.getFxImage());
                        bomberman.setObstacle(obstacleLayer);
                        entities.add(bomberman);
                        break;
                    case '1':
                        Balloon balloon = new Balloon(y, x, Sprite.balloom_left1.getFxImage());
                        balloon.setEnemyData(obstacleLayer);
                        entities.add(balloon);
                        enemyCount++;
                        break;
                    case '2':
                        Oneal oneal = new Oneal(y, x, Sprite.oneal_right1.getFxImage());
                        oneal.setEnemyData(bomberman, obstacleLayer);
                        entities.add(oneal);
                        enemyCount++;
                        break;
                    case '3':
                        Kondoria kondoria = new Kondoria(y, x, Sprite.kondoria_right1.getFxImage());
                        kondoria.setEnemyData(bomberman);
                        entities.add(kondoria);
                        enemyCount++;
                        break;
                    case '4':
                        Minvo minvo = new Minvo(y, x, Sprite.minvo_right1.getFxImage());
                        minvo.setEnemyData(bomberman, bombLayer, obstacleLayer, board);
                        entities.add(minvo);
                        enemyCount++;
                        break;

                    default:
                        break;
                }
            }
    }

    public void createMenu() {
        int gameWidth = Sprite.SCALED_SIZE * WIDTH + 100;
        int gameHeight = Sprite.SCALED_SIZE * HEIGHT;
        Button startButton = new Button("PLAY GAME", 100, Color.WHITE);
        Button exitButton = new Button("EXIT", 50, Color.WHITE);
        startMenu = new Menu(gameWidth, gameHeight, startButton, GameStatus.RUNNING);
        startMenu.placeButtonCentered(gameWidth, gameHeight);
        exitMenu = new Menu(gameWidth, gameHeight, exitButton, GameStatus.QUIT);
        exitMenu.placeButton(gameWidth -200, gameHeight -50);
    }

    public void renderMenu() {
        if (gameStatus ==GameStatus.MENU)
            startMenu.render(gc);
        else exitMenu.render(gc);
    }

    public void update() {
        if (gameStatus == GameStatus.RUNNING) {
            entities.forEach(Entity::update);
            bomberman.update();
            bombLayer.update();
            flameLayer.update();

            bomberman.collideEnemies(entities);
            bomberman.pickItem(obstacleLayer);
            bombLayer.handleExploding(bomberman, obstacleLayer, flameLayer);
            flameLayer.handleDisapeared();
            flameLayer.collideEntity(entities, this);
            flameLayer.handleChainExplosion(bombLayer, obstacleLayer);
            bomberman.handleGetInPortal(obstacleLayer, this);
            mediaPlayer.setAutoPlay(true);
        }

    }

    public void render() {
        if (gameStatus == GameStatus.MENU)
            renderMenu();
        else if (gameStatus == GameStatus.RUNNING)
            renderGame();
        else {
            renderMenu();
            renderEndLevel();
        }
    }

    private Timer deadTimer;

    public void renderGame() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        board.render(gc);
        obstacleLayer.render(gc);
        bombLayer.render(gc);
        flameLayer.render(gc);

        entities.forEach(entity -> entity.render(gc));
        if (bomberman.isDead()) {
            if (deadTimer == null) deadTimer = new Timer();
            if (!deadTimer.isElapsed(800))
                bomberman.handleDeadAnimation();
            else gameStatus = GameStatus.LOSE;
        }

        gc.setFont(Font.font("", FontWeight.BOLD, 15));

        gc.setFill(Color.WHEAT);
        gc.fillText("Score: " + score, Math.round(canvas.getWidth()) - 90, 25);
        gc.fillText("Time: " + time.timeElapse() / 1000, Math.round(canvas.getWidth()) - 90, 60);
    }

    //refactor things.
    public void removeEnemy() {
        enemyCount--;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}

