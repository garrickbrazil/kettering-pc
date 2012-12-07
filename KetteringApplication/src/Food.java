/********************************************************************
 * Class: Food
 * Purpose: holds food items
/*******************************************************************/
public class Food {

	private String soup;
	private String pastaStation;
	private String grill;
	private String pizza;
	private String deli;
	private String expo;
	private String dessert;
	
	
	/********************************************************************
	 * Constructor: Food
	 * Purpose: create food object
	/*******************************************************************/
	public Food(){
		
		this.soup = "";
		this.pastaStation = "";
		this.grill = "";
		this.pizza = "";
		this.deli = "";
		this.expo = "";
		this.dessert = "";
	}
	
	/********************************************************************
	 * Accessors: getSoup, getPastaStation, getGrill, getPizza, getDeli,
	 * 		getExpo, getDessert
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getSoup() { return this.soup; }
	public String getPastaStation() { return this.pastaStation; }
	public String getGrill() { return this.grill; }
	public String getPizza() { return this.pizza; }
	public String getDeli() { return this.deli; }
	public String getExpo() { return this.expo; }
	public String getDessert() { return this.dessert; }
	
	/********************************************************************
	 * Mutuators: setSoup, setPastaStation, setGrill, setPizza, setDeli,
	 * 		setExpo, setDessert
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public void setSoup(String soup) { this.soup = soup; }
	public void setPastaStation(String pastaStation) { this.pastaStation = pastaStation; }
	public void setGrill(String grill) { this.grill = grill; }
	public void setPizza(String pizza) { this.pizza = pizza; }
	public void setDeli(String deli) { this.deli = deli; }
	public void setExpo(String expo) { this.expo = expo; }
	public void setDessert(String dessert) { this.dessert = dessert; }
	
	
}
