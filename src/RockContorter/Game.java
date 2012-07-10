package RockContorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Game {
    public Board Board;
    public boolean mGameOver;
    public boolean Victory = false;
    public boolean MadeWave = false;
    public Direction playerDirection;

    public int moveCount = 0;
    public int updateCount = 0;

    public Point PlayerPosition;
    public Point LeapPosition;
    public Point RockShield;
    private Point newRockPosition;
    public Point IsThereARockHere;

    public Point TopOfShell;
    public Point LeftOfShell;
    public Point RightOfShell;
    public Point BottomOfShell;

    public Game(Board pBoard) {
        Board = pBoard;
        PlayerPosition = new Point(7, 5);
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
        TopOfShell = new Point(PlayerPosition.x, PlayerPosition.y - 1);
        Board.RockAsBoardState(TopOfShell);

        LeftOfShell = new Point(PlayerPosition.x - 1, PlayerPosition.y);
        Board.RockAsBoardState(LeftOfShell);

        RightOfShell = new Point(PlayerPosition.x + 1, PlayerPosition.y);
        Board.RockAsBoardState(RightOfShell);

        BottomOfShell =  new Point(PlayerPosition.x, PlayerPosition.y + 1);
        Board.RockAsBoardState(BottomOfShell);

    }

    public void RockWave(){
        if (MadeWave){
            Board.BoardGrid.put(TopOfShell, new Flying_Rock(TopOfShell, Direction.UP));
            Board.BoardGrid.put(LeftOfShell, new Flying_Rock(LeftOfShell, Direction.LEFT));
            Board.BoardGrid.put(RightOfShell, new Flying_Rock(RightOfShell, Direction.RIGHT));
            Board.BoardGrid.put(BottomOfShell, new Flying_Rock(BottomOfShell, Direction.DOWN));
         }
           MadeWave = false;
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




