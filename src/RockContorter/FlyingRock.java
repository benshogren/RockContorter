package RockContorter;

import java.awt.*;

public class FlyingRock implements BoardPiece {

    Game.Direction direction;
    Point point;

    public FlyingRock(Point point, Game.Direction directionToFly) {
        this.point = point;
        this.direction = directionToFly;
    }

    @Override
    public void Update(Board board, Point PlayerPosition) {
        board.BoardGrid.put(point, new Empty());
        Point nextPoint = Game.GetPointFromStartAndDirection(point, direction);

        if (board.StaticRockInTheWay(nextPoint) || board.WallInTheWay(nextPoint) || board.DisappearingRockInTheWay(nextPoint)) {
            return;
        }

        point = nextPoint;
        board.BoardGrid.put(point, this);
    }
}
