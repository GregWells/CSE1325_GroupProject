import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Program{

	
	public static void main(String[] args){
		int good;
		int index;
		int maxImages=50;
		String company;
		String filename;
		
		//Person p1 = new Person();
		//Display d1 = new Display(p1);
		
		//Initialize the logos
		Logo[] logoList2=new Logo[maxImages];
		LogoDepot ld1= new LogoDepot("Primary",logoList2);


		//loop to demonstrate random selection
		for (int i=0;i<10;i++){
			index=ld1.getRandomIndex(logoList2);
			good=ld1.getGoodAtIndex(logoList2,index);
			company=ld1.getCompanyAtIndex(logoList2,index);
			filename=ld1.getFilenameAtIndex(logoList2,index);	
			System.out.println("Random logo: "+good+"  "+company+"  "+filename);
		}
		
	}
}
