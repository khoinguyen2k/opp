package uet.oop.bomberman.misc;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GameStatus;

public class Menu {
    private int width;
    private int height;
    private Button button;
    private Color buttonColor;
    private GameStatus nextGameStatus;

    public Menu(int width, int height, Button button, GameStatus nextGameStatus) {
        this.width = width;
        this.height = height;
        this.button = button;
        buttonColor = button.getColor();
        this.nextGameStatus = nextGameStatus;
    }

    public void placeButtonCentered(int gameWidth, int gameHeight) {
        button.setPosition((gameWidth - button.getWidth()) / 2, (gameHeight + button.getHeight()) / 2);
    }

    public void placeButton(int x, int y) {
        button.setPosition(x, y);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setFont(Font.font("", FontWeight.BOLD, button.getHeight()));
        gc.setFill(button.getColor());
        gc.fillText(button.getText(), button.getX(), button.getY(), button.getWidth());
    }

    public void handleMouseMoved(MouseEvent event) {
        if (button.checkMouseFocus(event))
            button.setColor(Color.ORANGE);
        else button.setColor(buttonColor);
    }

    public void handleMouseClicked(MouseEvent event, BombermanGame game) {
        if (button.checkMouseFocus(event)) {
            if (nextGameStatus == GameStatus.QUIT)
                System.exit(1);
            else game.setGameStatus(nextGameStatus);
        }
    }
}
