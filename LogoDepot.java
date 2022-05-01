
import java.io.File;   
import java.io.FileNotFoundException;  
import java.util.Scanner; 

import java.nio.file.*;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class LogoDepot { 
	 
	String companyFile="companies.csv";
	static int logoCount=0;
	int index=0;
	double numLines=0;
	String line;
	String depotName;
	//String[] line;
	String[] companyInfo;
	
    public LogoDepot (String depotName,Logo []logoList)
    { 
		this.depotName=depotName;
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
				//System.out.println("Company: "+logoList[index].getCompany());
				//System.out.println("Good: "+logoList[index].isGood());
				index+=1;
			}
			logoCount=index;
		} catch (Exception e) {
			e.getStackTrace();
		}
		
    } 
	

    public int getSize() 
    { 
        return logoCount; 
    }

    public String getName() 
    { 
        return this.depotName; 
    } 
	public void setName(String n) 
    { 
        this.depotName=n; 
		return; 
    }
	public void showAll(Logo []ll) 
    { 
        for (int i=0;i< logoCount;i++){
			System.out.println("Company: "+ ll[i].isGood()+"  "+ ll[i].getCompany()+"  "+ ll[i].getFilename());

		}	
		return; 
    }
	public int getRandomIndex(Logo []ll) 
    { 
        Random rand = new Random();
		int randInt=rand.nextInt(logoCount);
		return randInt;
	}	
	public int getGoodAtIndex(Logo []ll,int i) 
    { 
		return ll[i].isGood();
	}	
	public String getCompanyAtIndex(Logo []ll,int i) 
    { 
		return ll[i].getCompany();
	}
	public String getFilenameAtIndex(Logo []ll,int i) 
    { 
		return ll[i].getFilename();
	}
	
} 
