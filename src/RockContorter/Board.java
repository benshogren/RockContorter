package RockContorter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Board {
    public HashMap<Point, BoardState> BoardGrid;
    public int BOARD_SIZE = 40;



    //This is the constructer for the Board
    public Board() {
        BoardGrid = new HashMap<Point, BoardState>();


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
            BoardState state = ConvertToBoardState(pMapString.charAt(XAxis));
            BoardGrid.put(point, state);
        }
        YAxis++;
    }

    private BoardState ConvertToBoardState(char pBoardCharacter) {
        BoardState state;
        switch (pBoardCharacter) {
            case '8':
                state = BoardState.WALL;
                break;
            case 'O':
                state = BoardState.ROCK;
                break;
            default:
                state = BoardState.EMPTY;
                break;
        }
        return state;
    }

    public BoardState GetState(Point pNewPosition) {
        return BoardGrid.get(pNewPosition);
    }

    public void RockAsBoardState(Point position) {
        BoardGrid.put(position, BoardState.ROCK);
    }

    public void BackToEmpty(Point RockShield) {
        BoardGrid.put(RockShield, BoardState.EMPTY);
    }
}
