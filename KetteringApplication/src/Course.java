import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/********************************************************************
 * Class: Course
 * Purpose: holds all data for a class
/*******************************************************************/
public class Course {
	
	// Properties
	private String courseName;
	private String courseID;
	private String section;
	private String instructor;
	private String location;
	private String dateRange;
	private String time;
	private String days;
	private double credits;
	private int crn;
	
	
	/********************************************************************
	 * Constructor: Course
	 * Purpose: creates a course with blank information
	/*******************************************************************/
	public Course(){
		
		// Defaults
		this.courseName = "";
		this.courseID = "";
		this.section = "";
		this.crn = 0;
		this.instructor = "";
		this.credits = 0;
		this.time = "";
		this.days = "";
		this.location = "";
		this.dateRange = "";
		
	}
	

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
		
		if(courseInfo.size() > 0 && timeInfo.size() > 0 && course.getElementsByClass("captiontext").size() > 0 && (course.getElementsByClass("captiontext").get(0).text().split("\\s[-]\\s").length == 3 || course.getElementsByClass("captiontext").get(0).text().split("\\s[-]\\s").length == 4)){
			
			String[] courseTitle = course.getElementsByClass("captiontext").get(0).text().split("\\s[-]\\s");
			
			if(courseTitle.length == 3){
			
				// Title info
				this.courseName = courseTitle[0];
				this.courseID = courseTitle[1];
				this.section = courseTitle[2];
			}	
			
			else{
			
				// First hyphen is part of name
				this.courseName = courseTitle[0] + "-" + courseTitle[1];
				this.courseID = courseTitle[2];
				this.section = courseTitle[3];
			}
			
			
			// Course info
			this.crn = Integer.parseInt(courseInfo.get(1).text());
			this.instructor = courseInfo.get(3).text();
			this.credits = Double.parseDouble(courseInfo.get(5).text());
			
			// Course times
			this.time = timeInfo.get(1).text();
			this.days = timeInfo.get(2).text();
			this.location = timeInfo.get(3).text();
			this.dateRange = timeInfo.get(4).text();
		}
		
		// Wrong format
		else {
			
			// Defaults
			this.courseName = "";
			this.courseID = "";
			this.section = "";
			this.crn = 0;
			this.instructor = "";
			this.credits = 0;
			this.time = "";
			this.days = "";
			this.location = "";
			this.dateRange = "";
		}
		
	}
	
	/********************************************************************
	 * Accessors: getCourseName(), getCRN(), getInstructor(), getCredits()
	 * 		getTime(), getDays(), getLocation(), getDateRange()
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getCourseName(){ return this.courseName; }
	public String getCourseID(){ return this.courseID; }
	public String getSection(){ return this.section; }
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
	public void setCourseID(String courseID){ this.courseID = courseID; }
	public void setSection(String section){ this.section = section; }
	
	
	/********************************************************************
	 * Method: toString()
	 * Purpose: converts the data into a formatted string
	 * Returns:
	 * 		String courseInfo: contains all class information
	/*******************************************************************/
	public String toString(){
		
		String courseInfo = "";
		courseInfo += "Class name: " + this.courseName;
		courseInfo += "\nCourse ID: " + this.courseID;
		courseInfo += "\nCourse Section: " + this.section;
		courseInfo += "\ncrn: " + this.crn;
		courseInfo += "\nInstructor: " + this.instructor;
		courseInfo += "\nCredits: " + this.credits;
		courseInfo += "\nMeeting Time: " + this.time;
		courseInfo += "\nDays: " + this.days;
		courseInfo += "\nLocation: " + this.location;
		courseInfo += "\nDate range: " + this.dateRange;
		
		return courseInfo;
		
	}
	
}
