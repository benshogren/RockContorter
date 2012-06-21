package PacMan;

import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public Board Board;
    public Point PacManPosition;
    public List<Point> MonsterPositions;
    public boolean mGameOver;
    public List<Point> Teleport;
    public boolean Victory = false;


    public int moveCount = 0;
    private Point PowerUp = new Point(5,3);
    public boolean PowerUpActive;
    public boolean Monster1Dead;

    public Game(Board pBoard) {
        Board = pBoard;
        PacManPosition = new Point(1, 1);
        MonsterPositions = new ArrayList<Point>();
        MonsterPositions.add(new Point(8,8));
        MonsterPositions.add(new Point(12,8));
        Teleport = new ArrayList<Point>();
        Teleport.add(new Point(6, 0));
        Teleport.add(new Point(7, 0));
        Teleport.add(new Point(8, 0));
        Teleport.add(new Point(6, 9));
        Teleport.add(new Point(7, 9));
        Teleport.add(new Point(8, 9));

    }

    public enum PacmanMove {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public void Update() {
        if (mGameOver) {
            return;
        }
        if (Board.AllPillsCollected()){
            Victory = true;
        }
        if (!PowerUpActive) {
            MoveAllMonsters();
        }
        if (MonsterEatsPacman() && !PowerUpActive) {
            mGameOver = true;
        }





        //        MonsterPositions.set(0, PointToSet);

//        if((PowerUpActive) && (PacManPosition == MonsterPositions.get(0))){
////                    MonsterPositions.set(0) (13,8));
////            its MonsterList[0] = PointYouWantToSet
////
//            MonsterPositions.set(0, new Point(13,8));
//
//
//        }

//
//
// if (Monster1Dead){
//
////            MonsterPositions.set(0,(13,8));
//
////            Point monster1ToDie = new Point(13,8);
////
////                   MonsterPositions.get(0);
////
////             monster1ToDie = new Point(13,8);
//
//
//        }

//            if (PacManPosition.equals(MonsterPositions.get(0))){
//                MonsterPositions.get(0) = new Point(13,8);
//            }
////            if (PacManPosition.equals(MonsterPositions.get(1))) {
//            }
//
//

    }

    private boolean MonsterEatsPacman() {
        return (PacManPosition.equals(MonsterPositions.get(0)) || PacManPosition.equals(MonsterPositions.get(1)));
    }

    public void Move (PacmanMove pMove) {
        Point newPosition = ConvertMoveToCoordinates(pMove);
        BoardState state = Board.GetState(newPosition);

        if (mGameOver) {
            return;
        }
        if (PowerUpActive){
            moveCount = moveCount + 1;
            if (moveCount > 15) {
                PowerUpActive = false;
                moveCount = 0;
            }
        }
        if (state == BoardState.WALL) {
            // dont move him
        } else {
            PacManPosition = newPosition;
        }

        if (PacManPosition.equals(PowerUp)) {
            PowerUpActive = true;
            moveCount = 0;
        }
        if (state == BoardState.PILL){
            Board.RemovePill(newPosition);
        }
        if (PacManPosition.equals(Teleport.get(0))) {
            PacManPosition = new Point(6,8);
        }
        if (PacManPosition.equals(Teleport.get(1))) {
            PacManPosition = new Point(7,8);
        }
        if (PacManPosition.equals(Teleport.get(2))) {
            PacManPosition = new Point(8,8);
        }
        if (PacManPosition.equals(Teleport.get(3))) {
            PacManPosition = new Point(6,1);
        }
        if (PacManPosition.equals(Teleport.get(4))) {
            PacManPosition = new Point(7,1);
        }
        if (PacManPosition.equals(Teleport.get(5))) {
            PacManPosition = new Point(8,1);
        }


    }

//
//    private void PowerUp() {
//
//
//        Point monsterToDie = MonsterPositions.get(0);
//        Point monsterToLive = MonsterPositions.get(1);
//        MonsterPositions.set(0, MonstersDie(monsterToDie, monsterToLive));

//        move second monster
//        Point monsterToDie = MonsterPositions.get(1);
//        Point monsterToLive = MonsterPositions.get(0);
//        MonsterPositions.set(1, MonstersDie(monsterToDie, monsterToLive));
//    }


//    MonsterList.set(0, PointYouWantToSet)
//    with 0 being the "first" one
//    if not that
//
//    its MonsterList[0] = PointYouWantToSet
//            or
//    MonsterList(0) = PointYouWantToSet

//    private void MoveAllMonsters() {
// // move first monster
// Point monsterToMove = MonsterPositions.get(0);
// Point monsterToDodge = MonsterPositions.get(1);
// MonsterPositions.set(0, GetNextSmartMove(monsterToMove, monsterToDodge));


//        MonsterPositions.set(0, GetNextSmartMove(monsterToMove, monsterToDodge));
//        MonsterPositions.set(0, PointToSet);
//        MonsterPositions.set(1, PointToSet);






    private Point MonstersDie(Point monsterToDie, Point monsterToLive){

        if((PowerUpActive) && (PacManPosition == monsterToDie) && (monsterToDie != monsterToLive)){
            monsterToDie = new Point(13,8);
        }

        return monsterToDie;
    }

    private void MoveAllMonsters() {
        // move first monster
        Point monsterToMove = MonsterPositions.get(0);
        Point monsterToDodge = MonsterPositions.get(1);
        MonsterPositions.set(0, GetNextSmartMove(monsterToMove, monsterToDodge));

        // move second monster
        monsterToMove = MonsterPositions.get(1);
        monsterToDodge = MonsterPositions.get(0);
        MonsterPositions.set(1, GetNextSmartMove(monsterToMove, monsterToDodge));
    }

    public boolean WallInTheWay(Point position) {
        return (Board.GetState(position) == BoardState.WALL);
    }

    private Point GetNextSmartMove(Point monsterToMove, Point monsterToDodge){
        if (monsterToMove.y > PacManPosition.y && !WallInTheWay(new Point(monsterToMove.x, monsterToMove.y - 1))){
            return new Point(monsterToMove.x, monsterToMove.y - 1);
        } else if (monsterToMove.y < PacManPosition.y && !WallInTheWay(new Point(monsterToMove.x, monsterToMove.y + 1))){
            return new Point(monsterToMove.x, monsterToMove.y + 1);
        }  else if (monsterToMove.x > PacManPosition.x && !WallInTheWay(new Point(monsterToMove.x - 1,monsterToMove.y))){
            return  new Point(monsterToMove.x - 1, monsterToMove.y);
        } else if (monsterToMove.x < PacManPosition.x && !WallInTheWay(new Point(monsterToMove.x + 1, monsterToMove.y))){
            return new Point(monsterToMove.x + 1, monsterToMove.y);
        } else {
//            // back-up random moves for stuck monsters
            List<Point> moves = new ArrayList<Point>();
            moves.add(new Point(monsterToMove.x - 1, monsterToMove.y));
            moves.add(new Point(monsterToMove.x + 1, monsterToMove.y));
            moves.add(new Point(monsterToMove.x, monsterToMove.y - 1));
            moves.add(new Point(monsterToMove.x, monsterToMove.y + 1));

            List<Point> validMoves = new ArrayList<Point>();
            for (Point move : moves) {
                if (!WallInTheWay(move) && monsterToMove != monsterToDodge) {
                    validMoves.add(move);
                }
            }
            if (validMoves.size() > 0) {
                return validMoves.get((int) (Math.random()*validMoves.size()));
            }
        }
        return monsterToMove;
    }

    private Point ConvertMoveToCoordinates(PacmanMove pMove) {
        Point newPosition;
        switch (pMove) {
            case LEFT:
                newPosition = new Point(PacManPosition.x - 1, PacManPosition.y);
                break;
            case RIGHT:
                newPosition = new Point(PacManPosition.x + 1, PacManPosition.y);
                break;
            case UP:
                newPosition = new Point(PacManPosition.x, PacManPosition.y - 1);
                break;
            default:
                newPosition = new Point(PacManPosition.x, PacManPosition.y + 1);
                break;
        }
        return newPosition;
    }

    public boolean GameOver() {
        return mGameOver;
    }
}
