package uet.oop.bomberman.graphics;

import uet.oop.bomberman.entities.Grass;

public class Board extends LayerBoard {

    public Board(DataMap map) {
        super(map);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char val = map.getAt(i, j);
                switch (val) {
                    case 'p':
                        entityBoard[i][j] = new Grass(j, i, Sprite.grass.getFxImage());
                        //will add in func createEntities
                        break;

                    case ' ':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                        entityBoard[i][j] = new Grass(j, i, Sprite.grass.getFxImage());
                        break;

                    default:
                        break;
                }
            }
        }


    }

}
