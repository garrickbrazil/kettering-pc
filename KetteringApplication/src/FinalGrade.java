/********************************************************************
 * Class: FinalGrade
 * Purpose: holds a single final grade and all its properties
/*******************************************************************/
public class FinalGrade {
	
	// Properties
	private int crn;
	private String subject;
	private int courseID;
	private int section;
	private String title;
	private String campus;
	private String grade;
	private double attemptedCredits;
	private double earnedCredits;
	private double GPAHours;
	private double qualityPoints;
	
	/********************************************************************
	 * Constructor: FinalGrade
	 * Purpose: create a default final grade object
	/*******************************************************************/
	public FinalGrade(){
		
		// Initialize
		this.crn = 0;
		this.subject = "";
		this.courseID = 0;
		this.section = 0;
		this.title = "";
		this.campus = "";
		this.grade = "";
		this.attemptedCredits = 0;
		this.earnedCredits = 0;
		this.GPAHours = 0;
		this.qualityPoints = 0;
		
	}
	
	/********************************************************************
	 * Constructor: UndergradSummary
	 * Purpose: create a final grade object with parameters
	/*******************************************************************/
	public FinalGrade(String todo){
		
	}
	
	/********************************************************************
	 * Accessors: getCrn, getSubject, getCourseId, getSection, getTitle, 
	 * 		getCampus, getGrade, getAttemptedCredits, getEarnedCredits,
	 * 		getGPAHours, getQualityPoints
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public int getCrn(){ return this.crn; }
	public String getSubject(){ return this.subject; }
	public int getCourseId(){ return this.courseID; }
	public int getSection(){ return this.section; }
	public String getTitle(){ return this.title; }
	public String getCampus(){ return this.campus; }
	public String getGrade(){ return this.grade; }
	public double getAttemptedCredits(){ return this.attemptedCredits; }
	public double getEarnedCredits(){ return this.earnedCredits; }
	public double getGPAHours(){ return this.GPAHours; }
	public double getQualityPoints(){ return this.qualityPoints; }

}
