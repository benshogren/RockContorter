package RockContorter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Board {
    public HashMap<Point, BoardPiece> BoardGrid;
    public HashMap<Point, Integer> StaticRockList = new HashMap<Point, Integer>();

    public int BOARD_SIZE = 40;

    public Board(HashMap<Point, BoardPiece> map) {
        BoardGrid = map;
        SaveAllStaticRockPositions();
    }

    //This is the constructer for the Board
    public Board() {
        BoardGrid = new HashMap<Point, BoardPiece>();
        MapConverter mapConverter = new MapConverter();
        mapConverter.convertToMap("888888888888888888888888888888888", BoardGrid);
        mapConverter.convertToMap("888888888888888888888888888888888", BoardGrid);
        mapConverter.convertToMap("888888888888888888888888888888888", BoardGrid);
        mapConverter.convertToMap("888                           888", BoardGrid);
        mapConverter.convertToMap("888                     OO    888", BoardGrid);
        mapConverter.convertToMap("888                     OOO   888", BoardGrid);
        mapConverter.convertToMap("888                    OOOO   888", BoardGrid);
        mapConverter.convertToMap("888                    OO     888", BoardGrid);
        mapConverter.convertToMap("888                           888", BoardGrid);
        mapConverter.convertToMap("888                      O    888", BoardGrid);
        mapConverter.convertToMap("888                    OO     888", BoardGrid);
        mapConverter.convertToMap("888                           888", BoardGrid);
        mapConverter.convertToMap("888                           888", BoardGrid);
        mapConverter.convertToMap("888                           888", BoardGrid);
        mapConverter.convertToMap("888                           888", BoardGrid);
        mapConverter.convertToMap("888                           888", BoardGrid);
        mapConverter.convertToMap("888                           888", BoardGrid);
        mapConverter.convertToMap("888888888888888888888888888888888", BoardGrid);
        mapConverter.convertToMap("888888888888888888888888888888888", BoardGrid);
        mapConverter.convertToMap("888888888888888888888888888888888", BoardGrid);
        SaveAllStaticRockPositions();
    }

    public static BoardPiece ConvertToBoardState(char pBoardCharacter) {
        BoardPiece piece;
        switch (pBoardCharacter) {
            case '8':
                piece = new Wall();
                break;
            case 'O':
                piece = new StaticRock();
                break;
            default:
                piece = new EmptyPiece();
                break;
        }
        return piece;
    }

    public void SaveAllStaticRockPositions(){
        for (Map.Entry<Point, BoardPiece> position : BoardGrid.entrySet()) {
            if (position.getValue() instanceof StaticRock){
                StaticRockList.put(position.getKey(), 0);
            }
        }
    }

    public BoardPiece GetState(Point pNewPosition) {
        return BoardGrid.get(pNewPosition);
    }

    public void RockAsBoardState(Point position) {
        BoardGrid.put(position, new DisappearingRock(position));
    }

    public void BackToEmpty(Point position) {
        BoardGrid.put(position, new EmptyPiece());
    }

    public boolean WallInTheWay(Point position) {
        return (GetState(position) instanceof Wall);
    }

    public boolean StaticRockInTheWay(Point position) {
        return (GetState(position) instanceof StaticRock);
    }

    public boolean PointIsEmpty(Point position) {
        return (GetState(position) instanceof EmptyPiece);
    }

    public boolean DisappearingRockInTheWay(Point position) {
        return (GetState(position) instanceof DisappearingRock);
    }

    public void Update() {
        for (Map.Entry<Point, Integer> position : StaticRockList.entrySet()) {
            if (! (BoardGrid.get(position.getKey())instanceof StaticRock)) {
                int halfLifeCounter = position.getValue() + 1;
                StaticRockList.put(position.getKey(),halfLifeCounter);
                if (halfLifeCounter == 50){
                    BoardGrid.put(position.getKey(), new StaticRock());
                    StaticRockList.put(position.getKey(), 0);
                }
            }
        }
    }
}
