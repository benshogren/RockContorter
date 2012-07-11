package RockContorter;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class Monsters implements BoardPiece{

Point here;

    public Monsters(Point here) {
        this.here = here;
    }

    @Override
    public void Update(Board board, Point PlayerPosition) {

        Point moveUp = new Point(here.x, here.y - 1);
        if (here.y > PlayerPosition.y && !board.WallInTheWay(moveUp) && !board.RockInTheWay(moveUp)){
            here = moveUp;
        }
        Point moveDown = new Point(here.x, here.y + 1);
        if (here.y < PlayerPosition.y && !board.WallInTheWay(moveDown) && !board.RockInTheWay(moveDown)){
            here = moveDown;
        }
        Point moveLeft = new Point(here.x - 1, here.y);
        if (here.x > PlayerPosition.x && !board.WallInTheWay(moveLeft) && !board.RockInTheWay(moveLeft)){
            here = moveLeft;
        }
        Point moveRight = new Point(here.x + 1, here.y);
        if (here.x < PlayerPosition.x && !board.WallInTheWay(moveRight) && !board.RockInTheWay(moveRight)){
            here = moveRight;
        } else {
//            // back-up random moves for stuck monsters
            java.util.List<Point> moves = new ArrayList<Point>();
            moves.add(new Point(here.x - 1, here.y));
            moves.add(new Point(here.x + 1, here.y));
            moves.add(new Point(here.x, here.y - 1));
            moves.add(new Point(here.x, here.y + 1));

            java.util.List<Point> validMoves = new ArrayList<Point>();
            for (Point move : moves) {
                if (!board.WallInTheWay(move)) {
                    validMoves.add(move);
                }
            }
            if (validMoves.size() > 0) {
                validMoves.get((int) (Math.random()*validMoves.size()));
            }
        }


    }


}

