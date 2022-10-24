package uet.oop.bomberman.graphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataMap {
    private char[][] data;
    private int height;
    private int width;

    public DataMap(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            int level = scanner.nextInt(); //not use but have to read.
            height = scanner.nextInt();
            width = scanner.nextInt();
            scanner.nextLine(); //fix scanner bug.

            data = new char[height][width];
            for (int i = 0; i < height; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < width; j++) {
                    data[i][j] = line.charAt(j);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public char getAt(int w, int h) {
        return data[w][h];
    }
}
