package PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class PacManGameStarter {

    public static void main(String[] args) {
        WindowGame c =  new WindowGame();
//        CommandLineGame c = new CommandLineGame();

        c.play();
    }




}
