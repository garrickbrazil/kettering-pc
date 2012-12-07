import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/********************************************************************
 * Class: Grade
 * Purpose: stores grades for a single class
/*******************************************************************/
public class Grade {
	
	// Properties
	private String courseName;
	private List<GradeItem> gradeItems;
	private boolean validCourseGrade;
	private double adjPointsPossible;
	private double adjTotal;
	
	
	/********************************************************************
	 * Constructor: Grade()
	 * Purpose: create a grade object
	 * Parameters:
	 *		String courseName: name of the course
	 *		String details: HTML details of course grade items
	/*******************************************************************/
	public Grade(String courseName, String details){
		
		// Initialize properties
		this.courseName = courseName;
		this.gradeItems = new ArrayList<GradeItem>();
		this.validCourseGrade = false;
		this.adjPointsPossible = 0;
		this.adjTotal = 0;
		
		// Document
		Document doc = Jsoup.parse(details);
		
		// Loaded correctly?
		if (doc.getElementById("pageTitleText").text().equalsIgnoreCase("My Grades")){
			
			Elements gradeElements = doc.getElementsByTag("tbody").get(0).children();
			
			// Remove title row
			gradeElements.remove(0);
			
			
			// Grade Items
			for (int i = 0; i < gradeElements.size(); i++){
				
				// Create
				this.gradeItems.add(new GradeItem(gradeElements.get(i).children()));
				GradeItem current = this.gradeItems.get(i);
				
				// Adjusted total & points possible
				if (current.getValidGrade() && !(current.getGradeName().equalsIgnoreCase("Total")|| current.getGradeName().equalsIgnoreCase("Weighted Total"))) { 
					
					this.validCourseGrade = true;
					
					// Add count
					this.adjTotal += this.gradeItems.get(i).getScore();
					this.adjPointsPossible += this.gradeItems.get(i).getPointsPossible();
				}
			}
		}
	}
	
	
	/********************************************************************
	 * Method: toString
	 * Purpose: format object into a string
	/*******************************************************************/
	public String toString(){
		String gradeStr = "";
				
		if(this.validCourseGrade){
			
			gradeStr = "[" + this.courseName + "]\n";
			
			for(int j = 0; j < this.gradeItems.size(); j++){
				GradeItem currentDetail = this.gradeItems.get(j);
				if (currentDetail.getValidGrade()){
					gradeStr += "\n" + currentDetail.getGradeName();
					gradeStr += " Score: " + currentDetail.getScore();
					gradeStr += " Possible: " + currentDetail.getPointsPossible();
					gradeStr += " Percentage: " + Math.round(currentDetail.getScore()/currentDetail.getPointsPossible()*100) + "%";
				}	
			}
			
			gradeStr += "\n\nAdjusted Total: " + this.adjTotal + "\n";
			gradeStr += "Adjusted Points Possible: " + this.adjPointsPossible + "\n";
			gradeStr += "Percentage: " + Math.round(this.adjTotal/this.adjPointsPossible * 100) + "%\n";
		}

		return gradeStr;
	}
	
	
	/********************************************************************
	 * Accessors: getCourseName, getValidGrade, getScore, getPointsPossible
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getCourseName() { return this.courseName; }
	public boolean getValidCourseGrade() { return this.validCourseGrade; }
	public double getAdjTotal() { return this.adjTotal; }
	public List<GradeItem> getGradeItems() { return this.gradeItems; }
	public double getAdjPointsPossible() { return this.adjPointsPossible; }
}
