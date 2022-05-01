
public class Person{ 
 	String name="NoName";  
    static int score=0;

    public Person() 
    { 
        this.name=name; 	
		this.score=0;		
    } 
 
     public String getName() 
    { 
        return this.name; 		
    } 
	public void setName(String s) 
    { 
        this.name=s;
		return; 		
    } 
    public int getScore() 
    { 
        return this.score; 		
    } 
    public void setScore(int sc) 
    { 
        this.score=sc;
		return; 		
    } 
	public void setFinalScore(int sc) 
    { 
		//save to score file?
		this.score=sc;
        return; 		
    } 
    public void incScore() 
    { 
		score+=1;
        return; 		
    } 
	public void correct() 
    { 
		score+=1;
        return; 		
    } 
	public void incorrect() 
    { 
		score-=1;
        return; 		
    } 
    public void decScore() 
    { 
		score-=1;
        return; 		
    } 
} 