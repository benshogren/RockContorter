package RockContorter;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class RangedMonsters implements BoardPiece{
    public enum Type{
        YAxis,
        XAxis
    }

    Type AxisType;
    Point here;
    int updateCount = 0;
    int throwCount = 0;

    public RangedMonsters(Point here, Type type) {
        this.here = here;
        this.AxisType = type;
    }

    @Override
    public void Update(Board board, Point PlayerPosition) {

        updateCount++;
        if  (updateCount == 2 && AxisType == Type.YAxis) {
            updateCount = 0;
            Point moveUp = new Point(here.x, here.y - 1);
            if (here.y > PlayerPosition.y + 5 && !board.WallInTheWay(moveUp) && !board.StaticRockInTheWay(moveUp) && !board.DisappearingRockInTheWay(moveUp)){
                moveToNewPosition(board, moveUp);
                return;
            }
            Point moveDown = new Point(here.x, here.y + 1);
            if (here.y < PlayerPosition.y - 5 && !board.WallInTheWay(moveDown) && !board.StaticRockInTheWay(moveDown) && !board.DisappearingRockInTheWay(moveDown)){
                moveToNewPosition(board, moveDown);
                return;
            }
            Point moveLeft = new Point(here.x - 1, here.y);
            if (here.x > PlayerPosition.x && !board.WallInTheWay(moveLeft) && !board.StaticRockInTheWay(moveLeft) && !board.DisappearingRockInTheWay(moveLeft)){
                moveToNewPosition(board, moveLeft);
                return;
            }
            Point moveRight = new Point(here.x + 1, here.y);
            if (here.x < PlayerPosition.x && !board.WallInTheWay(moveRight) && !board.StaticRockInTheWay(moveRight) && !board.DisappearingRockInTheWay(moveRight)){
                moveToNewPosition(board, moveRight);
                return;
            }
        }

        if  (updateCount == 2 && AxisType == Type.XAxis ) {
            updateCount = 0;
            Point moveLeftX = new Point(here.x - 1, here.y);
            if (here.x > PlayerPosition.x + 8 && !board.WallInTheWay(moveLeftX) && !board.StaticRockInTheWay(moveLeftX) && !board.DisappearingRockInTheWay(moveLeftX)){
                moveToNewPosition(board, moveLeftX);
                return;
            }
            Point moveRightX = new Point(here.x + 1, here.y);
            if (here.x < PlayerPosition.x - 8 && !board.WallInTheWay(moveRightX) && !board.StaticRockInTheWay(moveRightX) && !board.DisappearingRockInTheWay(moveRightX)){
                moveToNewPosition(board, moveRightX);
                return;
            }

            Point moveUpX = new Point(here.x, here.y - 1);
            if (here.y > PlayerPosition.y && !board.WallInTheWay(moveUpX) && !board.StaticRockInTheWay(moveUpX) && !board.DisappearingRockInTheWay(moveUpX)){
                moveToNewPosition(board, moveUpX);
                return;
            }
            Point moveDownX = new Point(here.x, here.y + 1);
            if (here.y < PlayerPosition.y && !board.WallInTheWay(moveDownX) && !board.StaticRockInTheWay(moveDownX) && !board.DisappearingRockInTheWay(moveDownX)){
                moveToNewPosition(board, moveDownX);
                return;
            }

        }




            throwCount  = throwCount + 1;
            if  (throwCount == 4) {
                throwCount = 0;
                if((PlayerPosition.x > here.x) && (PlayerPosition.y == here.y)){
                    board.BoardGrid.put(new Point(here.x + 2, here.y), new FlyingRock(new Point(here.x + 2, here.y), Game.Direction.RIGHT));
                } else if((PlayerPosition.x < here.x) && (PlayerPosition.y == here.y)){
                    board.BoardGrid.put(new Point(here.x - 2, here.y), new FlyingRock(new Point(here.x - 2, here.y), Game.Direction.LEFT));
                } else if((PlayerPosition.x == here.x) && (PlayerPosition.y > here.y)){
                    board.BoardGrid.put(new Point(here.x, here.y + 2), new FlyingRock(new Point(here.x, here.y + 2), Game.Direction.DOWN));
                } else if((PlayerPosition.x > here.x) && (PlayerPosition.y == here.y)){
                    board.BoardGrid.put(new Point(here.x, here.y - 2), new FlyingRock(new Point(here.x, here.y - 2), Game.Direction.RIGHT));
                } else {
                    return;
                }
            }
        }










    private void moveToNewPosition(Board board, Point moveLeft) {
        board.BoardGrid.put(here, new Empty());
        here = moveLeft;
        board.BoardGrid.put(here, this);
    }


}

