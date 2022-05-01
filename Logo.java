
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
 
    public Boolean isGood() 
    { 
        if (this.good==1){
			return true; 	
		}
		else{
			return false;
		}	
    } 
    public String getCompany() 
    { 
        return this.companyName; 		
    } 
    public String getFilename() 
    { 
        return this.logoFilename; 		
    } 
    public ImageIcon getPic() 
    { 
	    ImageIcon img = new ImageIcon(getFilename());
        return img; 		
    } 

} 
