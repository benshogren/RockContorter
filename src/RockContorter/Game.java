package RockContorter;

import java.awt.*;

public class Game {
    public Board Board;
    public boolean mGameOver;
    public boolean Victory = false;
    public boolean ThrewRock = false;

    public boolean FaceRight;
    public boolean FaceLeft;
    public boolean FaceUp;
    public boolean FaceDown;

    public int moveCount = 0;
    public int updateCount = 0;
    public int ThrowCounter = 0;

    public Point PlayerPosition;
    public Point RockShield;
    public Point ThrowRock;
    public Point newRockShieldPosition;
    public Point newThrowRockPosition;


    public Game(Board pBoard) {
        Board = pBoard;
        PlayerPosition = new Point(7, 5);
    }

    public enum Move {
        MOVELeft,
        MOVERight,
        MOVEUp,
        MoveDown
    }

    public void Update() {
        if  ((updateCount == 2)) {
            moveThrowRock();
            updateCount = 0;
        } else {
            updateCount = updateCount + 1;
        }
    }

    public void Move (Move pMove) {
        Point newPosition = ConvertMoveToCoordinates(pMove);
        BoardState state = Board.GetState(newPosition);
        if (mGameOver) {
            return;
        }
        if (newPosition.y < PlayerPosition.y) {
            FaceUp = true;
            FaceRight = false;
            FaceDown = false;
            FaceLeft = false;
        } else if (newPosition.x < PlayerPosition.x){
            FaceUp = false;
            FaceRight = false;
            FaceDown = false;
            FaceLeft = true;
        } else if (newPosition.y > PlayerPosition.y){
            FaceUp = false;
            FaceRight = false;
            FaceDown = true;
            FaceLeft = false;
        } else if (newPosition.x > PlayerPosition.x) {
            FaceUp = false;
            FaceRight = true;
            FaceDown = false;
            FaceLeft = false;
        }
        if ((state == BoardState.WALL)||(state == BoardState.ROCK) || (newPosition == newRockShieldPosition)) {
            // dont move him
        } else {
            PlayerPosition = newPosition;
        }
    }


    public Point RockPlace(){
//        BoardState state = Board.GetState(newRockShieldPosition);

        ThrewRock = false;

        ThrowCounter = 0;
        if (FaceUp && !FaceLeft && !FaceDown && ! FaceRight){
            newRockShieldPosition = new Point(PlayerPosition.x, PlayerPosition.y - 1);
        }
        if (FaceLeft && !FaceDown && !FaceRight && !FaceUp){
            newRockShieldPosition = new Point(PlayerPosition.x - 1, PlayerPosition.y);
        }
        if (FaceRight && !FaceDown && !FaceLeft && !FaceUp){
            newRockShieldPosition = new Point(PlayerPosition.x + 1, PlayerPosition.y);
        }
        if (FaceDown && !FaceLeft && !FaceRight && !FaceUp){
            newRockShieldPosition = new Point(PlayerPosition.x, PlayerPosition.y + 1);
        }

        RockShield = newRockShieldPosition;

        Board.RockShieldAsBoardState(RockShield);

        return RockShield;
    }

    public boolean WallInTheWay(Point newTRockPosition) {
        return (Board.GetState(newTRockPosition) == BoardState.WALL);
    }
    public Point moveThrowRock (){


        BoardState state = Board.GetState(RockShield);
        if (state == BoardState.WALL){
            return RockShield;
        }
        if (RockShield == null){
            return new Point(1,1);
        }
        if (!ThrewRock) {
            return new Point(1,2);
        }
        ThrowCounter = ThrowCounter + 1;
        if ((state == BoardState.ROCK) && (ThrowCounter < 2)){
            Board.BackToEmpty(RockShield);
        }
        if (FaceUp){
            newThrowRockPosition = new Point(RockShield.x, RockShield.y - 1);
        } else if (FaceLeft){
            newThrowRockPosition = new Point(RockShield.x - 1, RockShield.y);
        } else if (FaceRight) {
            newThrowRockPosition = new Point(RockShield.x + 1, RockShield.y);
        } else {
            newThrowRockPosition = new Point(RockShield.x, RockShield.y + 1);
        }
        if (!WallInTheWay(newThrowRockPosition)) {
            RockShield = newThrowRockPosition;
        } else {
            ThrewRock = false;
        }
        return RockShield;

    }



    private Point ConvertMoveToCoordinates(Move pMove) {
        Point newPosition;
        switch (pMove) {
            case MOVELeft:
                newPosition = new Point(PlayerPosition.x - 1, PlayerPosition.y);
                break;
            case MOVERight:
                newPosition = new Point(PlayerPosition.x + 1, PlayerPosition.y);
                break;
            case MOVEUp:
                newPosition = new Point(PlayerPosition.x, PlayerPosition.y - 1);
                break;
            default:
                newPosition = new Point(PlayerPosition.x, PlayerPosition.y + 1);
                break;
        }
        return newPosition;
    }

    public boolean GameOver() {
        return mGameOver;
    }
}




