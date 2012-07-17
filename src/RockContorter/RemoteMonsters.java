package RockContorter;

import java.awt.*;

public class RemoteMonsters extends Enemies {

    Game.Direction patrolDirection;

    Point ProtectedPoint;
    Point MonsterStartPoint;
    int walkCounter = 0;
    int directionCounter = 0;
    public boolean RemoteInSync = true;

    public RemoteMonsters(Point MonsterCurrentPoint, Point MonsterStartPoint, Point ProtectedPoint){
        this.here = MonsterCurrentPoint;
        this.ProtectedPoint = ProtectedPoint;
        this.MonsterStartPoint = MonsterStartPoint;
    }

    public void Update(Board board, Point PlayerPosition) {
        walkCounter = walkCounter + 1;
        directionCounter = directionCounter + 1;
        board.BoardGrid.put(ProtectedPoint, new StaticRock());
        if (RemoteInSync &&(((PlayerPosition.x > ProtectedPoint.x + 5) || (PlayerPosition.x < ProtectedPoint.x - 5)) || ((PlayerPosition.y > ProtectedPoint.y + 5) || (PlayerPosition.y < ProtectedPoint.y - 5)))){
            if (directionCounter >= 32){
                directionCounter = 0;
            } else if (directionCounter >= 25){
                patrolDirection = Game.Direction.DOWN;
            } else if (directionCounter >= 17){
                patrolDirection = Game.Direction.RIGHT;
            } else if(directionCounter >= 9){
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
        } else {
            RemoteInSync = false;
            if ((walkCounter == 4) &&(((PlayerPosition.x > ProtectedPoint.x + 8) || (PlayerPosition.x < ProtectedPoint.x - 8)) || ((PlayerPosition.y > ProtectedPoint.y + 8) || (PlayerPosition.y < ProtectedPoint.y - 8)))){
                walkCounter = 0;
                if (MoveMonster(board, MonsterStartPoint));
                if (here.equals(MonsterStartPoint)){
                    RemoteInSync = true;
                    directionCounter = 0;
                    return;
                }
            } else if (walkCounter == 4){
                walkCounter = 0;
                if (MoveMonster(board, PlayerPosition)) return;
            }
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

