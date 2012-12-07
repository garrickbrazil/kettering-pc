import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/********************************************************************
 * Class: Course
 * Purpose: holds all data for a class
/*******************************************************************/
public class Course {
	
	// Properties
	private String courseName;
	private String instructor;
	private String location;
	private String dateRange;
	private String time;
	private String days;
	private double credits;
	private int crn;
	

	/********************************************************************
	 * Constructor: Course
	 * Purpose: creates a course given the course info and times
	 * Parameters:
	 * 		Element course: DOM object containing course info
	 * 		Element times: DOM object containing course times
	/*******************************************************************/
	public Course(Element course, Element times){
		
		Elements courseInfo = course.getElementsByTag("td");
		Elements timeInfo = times.getElementsByTag("td");
		
		// Course info
		this.courseName = course.getElementsByClass("captiontext").get(0).text();
		this.crn = Integer.parseInt(courseInfo.get(1).text());
		this.instructor = courseInfo.get(3).text();
		this.credits = Double.parseDouble(courseInfo.get(5).text());
		
		// Course times
		this.time = timeInfo.get(1).text();
		this.days = timeInfo.get(2).text();
		this.location = timeInfo.get(3).text();
		this.dateRange = timeInfo.get(4).text();
	}
	
	
	/********************************************************************
	 * Accessors: getCourseName(), getCRN(), getInstructor(), getCredits()
	 * 		getTime(), getDays(), getLocation(), getDateRange()
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getCourseName(){ return this.courseName; }
	public String getInstructor(){ return this.instructor; }
	public String getLocation() { return this.location; }
	public String getDateRange() { return this.dateRange; }
	public String getTime(){ return this.time; }
	public String getDays() { return this.days; }
	public double getCredits(){ return this.credits; }
	public int getCRN(){ return this.crn; }
	
	
	/********************************************************************
	 * Mutators: setCourseName(), setCRN(), setInstructor(), setCredits()
	 * 		setTime(), setDays(), setLocation(), setDateRange()
	 * Purpose: set the corresponding data
	/*******************************************************************/
	public void setCourseName(String courseName){ this.courseName = courseName; }
	public void setInstructor(String instructor){ this.instructor = instructor; }
	public void setLocation(String location) { this.location = location; }
	public void setDateRange(String dateRange) { this.dateRange = dateRange; }
	public void setTime(String time){ this.time = time; }
	public void setDays(String days) { this.days = days; }
	public void setCredits(double credits){ this.credits = credits; }
	public void setCRN(int crn){ this.crn = crn; }
	
	
	
	/********************************************************************
	 * Method: toString()
	 * Purpose: converts the data into a formatted string
	 * Returns:
	 * 		String courseInfo: contains all class information
	/*******************************************************************/
	public String toString(){
		
		String courseInfo = "";
		courseInfo += "Information\n";
		courseInfo += "\n\tClass name: " + this.courseName;
		courseInfo += "\n\tcrn: " + this.crn;
		courseInfo += "\n\tInstructor: " + this.instructor;
		courseInfo += "\n\tCredits: " + this.credits;
		courseInfo += "\n\tMeeting Time: " + this.time;
		courseInfo += "\n\tDays: " + this.days;
		courseInfo += "\n\tLocation: " + this.location;
		courseInfo += "\n\tDate range: " + this.dateRange;
		
		return courseInfo;
		
	}
	
}
