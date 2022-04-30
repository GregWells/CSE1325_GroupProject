import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Program{
	HashMap<Boolean, JImage, String> logos = createLogos("logos.csv");

	public static void main(String[] args){
		Player p1 = new Player();
		Display d1 = new Display(p1);
	}

	public ArrayList<Logo> createLogos(File f){
		ArrayList<Logo> list = new ArrayList<Logo>();

		try{
			File fObj = new File(f);
			Scanner fIn = new Scanner(fObj);

			while(fIn.hasNextLine()){
				String in = fIn.nextLine();
				String[] sp = in.split(",");

				Logo logo = new Logo(sp[0],sp[1],sp[2]);
				list.add(logo);
			}
			fIn.close();
		}

		catch(Exception e){
			System.out.println("Logos could not be created because "+e);
		}
		return list;
	}

}