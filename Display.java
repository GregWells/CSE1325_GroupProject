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
	private JFrame frame, splashframe, frame2;
	private JLabel titleLabel, instructLabel, scoreLabel, askLabel, picLabel, pic2Label;
	private  JPanel titlePanel, instructPanel, logoPanel, scorePanel, askPanel, alertPanel, splashPanel;
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
	String[] companyInfo;
	int randIndex=0;
	public Logo logos[] =new Logo[maxImages];

	public Display(Person player){
	
		this.player=player;
		this.randIndex=randIndex;

 
		//Create logo objects from company csv file
		createLogos(logos,companyFile);
		
		curIndex = 0;
		
		//define frame parameters
		frame = new JFrame("Company Accountability Program for Ukraine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLayout(new GridLayout(1,1));
		
		splashframe = new JFrame("Company Accountability Program for Ukraine");
		splashframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		splashframe.setSize(600,400);
		splashframe.setLayout(new GridLayout(1,1));
		

		//define panels that will be placed on the frame
		titlePanel = new JPanel();
		instructPanel = new JPanel();
		logoPanel = new JPanel();  
		scorePanel = new JPanel();
		askPanel = new JPanel();
		alertPanel = new JPanel();
		splashPanel = new JPanel();
	  
		//Show opening splash screen with Ukrainian flag
		try {
			String imageName = ( "images/su.png");
			picLabel = new JLabel();	
			picLabel.setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );
			splashPanel.add(picLabel);
			splashframe.add(splashPanel);
			splashframe.setVisible(true);			
			Thread.sleep(2000);
			splashPanel.remove(picLabel);
			splashframe.remove(splashPanel);
			splashframe.setVisible(false);		
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
		
		//establish the labels and buttons that will be placed on the panels	
		askLabel = new JLabel();
		askLabel.setText("Do you need the instructions?");
		askLabel.setHorizontalAlignment(JLabel.CENTER);
		askLabel.setVerticalAlignment(JLabel.TOP);

		scoreLabel = new JLabel();
		scoreLabel.setText("Score: ");

		titleLabel = new JLabel("", JLabel.CENTER);
		//titleLabel.setText("Title");  //Not needed 
		titleLabel.setVerticalAlignment(JLabel.TOP);

		instructLabel = new JLabel();
		instructLabel.setText("<html>Instructions: <br>This is a<br>multline label<br> </html>");
		instructLabel.setVerticalAlignment(JLabel.TOP);
		
		pic2Label = new JLabel();

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


		//add the buttons and labels to the appropriate panels
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

		alertPanel.add(pic2Label);


		
		//First get the player's name.  This is a small popup window
		//The program requires the person object 
		//to be created first:   Display d1 = new Display(p1);
		// where p1 is the player object                            
		String playerName=getName("Name: ");
		player.setName(playerName);
		if (playerName.length()<1){
			playerName="Volodymyr Zelenskyy";
		}
		System.out.println("Player: "+playerName);
		player.setName(playerName);
		
		//Now add the title panel to the frame and make it visible
		//the title panel contains the start button
		frame.add(titlePanel);		
		frame.setVisible(true);
		
	}


  	public void displayLogo(){
		if (curIndex<loops){ 
			//frame.remove(instructPanel);  //redundant
			frame.setVisible(false);
			frame.add(logoPanel);

			curIndex+=1;
			
			randIndex=getRandomIndex();
			good=logos[randIndex].isGood();
			company=logos[randIndex].getCompany();
			logoFilename=logos[randIndex].getFilename();
			
			//System.out.println(curIndex+"Next random logo: "+good+"  "+company+"  "+logoFilename);			
			try {
				File file=new File("images/"+logoFilename);
				//System.out.println("Loading pic:"+file+"  "+ "  filesize:"+file.length());

				String imageName = ("images/"+logoFilename);
				picLabel.setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );

				logoPanel.add(picLabel);
				logoPanel.add(goodButton);
				logoPanel.add(badButton);

				goodButton.setActionCommand("GOOD");
				badButton.setActionCommand("BAD");
				frame.add(logoPanel); 
				frame.pack();
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
			frame.setVisible(false);
			frame.remove(instructPanel);
			//frame.add(logoPanel);
			//frame.setVisible(true);

			displayLogo();
		}
			
	}

	class GoodActionListener implements ActionListener{  // checks player choice/assigns points
		public void actionPerformed(ActionEvent a){
			//System.out.println("User pressed GOOD button  curIndex:"+curIndex);
			//System.out.println("randIndex:"+randIndex+ " logos[randIndex].isGood():"+logos[randIndex].isGood());   
			if(logos[randIndex].isGood()){              // isGood() returns	
				player.correct(); // increment score by 1 in Player class
			}
			else{
				player.incorrect(); // decrement score by 1 in Player class
				splashAlert("Wrong!");
			}
			displayLogo();
			mouseClicked=true;
		}
	}

	class BadActionListener implements ActionListener{  // checks player choice/assigns points
		public void actionPerformed(ActionEvent a){
			//System.out.println("User pressed BAD button");
			//System.out.println("randIndex:"+randIndex+ " logos[randIndex].isGood():"+logos[randIndex].isGood());   

			if( !(logos[randIndex].isGood()) ){
				player.correct(); // decrement score by 1 in Player class
			}
			else{
				splashAlert("Wrong!");
				player.incorrect(); // decrement score by 1 in Player class
			}	
			displayLogo();
			mouseClicked=true;
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

	public String getName(String m) {
		return JOptionPane.showInputDialog(null, m);
    }
	
	

	
	public void splashAlert(String s) {	
		//Show wrong answer pic
	
		try {
			frame.setVisible(false);
			frame.remove(logoPanel);
			String imageName = ( "images/wrong.png");
			pic2Label.setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );
			alertPanel.add(pic2Label);
			frame.add(alertPanel);
			frame.setVisible(true);			
			Thread.sleep(1500);
			alertPanel.remove(pic2Label);
			frame.remove(alertPanel);
			frame.add(logoPanel);
			//frame.setVisible(false);		
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
}
