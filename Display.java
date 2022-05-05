import usefulstuff.*; 

import java.io.*;
import java.util.*;
import java.lang.NullPointerException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.nio.file.*;


public class Display{
	private JFrame frame, splashframe, badframe, goodframe;
	private JLabel titleLabel, instructLabel, scoreLabel, askLabel, picLabel, pic2Label,finalPicLabel,introPicLabel,leadLabel;
	private  JPanel titlePanel, instructPaneltop, instructPanelcen, instructPanelbot, logoPanel, scorePanel, askPanel, alertPanel, splashPanel,goodPanel, badPanel, botBarPanel;
	private JButton playButton, exitButton, againButton, startButton, goodButton, badButton, yesButton, noButton;
	
    ///ArrayList<Logo> logos;
	Person player;
	int curIndex;
	//boolean mouseClicked=false;
	int loops=10;
	boolean good;
	int maxImages=50;
	String company;
	String logoFilename;	
	String leaderboardFilename="leaderboard.csv";
	boolean result=false;
	String companyFile="companies.csv";
	String progName="Company Accountability Program for Ukraine";
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
		frame = new JFrame(progName);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLayout(new GridLayout(1,1));
		
		splashframe = new JFrame("Slava Ukraini");
		splashframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		splashframe.setSize(600,380); 
		splashframe.setLayout(new GridLayout(1,1));
			

		//define panels that will be placed on the frame
		titlePanel = new JPanel();
		instructPanelcen = new JPanel();
		logoPanel = new JPanel();  
		scorePanel = new JPanel();
		askPanel = new JPanel();
		alertPanel = new JPanel();
		splashPanel = new JPanel();
	    goodPanel = new JPanel();
		badPanel = new JPanel();
		botBarPanel= new JPanel();
		
		splashSlava();  //Show opening splash screen with Ukrainian flag
		
		
		//establish the labels and buttons that will be placed on the panels	
		askLabel = new JLabel();
		askLabel.setText("Do you need the instructions?");
		askLabel.setHorizontalAlignment(JLabel.CENTER);
		askLabel.setVerticalAlignment(JLabel.TOP);

		scoreLabel = new JLabel("Score: ");
		leadLabel = new JLabel("Score Leader: ");
		//scoreLabel.setText("Score: "+player.getScore());

		titleLabel = new JLabel("", JLabel.CENTER);
		//titleLabel.setText("Title");  //Not needed 
		titleLabel.setVerticalAlignment(JLabel.TOP);
		
		pic2Label = new JLabel();
			
		introPicLabel = new JLabel();	
		String imageName = ( "images/intro.jpg");
		try{
			introPicLabel.setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );
		} catch (Exception e1) {
			System.out.println("Error 0003: failure to load: "+imageName);
			e1.printStackTrace();
		}
		


		//introPicLabel.setLayout( new GridBagLayout() );
		//introPicLabel.add(startButton, new GridBagConstraints());
		
		//image for the score screen  
		finalPicLabel = new JLabel();	
		imageName = ( "images/impact.jpg");
		try{
			finalPicLabel.setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );
		} catch (Exception e1) {
			System.out.println("Error 0004: failure to load: "+imageName);
			e1.printStackTrace();
		}
		

		
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

		titlePanel.add(introPicLabel);
		
		introPicLabel.setLayout( new GridBagLayout() );
		introPicLabel.add(startButton, new GridBagConstraints());

		//introPicLabel.add(startButton);
