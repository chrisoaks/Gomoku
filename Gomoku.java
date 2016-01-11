import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
	This serves as an entry point for interacting with the Game
*/

public class Gomoku extends JPanel {
	
  private Game myGame;
  private JButton undoButton;  
  private JButton restartButton;
  private JRadioButton humanhuman;
  private JRadioButton humancomputer;
  private ButtonGroup modeGroup;
  private JLabel message;
  private boolean mode;

	public static void main (String[] args) {
    JFrame window = new JFrame("Five in a Row");
    Dimension monitorsize = Toolkit.getDefaultToolkit().getScreenSize();
    window.setLocation(monitorsize.width/3, monitorsize.height/4 );
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    window.setResizable(true);  
    window.setVisible(true);
    Gomoku content = new Gomoku(monitorsize);
    window.setContentPane(content);
    window.pack();
  }
	
  public Gomoku(Dimension monitorsize){
		mode = false;
    myGame = new Game();
    setLayout(null);
    setPreferredSize(new Dimension(monitorsize.width/2,2*monitorsize.height/3) );
    setBackground(new Color(150,140,200));
      
        // Create the components and add them to the applet. 
        //Game myGame = new Game();
        Board board = new Board();  // Note: The constructor for the
                                  //   board also creates the buttons
                                  //   and label.
        
        add(board);         //game has 6 components
        add(undoButton);
        add(restartButton);
        add(message);
        add(humanhuman);
        add(humancomputer);
        modeGroup.add(humanhuman);
        modeGroup.add(humancomputer);
       
        /* Set the position and size of each component by calling
        its setBounds() method. 
        */ 
        board.setBounds(0,0,300,300);
        undoButton.setBounds(560, 380, 100, 30);
        restartButton.setBounds(560, 440, 100, 30);
        humanhuman.setBounds(300, 400, 200, 30);
        humancomputer.setBounds(300, 440, 200, 30);
        message.setBounds(460, 200, 350, 30);
	} // end gomoku constructor
    private class Board extends JPanel implements ActionListener, MouseListener {
        Board() {

       		setBackground(Color.BLACK);
        	addMouseListener(this);
        	undoButton = new JButton("Undo");
        	restartButton = new JButton("Restart");
          humanhuman = new JRadioButton("Human vs. Human");
          humancomputer = new JRadioButton("Human vs. Computer");
          modeGroup = new ButtonGroup();
        	message = new JLabel("",JLabel.LEFT);
        	restartButton.addActionListener(this);
        	undoButton.addActionListener(this);
          humanhuman.addActionListener(this);
          humancomputer.addActionListener(this);
        	message.setFont(new  Font("Serif", Font.BOLD, 14));
        	message.setForeground(Color.green);
        	//message.setText("Sample msg");
	    }
	    public void actionPerformed(ActionEvent evt) {
         Object src = evt.getSource();
         if (src == undoButton) {
            myGame.undo();
            repaint();
          }
          if (src == restartButton) {
            myGame = new Game();
              if (mode==true) {
              if((int)(Math.random()+.5) == 0) {
                Coordinate C = new Coordinate();
                C.x=7;
                C.y=7;
                myGame.go(C);
                //repaint();
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
                myGame.go(C);
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
       
            int col = (evt.getX() - 2) / 20;
            int row = (evt.getY() - 2) / 20;
            if (col >= 0 && col < 15 && row >= 0 && row < 15) {
              System.out.println("Before index = " + myGame.index);
               doClickSquare(row,col);
              System.out.println("After index = " + myGame.index);
            }
        }
      	public void doClickSquare(int row, int col) {
      		Coordinate C = new Coordinate();
      		C.x = row;
      		C.y = col;
       		myGame.go(C);
          repaint();
          if(mode==true) {
            myGame.go(pickCentered(C.x,C.y));
          }
          repaint();
          if(myGame.over == true) {
            if(myGame.turn == true) 
              message.setText("Game is over, black wins");
            else message.setText("Game is over, white wins");
          }

        	}
        public Coordinate pickCentered(int x, int y) {
        Coordinate C = new Coordinate();
        C.x = (int) (Math.random()*3) -1 + x;
        C.y = (int) (Math.random()*3) -1 + y;
            //System.out.println("Tried" + C.x + "," + C.y+".");
            if((C.x <0 || C.x>14) || (C.y <0 || C.y>14)) return pickRandom();
            //System.out.println("AfterArrayCheckIf");
            if (myGame.gamegraph[C.x][C.y][myGame.index]==0)return C;
        return pickCentered(C.x,C.y);
    }
        public Coordinate pickRandom() {
        Coordinate C = new Coordinate();
        C.x = (int) (Math.random()*15);
        C.y = (int) (Math.random()*15);
        if (myGame.gamegraph[C.x][C.y][myGame.index]==0)return C;
        else return pickRandom();
    }
        public void paintComponent(Graphics g) {
         
         /* Draw a two-pixel black border around the edges of the canvas. */
         
        	g.setColor(Color.black);
         	//g.drawRect(0,0,getSize().width-25,getSize().height-25);
         	//0g.drawRect(1,1,getSize().width-25,getSize().height-25);
         //Draw the squares of the checkerboard and the checkers. 
         
         	for (int row = 0; row < 15; row++) {
            	for (int col = 0; col < 15; col++) {
               		if ( row % 2 == col % 2 )
                  		g.setColor(Color.LIGHT_GRAY);
               		else
                  		g.setColor(Color.GRAY);
               		g.fillRect(col*20,row*20, 20, 20);
               		if (myGame.index != 0) {
               		switch (myGame.gamegraph[row][col][myGame.index-1]) {
               			case 1:
               			    g.setColor(Color.BLACK);
                  			g.fillOval(4 + col*20, 4 + row*20, 15, 15);                  			
                  			break;
               			case 2:
                  			g.setColor(Color.WHITE);
                  			g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                  		break;
              		}
                }
                }
            }

        }
         
    }//end Board
}
	