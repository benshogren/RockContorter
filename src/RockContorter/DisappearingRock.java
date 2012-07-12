package RockContorter;

import java.awt.*;

public class DisappearingRock implements BoardPiece {

    Point here;
    int lifeSpan = 0;


    public DisappearingRock(Point here) {
        this.here = here;
    }

    @Override
    public void Update(Board board, Point PlayerPosition) {
        if (here.equals(new Point(PlayerPosition.x, PlayerPosition.y + 1))){
            return;
            // test
        } else if (here.equals(new Point(PlayerPosition.x, PlayerPosition.y - 1))){
            return;
        } else if (here.equals(new Point(PlayerPosition.x + 1, PlayerPosition.y))) {
            return;
        } else if (here.equals(new Point(PlayerPosition.x - 1, PlayerPosition.y))){
            return;
        } else {
            lifeSpan = lifeSpan + 1;

            if (lifeSpan == 15){
                board.BoardGrid.put(here, new Empty());
            }
        }
    }
}
