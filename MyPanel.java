import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyPanel implements ActionListener{
	private static final String NOTESET = "ABCDEFG";
	private static int[][][] tallies;
	private static int a;
	private static int b;
	private static int c;
	public static void main(String[] args) {
		System.out.println("Something!!");
		JFrame window = new JFrame();
		JPanel content = new JPanel();
		JButton startbutton = new JButton("Start");
		JLabel message = new JLabel();
		window.setContentPane(content);
		window.setVisible(true);
		window.setSize(700,500);
		window.setLocation(100,100);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startbutton.setBounds(250,250,100,30);
		window.add(startbutton);
		window.add(message);
		message.setFont(new  Font("Serif", Font.BOLD, 56));
	   	message.setForeground(Color.black);
		message.setBounds(300,300,100,30);
		message.setText("oueuo");
		int i = 1;
		for (int n =0;n<100;n++) {
			try {
				Thread.sleep(5000);
				String S = gen();
				message.setText(S);
				System.out.println(i);
				i++;
			}
			catch(InterruptedException E) {
				Thread.currentThread().interrupt();
				System.out.println("boueu");
			}
		}
	}
	public void actionPerformed(ActionEvent e) {}
	public static String gen(){
		double x = java.lang.Math.random()*7;
		System.out.println("x = " + x);
		double y = java.lang.Math.random()*7;
		System.out.println("y = " + y);
		int intx= (int) x;
		int inty=(int) y;
		String S = NOTESET.substring(intx,intx+1);
		a = intx;
		double z = java.lang.Math.random();
		if(z < .33 && !S.equals("E") && !S.equals("B")) {
			S = S + "#";
			c = 0;
		}
		else if (z <.66 && !S.equals("C") && !S.equals("F")) {
			S = S + "b";
			c = 1;
		}
		else c =2;
		b = inty;
		switch (inty) {
			case 1: S = S +"maj7";
				break;
			case 2: S = S +"m7";
				break;
			case 3: S = S + "m7b5";
				break;
			case 4: S = S +"dim7";
				break;
			case 5: S = S +"m";
				break;
			case 6: S = S + "7";
				break;
			case 7: S = S;
				break;
		}
		return S;
	}
}