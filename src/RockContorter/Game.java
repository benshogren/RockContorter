package RockContorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Game {
    public Board Board;
    public boolean mGameOver;
    public boolean Victory = false;
    //public boolean ThrewRock = false;
    public boolean MadeWave = false;
    public Direction playerDirection;

    public int moveCount = 0;
    public int updateCount = 0;
    public int WaveCounter = 0;

    public Point PlayerPosition;
    public Point LeapPosition;
    public Point RockShield;
    private Point newRockPosition;
    public Point IsThereARockHere;

    public java.util.List<Point> Shell;

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

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public void Update() {
        updateCount++;
        if  (updateCount % 2 == 0) {
            ArrayList<BoardPiece> test = new ArrayList<BoardPiece>();
            for (BoardPiece point : Board.BoardGrid.values()) {
                test.add(point);
            }
            for (BoardPiece point : test) {
                point.Update(Board);
            }
            RockWave();
        }
        WaveCounter++;
    }

    public void Move (Direction pDirection) {
        Point newPosition = ConvertMoveToCoordinates(pDirection);
        BoardPiece piece = Board.GetState(newPosition);
        if (mGameOver) {
            return;
        }
        playerDirection = pDirection;

        if ((piece instanceof Wall)||(piece instanceof Static_Rock) || (newPosition == newRockPosition)) {
            // dont move him
        } else {
            PlayerPosition = newPosition;
        }
    }

    public Point RockPlace(){
        newRockPosition = GetPointFromStartAndDirection(PlayerPosition, playerDirection);
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

    public void StartThrowRock(){
        Point putRockHere = GetPointFromStartAndDirection(PlayerPosition, playerDirection);
        this.Board.BoardGrid.put(putRockHere, new Flying_Rock(putRockHere, playerDirection));
    }

    public void ChopWall(){
        Point chopWallSection1 = GetPointFromStartAndDirection(PlayerPosition, playerDirection);
        Point chopWallSection2 = GetPointFromStartAndDirection(chopWallSection1, playerDirection);
        Board.RockAsBoardState(chopWallSection1);
        Board.RockAsBoardState(chopWallSection2);
        Board.RockAsBoardState(GetPointFromStartAndDirection(chopWallSection2, playerDirection));

        //later make so it hurts enemies when hits, but not forever: null the points in update
    }

    public Point Leap(){
        for (int i = 3; i > 0; i--) {
            LeapPosition = GetPointFromStartAndDirection(PlayerPosition, playerDirection, i);
            if (!Board.WallInTheWay(LeapPosition) && !Board.RockInTheWay(LeapPosition)){
                PlayerPosition = LeapPosition;
                return PlayerPosition;
            }
        }
        return PlayerPosition;
    }

    public static Point GetPointFromStartAndDirection(Point startingPoint, Direction direction, int NumberOfSpacesToMove) {
        Point nextPoint = startingPoint;
        if (direction == Direction.DOWN) {
            nextPoint = new Point(startingPoint.x, startingPoint.y + NumberOfSpacesToMove);
        } else if (direction == Direction.UP) {
            nextPoint = new Point(startingPoint.x, startingPoint.y - NumberOfSpacesToMove);
        } else if (direction == Direction.RIGHT) {
            nextPoint = new Point(startingPoint.x + NumberOfSpacesToMove, startingPoint.y);
        } else if (direction == Direction.LEFT) {
            nextPoint = new Point(startingPoint.x - NumberOfSpacesToMove, startingPoint.y);
        }
        return nextPoint;
    }

    public static Point GetPointFromStartAndDirection(Point p, Direction m) {
        return GetPointFromStartAndDirection(p, m, 1);
    }

    private Point ConvertMoveToCoordinates(Direction pDirection) {
        return GetPointFromStartAndDirection(PlayerPosition, pDirection);
    }

    public boolean GameOver() {
        return mGameOver;
    }
}




