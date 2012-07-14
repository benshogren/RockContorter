package RockContorter;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class Monsters extends Enemies{

int updateCount = 0;

    public Monsters(Point here) {
        this.here = here;
    }

    @Override
    public void Update(Board board, Point PlayerPosition) {

        updateCount++;
        if  (updateCount == 4) {
            updateCount = 0;
            if (moveToSpecificPoint(board, PlayerPosition)) return;
//            // back-up random moves for stuck monsters
            randomMove(board);

        }
    }


}

