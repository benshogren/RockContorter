package RockContorter;

import java.awt.*;

public class RemoteMonsters extends Enemies {

    Game.Direction patrolDirection;

    Point ProtectedPoint;
    int walkCounter = 0;
    int directionCounter = 0;

    public RemoteMonsters(Point MonsterPoint){
        this.here = MonsterPoint;
//        this.ProtectedPoint = ProtectedPoint;
    }


    public void Update(Board board, Point PlayerPosition) {
        walkCounter = walkCounter + 1;
        directionCounter = directionCounter + 1;
        if (directionCounter >= 63){
            directionCounter = 0;
        } else if (directionCounter >= 48){
            patrolDirection = Game.Direction.DOWN;
        } else if (directionCounter >= 32){
            patrolDirection = Game.Direction.RIGHT;
        } else if(directionCounter >= 16){
            patrolDirection = Game.Direction.UP;
        } else if (directionCounter >= 0){
            patrolDirection = Game.Direction.LEFT;
        }
        if (walkCounter == 4){
            board.BoardGrid.put(here, new EmptyPiece());
            MoveRemotes();
            board.BoardGrid.put(here, this);
            walkCounter = 0;
            return;
        }
    }

    public void MoveRemotes(){
        if (patrolDirection == Game.Direction.LEFT){
            here = new Point(here.x - 1, here.y);
            return;
        } else if (patrolDirection == Game.Direction.UP){
            here = new Point(here.x, here.y - 1);
            return;
        } else if (patrolDirection == Game.Direction.RIGHT){
            here = new Point(here.x + 1, here.y);
            return;
        } else if (patrolDirection == Game.Direction.DOWN){
            here = new Point(here.x, here.y + 1);
            return;
        }
    }

}

