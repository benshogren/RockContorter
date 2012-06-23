package RockContorter;

import java.awt.*;

public class Game {
    public Board Board;
    public Point PlayerPosition;
    public boolean mGameOver;
    public boolean Victory = false;
    public int moveCount = 0;


    public boolean FaceRight = false;
    public boolean FaceLeft = false;
    public boolean FaceUp = false;
    public boolean FaceDown = false;

    public Game(Board pBoard) {
        Board = pBoard;
        PlayerPosition = new Point(1, 1);

    }

    public enum Move {
        MOVELeft,
        MOVERight,
        MOVEUp,
        MoveDown
    }
//
//    public enum Direction {
//        FACELeft,
//        FACERight,
//        FACEUp,
//        FACEDown
//    }

    public void Update() {


    }

    public void Move (Move pMove) {
        Point newPosition = ConvertMoveToCoordinates(pMove);
        BoardState state = Board.GetState(newPosition);
        if (mGameOver) {
            return;
        }


        if (newPosition.y < PlayerPosition.y) {
            FaceRight = false;
            FaceUp = true;
        }

        else if (newPosition.x < PlayerPosition.x){

            FaceRight = false;
            FaceLeft = true;
        }
        else if (newPosition.y > PlayerPosition.y){

            FaceRight = false;
            FaceDown = true;
        }
        else if (newPosition.x > PlayerPosition.x) {

            FaceRight = true;
        }
        if (state == BoardState.WALL) {
            // dont move him
        } else {
            PlayerPosition = newPosition;
        }
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




