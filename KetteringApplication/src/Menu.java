import java.io.IOException;
import java.io.PrintWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/********************************************************************
 * Class: Menu
 * Purpose: stores 1 week lunch and dinner menu
/*******************************************************************/
public class Menu {

	private DailyMenu sunday;
	private DailyMenu monday;
	private DailyMenu tuesday;
	private DailyMenu wednesday;
	private DailyMenu thursday;
	private DailyMenu friday;
	private DailyMenu saturday;

	/********************************************************************
	 * Constructor: Menu
	 * Purpose: create a default menu item
	/*******************************************************************/
	public Menu() throws ClientProtocolException, IOException{
		
		
		this.sunday = new DailyMenu();
		this.monday = new DailyMenu();
		this.tuesday = new DailyMenu();
		this.wednesday = new DailyMenu();
		this.thursday = new DailyMenu();
		this.friday = new DailyMenu();
		this.saturday = new DailyMenu();
		
		
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet menuMethod = new HttpGet("https://www.kettering.edu/offices-facilities/campus-dining/daily-menus");
		HttpResponse response = client.execute(menuMethod);
		
		String html = HTMLParser.parseResponse(response);
		
		// Write to file
		PrintWriter printer = new PrintWriter("HTML Responses/menu.html");
		printer.print(html);	    	
		printer.close();
		
		System.out.println("Successfully stored menu to \"HTML Responses/menu.html\".");
		
		Elements tables = Jsoup.parse(html).getElementsByTag("table");
		if (tables.size() >=2){
			Elements lunch = tables.get(0).getElementsByTag("tr");
			Elements dinner = tables.get(1).getElementsByTag("tr");
			
			// Delete first two rows
			lunch.remove(0); lunch.remove(0);
			dinner.remove(0); dinner.remove(0);
			
			Elements soups;
			Elements pastaStations;
			Elements grills;
			Elements pizzas;
			Elements delis;
			Elements expos;
			Elements desserts;
			
			// Lunch
			if(lunch.size() == 7){
				
				// Soup
				soups = lunch.get(0).getElementsByTag("td");
				soups.remove(0);
				this.sunday.getLunch().setSoup(soups.get(0).text());
				this.monday.getLunch().setSoup(soups.get(1).text());
				this.tuesday.getLunch().setSoup(soups.get(2).text());
				this.wednesday.getLunch().setSoup(soups.get(3).text());
				this.thursday.getLunch().setSoup(soups.get(4).text());
				this.friday.getLunch().setSoup(soups.get(5).text());
				this.saturday.getLunch().setSoup(soups.get(6).text());
				
				// Pasta Station
				pastaStations = lunch.get(1).getElementsByTag("td");
				pastaStations.remove(0);
				this.sunday.getLunch().setPastaStation(pastaStations.get(0).text());
				this.monday.getLunch().setPastaStation(pastaStations.get(1).text());
				this.tuesday.getLunch().setPastaStation(pastaStations.get(2).text());
				this.wednesday.getLunch().setPastaStation(pastaStations.get(3).text());
				this.thursday.getLunch().setPastaStation(pastaStations.get(4).text());
				this.friday.getLunch().setPastaStation(pastaStations.get(5).text());
				this.saturday.getLunch().setPastaStation(pastaStations.get(6).text());
				
				// Grills
				grills = lunch.get(2).getElementsByTag("td");
				grills.remove(0);
				this.sunday.getLunch().setGrill(grills.get(0).text());
				this.monday.getLunch().setGrill(grills.get(1).text());
				this.tuesday.getLunch().setGrill(grills.get(2).text());
				this.wednesday.getLunch().setGrill(grills.get(3).text());
				this.thursday.getLunch().setGrill(grills.get(4).text());
				this.friday.getLunch().setGrill(grills.get(5).text());
				this.saturday.getLunch().setGrill(grills.get(6).text());
				
				// Pizza
				pizzas = lunch.get(3).getElementsByTag("td");
				pizzas.remove(0);
				this.sunday.getLunch().setPizza(pizzas.get(0).text());
				this.monday.getLunch().setPizza(pizzas.get(1).text());
				this.tuesday.getLunch().setPizza(pizzas.get(2).text());
				this.wednesday.getLunch().setPizza(pizzas.get(3).text());
				this.thursday.getLunch().setPizza(pizzas.get(4).text());
				this.friday.getLunch().setPizza(pizzas.get(5).text());
				this.saturday.getLunch().setPizza(pizzas.get(6).text());
				
				// Delis
				delis = lunch.get(4).getElementsByTag("td");
				delis.remove(0);
				this.sunday.getLunch().setDeli(delis.get(0).text());
				this.monday.getLunch().setDeli(delis.get(1).text());
				this.tuesday.getLunch().setDeli(delis.get(2).text());
				this.wednesday.getLunch().setDeli(delis.get(3).text());
				this.thursday.getLunch().setDeli(delis.get(4).text());
				this.friday.getLunch().setDeli(delis.get(5).text());
				this.saturday.getLunch().setDeli(delis.get(6).text());
				
				// Expos
				expos = lunch.get(5).getElementsByTag("td");
				expos.remove(0);
				this.sunday.getLunch().setExpo(expos.get(0).text());
				this.monday.getLunch().setExpo(expos.get(1).text());
				this.tuesday.getLunch().setExpo(expos.get(2).text());
				this.wednesday.getLunch().setExpo(expos.get(3).text());
				this.thursday.getLunch().setExpo(expos.get(4).text());
				this.friday.getLunch().setExpo(expos.get(5).text());
				this.saturday.getLunch().setExpo(expos.get(6).text());
				
				// Desserts
				desserts = lunch.get(6).getElementsByTag("td");
				desserts.remove(0);
				this.sunday.getLunch().setDessert(desserts.get(0).text());
				this.monday.getLunch().setDessert(desserts.get(1).text());
				this.tuesday.getLunch().setDessert(desserts.get(2).text());
				this.wednesday.getLunch().setDessert(desserts.get(3).text());
				this.thursday.getLunch().setDessert(desserts.get(4).text());
				this.friday.getLunch().setDessert(desserts.get(5).text());
				this.saturday.getLunch().setDessert(desserts.get(6).text());				
			}
			
			// Dinner
			if(dinner.size() == 7){
				
				// Soup
				soups = dinner.get(0).getElementsByTag("td");
				soups.remove(0);
				this.sunday.getDinner().setSoup(soups.get(0).text());
				this.monday.getDinner().setSoup(soups.get(1).text());
				this.tuesday.getDinner().setSoup(soups.get(2).text());
				this.wednesday.getDinner().setSoup(soups.get(3).text());
				this.thursday.getDinner().setSoup(soups.get(4).text());
				this.friday.getDinner().setSoup(soups.get(5).text());
				this.saturday.getDinner().setSoup(soups.get(6).text());
				
				// Pasta Station
				pastaStations = dinner.get(1).getElementsByTag("td");
				pastaStations.remove(0);
				this.sunday.getDinner().setPastaStation(pastaStations.get(0).text());
				this.monday.getDinner().setPastaStation(pastaStations.get(1).text());
				this.tuesday.getDinner().setPastaStation(pastaStations.get(2).text());
				this.wednesday.getDinner().setPastaStation(pastaStations.get(3).text());
				this.thursday.getDinner().setPastaStation(pastaStations.get(4).text());
				this.friday.getDinner().setPastaStation(pastaStations.get(5).text());
				this.saturday.getDinner().setPastaStation(pastaStations.get(6).text());
				
				// Grills
				grills = dinner.get(2).getElementsByTag("td");
				grills.remove(0);
				this.sunday.getDinner().setGrill(grills.get(0).text());
				this.monday.getDinner().setGrill(grills.get(1).text());
				this.tuesday.getDinner().setGrill(grills.get(2).text());
				this.wednesday.getDinner().setGrill(grills.get(3).text());
				this.thursday.getDinner().setGrill(grills.get(4).text());
				this.friday.getDinner().setGrill(grills.get(5).text());
				this.saturday.getDinner().setGrill(grills.get(6).text());
				
				// Pizza
				pizzas = dinner.get(3).getElementsByTag("td");
				pizzas.remove(0);
				this.sunday.getDinner().setPizza(pizzas.get(0).text());
				this.monday.getDinner().setPizza(pizzas.get(1).text());
				this.tuesday.getDinner().setPizza(pizzas.get(2).text());
				this.wednesday.getDinner().setPizza(pizzas.get(3).text());
				this.thursday.getDinner().setPizza(pizzas.get(4).text());
				this.friday.getDinner().setPizza(pizzas.get(5).text());
				this.saturday.getDinner().setPizza(pizzas.get(6).text());
				
				// Delis
				delis = dinner.get(4).getElementsByTag("td");
				delis.remove(0);
				this.sunday.getDinner().setDeli(delis.get(0).text());
				this.monday.getDinner().setDeli(delis.get(1).text());
				this.tuesday.getDinner().setDeli(delis.get(2).text());
				this.wednesday.getDinner().setDeli(delis.get(3).text());
				this.thursday.getDinner().setDeli(delis.get(4).text());
				this.friday.getDinner().setDeli(delis.get(5).text());
				this.saturday.getDinner().setDeli(delis.get(6).text());
				
				// Expos
				expos = dinner.get(5).getElementsByTag("td");
				expos.remove(0);
				this.sunday.getDinner().setExpo(expos.get(0).text());
				this.monday.getDinner().setExpo(expos.get(1).text());
				this.tuesday.getDinner().setExpo(expos.get(2).text());
				this.wednesday.getDinner().setExpo(expos.get(3).text());
				this.thursday.getDinner().setExpo(expos.get(4).text());
				this.friday.getDinner().setExpo(expos.get(5).text());
				this.saturday.getDinner().setExpo(expos.get(6).text());
				
				// Desserts
				desserts = dinner.get(6).getElementsByTag("td");
				desserts.remove(0);
				this.sunday.getDinner().setDessert(desserts.get(0).text());
				this.monday.getDinner().setDessert(desserts.get(1).text());
				this.tuesday.getDinner().setDessert(desserts.get(2).text());
				this.wednesday.getDinner().setDessert(desserts.get(3).text());
				this.thursday.getDinner().setDessert(desserts.get(4).text());
				this.friday.getDinner().setDessert(desserts.get(5).text());
				this.saturday.getDinner().setDessert(desserts.get(6).text());				
			}
		}
	}
	
	/********************************************************************
	 * Accessors: getSunday, getMonday, getTuesday, getWednesday,
	 * 		getThursday, getFriday, getSaturday
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public DailyMenu getSunday() { return this.sunday; }
	public DailyMenu getMonday() { return this.monday; }
	public DailyMenu getTuesday() { return this.tuesday; }
	public DailyMenu getWednesday() { return this.wednesday; }
	public DailyMenu getThursday() { return this.thursday; }
	public DailyMenu getFriday() { return this.friday; }
	public DailyMenu getSaturday() { return this.saturday; }
	
}
