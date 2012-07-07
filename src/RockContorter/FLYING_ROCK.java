package RockContorter;

import java.awt.*;

public class Flying_Rock implements BoardPiece {

    Game.Move direction;
    Point p;

    public Flying_Rock(Point p, Game.Move directionToFly) {
        this.p = p;
        this.direction = directionToFly;
    }

    @Override
    public void Update(Board board) {

    }
}
