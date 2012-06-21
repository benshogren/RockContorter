package PacMan;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    public HashMap<Point, BoardState> BoardGrid;
    public int BOARD_SIZE = 15;



    //This is the constructer for the Board
    public Board() {
        BoardGrid = new HashMap<Point, BoardState>();

//        convertToMap("######OOO######");
        convertToMap("######OOO######");
        convertToMap("#.............#");
        convertToMap("#..##..#......#");
        convertToMap("#....!#.......#");
        convertToMap("#...........#.#");
        convertToMap("#..##.........#");
        convertToMap("#.###.......#.#");
        convertToMap("#.........##..#");
        convertToMap("#.............#");
        convertToMap("######OOO######");
//        convertToMap("######OOO######");
    }

    public boolean AllPillsCollected() {
        for (Map.Entry<Point, BoardState> entry : BoardGrid.entrySet()) {
            BoardState value = entry.getValue();
            if (value == BoardState.PILL) {
                return false;
            }
        }
        return true;
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
            case '#':
                state = BoardState.WALL;
                break;
            case '.':
                state = BoardState.PILL;
                break;
            case 'O':
                state = BoardState.TELEPORT;
                break;
            case '!':
                state = BoardState.POWERUP;
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

    public void RemovePill(Point pNewPosition) {
        BoardGrid.put(pNewPosition, BoardState.EMPTY);
    }
}
