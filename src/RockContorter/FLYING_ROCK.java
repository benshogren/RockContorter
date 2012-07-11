package RockContorter;

import java.awt.*;

public class Flying_Rock implements BoardPiece {

    Game.Direction direction;
    Point point;

    public Flying_Rock(Point point, Game.Direction directionToFly) {
        this.point = point;
        this.direction = directionToFly;
    }

    @Override
    public void Update(Board board, Point PlayerPosition) {
        board.BoardGrid.put(point, new Empty());
        Point nextPoint = Game.GetPointFromStartAndDirection(point, direction);

        if (board.RockInTheWay(nextPoint) || board.WallInTheWay(nextPoint)) {
            return;
        }

        point = nextPoint;
        board.BoardGrid.put(point, this);
    }
}
