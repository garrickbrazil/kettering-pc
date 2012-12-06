import org.jsoup.select.Elements;

/********************************************************************
 * Class: GradeDetail
 * Purpose: stores 1 detailed grade
/*******************************************************************/
public class GradeItem {

	private String gradeName;
	private boolean validGrade;
	private double score;
	private double pointsPossible;
	
	/********************************************************************
	 * Constructor: GradeDetail
	 * Purpose: create a grade detail object
	 * Parameters:
	 * 		Element gradeDetail: the element containing DOM information
	/*******************************************************************/
	public GradeItem(Elements gradeDetail){
		
		
		this.gradeName = gradeDetail.get(0).text();
		this.validGrade = true;
		
		try {
			this.score = Double.parseDouble(gradeDetail.get(5).text());
		}
		
		catch(Exception e) {
			this.score = -1;
			this.validGrade = false;
		}
		
		try {
			this.pointsPossible = Double.parseDouble(gradeDetail.get(6).text());
		}
		
		catch(Exception e) {
			this.pointsPossible = -1;
			this.validGrade = false;
		}
	}
	
	/********************************************************************
	 * Accessors: getGradeName, getValidGrade, getScore, getPointsPossible
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getGradeName() { return this.gradeName; }
	public boolean getValidGrade() { return this.validGrade; }
	public double getScore() { return this.score; }
	public double getPointsPossible() { return this.pointsPossible; }
	
}
