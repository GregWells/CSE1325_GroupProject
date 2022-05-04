// Student: Greg Wells  ID: 1001995134
package usefulstuff; 
 
import java.util.*; //gets Scanner and other classes 
import java.io.File;   
import java.io.IOException;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

 
public class Useful{ 
 
    public static String getUserInput(String message) 
    { 
        System.out.printf(message); 
		System.out.println(" ");

        Scanner in=new Scanner(System.in); 
 
        return in.nextLine(); 
    } 
	public static int getUserIntegerInput(String message) 
    { 
        System.out.printf(message); 

        Scanner in=new Scanner(System.in); 
 
        return in.nextInt(); 
    } 
	public static boolean write(final String s, String fn) throws IOException {
		Path filePath = Paths.get(fn);
		Files.writeString(filePath,s, StandardOpenOption.APPEND);
		return true;
	}
	public static boolean writeNew(final String s, String fn) throws IOException {
		Path filePath = Paths.get(fn);
		Files.writeString(filePath,s, StandardOpenOption.CREATE_NEW,StandardOpenOption.APPEND);

		return true;
	}
	public static boolean writeNew2(final String s, String fn) throws IOException {
		Path filePath = Paths.get(fn);
		File file = new File(fn);
		try {
			if (file.createNewFile()) {         
				System.out.println("File "+fn+" has been created.");
			} else {
				System.out.println("File "+fn+" already exists.");
			}
			Files.writeString(filePath,s, StandardOpenOption.APPEND);
			return true;
		}
		catch (Exception e){
			Files.writeString(filePath,s, StandardOpenOption.CREATE);
			Files.writeString(filePath,s, StandardOpenOption.APPEND);

			return true;
		}
	}
	public static String pickChoice(String userInput,String options ){
		int i=0;
		int l=0;   //length variable
		String []option=options.split(","); //split with comma delimiter
		l=option.length;
		for (i=0;i<l;i++){
			//System.out.println("\n");
			if (option[i].toLowerCase().equals(userInput.toLowerCase())){
				return (option[i].toLowerCase());
			}
		}
		System.out.println("Error: you didn't make a valid selection so we will give you our favorite!\n");
		return (option[0].toLowerCase());

		
	}
	
}