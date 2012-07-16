package RockContorter;

import java.awt.*;

public class RemoteMonsters extends Enemies {

    Point ProtectedPoint;
    int walkCounter = 0;

    public RemoteMonsters(Point MonsterPoint){
        this.here = MonsterPoint;
//        this.ProtectedPoint = ProtectedPoint;
    }


    public void Update(Board board, Point PlayerPosition) {
        Point nextRemotePoint;
        walkCounter = walkCounter + 1;

        if (walkCounter == 4){
            board.BoardGrid.put(here, new EmptyPiece());
            here = new Point(here.x - 1, here.y);
            board.BoardGrid.put(here, this);
            return;
        } else if (walkCounter == 8){
            board.BoardGrid.put(here, new EmptyPiece());
            here = new Point(here.x - 1, here.y);
            board.BoardGrid.put(here, this);
            return;
        } else if (walkCounter == 12){
            board.BoardGrid.put(here, new EmptyPiece());
            here = new Point(here.x, here.y - 1);
            board.BoardGrid.put(here, this);
            return;
        } else if (walkCounter == 16){
            board.BoardGrid.put(here, new EmptyPiece());
            here = new Point(here.x, here.y - 1);
            board.BoardGrid.put(here, this);
            return;
        } else if (walkCounter == 20){
            board.BoardGrid.put(here, new EmptyPiece());
            here = new Point(here.x + 1, here.y);
            board.BoardGrid.put(here, this);
            return;
        } else if (walkCounter == 24){
            board.BoardGrid.put(here, new EmptyPiece());
            here = new Point(here.x + 1, here.y);
            board.BoardGrid.put(here, this);
            return;
        } else if (walkCounter == 28){
            board.BoardGrid.put(here, new EmptyPiece());
            here = new Point(here.x, here.y + 1);
            board.BoardGrid.put(here, this);
            return;
        } else if (walkCounter == 32){
            board.BoardGrid.put(here, new EmptyPiece());
            here = new Point(here.x, here.y + 1);
            board.BoardGrid.put(here, this);
            walkCounter = 0;
            return;
        }


        return;


    }
















}
