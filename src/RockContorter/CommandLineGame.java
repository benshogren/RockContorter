package RockContorter;


import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class CommandLineGame {

    public void play() {
        Board board = new Board();
        Game game = new Game(board);
        System.out.println(present(game));
        while (!game.GameOver()) {
            try {
                int tmp = System.in.read();
                char line = (char) tmp;
                String s = String.valueOf(line);
                if (s.toLowerCase().equals("w")) {
                    game.Move(Game.Direction.UP);
                }
                else if (s.toLowerCase().equals("a")) {
                    game.Move(Game.Direction.LEFT);
                }
                else if (s.toLowerCase().equals("s")) {
                    game.Move(Game.Direction.DOWN);
                }
                else if (s.toLowerCase().equals("d")) {
                    game.Move(Game.Direction.RIGHT);
                }
                System.out.println(present(game));
            }
            catch (IOException e) {}
        }
    }
    public static String present(Game game) {
        String boardText = ConvertBoardToString(game);
        if (game.GameOver()) {
            return ConvertBoardToString(game) + "GAME OVER YOU LOST!!!!1!";
        }
        if (game.Victory){
            return "YOU WON!!1";
        }
        return boardText;
    }

    public static String ConvertBoardToString(Game game) {
        String[] boardString = new String[game.Board.BOARD_SIZE];
        for (int i = 0; i < game.Board.BOARD_SIZE; i++) {
            boardString[i] = "                                                         ";
        }

        for (Map.Entry<Point, BoardPiece> position : game.Board.BoardGrid.entrySet()) {
            String character = "";

            if((position.getKey().equals(game.PlayerPosition)) && (game.playerDirection == Game.Direction.LEFT)){
                character = "<";
            } else if((position.getKey().equals(game.PlayerPosition)) && (game.playerDirection == Game.Direction.UP)){
                character = "^";
            } else if((position.getKey().equals(game.PlayerPosition)) && (game.playerDirection == Game.Direction.DOWN)){
                character = "v";
            } else if((position.getKey().equals(game.PlayerPosition)) && (game.playerDirection == Game.Direction.RIGHT)){
                character = ">";
//            } else if(position.getKey().equals(game.PlayerPosition)){
//                character = "^";
            } else if (position.getValue() instanceof Wall) {
                character = "#";
            } else if (position.getValue() instanceof Flying_Rock) {
                character = "O";
            } else if (position.getValue() instanceof  Static_Rock) {
                character = "O";
            } else if (position.getValue() instanceof  Empty) {
                character = " ";
            } else if (position.getValue() instanceof  Dying_Rock) {
                character = "O";
            } else {
                character = " ";
            }
            int boardIndex = (int) position.getKey().getY();
            StringBuilder sb = new StringBuilder(boardString[boardIndex]);
            sb.setCharAt(position.getKey().x, character.toCharArray()[0]);
            boardString[boardIndex] = sb.toString();
        }

        StringBuilder sb = new StringBuilder();
        for (String s : boardString) {
            sb.append(s + "\n");
        }
        sb.append(game.moveCount);
        return sb.toString();
    }
}
