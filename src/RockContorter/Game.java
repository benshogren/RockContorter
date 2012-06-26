package RockContorter;

import java.awt.*;

public class Game {
    public Board Board;
    public Point PlayerPosition;
    public boolean mGameOver;
    public boolean Victory = false;
    public int moveCount = 0;


    public boolean FaceRight;
    public boolean FaceLeft;
    public boolean FaceUp;
    public boolean FaceDown;
    public Point RockShield;
    public int updateCount = 0;
    public int ThrowCounter = 0;
    public Point tRockShield;
    public Point newRockPosition;




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
        updateCount = updateCount + 1;

        if  (updateCount == 10) {
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
            FaceDown=false;
            FaceLeft = false;

        }
        else if (newPosition.x < PlayerPosition.x){
            FaceUp = false;
            FaceRight = false;
            FaceDown=false;
            FaceLeft = true;
        }
        else if (newPosition.y > PlayerPosition.y){
            FaceUp = false;
            FaceRight = false;
            FaceDown=true;
            FaceLeft = false;
        }
        else if (newPosition.x > PlayerPosition.x) {
            FaceUp = false;
            FaceRight = true;
            FaceDown=false;
            FaceLeft = false;
        }

        if ((state == BoardState.WALL)||(state == BoardState.ROCK) || (newPosition == newRockPosition)) {
            // dont move him
        } else {
            PlayerPosition = newPosition;
        }
    }


    public Point RockPlace(){
//        BoardState state = Board.GetState(newRockPosition);

        //here make 1)newRockPosition, 2) if newRP BS is ROCK, make Empty, 3) if newRB BS is ROCK, RockShield disappears

        ThrowCounter = 0;
        if (FaceUp && !FaceLeft && !FaceDown && ! FaceRight){
            newRockPosition = new Point(PlayerPosition.x, PlayerPosition.y - 1);
        }

        if (FaceLeft && !FaceDown && !FaceRight && !FaceUp){
            newRockPosition = new Point(PlayerPosition.x - 1, PlayerPosition.y);
        }
        if (FaceRight && !FaceDown && !FaceLeft && !FaceUp){
            newRockPosition = new Point(PlayerPosition.x + 1, PlayerPosition.y);
        }
        if (FaceDown && !FaceLeft && !FaceRight && !FaceUp){
            newRockPosition = new Point(PlayerPosition.x, PlayerPosition.y + 1);
        }
        RockShield = newRockPosition;
        Board.RockShieldAsBoardState(RockShield);

        return RockShield;
    }


    public Point moveThrowRock (){
        BoardState state = Board.GetState(RockShield);
        ThrowCounter = ThrowCounter + 1;
        if ((state == BoardState.ROCK) && (ThrowCounter < 2)){
            Board.BackToEmpty(RockShield);
        }

        if (FaceUp){
            RockShield = new Point(RockShield.x, RockShield.y - 1);
        } else if (FaceLeft){
            RockShield = new Point(RockShield.x - 1, RockShield.y);
        } else if (FaceRight) {
            RockShield = new Point(RockShield.x + 1, RockShield.y);
        } else {
            RockShield = new Point(RockShield.x, RockShield.y + 1);
        }

        return RockShield;
    }

    //    public Point moveThrowRock (){
//        Board.BackToEmpty(RockShield);
//
//        if (FaceUp){
//            return FlyUp();
//        } else if (FaceLeft){
//            return FlyLeft();
//        } else if (FaceRight) {
//            return FlyRight();
//        } else {
//            return FlyDown();
//        }
//
//    }

//    public Point FlyUp(){
//        Point flyingUp = new Point(RockShield.x, RockShield.y - 1);
//        RockShield = flyingUp;
//        return RockShield;
//    }
//
//    public Point FlyLeft(){
//        Point flyingLeft = new Point(RockShield.x - 1, RockShield.y);
//        RockShield = flyingLeft;
//        return RockShield;
//    }
//    public Point FlyRight(){
//        Point flyingRight = new Point(RockShield.x + 1, RockShield.y);
//        RockShield = flyingRight;
//        return RockShield;
//    }
//
//    public Point FlyDown(){
//        Point flyingDown = new Point(RockShield.x, RockShield.y + 1);
//        RockShield = flyingDown;
//        return RockShield;
//    }


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




