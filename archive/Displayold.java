import java.io.*;
import java.util.*;
import java.lang.NullPointerException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;   
import java.io.FileNotFoundException;  
import java.nio.file.*;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class Display{
	private JFrame frame;
	private JLabel titleLabel, instructLabel, scoreLabel, askLabel, picLabel;
	private  JPanel titlePanel, instructPanel, logoPanel, scorePanel, askPanel;
	private JButton playButton, exitButton, againButton, startButton, goodButton, badButton, yesButton, noButton;
	
    ///ArrayList<Logo> logos;
	Person player;
	int curIndex;
	int loops=10;
	boolean good;
	int maxImages=50;
	String company;
	String logoFilename;	
	
	String companyFile="companies.csv";
	static int logoCount=0;
	int index=0;
	double numLines=0;
	String line;
	String depotName;
	//String[] line;
	String[] companyInfo;
	public Logo logos[] =new Logo[maxImages];

	public Display(Person player){
		///logos = createLogos("companies.csv");
		//Logo logos[] =new Logo[maxImages];
		createLogos(logos,companyFile);
		
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
		
	
		// Import ImageIcon   
		picLabel = new JLabel();		
		ImageIcon iconLogo = new ImageIcon("images/aa.png");
		picLabel.setIcon(iconLogo);
		
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

		logoPanel.add(picLabel);
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
		frame.setVisible(false);
		frame.add(logoPanel);
		frame.setVisible(true);
		for(int i = 0; i < loops;i++){
			curIndex = i;
			//int randIndex=getRandomIndex(logoList);
			int randIndex=getRandomIndex();
			good=logos[randIndex].isGood();
			company=logos[randIndex].getCompany();
			logoFilename=logos[randIndex].getFilename();
			
			//icon = new ImageIcon(logoFilename);
			System.out.println("Next random logo: "+good+"  "+company+"  "+logoFilename);
			
			//logoImg = logos.get(i).getPic(); // change image logo ; getPic() returns ImageIcon 
			
			try {
				File file=new File("images/"+logoFilename);
				System.out.println("Loading pic:"+file+"  "+ "  filesize:"+file.length());
				ImageIcon iconLogo = new ImageIcon(file);
				picLabel.setIcon(iconLogo);
				picLabel.setText("images/"+logoFilename);
		
			} catch (Exception e) {
				System.out.println("Error loading pic"+"images/"+logoFilename);
				e.getStackTrace();
			}
			
			logoPanel.add(picLabel);
			logoPanel.remove(picLabel); // reset image
			logoPanel.add(picLabel);

			frame.remove(logoPanel);//reset panel
			frame.add(logoPanel); 
			frame.setVisible(true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			
			while(curIndex!=i); // waits until player presses a good/bad button to continue
		}

		frame.remove(logoPanel);
		frame.add(scorePanel);
		frame.setVisible(true);
	}
	public void createLogos(Logo []logoList, String fn){
		//Initialize the logos

		try {
			Path cfile = Paths.get(companyFile);
			numLines = Files.lines(cfile).count();
			//System.out.println("Total lines: " + numLines);
		} catch (Exception e) {
			e.getStackTrace();
		}
		if (companyFile.length()<1){
			System.out.println("Company file: " + companyFile+ "is empty or does not exist.");
		}		
		
		try { 
			File myObj = new File(companyFile); 
			Scanner inFile= new Scanner(myObj);  
			while (inFile.hasNextLine()){	
				//Get the next line
				line= inFile.nextLine(); 
				//System.out.println(line);  //print each line of the file 
				//skip blank lines
				if (line.length()<1){
					//System.out.println("Skipping blank line in company file.\n");
					line=inFile.nextLine();
				}
				companyInfo=line.split(",");
				logoList[index]=new Logo(Integer.parseInt(companyInfo[0]),companyInfo[1],companyInfo[2]);

				index+=1;
			}
			logoCount=index;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ;	
	}	
	
	public void displayScore(){
		frame.remove(logoPanel);
		player.setFinalScore(player.getScore());  // finalizes player score ; need getScore() accessor and setFinalScore() setter
		///scoreLabel.setText("Score: "+player.getScore()+"/"+logos.size());
		scoreLabel.setText("Score: "+player.getScore()+"/");

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
			//if(logos.get(curIndex).isGood()){              // isGood() returns
			if(logos[curIndex].isGood()){              // isGood() returns

				player.correct(); // increment score by 1 in Player class
			}

			curIndex++;
		}
	}

	class BadActionListener implements ActionListener{  // checks player choice/assigns points
		public void actionPerformed(ActionEvent a){
			if(!logos[curIndex].isGood()){
				player.incorrect(); // decrement score by 1 in Player class
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
			frame.add(instructPanel);
			frame.setVisible(true);
		}
	}

	class NoActionListener implements ActionListener{ // ask screen -> logo gameplay
		public void actionPerformed(ActionEvent a){
			frame.remove(askPanel);
			displayLogo();
		}
	}
	
	public int getRandomIndex() 
    { 
        Random rand = new Random();
		int randInt=rand.nextInt(logoCount);
		return randInt;
	}	

}
