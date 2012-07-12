package RockContorter;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class RangedMonsters implements BoardPiece{

    Point here;
    int updateCount = 0;

    public RangedMonsters(Point here) {
        this.here = here;
    }

    @Override
    public void Update(Board board, Point PlayerPosition) {


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

////            // back-up random moves for stuck monsters
//        java.util.List<Point> moves = new ArrayList<Point>();
//        moves.add(new Point(here.x - 1, here.y));
//        moves.add(new Point(here.x + 1, here.y));
//        moves.add(new Point(here.x, here.y - 1));
//        moves.add(new Point(here.x, here.y + 1));
//
//        java.util.List<Point> validMoves = new ArrayList<Point>();
//        for (Point move : moves) {
//            if (!board.WallInTheWay(move) && !board.DisappearingRockInTheWay(move) && !board.StaticRockInTheWay(move)) {
//                validMoves.add(move);
//            }
//        }
//        if (validMoves.size() > 0) {
//            here = validMoves.get((int) (Math.random()*validMoves.size()));
//        }




        updateCount = updateCount + 1;
        if  (updateCount == 4) {
            updateCount = 0;

            if((PlayerPosition.x > here.x) && (PlayerPosition.y == here.y)){
                board.BoardGrid.put(new Point(here.x + 1, here.y), new FlyingRock(new Point(here.x + 1, here.y), Game.Direction.RIGHT));
            } else if((PlayerPosition.x < here.x) && (PlayerPosition.y == here.y)){
                board.BoardGrid.put(new Point(here.x - 1, here.y), new FlyingRock(new Point(here.x - 1, here.y), Game.Direction.LEFT));
            } else if((PlayerPosition.x == here.x) && (PlayerPosition.y > here.y)){
                board.BoardGrid.put(new Point(here.x, here.y + 1), new FlyingRock(new Point(here.x, here.y + 1), Game.Direction.DOWN));
            } else if((PlayerPosition.x > here.x) && (PlayerPosition.y == here.y)){
                board.BoardGrid.put(new Point(here.x, here.y - 1), new FlyingRock(new Point(here.x, here.y - 1), Game.Direction.RIGHT));
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

