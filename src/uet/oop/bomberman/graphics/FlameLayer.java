package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class FlameLayer extends LayerBoard {
    private List<FlameChain> flameChainList = new ArrayList<>();

    public FlameLayer(DataMap map) {
        super(map);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        flameChainList.forEach(flameChain -> flameChain.render(gc));
    }

    public void addFlameChain(FlameChain flameChain) {
        flameChainList.add(flameChain);
    }

    public void handleDisapeared() {
        flameChainList.forEach(flameChain -> flameChain.handleDisapeared());
    }

    public void collideEntity(List<Entity> entities, BombermanGame game) {
        flameChainList.forEach(flameChain -> flameChain.collideEntity(entities, game));
    }

    public void handleChainExplosion(BombLayer bombLayer, ObstacleLayer obstacleLayer) {
        for (int k = 0; k < flameChainList.size(); k++) {
            for (int i = 0; i < bombLayer.getHeight(); i++)
                for (int j = 0; j < bombLayer.getWidth(); j++)
                    if (bombLayer.hasBomb(i, j)) {
                        Bomb b = (Bomb) bombLayer.getEntityAt(i, j);
                        if (flameChainList.get(k).collideBomb(b)) {
                            bombLayer.remove(i, j);
                            flameChainList.add(new FlameChain(obstacleLayer, i, j));
                        }
                    }
        }
    }
}
