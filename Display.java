import java.io.*;
import java.util.*;
import java.lang.NullPointerException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Display{
	private JFrame frame;
	private JLabel titleLabel, instructLabel, scoreLabel, askLabel;
	private  JPanel titlePanel, instructPanel, logoPanel, scorePanel, askPanel;
	private JButton playButton, exitButton, againButton, startButton, goodButton, badButton, yesButton, noButton;
	private ImageIcon logoImg;
	Person player;
	int curIndex;

	public Display{
		player = new Person();
		curIndex = 0;

		frame = new JFrame("Company Accountability Program for Ukraine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setLayout(new GridLayout(3,1));


		logoPanel = new JPanel();
		titlePanel = new JPanel();
		instructPanel = new JPanel();
		scorePanel = new JPanel();
		askPanel = new JPanel();


		logoImg = new ImageIcon();

		askLabel = new JLabel();
		askLabel.setText("Do you need the instructions?");
		askLabel.setHorizontalAlignment(JLabel.CENTER);
		askLabel.setVerticalAlignment(JLabel.TOP);

		scoreLabel = new JLabel();
		scoreLabel.setText("Score: ");

		titleLabel = new JLabel("", JLabel.CENTER);
		titleLabel.setText("Title");
		titleLabel.setVerticalAlignment(JLabel.TOP);

		instructLabel = new JLabel();
		instructLabel.setText("Instructions...");
		instructLabel.setVerticalAlignment(JLabel.TOP);


		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitActionListener());

		startButton = new JButton("Start");
		startButton.addActionListener(new StartActionListener());

		goodButton = new JButton("Good");
		goodButton.addActionListener(new GoodActionListener());

		badButton = new JButton("Bad");
		badButton.addActionListener(new BadActionListener());

		againButton = new JButton("Play Again");
		againButton.addActionListener(new AgainActionListener());

		playButton = new JButton("Play Game");
		playButton.addActionListener(new PlayActionListener());

		yesButton = new JButton("Yes");
		yesButton.addActionListener(new YesActionListener());

		noButton = new JButton("No");
		noButton.addActionListener(new NoActionListener());

		titlePanel.add(titleLabel);
		titlePanel.add(startButton);
		titlePanel.add(exitButton);

		instructPanel.add(instructLabel);
		instructPanel.add(playButton);

		logoPanel.add(logoImg);
		logoPanel.add(goodButton);
		logoPanel.add(badButton);

		scorePanel.add(scoreLabel);
		scorePanel.add(againButton);
		scorePanel.add(exitButton);

		askPanel.add(askLabel);
		askPanel.add(yesButton);
		askPanel.add(noButton);

		frame.add(titlePanel);
		frame.setVisible(true);
	}

	public void displayLogo(){
		frame.remove(instructPanel);
		for(int i = 0; i < logos.size();i++){
			curIndex = i;
			logoImg = logos.get(i).getPic(); // change image logo ; getPic() returns ImageIcon 
			logoPanel.remove(logoImg); // reset image
			logoPanel.add(logoImg);

			frame.remove(logoPanel);//reset panel
			frame.add(logoPanel); 
			frame.setVisible(true);
			while(curIndex!=i); // waits until player presses a good/bad button to continue
		}

		frame.remove(logoPanel);
		frame.add(scorePanel);
		frame.setVisible(true);
	}

	public void displayScore(){
		frame.remove(logoPanel);
		player.finalScore(player.getScore());  // finalizes player score ; need getScore() accessor
		scoreLabel.setText("Score: "+player.getScore()+"/"+logos.size());
		frame.add(scorePanel);
		frame.setVisible(true);
	}

	public void displayInstructions(){
		frame.remove(titlePanel);
		frame.add(instructPanel);
		frame.setVisible(true);
	}
 
	class StartActionListener implements ActionListener{  // title screen -> instructions
		public void actionPerformed(ActionEvent a){
			displayInstructions();
		}
	}

	class PlayActionListener implements ActionListener{  // instructions -> logo gameplay
		public void actionPerformed(ActionEvent a){
			displayLogo();
		}
	}

	class GoodActionListener implements ActionListener{  // checks player choice/assigns points
		public void actionPerformed(ActionEvent a){
			if(logos.get(curIndex).isGood(){              // isGood() returns
				player.correct(); // increment score by 1 in Player class
			}

			curIndex++;
		}
	}

	class BadActionListener implements ActionListener{  // checks player choice/assigns points
		public void actionPerformed(ActionEvent a){
			if(!logos.get(curIndex).isGood(){
				player.correct(); // increment score by 1 in Player class
			}

			curIndex++;
		}
	}

	class ExitActionListener implements ActionListener{ // title screen -> end program && scoreboard -> end program
		public void actionPerformed(ActionEvent a){
			System.exit(0);
		}
	}

	class AgainActionListener implements ActionListener{ // after play again chosen -> instruction ask screen
		public void actionPerformed(ActionEvent a){
			frame.remove(scorePanel);
			frame.add(askPanel);
			frame.setVisible(true);
		}
	}

	class YesActionListener implements ActionListener{ // ask screen -> instruction screen
		public void actionPerformed(ActionEvent a){
			frame.remove(askPanel);
			frame.add(instructPanel):
			frame.setVisible(true);
		}
	}

	class NoActionListener implements ActionListener{ // ask screen -> logo gameplay
		public void actionPerformed(ActionEvent a){
			frame.remove(askPanel);
			displayLogo();
		}
	}
}