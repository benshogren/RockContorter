package PacMan;


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
                    game.Move(Game.PacmanMove.UP);
                }
                else if (s.toLowerCase().equals("a")) {
                    game.Move(Game.PacmanMove.LEFT);
                }
                else if (s.toLowerCase().equals("s")) {
                    game.Move(Game.PacmanMove.DOWN);
                }
                else if (s.toLowerCase().equals("d")) {
                    game.Move(Game.PacmanMove.RIGHT);
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
            boardString[i] = "               ";
        }
        for (Map.Entry<Point, BoardState> position : game.Board.BoardGrid.entrySet()) {
            String character = "";
            if(position.getKey().equals(game.PacManPosition)){
                character = "C";
            } else if (position.getKey().equals(game.MonsterPositions.get(0))) {
                character = "M";
            } else if (position.getKey().equals(game.MonsterPositions.get(1)))   {
                character = "M";
            } else if (position.getValue() == BoardState.WALL) {
                character = "#";
            } else if (position.getValue() == BoardState.PILL) {
                character = "*";
            } else if (position.getValue() == BoardState.TELEPORT) {
                character = "O";
            } else if (position.getValue() == BoardState.POWERUP){
                character = "!";
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
