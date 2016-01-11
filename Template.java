import javax.swing.*;

public class Template{
	public static void main(String[] args) {
		JFrame window = new JFrame();
		JPanel content = new JPanel();
		JButton startbutton = new JButton("Start");
		window.setContentPane(content);
		window.setVisible(true);
		window.setSize(700,500);
		window.setLocation(100,100);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startbutton.setBounds(250,250,100,30);
		window.add(startbutton);
	}
}