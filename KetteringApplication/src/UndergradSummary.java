/********************************************************************
 * Class: UndergradSummary
 * Purpose: information obtained from final grades (GPA, credits, etc)
/*******************************************************************/
public class UndergradSummary {
	
	// Properties
	private UndergradSummaryDetail currentTerm;
	private UndergradSummaryDetail cumulative;
	private UndergradSummaryDetail transfer;
	private UndergradSummaryDetail overall;
	
	
	/********************************************************************
	 * Constructor: UndergradSummary
	 * Purpose: create a default undergrad summary object
	/*******************************************************************/
	public UndergradSummary(){
		
		// Initialize
		this.currentTerm = new UndergradSummaryDetail();
		this.cumulative = new UndergradSummaryDetail();
		this.transfer = new UndergradSummaryDetail();
		this.overall = new UndergradSummaryDetail();
	}

	/********************************************************************
	 * Constructor: UndergradSummary
	 * Purpose: create undergrad summary object with parameters
	/*******************************************************************/
	public UndergradSummary(String TODO){
		
		
	}
	
	
	/********************************************************************
	 * Accessors: getCurrentTerm, getCumulative, getTransfer, getOverall
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public UndergradSummaryDetail getCurrentTerm(){ return this.currentTerm; }
	public UndergradSummaryDetail getCumulative(){ return this.cumulative; }
	public UndergradSummaryDetail getTransfer(){ return this.transfer; }
	public UndergradSummaryDetail getOverall(){ return this.overall; }
	
	
}


/********************************************************************
 * Class: UndergradSummaryDetail
 * Purpose: details of an undergrad summary
/*******************************************************************/
class UndergradSummaryDetail{
	
	// Properties
	private double attempted;
	private double earned;
	private double GPAHours;
	private double qualityPoints;
	private double GPA;
	
	
	/********************************************************************
	 * Constructor: UndergradSummaryDetail
	 * Purpose: create default undergrad summary detail
	/*******************************************************************/
	public UndergradSummaryDetail(){
		
		// Initialize
		this.attempted = 0;
		this.earned = 0;
		this.GPAHours = 0;
		this.qualityPoints = 0;
		this.GPA = 0;
	}
	
	
	/********************************************************************
	 * Constructor: UndergradSummaryDetail
	 * Purpose: create undergrad summary detail with parameters
	/*******************************************************************/
	public UndergradSummaryDetail(double attempted, double earned, double GPAHours, double qualityPoints, double GPA){
		
		// Set properties
		this.attempted = attempted;
		this.earned = earned;
		this.GPAHours = GPAHours;
		this.qualityPoints = qualityPoints;
		this.GPA = GPA;
	}
	
	/********************************************************************
	 * Accessors: getAttempted, getEarned, getGPAHours, getQualityPoints, getGPA
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public double getAttempted(){ return this.attempted; }
	public double getEarned(){ return this.earned; }
	public double getGPAHours(){ return this.GPAHours; }
	public double getQualityPoints(){ return this.qualityPoints; }
	public double getGPA(){ return this.GPA; }
	
	
	/********************************************************************
	 * Mutators: setAttempted, setEarned, setGPAHours, setQualityPoints, setGPA
	 * Purpose: set the corresponding data
	/*******************************************************************/
	public void setAttempted(double attempted){ this.attempted = attempted; }
	public void setEarned(double earned){ this.earned = earned; }
	public void setGPAHours(double GPAHours){ this.GPAHours = GPAHours; }
	public void setQualityPoints(double qualityPoints){ this.qualityPoints = qualityPoints; }
	public void setGPA(double GPA){ this.GPA = GPA; }
	
}