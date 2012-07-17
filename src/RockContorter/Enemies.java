package RockContorter;

import java.awt.*;
import java.util.ArrayList;


public abstract class Enemies implements BoardPiece{


    Point here;

    protected void randomMove(Board board) {
        java.util.List<Point> moves = new ArrayList<Point>();
        moves.add(new Point(here.x - 1, here.y));
        moves.add(new Point(here.x + 1, here.y));
        moves.add(new Point(here.x, here.y - 1));
        moves.add(new Point(here.x, here.y + 1));

        java.util.List<Point> validMoves = new ArrayList<Point>();
        for (Point move : moves) {
            if (!board.WallInTheWay(move) && !board.DisappearingRockInTheWay(move) && !board.StaticRockInTheWay(move)) {
                validMoves.add(move);
            }
        }
        if (validMoves.size() > 0) {
            moveToNewPosition(board, new Point(validMoves.get((int) (Math.random() * validMoves.size()))));
            return;
        }
    }

    protected void moveToNewPosition(Board board, Point point) {
        board.BoardGrid.put(here, new EmptyPiece());
        here = point;
        board.BoardGrid.put(here, this);
    }

    protected boolean MoveMonster(Board board, Point point) {
        Point moveUp = new Point(here.x, here.y - 1);
        if (here.y > point.y && board.PointIsEmpty(moveUp)){
            moveToNewPosition(board, moveUp);
            return true;
        }
        Point moveDown = new Point(here.x, here.y + 1);
        if (here.y < point.y && board.PointIsEmpty(moveDown)){
            moveToNewPosition(board, moveDown);
            return true;
        }
        Point moveLeft = new Point(here.x - 1, here.y);
        if (here.x > point.x && board.PointIsEmpty(moveLeft)){
            moveToNewPosition(board, moveLeft);
            return true;
        }
        Point moveRight = new Point(here.x + 1, here.y);
        if (here.x < point.x && board.PointIsEmpty(moveRight)){
            moveToNewPosition(board, moveRight);
            return true;
        }
        return false;
    }

    protected void throwAtPlayer(Board board, Point PlayerPosition) {
// add throwcounter for each using class.

        if((PlayerPosition.x > here.x) && (PlayerPosition.y == here.y)){
            board.BoardGrid.put(new Point(here.x + 2, here.y), new FlyingRock(new Point(here.x + 2, here.y), Game.Direction.RIGHT));
        } else if((PlayerPosition.x < here.x) && (PlayerPosition.y == here.y)){
            board.BoardGrid.put(new Point(here.x - 2, here.y), new FlyingRock(new Point(here.x - 2, here.y), Game.Direction.LEFT));
        } else if((PlayerPosition.x == here.x) && (PlayerPosition.y > here.y)){
            board.BoardGrid.put(new Point(here.x, here.y + 2), new FlyingRock(new Point(here.x, here.y + 2), Game.Direction.DOWN));
        } else if((PlayerPosition.x == here.x) && (PlayerPosition.y < here.y)){
            board.BoardGrid.put(new Point(here.x, here.y - 2), new FlyingRock(new Point(here.x, here.y - 2), Game.Direction.UP));
        } else {
            return;
        }
    }
}
