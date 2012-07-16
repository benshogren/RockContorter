package RockContorter;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class RangedMonsters extends Enemies{
    public enum Type{
        YAxis,
        XAxis
    }

    int updateCount = 0;
    int throwCount = 0;

    public Point getPoint() {
        return here;
    }
    Type AxisType;
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
            } else {
                randomMove(board);
            }
        }
        throwCount  = throwCount + 1;
        if  (throwCount == 8) {
            throwCount = 0;
        throwAtPlayer(board, PlayerPosition);
        }
    }
}

