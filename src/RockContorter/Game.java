package RockContorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Game {
    public Board Board;
    public boolean mGameOver;
    public boolean Victory = false;
    public boolean ThrewRock = false;
    public boolean MadeWave = false;

    public Move playerDirection;




    public int moveCount = 0;
    public int updateCount = 0;
    public int ThrowCounter = 0;
    public int WaveCounter = 0;

    public Point PlayerPosition;
    public Point LeapPosition;
    public Point RockShield;
    private Point newRockPosition;
    public Point IsThereARockHere;


    public java.util.List<Point> Shell;

    public Point ChopWallSection1;
    public Point ChopWallSection2;
    public Point ChopWallSection3;

    public Point TopOfShell;
    public Point LeftOfShell;
    public Point RightOfShell;
    public Point BottomOfShell;


    public Game(Board pBoard) {
        Board = pBoard;
        PlayerPosition = new Point(7, 5);

        Shell = new ArrayList<Point>();
        Shell.add(new Point());
        Shell.add(new Point());
        Shell.add(new Point());
        Shell.add(new Point());

    }

    public enum Move {
        MOVELeft,
        MOVERight,
        MOVEUp,
        MoveDown
    }

    public void Update() {


        if  (updateCount == 0) {
            //ThrowRock();
            for (Map.Entry<Point, BoardPiece> position : Board.BoardGrid.entrySet()) {
                position.getValue().Update(Board);
            }
            RockWave();
            updateCount = 0;
        } else {
            updateCount = updateCount + 1;
        }

          WaveCounter = WaveCounter + 1;

    }

    public void Move (Move pMove) {
        Point newPosition = ConvertMoveToCoordinates(pMove);
        BoardPiece piece = Board.GetState(newPosition);
        piece.Update(new Board());
        if (mGameOver) {
            return;
        }
        if (newPosition.y < PlayerPosition.y) {
            playerDirection = Move.MOVEUp;
        } else if (newPosition.x < PlayerPosition.x){
            playerDirection = Move.MOVELeft;
        } else if (newPosition.y > PlayerPosition.y){
            playerDirection = Move.MoveDown;
        } else if (newPosition.x > PlayerPosition.x) {
            playerDirection = Move.MOVERight;
        }
        if ((piece instanceof Wall)||(piece instanceof Static_Rock) || (newPosition == newRockPosition)) {
            // dont move him
        } else {
            PlayerPosition = newPosition;
        }
    }

    public Point RockPlace(){
        newRockPosition = ConvertPointFromMove(PlayerPosition, playerDirection);
        RockShield = newRockPosition;
        Board.RockAsBoardState(RockShield);
        return RockShield;
    }

    public void RockShell(){
        Shell.set(0, new Point(PlayerPosition.x, PlayerPosition.y - 1));
        TopOfShell = Shell.get(0);
        Board.RockAsBoardState(Shell.get(0));

        Shell.set(1, new Point(PlayerPosition.x - 1, PlayerPosition.y));
        LeftOfShell = Shell.get(1);
        Board.RockAsBoardState(Shell.get(1));

        Shell.set(2, new Point(PlayerPosition.x + 1, PlayerPosition.y));
        RightOfShell = Shell.get(2);
        Board.RockAsBoardState(Shell.get(2));

        Shell.set(3, new Point(PlayerPosition.x, PlayerPosition.y + 1));
        BottomOfShell = Shell.get(3);
        Board.RockAsBoardState(Shell.get(3));
    }

    public void ShellClear(){
        Board.BackToEmpty(TopOfShell);
        Board.BackToEmpty(LeftOfShell);
        Board.BackToEmpty(RightOfShell);
        Board.BackToEmpty(BottomOfShell);
    }

    public void RockWave(){

//        if (!MadeWave){
//            Shell.set(0, null);
//            Shell.set(1, null);
//            Shell.set(2, null);
//            Shell.set(3, null);
//        }
        if (WaveCounter > 10){
            MadeWave = false;

        }

        if (MadeWave){
            if (Shell.get(0) != null){
                Shell.set(0, new Point(Shell.get(0).x, Shell.get(0).y - 1));
            }
            if (Shell.get(1) != null){
                Shell.set(1, new Point(Shell.get(1).x - 1, Shell.get(1).y));
            }
            if (Shell.get(2) != null) {
                Shell.set(2, new Point(Shell.get(2).x + 1, Shell.get(2).y));
            }
            if (Shell.get(3) != null){
                Shell.set(3, new Point(Shell.get(3).x, Shell.get(3).y + 1));
            }
            if (Board.WallInTheWay(Shell.get(0)) || Board.RockInTheWay(Shell.get(0))) {
                Shell.set(0, null);
            } else {
                TopOfShell = Shell.get(0);
            }
            if (Board.WallInTheWay(Shell.get(1)) || Board.RockInTheWay(Shell.get(1))) {
                Shell.set(1, null);
            } else {
                LeftOfShell = Shell.get(1);
            }
            if (Board.WallInTheWay(Shell.get(2)) || Board.RockInTheWay(Shell.get(2))) {
                Shell.set(2, null);
            } else {
                RightOfShell = Shell.get(2);
            }
            if (Board.WallInTheWay(Shell.get(3)) || Board.RockInTheWay(Shell.get(3))) {
                Shell.set(3, null);
            } else {
                BottomOfShell = Shell.get(3);
            }

         }

    }



    public void RockInFront(){
        BoardPiece isThere = Board.GetState(IsThereARockHere);
        if (! (isThere instanceof Static_Rock)){
            ThrewRock = false;
        }
    }

    public void PositionRock(){
        Point putRockHere = ConvertPointFromMove(PlayerPosition, playerDirection);
        this.Board.BoardGrid.put(putRockHere, new Flying_Rock(putRockHere, playerDirection));
    }


    public void ChopWall(){

        ChopWallSection1 = ConvertPointFromMove(PlayerPosition, playerDirection);
        ChopWallSection2 = ConvertPointFromMove(ChopWallSection1, playerDirection);
        ChopWallSection3 = ConvertPointFromMove(ChopWallSection2, playerDirection);

        Board.RockAsBoardState(ChopWallSection1);
        Board.RockAsBoardState(ChopWallSection2);
        Board.RockAsBoardState(ChopWallSection3);

        //later make so it hurts enemies when hits, but not forever: null the points in update
    }

    public Point Leap(){
        if (playerDirection == Move.MOVEUp){
            LeapPosition = new Point(PlayerPosition.x, PlayerPosition.y - 3);
            if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                PlayerPosition = LeapPosition;
                return PlayerPosition;
            } else
                LeapPosition = new Point(PlayerPosition.x, PlayerPosition.y - 2);
                if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                    PlayerPosition = LeapPosition;
                    return PlayerPosition;
                } else
                    LeapPosition = new Point(PlayerPosition.x, PlayerPosition.y - 1);
                    if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                        PlayerPosition = LeapPosition;
                        return PlayerPosition;
                    } else
                        return new Point(1,1);

        } else if (playerDirection == Move.MOVELeft){
            LeapPosition = new Point(PlayerPosition.x - 3, PlayerPosition.y);
            if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                PlayerPosition = LeapPosition;
                return PlayerPosition;
            } else
                LeapPosition = new Point(PlayerPosition.x - 2, PlayerPosition.y);
                if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                    PlayerPosition = LeapPosition;
                    return PlayerPosition;
                } else
                    LeapPosition = new Point(PlayerPosition.x - 1, PlayerPosition.y);
                    if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                        PlayerPosition = LeapPosition;
                        return PlayerPosition;
                    } else
                        return new Point(1,1);

        } else if (playerDirection == Move.MOVERight){
            LeapPosition = new Point(PlayerPosition.x + 3, PlayerPosition.y);
            if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                PlayerPosition = LeapPosition;
                return PlayerPosition;
            }else
                LeapPosition = new Point(PlayerPosition.x + 2, PlayerPosition.y);
                if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                    PlayerPosition = LeapPosition;
                    return PlayerPosition;
                } else
                    LeapPosition = new Point(PlayerPosition.x + 1, PlayerPosition.y);
                    if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                        PlayerPosition = LeapPosition;
                        return PlayerPosition;
                    } else
                        return new Point(1,1);

        } else
            LeapPosition = new Point(PlayerPosition.x, PlayerPosition.y + 3);
            if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                PlayerPosition = LeapPosition;
                return PlayerPosition;
            } else
                LeapPosition = new Point(PlayerPosition.x, PlayerPosition.y + 2);
                if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                    PlayerPosition = LeapPosition;
                    return PlayerPosition;
                } else
                    LeapPosition = new Point(PlayerPosition.x, PlayerPosition.y + 1);
                    if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                        PlayerPosition = LeapPosition;
                        return PlayerPosition;
                    } else
                        return new Point(1,1);
    }

    public static Point ConvertPointFromMove(Point p, Move m) {
        Point nextPoint = p;
        if (m == Game.Move.MoveDown) {
            nextPoint = new Point(p.x, p.y+1);
        } else if (m == Move.MOVEUp) {
            nextPoint = new Point(p.x, p.y-1);
        } else if (m == Move.MOVERight) {
            nextPoint = new Point(p.x+1, p.y);
        } else if (m == Move.MOVELeft) {
            nextPoint = new Point(p.x-1, p.y);
        }
        return nextPoint;
    }

    private Point ConvertMoveToCoordinates(Move pMove) {
        return ConvertPointFromMove(PlayerPosition, pMove);
    }

    public boolean GameOver() {
        return mGameOver;
    }
}




