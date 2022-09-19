package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
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

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();


    }



    public void createMap() throws IOException {

        URL absPath = new URL("https://raw.githubusercontent.com/bqcuong/bomberman-starter/starter-2/res/levels/Level1.txt");
        System.out.println(absPath);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(absPath.openStream()));
        String data = br.readLine();

        StringTokenizer tokens = new StringTokenizer(data);

        int _level = Integer.parseInt(tokens.nextToken());
        int _height = Integer.parseInt(tokens.nextToken());
        int _width = Integer.parseInt(tokens.nextToken());
        String[] _lineTiles = new String[_height];

        for (int i = 0; i < _height; ++i) {
            _lineTiles[i] = br.readLine().substring(0, _width);
        }

        br.close();

        for (int y = 0; y < _height; y++) {
            for (int x = 0; x < _width; x++) {

                switch (_lineTiles[y].charAt(x)) {
                    case '#':
                    {
                        Entity object = new Wall(x, y, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    }

                    case '*':
                    {
                        Entity object1 = new Brick(x, y, Sprite.brick.getFxImage());
                        stillObjects.add(object1);
                        break;
                    }
                    case '1':
                        Entity object2 = new Grass(x, y, Sprite.grass.getFxImage());
                        stillObjects.add(object2);
                        Entity object = new Balloon(x, y, Sprite.balloom_left1.getFxImage());
                        entities.add(object);
                        break;
                    case 'p':
                        Entity object3 = new Grass(x, y, Sprite.grass.getFxImage());
                        stillObjects.add(object3);
                        Entity bomberman = new Bomber(x, y, Sprite.player_right.getFxImage());
                        entities.add(bomberman);
                        break;


                    case ' ':
                        Entity object4 = new Grass(x, y, Sprite.grass.getFxImage());
                        stillObjects.add(object4);

                        break;

                }


            }
        }


    }
    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}

