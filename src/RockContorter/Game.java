package RockContorter;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    public Board Board;

    public boolean mGameOver;
    public boolean Victory = false;
    public boolean MadeWave = false;
    public boolean ShellInPlace = false;

    public Point PlayerPosition;
    public Direction playerDirection;

    public int moveCount = 0;
    public int updateCount = 0;
    public int staticRockRemovalCount = 0;

    public Game(Board pBoard) {
        Board = pBoard;
        PlayerPosition = new Point(10, 16);
        playerDirection = Direction.UP;
        MoveMonsters();
    }

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        UPLEFT,
        UPRIGHT,
        DOWNRIGHT,
        DOWNLEFT
    }

    public void Update() {
        updateCount++;
        staticRockRemovalCount++;
//        if  (updateCount % 2 == 0) {
            ArrayList<BoardPiece> test = new ArrayList<BoardPiece>();
            for (BoardPiece point : Board.BoardGrid.values()) {
                test.add(point);
            }
            for (BoardPiece point : test) {
                point.Update(Board, PlayerPosition);
            }
            RockWave();
        Board.Update();
//        }
    }

    public void MoveMonsters(){
//        Board.BoardGrid.put(new Point(5,4), new RangedMonsters(new Point(5,4), RangedMonsters.Type.YAxis));
//        Board.BoardGrid.put(new Point(7,7), new RangedMonsters(new Point(7,7), RangedMonsters.Type.XAxis));

//        Board.BoardGrid.put(new Point(17,6), new Monsters(new Point(17,6)));
//        Board.BoardGrid.put(new Point(10, 6), new Monsters(new Point(10, 6)));
//        Board.BoardGrid.put(new Point(9,4), new Monsters(new Point(9,4)));
//        Board.BoardGrid.put(new Point(4, 4), new Monsters(new Point(4, 4)));
//        Board.BoardGrid.put(new Point(8,6), new Monsters(new Point(8,6)));
//        Board.BoardGrid.put(new Point(7, 7), new Monsters(new Point(7, 7)));
    }

    public void Move (Direction pDirection) {
        Point newPosition = ConvertMoveToCoordinates(pDirection);
        BoardPiece piece = Board.GetState(newPosition);
        if (mGameOver) {
            return;
        }
        playerDirection = pDirection;

        if ((piece instanceof Wall)||(piece instanceof StaticRock) || (piece instanceof DisappearingRock)) {
            // dont move him
        } else {
            PlayerPosition = newPosition;
        }
    }

    public Point RockPlace(){
        Point RockShield = GetPointFromStartAndDirection(PlayerPosition, playerDirection);
        if (Board.GetState(RockShield) instanceof StaticRock || Board.GetState(RockShield) instanceof Wall){
        } else {
        Board.PlaceRock(RockShield);
        }
        return RockShield;
    }

    public void ThrowARock(){
        Point putRockHere = GetPointFromStartAndDirection(PlayerPosition, playerDirection);
        if (Board.GetState(putRockHere) instanceof DisappearingRock){
            this.Board.BoardGrid.put(putRockHere, new FlyingRock(putRockHere, playerDirection));
        } else if (Board.GetState(putRockHere) instanceof StaticRock) {
            this.Board.BoardGrid.put(putRockHere, new FlyingRock(putRockHere, playerDirection));
        }
    }

    public void RockShell(){
        Board.PlaceRock(new Point(PlayerPosition.x, PlayerPosition.y - 1));
        Board.PlaceRock(new Point(PlayerPosition.x - 1, PlayerPosition.y));
        Board.PlaceRock(new Point(PlayerPosition.x + 1, PlayerPosition.y));
        Board.PlaceRock(new Point(PlayerPosition.x, PlayerPosition.y + 1));
        Board.PlaceRock(new Point(PlayerPosition.x - 1, PlayerPosition.y - 1));
        Board.PlaceRock(new Point(PlayerPosition.x + 1, PlayerPosition.y - 1));
        Board.PlaceRock(new Point(PlayerPosition.x - 1, PlayerPosition.y + 1));
        Board.PlaceRock(new Point(PlayerPosition.x + 1, PlayerPosition.y + 1));
    }

    public void RockWave(){
        if (MadeWave && ShellInPlace){
            Board.PlaceThrowingRock((new Point(PlayerPosition.x, PlayerPosition.y - 1)), Direction.UP);
            Board.PlaceThrowingRock((new Point(PlayerPosition.x - 1, PlayerPosition.y)), Direction.LEFT);
            Board.PlaceThrowingRock((new Point(PlayerPosition.x + 1 , PlayerPosition.y)), Direction.RIGHT);
            Board.PlaceThrowingRock((new Point(PlayerPosition.x, PlayerPosition.y + 1)), Direction.DOWN);
            Board.PlaceThrowingRock((new Point(PlayerPosition.x - 1, PlayerPosition.y - 1)), Direction.UPLEFT);
            Board.PlaceThrowingRock((new Point(PlayerPosition.x + 1, PlayerPosition.y - 1)), Direction.UPRIGHT);
            Board.PlaceThrowingRock((new Point(PlayerPosition.x - 1, PlayerPosition.y + 1)), Direction.DOWNLEFT);
            Board.PlaceThrowingRock((new Point(PlayerPosition.x + 1, PlayerPosition.y + 1)), Direction.DOWNRIGHT);
            ShellInPlace = false;
         }
           MadeWave = false;
    }

    public void ChopWall(){
        Point chopWallSection1 = PositionChopWall(PlayerPosition, playerDirection);
        Point chopWallSection2 = GetPointFromStartAndDirection(chopWallSection1, playerDirection);
        Board.PlaceRock(chopWallSection1);
        Board.PlaceRock(chopWallSection2);
        Board.PlaceRock(GetPointFromStartAndDirection(chopWallSection2, playerDirection));
    }

    public Point PositionChopWall(Point wallStartPoint, Direction wallDirection){
        if (wallDirection.equals(Direction.UP)){
            wallStartPoint = new Point(PlayerPosition.x + 1, PlayerPosition.y);
        } else if (wallDirection.equals(Direction.LEFT)){
            wallStartPoint = new Point(PlayerPosition.x, PlayerPosition.y - 1);
        } else  if (wallDirection.equals(Direction.RIGHT)){
            wallStartPoint = new Point(PlayerPosition.x, PlayerPosition.y + 1);
        } else  if (wallDirection.equals(Direction.DOWN)){
            wallStartPoint = new Point(PlayerPosition.x - 1, PlayerPosition.y);
        }
        return wallStartPoint;
    }

    public Point Leap(){
        for (int i = 3; i > 0; i--) {
            Point LeapPosition = GetPointFromStartAndDirection(PlayerPosition, playerDirection, i);
            if (!Board.WallInTheWay(LeapPosition) && !Board.StaticRockInTheWay(LeapPosition) && !Board.DisappearingRockInTheWay(LeapPosition)){
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
        } else if (direction == Direction.UPLEFT) {
            nextPoint = new Point(startingPoint.x - 1, startingPoint.y - 1);
        } else if (direction == Direction.UPRIGHT) {
            nextPoint = new Point(startingPoint.x + 1, startingPoint.y - 1);
        } else if (direction == Direction.DOWNLEFT) {
            nextPoint = new Point(startingPoint.x - 1, startingPoint.y + 1);
        } else if (direction == Direction.DOWNRIGHT) {
            nextPoint = new Point(startingPoint.x + 1, startingPoint.y + 1);
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




