import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

import java.io.*;
import java.lang.Thread;

public class Goldteam {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1200, 1000));
		frame.getContentPane().setBackground(Color.BLUE);
		frame.pack();

		frame.setVisible(true);
		Thread.sleep(400);
		frame.setVisible(false);
		frame.getContentPane().setBackground(Color.RED);
		frame.pack();
		frame.setVisible(true);
		try { for (int i = 0; i < 5; i++) {
			Thread.sleep(400);
			}
		}
		catch (Exception e) {
			
			frame.setVisible(true);
		}
		System.exit(0);

   }
}