
public class Logo{ 
 
    int good;
	String companyName;  
	String logoFilename;
	
 
    public Logo(int good,String companyName,String logoFilename) 
    { 
        this.good=good; 
        this.companyName=companyName; 
        this.logoFilename=logoFilename; 		
    } 
 
    public int isGood() 
    { 
        return this.good; 		
    } 
    public String getCompany() 
    { 
        return this.companyName; 		
    } 
    public String getFilename() 
    { 
        return this.logoFilename; 		
    } 	

} 