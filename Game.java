
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
This serves as an entry point for interacting with the Gomoku
*/

public class Game extends JPanel {
    private Gomoku myGomoku;
    private JButton undoButton;
    private JButton restartButton;
    private JButton saveButton;
    private JButton loadButton;
    private JRadioButton humanhuman;
    private JRadioButton humancomputer;
    private ButtonGroup modeGroup;
    private JLabel message;
    private boolean mode;  // Black = false, White = true
    
    public static void main (String[] args) {
        JFrame window = new JFrame("Five in a Row");
        Dimension monitorsize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(monitorsize.width/3, monitorsize.height/4 );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable(true);
        window.setVisible(true);
        Game content = new Game(monitorsize);
        window.setContentPane(content);
        window.pack();
    }
    
    public Game(Dimension monitorsize){
        mode = false;
        myGomoku = new Gomoku();
        setLayout(null);
        setPreferredSize(new Dimension(monitorsize.width/2,2*monitorsize.height/3) );
        setBackground(new Color(150,140,200));
        
        
        Board board = new Board();  // Note: The constructor for the
        //   board also creates the buttons
        //   and label.
        
        add(board);
        add(undoButton);
        add(restartButton);
        add(loadButton);
        add(saveButton);
        add(message);
        add(humanhuman);
        add(humancomputer);
        
        // Radio buttons share a modeGroup
        modeGroup.add(humanhuman);
        modeGroup.add(humancomputer);
        
        /* Set the position and size of each component by calling
        its setBounds() method.
        */
        board.setBounds(0,0,450,450);
        undoButton.setBounds(560, 380, 100, 30);
        restartButton.setBounds(560, 440, 100, 30);
        loadButton.setBounds(560, 480, 100, 30);
        saveButton.setBounds(560, 540, 100, 30);
        humanhuman.setBounds(300, 500, 200, 30);
        humancomputer.setBounds(300, 540, 200, 30);
        message.setBounds(460, 200, 350, 30);
    }
    private class Board extends JPanel implements ActionListener, MouseListener {
        Board() {
            
            setBackground(Color.BLACK);
            addMouseListener(this);
            undoButton = new JButton("Undo");
            restartButton = new JButton("Restart");
            saveButton = new JButton("Save");
            loadButton = new JButton("Load");
            humanhuman = new JRadioButton("Human vs. Human");
            humancomputer = new JRadioButton("Human vs. Computer");
            modeGroup = new ButtonGroup();
            message = new JLabel("",JLabel.LEFT);
            restartButton.addActionListener(this);
            undoButton.addActionListener(this);
            saveButton.addActionListener(this);
            loadButton.addActionListener(this);
            humanhuman.addActionListener(this);
            humancomputer.addActionListener(this);
            message.setFont(new  Font("Serif", Font.BOLD, 14));
            message.setForeground(Color.green);
        }
        public void actionPerformed(ActionEvent evt) {
            Object src = evt.getSource();
            if (src == undoButton) {
                myGomoku.undo();
                message.setText("");
                repaint();
            }
            if (src == saveButton) {
                myGomoku.save();
            }
            if (src == loadButton) {
                myGomoku.load();
                message.setText("");
                repaint();
            }
            if (src == restartButton) {
                myGomoku = new Gomoku();
                if (mode==true) {
                    if((int)(Math.random()+.5) == 0) {
                        Coordinate C = new Coordinate();
                        C.x=7;
                        C.y=7;
                        myGomoku.go(C);
                    }
                }
                message.setText("");
                repaint();
            }
            if (src == humanhuman) {
                System.out.println("hh");
                mode=false;
            }
            if (src == humancomputer) {
                System.out.println("humancom");
                if (mode==false) {
                    if((int)(Math.random()+.5) == 0) {
                        Coordinate C = new Coordinate();
                        C.x=7;
                        C.y=7;
                        myGomoku.go(C);
                        repaint();
                    }
                }
                mode=true;
            }
        }
        public void mouseReleased(MouseEvent evt) { }
        public void mouseClicked(MouseEvent evt) { }
        public void mouseEntered(MouseEvent evt) { }
        public void mouseExited(MouseEvent evt) { }
        public void mousePressed(MouseEvent evt) {
            
            int col = (evt.getY() - 2) / 30;
            int row = (evt.getX() - 2) / 30;
            if (col >= 0 && col < 15 && row >= 0 && row < 15) {
                doClickSquare(row,col);
            }
        }
        public void doClickSquare(int row, int col) {
            Coordinate C = new Coordinate();
            C.x = row;
            C.y = col;
            myGomoku.go(C);
            repaint();
            if(mode==true) {
                myGomoku.go(pickCentered(C.x,C.y));
            }
            repaint();
            if(myGomoku.over == true) {
                if(myGomoku.turn == true)
                message.setText("Game is over, black wins");
                else message.setText("Game is over, white wins");
                }
        }

        /* Rudimentary computer pseudo AI that simply goes in a random location nearby */
        public Coordinate pickCentered(int x, int y) {
            Coordinate C = new Coordinate();
            C.x = (int) (Math.random()*3) -1 + x;
            C.y = (int) (Math.random()*3) -1 + y;
            if((C.x <0 || C.x>14) || (C.y <0 || C.y>14)) return pickRandom();
            if (myGomoku.gamegraph[C.x][C.y][myGomoku.index]==0)return C;
            return pickCentered(C.x,C.y);
        }
        public Coordinate pickRandom() {
            Coordinate C = new Coordinate();
            C.x = (int) (Math.random()*15);
            C.y = (int) (Math.random()*15);
            if (myGomoku.gamegraph[C.x][C.y][myGomoku.index]==0)return C;
            else return pickRandom();
            }
        public void paintComponent(Graphics g) {
            /* Draw the checker pattern and populate it with the pieces. */
            
            g.setColor(Color.black);
            
            for (int row = 0; row < 15; row++) {
                for (int col = 0; col < 15; col++) {
                    if ( row % 2 == col % 2 )
                        g.setColor(Color.LIGHT_GRAY);
                    else
                        g.setColor(Color.GRAY);
                    g.fillRect(col*30,row*30, 30, 30);
                    if (myGomoku.index != 0) {
                        switch (myGomoku.gamegraph[col][row][myGomoku.index-1]) {
                            case 1:
                            g.setColor(Color.BLACK);
                            g.fillOval(2 + col*30, 2 + row*30, 26, 26);
                            break;
                            case 2:
                            g.setColor(Color.WHITE);
                            g.fillOval(2 + col*30, 2 + row*30, 26, 26);
                            break;
                        }
                    }
                }
            }   
        }   
    }
}
