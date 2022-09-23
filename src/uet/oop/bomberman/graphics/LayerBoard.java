package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class LayerBoard {
    protected Entity[][] entityBoard;
    protected int height;
    protected int width;

    public LayerBoard() {
    }

    public static char[][] readMap(String path) {
        char[][] data = null;
        try {
            Scanner scanner = new Scanner(new File(path));
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Entity getEntity(int x, int y) {
        return entityBoard[x][y];
    }

    public void render(GraphicsContext gc) {
        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++) {
                if (entityBoard[h][w] != null)
                    entityBoard[h][w].render(gc);
            }
    }
}
