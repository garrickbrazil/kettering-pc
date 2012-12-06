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
	
	private String courseName;
	private double total;
	private double pointsPossible;
	private boolean validCourseGrade;
	private List<GradeItem> gradeItems;
	
	
	/********************************************************************
	 * Constructor: Grade()
	 * Purpose: create a grade object
	/*******************************************************************/
	public Grade(String courseName, String details){
		
		this.courseName = courseName;
		this.total = 0;
		this.pointsPossible = 0;
		this.gradeItems = new ArrayList<GradeItem>();
		this.validCourseGrade = false;
		
		Document doc = Jsoup.parse(details);
		if (!doc.getElementById("pageTitleText").text().equalsIgnoreCase("error")){
			Elements gradeElements = doc.getElementsByTag("tbody").get(0).children();
			gradeElements.remove(0);
			
			for (int i = 0; i < gradeElements.size(); i++){
				this.gradeItems.add(new GradeItem(gradeElements.get(i).children()));
				
				if (this.gradeItems.get(i).getValidGrade() && !(this.gradeItems.get(i).getGradeName().equalsIgnoreCase("Total")|| this.gradeItems.get(i).getGradeName().equalsIgnoreCase("Weighted Total"))) { 
					this.validCourseGrade = true;
					this.total += this.gradeItems.get(i).getScore();
					this.pointsPossible += this.gradeItems.get(i).getPointsPossible();
				}
			}
		}
	}
	
	/********************************************************************
	 * Accessors: getCourseName, getValidGrade, getScore, getPointsPossible
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getCourseName() { return this.courseName; }
	public boolean getValidCourseGrade() { return this.validCourseGrade; }
	public double getTotal() { return this.total; }
	public List<GradeItem> getGradeItems() { return this.gradeItems; }
	public double getPointsPossible() { return this.pointsPossible; }
}
