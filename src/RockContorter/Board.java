package RockContorter;

import java.awt.*;
import java.util.HashMap;

public class Board {
    public HashMap<Point, BoardPiece> BoardGrid;
    public int BOARD_SIZE = 40;



    //This is the constructer for the Board
    public Board() {
        BoardGrid = new HashMap<Point, BoardPiece>();


        convertToMap("888888888888888888888888888888888");
        convertToMap("888888888888888888888888888888888");
        convertToMap("888888888888888888888888888888888");
        convertToMap("888                           888");
        convertToMap("888                     OO    888");
        convertToMap("888                     OOO   888");
        convertToMap("888                    OOOO   888");
        convertToMap("888                    OO     888");
        convertToMap("888                           888");
        convertToMap("888                      O    888");
        convertToMap("888                    OO     888");
        convertToMap("888                           888");
        convertToMap("888                           888");
        convertToMap("888                           888");
        convertToMap("888                           888");
        convertToMap("888                           888");
        convertToMap("888                           888");
        convertToMap("888888888888888888888888888888888");
        convertToMap("888888888888888888888888888888888");
        convertToMap("888888888888888888888888888888888");

    }

    private int YAxis = 0;

    private void convertToMap(String pMapString) {
        for (int XAxis = 0; XAxis < pMapString.length(); XAxis++) {
            Point point = new Point(XAxis, YAxis);
            BoardPiece piece = ConvertToBoardState(pMapString.charAt(XAxis));
            BoardGrid.put(point, piece);
        }
        YAxis++;
    }

    private BoardPiece ConvertToBoardState(char pBoardCharacter) {
        BoardPiece piece;
        switch (pBoardCharacter) {
            case '8':
                piece = new Wall();
                break;
            case 'O':
                piece = new StaticRock();
                break;
            default:
                piece = new Empty();
                break;
        }
        return piece;
    }

    public BoardPiece GetState(Point pNewPosition) {
        return BoardGrid.get(pNewPosition);
    }

    public void RockAsBoardState(Point position) {
        BoardGrid.put(position, new DisappearingRock(position));
    }

    public void BackToEmpty(Point position) {
        BoardGrid.put(position, new Empty());
    }


    public boolean WallInTheWay(Point position) {
        return (GetState(position) instanceof Wall);
    }

    public boolean StaticRockInTheWay(Point position) {
        return (GetState(position) instanceof StaticRock);
    }

    public boolean DisappearingRockInTheWay(Point position) {
        return (GetState(position) instanceof DisappearingRock);
    }

}
