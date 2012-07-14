package Tests;

import RockContorter.*;
import junit.framework.TestCase;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RangedMonsterTest extends TestCase {

    private Board testBoard;

    //"8888888"
    //"8M   P8"
    //"8     8"
    //"8     8"
    //"8888888"
    public void testMonsterShouldMoveCloserIfOnWrongAxisToRight() {
        setupBoard();
        Point monsterPoint = new Point(1, 1);
        RangedMonsters monster = new RangedMonsters(monsterPoint, RangedMonsters.Type.YAxis);
        testBoard.BoardGrid.put(monsterPoint, monster);

        Point playerPosition = new Point(5, 1);
        updateMonsters(playerPosition);

        assertTrue("monster moved to: " + monster.getPoint().toString(),
                monster.getPoint().equals(new Point(1, 2)));
    }

    //"8888888"
    //"8P M  8"
    //"8     8"
    //"8     8"
    //"8888888"
    public void testMonsterShouldMoveCloserIfOnWrongAxis() {
        setupBoard();
        Point monsterPoint = new Point(3, 1);
        RangedMonsters monster = new RangedMonsters(monsterPoint, RangedMonsters.Type.YAxis);
        testBoard.BoardGrid.put(monsterPoint, monster);

        Point playerPosition = new Point(1, 1);
        updateMonsters(playerPosition);

        assertTrue("monster moved to: " + monster.getPoint().toString(),
                monster.getPoint().equals(new Point(3, 2)));
    }

    //"8888888"
    //"8  M  8"
    //"8     8"
    //"8 P   8"
    //"8888888"
    public void testMonsterShouldMoveToPlayerLeftAndBelow() {
        setupBoard();
        Point monsterPoint = new Point(3, 1);
        RangedMonsters monster = new RangedMonsters(monsterPoint, RangedMonsters.Type.YAxis);
        testBoard.BoardGrid.put(monsterPoint, monster);

        Point playerPosition = new Point(2, 3);
        updateMonsters(playerPosition);

        assertTrue("monster moved to: " + monster.getPoint().toString(),
                monster.getPoint().equals(new Point(2, 1)));
    }

    //"8888888"
    //"8  M  8"
    //"8     8"
    //"8   P 8"
    //"8888888"
    public void testMonsterShouldMoveToPlayerRightAndBelow() {
        setupBoard();
        Point monsterPoint = new Point(3, 1);
        RangedMonsters monster = new RangedMonsters(monsterPoint, RangedMonsters.Type.YAxis);
        testBoard.BoardGrid.put(monsterPoint, monster);

        Point playerPosition = new Point(4, 3);
        updateMonsters(playerPosition);

        assertTrue("monster moved to: " + monster.getPoint().toString(),
                monster.getPoint().equals(new Point(4, 1)));
    }

    //"8888888"
    //"8  M  8"
    //"8     8"
    //"8  P  8"
    //"8888888"
    public void testMonsterShouldNotMoveToPlayerRightBelow() {
        setupBoard();
        Point monsterPoint = new Point(3, 1);
        RangedMonsters monster = new RangedMonsters(monsterPoint, RangedMonsters.Type.YAxis);
        testBoard.BoardGrid.put(monsterPoint, monster);

        Point playerPosition = new Point(3, 3);
        updateMonsters(playerPosition);

        assertTrue("monster moved to: " + monster.getPoint().toString(),
                monster.getPoint().equals(new Point(3, 1)));
    }

    //"8888888"
    //"8    P8"
    //"8     8"
    //"8  M  8"
    //"8888888"
    public void testMonsterShouldMoveToPlayerToTheRightAndUbove() {
        setupBoard();
        Point monsterPoint = new Point(3, 3);
        RangedMonsters monster = new RangedMonsters(monsterPoint, RangedMonsters.Type.YAxis);
        testBoard.BoardGrid.put(monsterPoint, monster);

        Point playerPosition = new Point(5, 1);
        updateMonsters(playerPosition);

        assertTrue("monster moved to: " + monster.getPoint().toString(),
                monster.getPoint().equals(new Point(4, 3)));
    }

    //"8888888"
    //"8   P 8"
    //"8     8"
    //"8  M  8"
    //"8888888"
    public void testMonsterShouldMoveIfPlayerToTheRightAndUbove() {
        setupBoard();
        Point monsterPoint = new Point(3, 3);
        RangedMonsters monster = new RangedMonsters(monsterPoint, RangedMonsters.Type.YAxis);
        testBoard.BoardGrid.put(monsterPoint, monster);

        Point playerPosition = new Point(4, 1);
        updateMonsters(playerPosition);

        assertTrue("monster moved to: " + monster.getPoint().toString(),
                monster.getPoint().equals(new Point(4, 3)));
    }

    //"8888888"
    //"8  P  8"
    //"8     8"
    //"8  M  8"
    //"8888888"
    public void testMonsterShouldNotMoveIfPlayerRightAbove() {
        setupBoard();
        Point monsterPoint = new Point(3, 3);
        RangedMonsters monster = new RangedMonsters(monsterPoint, RangedMonsters.Type.YAxis);
        testBoard.BoardGrid.put(monsterPoint, monster);

        Point playerPosition = new Point(3, 1);
        updateMonsters(playerPosition);

        assertTrue(monster.getPoint().equals(monsterPoint));
    }

    private void updateMonsters(Point playerPosition) {
        ArrayList<BoardPiece> test = new ArrayList<BoardPiece>();
        for (BoardPiece point : testBoard.BoardGrid.values()) {
            test.add(point);
        }
        for (BoardPiece point : test) {
            point.Update(testBoard, playerPosition);
        }
        for (BoardPiece point : test) {
            point.Update(testBoard, playerPosition);
        }
    }

    private void setupBoard() {
        HashMap<Point, BoardPiece> expected = new HashMap<Point, BoardPiece>();
        MapConverter mapConverter = new MapConverter();
        mapConverter.convertToMap("8888888", expected);
        mapConverter.convertToMap("8     8", expected);
        mapConverter.convertToMap("8     8", expected);
        mapConverter.convertToMap("8     8", expected);
        mapConverter.convertToMap("8888888", expected);
        testBoard = new Board(expected);
    }
}

