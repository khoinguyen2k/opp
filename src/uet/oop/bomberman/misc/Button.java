package uet.oop.bomberman.misc;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Button {
    private String text;
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    public Button(String text, int size, Color color) {
        this.text = text;
        this.x = 0;
        this.y = 0;
        this.width = (size - 30) * text.length();
        this.height = size;
        this.color = color;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean checkMouseFocus(MouseEvent event) {
        return event.getX() >= x && event.getX() <= x + width
                && event.getY() >= y - height && event.getY() <= y;
    }
}
