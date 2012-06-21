package PacMan;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;

public class WindowGame implements ActionListener, KeyListener {

    // Definition of global values and items that are part of the GUI.
    int redScoreAmount = 0;
    int blueScoreAmount = 0;

    JPanel titlePanel, scorePanel, buttonPanel;
    JTextArea gameTextAre;
    JButton leftButton, rightButton, upButton, downButton;
    public static Game game;
    private Timer clock;

    public WindowGame() {
        Board board = new Board();
        game = new Game(board);
    }

    public JPanel createContentPane (){

        // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel();
        totalGUI.setLayout(null);
        totalGUI.addKeyListener(this);

        // Creation of a Panel to contain the title labels
        titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setLocation(10, 0);
        titlePanel.setSize(300, 300);
        titlePanel.addKeyListener(this);
        totalGUI.add(titlePanel);


        gameTextAre = new JTextArea();
        gameTextAre.setText(CommandLineGame.present(game));
        gameTextAre.setLocation(0, 0);
        gameTextAre.setSize(500, 500);
        gameTextAre.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gameTextAre.setForeground(Color.black);
//        gameTextAre.setEnabled(false);
        gameTextAre.addKeyListener(this);
        titlePanel.add(gameTextAre);


        // Creation of a Panel to contain the score labels.
        scorePanel = new JPanel();
        scorePanel.setLayout(null);
        scorePanel.setLocation(10, 40);
        scorePanel.setSize(260, 30);
        scorePanel.addKeyListener(this);
        totalGUI.add(scorePanel);



        // Creation of a Panel to contain all the JButtons.
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setLocation(0, 350);
        buttonPanel.setSize(260, 160);
        buttonPanel.addKeyListener(this);
        totalGUI.add(buttonPanel);

        // We create a button and manipulate it using the syntax we have
        // used before. Now each button has an ActionListener which posts
        // its action out when the button is pressed.
        leftButton = new JButton("Left");
        leftButton.setLocation(30,80);
        leftButton.setSize(70, 70);
        leftButton.addActionListener(this);
        leftButton.addKeyListener(this);
        buttonPanel.add(leftButton);

        downButton = new JButton("Down");
        downButton.setLocation(110, 80);
        downButton.setSize(70, 70);
        downButton.addActionListener(this);
        downButton.addKeyListener(this);
        buttonPanel.add(downButton);

        rightButton = new JButton("Right");
        rightButton.setLocation(190, 80);
        rightButton.setSize(70, 70);
        rightButton.addActionListener(this);
        rightButton.addKeyListener(this);
        buttonPanel.add(rightButton);

        upButton = new JButton("Up");
        upButton.setLocation(110, 0);
        upButton.setSize(70, 70);
        upButton.addActionListener(this);
        upButton.addKeyListener(this);
        buttonPanel.add(upButton);

        totalGUI.setOpaque(true);
        return totalGUI;
    }

    // This is the new ActionPerformed Method.
    // It catches any events with an ActionListener attached.
    // Using an if statement, we can determine which button was pressed
    // and change the appropriate values in our GUI.
    public void actionPerformed(ActionEvent e) {
        if((e.getSource() == leftButton)){
            game.Move(Game.PacmanMove.LEFT);
        }else if(e.getSource() == rightButton){
            game.Move(Game.PacmanMove.RIGHT);
        }else if(e.getSource() == upButton){
            game.Move(Game.PacmanMove.UP);
        } else if(e.getSource() == downButton) {
            game.Move(Game.PacmanMove.DOWN);
        } else if (e.getSource() == clock ) {
            WindowGame.game.Update();
            updateUI();
            return;
        }
        WindowGame.game.Update();
        updateUI();
    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("[=] JButton Scores! [=]");

        //Create and set up the content pane.
        WindowGame demo = new WindowGame();
        frame.setContentPane(demo.createContentPane());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setVisible(true);
        frame.addKeyListener(demo);

        demo.clock = new Timer(1000, demo);
        demo.clock.start();
    }

    private void updateUI() {
        if(gameTextAre!=null)
            gameTextAre.setText(CommandLineGame.present(game));
    }

    public void play(){
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                game.Move(Game.PacmanMove.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                game.Move(Game.PacmanMove.DOWN);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                game.Move(Game.PacmanMove.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                game.Move(Game.PacmanMove.LEFT);
                break;
        }
        updateUI();
    }
}
