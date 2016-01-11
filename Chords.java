import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Chords extends JPanel {  //should class be public?
	public static void main(String[] args) {
		JFrame window = new JFrame("Chord Practice");
    	Dimension monitorsize = Toolkit.getDefaultToolkit().getScreenSize();
    	window.setLocation(monitorsize.width/3, monitorsize.height/4 );
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
    	window.setResizable(false);  
		window.setVisible(true);
		window.setSize(200,500);
		window.pack();
		window.add(new Chords(monitorsize));
	}
	public Chords (java.awt.Dimension monitorsize){
		Stuff myStuff = new Stuff();
		add(myStuff);
	}
	private class Stuff extends JPanel{
		Stuff(){
       		setBackground(Color.BLACK);
        	//addMouseListener(this);
		}
	}
}