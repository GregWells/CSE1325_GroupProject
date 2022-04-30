import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Program{
	ArrayList<Logo> logos = createLogos("companies.csv");

	public static void main(String[] args){
		Person p1 = new Person();
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

				Logo logo = new Logo(Integer.parseInt(sp[0]),sp[1],sp[2]);  // bad/good, name, img filename
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
