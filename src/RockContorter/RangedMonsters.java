package RockContorter;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class RangedMonsters extends Enemies{
    public enum Type{
        YAxis,
        XAxis
    }

    Type AxisType;

    int updateCount = 0;
    int throwCount = 0;

    public Point getPoint() {
        return here;
    }
    public RangedMonsters(Point here, Type type) {
        this.here = here;
        this.AxisType = type;
    }

    @Override
    public void Update(Board board, Point PlayerPosition) {
        updateCount++;
        if  (updateCount == 2) {
            updateCount = 0;
            if( AxisType == Type.YAxis) {
                if (here.y >= PlayerPosition.y + 5 || (here.y >= PlayerPosition.y && here.x != PlayerPosition.x)) {
                    if (moveToSpecificPoint(board, new Point(PlayerPosition.x, PlayerPosition.y + 5))){ return; }
                }
                if (here.y < PlayerPosition.y - 5 || (here.y < PlayerPosition.y && here.x != PlayerPosition.x)) {
                    if (moveToSpecificPoint(board, new Point(PlayerPosition.x, PlayerPosition.y - 5))){ return; }
                }
            } else if (AxisType == Type.XAxis) {
                if (here.x >= PlayerPosition.x + 8 || (here.x >= PlayerPosition.x && here.y != PlayerPosition.y)) {
                    if (moveToSpecificPoint(board, new Point(PlayerPosition.x+8, PlayerPosition.y))){ return; }
                }
                if (here.x < PlayerPosition.x - 8 || (here.x < PlayerPosition.x && here.y != PlayerPosition.y)) {
                    if (moveToSpecificPoint(board, new Point(PlayerPosition.x-8, PlayerPosition.y))){ return; }
                }
            }
        }
        throwAtPlayer(board, PlayerPosition);
    }

    private void throwAtPlayer(Board board, Point PlayerPosition) {
        throwCount  = throwCount + 1;
        if  (throwCount == 4) {
            throwCount = 0;
            if((PlayerPosition.x > here.x) && (PlayerPosition.y == here.y)){
                board.BoardGrid.put(new Point(here.x + 2, here.y), new FlyingRock(new Point(here.x + 2, here.y), Game.Direction.RIGHT));
            } else if((PlayerPosition.x < here.x) && (PlayerPosition.y == here.y)){
                board.BoardGrid.put(new Point(here.x - 2, here.y), new FlyingRock(new Point(here.x - 2, here.y), Game.Direction.LEFT));
            } else if((PlayerPosition.x == here.x) && (PlayerPosition.y > here.y)){
                board.BoardGrid.put(new Point(here.x, here.y + 2), new FlyingRock(new Point(here.x, here.y + 2), Game.Direction.DOWN));
            } else if((PlayerPosition.x == here.x) && (PlayerPosition.y < here.y)){
                board.BoardGrid.put(new Point(here.x, here.y - 2), new FlyingRock(new Point(here.x, here.y - 2), Game.Direction.UP));
            } else {
                return;
            }
        }
    }
}

