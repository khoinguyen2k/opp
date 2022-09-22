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
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Board;
import uet.oop.bomberman.graphics.FlameSprite;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.items.BombList;


import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {
    public static final int mainCharacterSpeed = 5;
    //remove final to read value
    public static int WIDTH = 20;
    public static int HEIGHT = 15;

    public static int score = 0;
    Timer time = new Timer();

    private GraphicsContext gc;
    private Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();

    private static List<Balloon> enemyObjects = new ArrayList<>();
    public static long start = System.currentTimeMillis();
    Bomber bomberman;

    public static Board board =new Board();
    private static BombList bombList =new BombList(board.getHeight(), board.getWidth());
    private static List<FlameSprite> flameSpriteList =new ArrayList<>();
    public static List<Coordination> unTravelableList = board.getUnTravelableList();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //createMap chuyen len dau de doc WIDTH, HEIGHT tu file map
        createMap();
        createEntities();

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
                        boolean check3 = true;
                        for (Coordination i : unTravelableList) {

                            if (checkCollision(bomberman.getX()+5, bomberman.getY()
                                    , i.getX(), i.getY()
                            )) {

                                check3 = false;
                            }

                        }

                        if (check3)
                            bomberman.moveRight();
                        break;
                    case DOWN:
                        boolean check2 = true;
                        for (Coordination i : unTravelableList) {
                            if (checkCollision(bomberman.getX(), bomberman.getY() + 5
                                    , i.getX(), i.getY()
                            )) {
                                check2 = false;
                            }

                        }

                        if (check2) bomberman.moveDown();
                        break;
                    case LEFT:
                        boolean check1 = true;
                        for (Coordination i : unTravelableList) {
                            if (checkCollision(bomberman.getX()-5, bomberman.getY()
                                    , i.getX(), i.getY()
                            )) {
                                check1 = false;
                            }

                        }

                        if (check1) bomberman.moveLeft();
                        break;
                    case UP:
                        boolean check = true;
                        for (Coordination i : unTravelableList) {
                            if (checkCollision(bomberman.getX(), bomberman.getY() - 5
                                    , i.getX(), i.getY()
                            )) {
                                check = false;
                            }

                        }

                        if (check) bomberman.moveUp();
                        break;

                    case Z:
                        int bombX =(bomberman.getY() +Sprite.SCALED_SIZE /2) /Sprite.SCALED_SIZE;
                        int bombY =(bomberman.getX() +Sprite.SCALED_SIZE /2) /Sprite.SCALED_SIZE;
                        bomberman.placeBomb(bombList, bombX, bombY);
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

    public static boolean checkCollision(int left_a,int top_a,int left_b,int top_b) {
        return (Math.abs((left_a-left_b) )< Sprite.SCALED_SIZE-5 && Math.abs((top_a-top_b) )< Sprite.SCALED_SIZE-5);
    }



    public void createMap() {
        HEIGHT =board.getHeight();
        WIDTH =board.getWidth();
    }

    public void createEntities() {
        char[][] data =Board.readMap();
        for (int x =0; x <data.length; x++) //numRow
            for (int y =0; y <data[0].length; y++) //numCol
                switch (data[x][y]) {
                    case 'p':
                        bomberman =new Bomber(y, x, Sprite.player_right.getFxImage());
                        entities.add(bomberman);
                        break;
                    case '1':
                        Balloon object = new Balloon(y, x, Sprite.balloom_left1.getFxImage());
                        entities.add(object);
                        enemyObjects.add(object);
                        break;
                    case '2':
                        break;
                    default: break;
                }
    }

    public void update() {
        entities.forEach(Entity::update);
        bombList.handleExploding(bomberman, board, flameSpriteList);
        flameSpriteList.forEach(f ->f.handleDisapeared());
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        board.render(gc);
        bombList.render(gc);
        flameSpriteList.forEach(f ->f.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        gc.setFont(Font.font("", FontWeight.BOLD,25));

        gc.setFill(Color.ORANGE);
        gc.fillText("Score:"+score,150,25);
        gc.fillText("Time:"+time.timeElapse()/1000,Math.round(canvas.getWidth())-150,25);
    }



}

