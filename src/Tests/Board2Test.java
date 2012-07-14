package Tests;

import RockContorter.*;
import junit.framework.TestCase;

import java.awt.*;
import java.util.HashMap;

public class Board2Test extends TestCase {

    private Board testBoard;

    public void testItsPossibleToPassInADifferentBoard() {
        // setup
        HashMap<Point, BoardPiece> expected = new HashMap<Point, BoardPiece>();
        //sut
        testBoard = new Board(expected);
        //asserts
        assertEquals("The passed in board grid was not used", expected,testBoard.BoardGrid);
    }

    public void testStaticRocksAreReplacedAfterXTurns() {
        // setup
        HashMap<Point, BoardPiece> expected = new HashMap<Point, BoardPiece>();
        MapConverter mapConverter = new MapConverter();
        mapConverter.convertToMap("88888", expected);
        mapConverter.convertToMap("8O  8", expected);
        mapConverter.convertToMap("8   8", expected);
        mapConverter.convertToMap("8   8", expected);
        mapConverter.convertToMap("88888", expected);
        testBoard = new Board(expected);
        assertTrue("BoardConstructor did not correctly set the StaticRock ",
                testBoard.BoardGrid.get(new Point(1, 1)) instanceof StaticRock);

        //sut
        testBoard.BoardGrid.put(new Point(1, 1), new EmptyPiece());
        for (int i = 0; i < 16; i++) {
            testBoard.Update();
        }

        //asserts
        assertTrue("BoardUpdate did not correctly replace StaticRock",
                testBoard.BoardGrid.get(new Point(1, 1)) instanceof StaticRock);

    }
}
