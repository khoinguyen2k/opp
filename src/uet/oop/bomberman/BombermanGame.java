package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {
    //remove final to read value
    public static int WIDTH = 20;
    public static int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();

    private static List<Balloon> enemyObjects = new ArrayList<>();
    public  static long start = System.currentTimeMillis();
    Bomber bomberman;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {

        //createMap chuyen len dau de doc WIDTH, HEIGHT tu file map
        createMap();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
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
                switch (event.getCode()) {
                    case RIGHT:
                        bomberman.moveRight();
                        break;
                    case DOWN:
                        bomberman.moveDown();
                        break;
                    case LEFT:
                        bomberman.moveLeft();
                        break;
                    case UP:
                        bomberman.moveUp();
                        break;
                }
            }
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


    public void createMap() throws IOException {

        Scanner scanner = new Scanner(new File("res/levels/Level1.txt"));
        int _level = scanner.nextInt();
        int _height = scanner.nextInt();
        HEIGHT = _height;
        int _width = scanner.nextInt();
        WIDTH = _width;
        scanner.nextLine(); //fix scanner bug

        char[][] _lineTiles = new char[_height][_width];
        for (int i = 0; i < _height; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < _width; j++)
                _lineTiles[i][j] = line.charAt(j);
        }

        scanner.close();


        for (int y = 0; y < _height; y++)
            for (int x = 0; x < _width; x++)
                switch (_lineTiles[y][x]) {
                    case '#': {
                        Entity object = new Wall(x, y, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    }

                    case '*': {
                        Entity object1 = new Brick(x, y, Sprite.brick.getFxImage());
                        stillObjects.add(object1);
                        break;
                    }

                    case '1':
                        Entity object2 = new Grass(x, y, Sprite.grass.getFxImage());
                        stillObjects.add(object2);
                        Balloon object = new Balloon(x, y, Sprite.balloom_left1.getFxImage());
                        entities.add(object);
                         enemyObjects.add(object);
                        break;

                    case 'p':
                        Entity object3 = new Grass(x, y, Sprite.grass.getFxImage());
                        stillObjects.add(object3);
                         bomberman = new Bomber(x, y, Sprite.player_right.getFxImage());
                        entities.add(bomberman);
                        break;

                    case ' ':
                        Entity object4 = new Grass(x, y, Sprite.grass.getFxImage());
                        stillObjects.add(object4);
                        break;

                    default:
                        break;
                }


    }
    public void baloonMove() {

        for (int i=0 ; i< enemyObjects.size(); i ++) {
            int rand = (int)(Math.floor(Math.random()*4));

// ...
            long finish = System.currentTimeMillis();
            long timeElapsed = (finish - start) % 1000;
            System.out.println(timeElapsed);
            if (timeElapsed  > 500) {
                enemyObjects.get(i).moveRight();
            }
            else if (timeElapsed  < 500) {
                enemyObjects.get(i).moveLeft();
            }

        }
    }

    public void update() {
        entities.forEach(Entity::update);
        baloonMove();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}

