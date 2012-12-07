/********************************************************************
 * Class: DailyMenu
 * Purpose: holds a single days menu
/*******************************************************************/
public class DailyMenu {

	private Food lunch;
	private Food dinner;
	
	/********************************************************************
	 * Constructor: DailyMenu
	 * Purpose: creates a default daily menu
	/*******************************************************************/
	public DailyMenu(){
		this.lunch = new Food();
		this.dinner = new Food();
	}
	
	/********************************************************************
	 * Accessors: getLunch, getDinner
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public Food getLunch() { return this.lunch; }
	public Food getDinner(){ return this.dinner; }
	
	
}
