package RockContorter;

import java.awt.*;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: ben
 * Date: 7/14/12
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class MapConverter {

    private int YAxis = 0;

    public void convertToMap(String pMapString, HashMap<Point, BoardPiece> map) {
        for (int XAxis = 0; XAxis < pMapString.length(); XAxis++) {
            Point point = new Point(XAxis, YAxis);
            BoardPiece piece = Board.ConvertToBoardState(pMapString.charAt(XAxis));
            map.put(point, piece);
        }
        YAxis++;
    }
}
