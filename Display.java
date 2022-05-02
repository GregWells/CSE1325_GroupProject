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
	boolean mouseClicked=false;
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
	int randIndex=0;
	public Logo logos[] =new Logo[maxImages];

	public Display(Person player){
		///logos = createLogos("companies.csv");
		//Logo logos[] =new Logo[maxImages];
		this.player=player;
		this.randIndex=randIndex;
		createLogos(logos,companyFile);
		
		curIndex = 0;

		frame = new JFrame("Company Accountability Program for Ukraine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLayout(new GridLayout(2,1));


		logoPanel = new JPanel();
		titlePanel = new JPanel();
		instructPanel = new JPanel();
		scorePanel = new JPanel();
		askPanel = new JPanel();
		
	
		// Import ImageIcon   
		picLabel = new JLabel();	
		try {
			String imageName = ( "images/su.png");
			picLabel.setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );
			titlePanel.add(picLabel);
			frame.add(titlePanel);
			frame.setVisible(true);			
			Thread.sleep(1500);
			titlePanel.remove(picLabel);
			frame.remove(titlePanel);
			frame.setVisible(false);		
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
		
			
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
		if (curIndex<loops){ 
			frame.remove(instructPanel);
			frame.setVisible(false);
			frame.add(logoPanel);
			frame.pack();
			frame.setVisible(true);
			curIndex+=1;
			mouseClicked=false;
			
			System.out.println("got here:"+curIndex);
			randIndex=getRandomIndex();
			good=logos[randIndex].isGood();
			company=logos[randIndex].getCompany();
			logoFilename=logos[randIndex].getFilename();
			
			System.out.println(curIndex+"Next random logo: "+good+"  "+company+"  "+logoFilename);			
			try {
				File file=new File("images/"+logoFilename);
				System.out.println("Loading pic:"+file+"  "+ "  filesize:"+file.length());

				String imageName = ("images/"+logoFilename);
				picLabel.setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );

				logoPanel.add(picLabel);
				logoPanel.add(goodButton);
				logoPanel.add(badButton);

				goodButton.setActionCommand("GOOD");
				badButton.setActionCommand("BAD");
				frame.add(logoPanel); 
				frame.setVisible(true);
			
			} 
			catch (Exception e) {
				System.out.println("Error loading pic"+"images/"+logoFilename);
				e.getStackTrace();
			}
				
			try {
				Thread.sleep(200);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
		}	//mouseClicked=false;	
		else{			
			frame.setVisible(false);
			frame.remove(logoPanel);
			frame.add(scorePanel);
			frame.setVisible(true);
		}
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
			frame.remove(instructPanel);
			frame.add(logoPanel);
			frame.setVisible(true);

			displayLogo();
			mouseClicked=true;
		}
			
	}

	class GoodActionListener implements ActionListener{  // checks player choice/assigns points
		public void actionPerformed(ActionEvent a){
			System.out.println("User pressed GOOD button  curIndex:"+curIndex);
			if(logos[randIndex].isGood()){              // isGood() returns
				
				player.correct(); // increment score by 1 in Player class
			}
			displayLogo();
			mouseClicked=true;
			//curIndex++;
		}
	}

	class BadActionListener implements ActionListener{  // checks player choice/assigns points
		public void actionPerformed(ActionEvent a){
			System.out.println("User pressed BAD button");
			if(!logos[randIndex].isGood()){
				System.out.println("GGot here");
				player.incorrect(); // decrement score by 1 in Player class
			}
			displayLogo();
			mouseClicked=true;
			//curIndex++;
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
			frame.remove(instructPanel);
			frame.setVisible(false);
			frame.add(logoPanel);
			frame.setVisible(true);
			System.out.println("Got here: NoActionListener");
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
