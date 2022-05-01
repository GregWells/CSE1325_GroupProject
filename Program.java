import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Program{

	
	public static void main(String[] args){
		
		Person p1 = new Person();
		p1.setName("Volodymyr Zelenskyy");    //temporary; need GUI component 
		
		Display d1 = new Display(p1);

	}
}