//		titlePanel.add(exitButton);


		
		//logoPanel.add(goodButton);    //move the good/bad buttons onto the picLabel
		//logoPanel.add(badButton);
		
		//scoreLabel.setBounds(1, 1, 100, 100);
		//picLabel.add(scoreLabel);
		
		picLabel.add(goodButton);
		picLabel.add(badButton);
		goodButton.setBounds(50, 50, 200, 60);   //JButton.setBounds(x,y,w,h)
		badButton.setBounds(350, 50, 200, 60);   //JButton.setBounds(x,y,w,h)
		goodButton.setBackground(Color.LIGHT_GRAY);	
		goodButton.setFont(new Font("Arial", Font.BOLD, 18));
		badButton.setBackground(Color.LIGHT_GRAY);
		badButton.setFont(new Font("Arial", Font.BOLD, 18));
		//picLabel.add(scoreLabel);
		logoPanel.add(picLabel);
		//botBarPanel.add(scoreLabel);
		

		//scorePanel.add(scoreLabel);
		//scorePanel.add(againButton);
		//scorePanel.add(exitButton);
		
		
		

		askPanel.add(askLabel);
		askPanel.add(yesButton);
		askPanel.add(noButton);

		alertPanel.add(pic2Label);
		
		//goodPanel.add(goodpicLabel);
		//badPanel.add(badpicLabel);
		

		//Now add the title panel to the frame and make it visible
		//the title panel contains the start button
		frame.add(titlePanel);		
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
	}


  	public void displayLogo(){
		if (curIndex<loops){ 
			//frame.remove(instructPanel);  //redundant
			frame.setVisible(false);
			frame.add(logoPanel);
			frame.setTitle(player.getName()+"   Score: "+player.getScore() +"     Guess "+curIndex+" of "+loops);
			
			//frame.add(botBarPanel);

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

				goodButton.setActionCommand("GOOD");
				badButton.setActionCommand("BAD");
				//frame.add(logoPanel); 
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
				frame.pack();
				frame.setLocationRelativeTo(null);
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
		}		
		else{			
			frame.setVisible(false);
			frame.remove(logoPanel);
			scoreLabel.setText(player.getName()+"   Score: "+player.getScore());
			displayScore();
			frame.add(scorePanel);
			frame.setLocationRelativeTo(null);
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
		
		//scorePanel.add(scoreLabel);
		//scorePanel.add(againButton);
		//scorePanel.add(exitButton);
		//scorePanel.remove(scoreLabel);
		//scorePanel.remove(againButton);
		//scorePanel.remove(exitButton);		
		
		
		frame.setTitle(progName);
		//save this score to the leaderboard csv file
		writeResult( leaderboardFilename, player.getName(), player.getScore());
		player.setFinalScore(player.getScore());  // finalizes player score ; need getScore() accessor and setFinalScore() setter
		///scoreLabel.setText("Score: "+player.getName()+"   "+player.getScore()+"/"+logos.size());
		//System.out.println("Leader:  "+showLeaderboard( leaderboardFilename));
		
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
		scoreLabel.setText("Score: "+player.getName()+"   "+player.getScore()+" ");
		scoreLabel.setForeground(Color.BLACK);
		scorePanel.add(finalPicLabel);
		
		leadLabel.setFont(new Font("Arial", Font.BOLD, 30));
		leadLabel.setText("Leaderboard Top Score: "+showLeaderboard( leaderboardFilename));
		leadLabel.setForeground(Color.BLUE);
		finalPicLabel.add(leadLabel);
		
		//finalPicLabel.setLayout( new GridBagLayout() );
		finalPicLabel.setLayout( new FlowLayout(FlowLayout.CENTER, 200,10) );
		//finalPicLabel.add(againButton, new GridBagConstraints());
		finalPicLabel.add(againButton);
		finalPicLabel.add(scoreLabel);
		finalPicLabel.add(exitButton);

		scorePanel.add(finalPicLabel);
				
		frame.add(scorePanel);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void displayInstructions(){
		frame.remove(titlePanel);
		frame.setVisible(false);
		getPlayerName();
		frame.setLayout(new BorderLayout());
		int width=800;
		int height=500;
		//Jared: I want this to say:
		//"This is a game meant to quiz your knowledge on whether the following companies are aiding in the effort to support Ukraine"
		//"hit 'good' for if the company is in the support for Ukraine, hit 'bad' if otherwise"
		//"and don't forget to have fun!!!"
		instructLabel = new JLabel();
		instructLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		//instructLabel.setText("<html>Instructions: <br><br>This is a game meant to quiz your knowledge<br>on whether the following companies are aiding in the effort to support Ukraine<br>hit 'good' for if the company is in the support for Ukraine, hit 'bad' if otherwise<br>and don't forget to have fun!!!</html>");
		instructLabel.setText("<html><br>Instructions:<br><br>This is a game meant to quiz your knowledge on whether certain <br> companies have taken steps to cease business activities in Russia<br> in support of Ukraine or are continuing their business activities with Russia.<br>Hit 'good' if the company is supporting Ukraine by ceasing Russian operations,<br> or hit 'bad' if the company is pursuing business as usual in Russia. <br></html>");

		instructLabel.setVerticalAlignment(SwingConstants.CENTER); //Jared: Haven't figureed out how to center text yet.
		instructLabel.setForeground(Color.WHITE);

		instructPaneltop = new JPanel();

		instructPaneltop.add(instructLabel);
		instructPaneltop.setBackground(new Color(3,85,184));
		instructPaneltop.setPreferredSize(new Dimension(width,(height/2)-4) );
		instructPaneltop.setMaximumSize(new Dimension(width,(height/2)-4) );

		frame.add(instructPaneltop,BorderLayout.PAGE_START);

		instructPanelbot = new JPanel();
		
		instructPanelbot.add(playButton);
		playButton.setVerticalAlignment(JButton.CENTER); //Jared: This doesn't work yet, don't know what I'm missing
		instructPanelbot.setBackground(new Color(255,208,1));
		instructPanelbot.setPreferredSize(new Dimension(width,height/2));
		instructPanelbot.setMaximumSize(new Dimension(width,height/2));

		frame.add(instructPanelbot,BorderLayout.SOUTH);

		frame.setSize(width,height);
		frame.setLocationRelativeTo(null);
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
			frame.remove(instructPaneltop);
			frame.remove(instructPanelbot);
			frame.remove(instructLabel);
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
				showResultWithTimer("Correct",frame,logoPanel );
				player.correct(); // increment score by 1 in Player class

			}
			else{
				showResultWithTimer("Wrong",frame,logoPanel);
				player.incorrect(); // decrement score by 1 in Player class

			}

			displayLogo();
		}
	}

	class BadActionListener implements ActionListener{  // checks player choice/assigns points
		public void actionPerformed(ActionEvent a){
			//System.out.println("User pressed BAD button");
			//System.out.println("randIndex:"+randIndex+ " logos[randIndex].isGood():"+logos[randIndex].isGood());   

			if( !(logos[randIndex].isGood()) ){
				showResultWithTimer("Correct",frame,logoPanel);
				player.correct(); // increment score by 1 in Player class

			}
			else{
				showResultWithTimer("Wrong",frame,logoPanel);
				player.incorrect(); // decrement score by 1 in Player class

			}	

			displayLogo();

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
			//frame.add(askPanel);      //go straight to logo panel without asking about instructions
			
			
			frame.remove(askPanel);
			frame.remove(instructPaneltop);
			frame.remove(instructPanelbot);
			frame.remove(instructLabel);
			frame.remove(playButton);
			frame.setVisible(false);
			frame.add(logoPanel);
			frame.setLocationRelativeTo(null);
			//frame.setVisible(true);
			//System.out.println("Got here: NoActionListener");
			resetGame();
			
			displayLogo();
			//frame.add(logoPanel);
			
			
			
			//frame.setLocationRelativeTo(null);
			//frame.setVisible(true);
		}
	}

	class YesActionListener implements ActionListener{ // ask screen -> instruction screen
		public void actionPerformed(ActionEvent a){
			frame.remove(askPanel);

			frame.setLayout(new BorderLayout());
	
			//Jared: I want this to say:
			//"This is a game meant to quiz your knowledge on whether the following companies are aiding in the effort to support Ukraine"
			//"hit 'good' for if the company is in the support for Ukraine, hit 'bad' if otherwise"
			//"and don't forget to have fun!!!"
			instructLabel = new JLabel();
			instructLabel.setText("<html>Instructions: <br>This is a game meant to quiz your knowledge on whether certain <br> companies have taken steps to cease business activities in Russia <br> or are continuing their business activities with Russia in support of Ukraine. <br>hit 'good' if the company is supporting Ukraine by ceasing Russian operations,<br> or hit 'bad' if the company is pursuing business as usual in Russia. <br></html>");
			instructLabel.setVerticalAlignment(SwingConstants.CENTER); //Jared: Haven't figureed out how to center text yet.
			instructLabel.setForeground(Color.WHITE);
	
			instructPaneltop = new JPanel();
	
			instructPaneltop.add(instructLabel);
			instructPaneltop.setBackground(new Color(3,85,184));
			instructPaneltop.setPreferredSize(new Dimension(500,131));
			instructPaneltop.setMaximumSize(new Dimension(500,131));
	
			frame.add(instructPaneltop,BorderLayout.PAGE_START);
	
			instructPanelbot = new JPanel();
			
			instructPanelbot.add(playButton);
			playButton.setVerticalAlignment(JButton.CENTER); //Jared: This doesn't work yet, don't know what I'm missing
			instructPanelbot.setBackground(new Color(255,208,1));
			instructPanelbot.setPreferredSize(new Dimension(500,131));
			instructPanelbot.setMaximumSize(new Dimension(500,131));
	
			frame.add(instructPanelbot,BorderLayout.SOUTH);
	
			frame.setSize(500,300);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}

	class NoActionListener implements ActionListener{ // ask screen -> logo gameplay
		public void actionPerformed(ActionEvent a){
			frame.remove(askPanel);
			frame.remove(instructPaneltop);
			frame.remove(instructPanelbot);
			frame.remove(instructLabel);
			frame.remove(playButton);
			frame.setVisible(false);
			frame.add(logoPanel);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			//System.out.println("Got here: NoActionListener");
			resetGame();
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
		return "NotAName";
		//return frame.add(JOptionPane.showInputDialog(null, m));
    }
	
	public void showResult(String m) {
		JOptionPane.showMessageDialog(frame, m); //basic dialog 
		return;
    }
	
	public void showResultWithTimer(String m,JFrame frame, JPanel logoPanel) {

			int TIME_VISIBLE=1500;

			JOptionPane pane = new JOptionPane();
			pane.setMessageType(JOptionPane.PLAIN_MESSAGE);
			UIManager UI=new UIManager();
			if (m.equals("Wrong") ){
				UI.put("Panel.background", Color.red);
			}
			else {
				UI.put("Panel.background", Color.green);
			}
			
			pane.setMessage("                         "+m);
			JDialog dialog = pane.createDialog(null,m+" Answer");

			// Set a timer
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(TIME_VISIBLE);
					} catch (Exception e) {
					}
					dialog.dispose();
				}

			}).start();

			dialog.setVisible(true);
		return;
    }
	
	public void waitasec() {	
		
		try {
			Thread.sleep(1500);		
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
	}
		
	public void splashSlava() {	
		//Show opening splash screen with Ukrainian flag
		try {
			String imageName = ( "images/su.png");
			picLabel = new JLabel();	
			picLabel.setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );
			splashPanel.add(picLabel);
			splashframe.add(splashPanel);
			splashframe.setLocationRelativeTo(null);
			splashframe.setVisible(true);			
			Thread.sleep(2000);
			//splashPanel.remove(picLabel);
			//splashframe.remove(splashPanel);
			splashframe.setLocationRelativeTo(null);
			splashframe.setVisible(false);	
			splashframe.dispose();			
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}	
	
	public void getPlayerName() {		


		//First get the player's name.  This is a small popup window
                            
		String playerName= JOptionPane.showInputDialog(null, "Enter player name: ");

		player.setName(playerName);
		if (playerName.length()<1){
			playerName="Volodymyr Zelenskyy";
		}
		//System.out.println("Player: "+playerName);
		player.setName(playerName);
	}	
		
	public void resetGame(){
		curIndex=0;    //reset iteration counter
		player.setScore(0);
		index=0;
		randIndex=0;
		//frame.dispose();
	}	
	
	public void writeResult(String filename, String name, int score){
		try { 
						File leaderboardFile = new File(filename); 
						Scanner outFile= new Scanner(leaderboardFile);
						String dataLine=(Integer.toString(score)+","+name+"\n");
						result=Useful.write( dataLine, filename);
						//System.out.println("Wrote : "+name+" score:"+score +"to file:"+filename);
						
					}
					catch (Exception e) {
						System.out.println("Error writing to: "+filename);
						}
	
		return;
	}
	
	public String showLeaderboard(String filename){
		int highScore=-99;
		int highScoreIndex=0;
		int score[]=new int[200];
		String name[]= new String[200];
		String resultString="";
		int counter=0;
		try { 
			File myObj = new File(filename); 
			Scanner inFile= new Scanner(myObj);  
			
			while (inFile.hasNextLine()){
				//
				String info= inFile.nextLine(); 
				///System.out.println(info);

				String []line=info.split(","); //split with comma delimiter
				
				score[counter]=Integer.parseInt(line[0]); 
				name[counter]=line[1];
				
				///System.out.println("Score: "+score[counter]);
				
				///System.out.println("Score: "+score[counter]+"    "+name[counter]);

				if (score[counter]>highScore){
					highScore=score[counter];
					highScoreIndex=counter;
				}
				counter++;	
				
			}	
		///System.out.println("High:"+highScore + "   "+name[highScoreIndex]);					
		}
		catch (Exception e) { //report exception here 
			//System.out.println("Here is what happened: "+e); 
			///System.out.println("High:"+highScore + "   "+name[highScoreIndex]);
			resultString=(Integer.toString(highScore)+"   "+name[highScoreIndex]);
		} 
		resultString=(Integer.toString(highScore)+"   "+name[highScoreIndex]);
		return (resultString);
	}		
}