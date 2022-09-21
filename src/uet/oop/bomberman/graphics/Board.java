package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Coordination;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private Entity[][] playGround;
    private int height;
    private int width;

    public static List<Coordination> getUnTravelableList() {
        return unTravelableList;
    }

    public static void setUnTravelableList(List<Coordination> unTravelableList) {
        Board.unTravelableList = unTravelableList;
    }

    public static List<Coordination> unTravelableList = new ArrayList<>();


    public static char[][] readMap() {
        char[][] data = null;
        try {
            Scanner scanner = new Scanner(new File("res/levels/Level1.txt"));
            int _level = scanner.nextInt(); //not use but have to read.
            int _height = scanner.nextInt();
            int _width = scanner.nextInt();
            scanner.nextLine(); //fix scanner bug

            data = new char[_height][_width];
            for (int i = 0; i < _height; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < _width; j++) {
                    data[i][j] = line.charAt(j);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;

    }

    public Board() {
        char[][] data = readMap();
        height = data.length;
        width = data[0].length;
        playGround = new Entity[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char val = data[i][j];
                switch (val) {
                    case '#':
                        playGround[i][j] =new Wall(j, i, Sprite.wall.getFxImage());
                        unTravelableList.add(new Coordination(j,i));

                        break;

                    case '*':
                        playGround[i][j] =new Brick(j, i, Sprite.brick.getFxImage());
                        unTravelableList.add(new Coordination(j,i));
                        break;

                    case 'b':case 'f':case 's':
                        playGround[i][j] =new Brick(j, i, Sprite.brick.getFxImage());
                        unTravelableList.add(new Coordination(j,i));
                        //will add in func createEntities
                        break;

                    case 'p':
                        playGround[i][j] =new Grass(j, i, Sprite.grass.getFxImage());

                        //will add in func createEntities
                        break;

                    case ' ':case '1':case '2':
                        playGround[i][j] =new Grass(j, i, Sprite.grass.getFxImage());
                        break;

                    default: break;
                }
            }
        }


    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Entity getEntity(int x, int y) {
        return playGround[x][y];
    }

    public void render(GraphicsContext gc) {
        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++) {
                if (playGround[h][w] !=null)
                    playGround[h][w].render(gc);
            }
    }

}
