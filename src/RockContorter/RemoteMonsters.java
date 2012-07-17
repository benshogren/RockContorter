package RockContorter;

import java.awt.*;

public class RemoteMonsters extends Enemies {

    Game.Direction patrolDirection;

    Point ProtectedPoint;
    int walkCounter = 0;
    int directionCounter = 0;

    public RemoteMonsters(Point MonsterPoint, Point ProtectedPoint){
        this.here = MonsterPoint;
        this.ProtectedPoint = ProtectedPoint;
    }


    public void Update(Board board, Point PlayerPosition) {
        walkCounter = walkCounter + 1;
        directionCounter = directionCounter + 1;
        board.BoardGrid.put(ProtectedPoint, new StaticRock());
        if (((PlayerPosition.x > ProtectedPoint.x + 3) || (PlayerPosition.x < ProtectedPoint.x - 3)) || ((PlayerPosition.y > ProtectedPoint.y + 3) || (PlayerPosition.y < ProtectedPoint.y - 3))){
            if (directionCounter >= 47){
                directionCounter = 0;
            } else if (directionCounter >= 36){
                patrolDirection = Game.Direction.DOWN;
            } else if (directionCounter >= 24){
                patrolDirection = Game.Direction.RIGHT;
            } else if(directionCounter >= 12){
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
        } else if (walkCounter == 4){
            walkCounter = 0;
            if (MoveMonster(board, PlayerPosition)) return;
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

