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

    protected void moveToNewPosition(Board board, Point moveLeft) {
        board.BoardGrid.put(here, new EmptyPiece());
        here = moveLeft;
        board.BoardGrid.put(here, this);
    }

    protected boolean moveToSpecificPoint(Board board, Point PlayerPosition) {
        Point moveUp = new Point(here.x, here.y - 1);
        if (here.y > PlayerPosition.y && board.PointIsEmpty(moveUp)){
            moveToNewPosition(board, moveUp);
            return true;
        }
        Point moveDown = new Point(here.x, here.y + 1);
        if (here.y < PlayerPosition.y && board.PointIsEmpty(moveDown)){
            moveToNewPosition(board, moveDown);
            return true;
        }
        Point moveLeft = new Point(here.x - 1, here.y);
        if (here.x > PlayerPosition.x && board.PointIsEmpty(moveLeft)){
            moveToNewPosition(board, moveLeft);
            return true;
        }
        Point moveRight = new Point(here.x + 1, here.y);
        if (here.x < PlayerPosition.x && board.PointIsEmpty(moveRight)){
            moveToNewPosition(board, moveRight);
            return true;
        }
        return false;
    }
}
