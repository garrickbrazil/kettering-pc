import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/********************************************************************
 * Class: Library
 * Purpose: an object able to search kettering's library
/*******************************************************************/
public class Library {

	// Properties
	private DefaultHttpClient client;
	private List<LibraryItem> libraryItems;
	
	/********************************************************************
	 * Constructor: Library
	 * Purpose: creates default library object
	/*******************************************************************/
	public Library(){
		
		this.client = new DefaultHttpClient();
		this.libraryItems = new ArrayList<LibraryItem>();
	}
	
	
	/********************************************************************
	 * Method: search()
	 * Purpose: search with given parameter
	/*******************************************************************/
	public void search(String search){
		
		
		try{

			// Connect
			HttpGet libGet = new HttpGet("http://catalog.palnet.info/uhtbin/cgisirsi/x/0/0/57/5?search_type=search&searchdata1=" + search + "&srchfield1=GENERAL%5ESUBJECT%5EGENERAL%5E%5Ewords+or+phrase&library=KU&SearchNow=Search&user_id=kuweb");
			HttpResponse response = this.client.execute(libGet);
			String html = HTMLParser.parse(response);
			
			
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/Library/librarysearch_page1.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"librarysearch_page1.html\".");
			
			
			Document doc = Jsoup.parse(html);
			Elements hitlist = doc.getElementsByClass("hit_list_item_info");
			
			
			// Add First Page Items
			for(Element item : hitlist) this.libraryItems.add(new LibraryItem(item));
			
			
			Elements pagination = doc.getElementsByClass("searchsummary_pagination");
			
			// Other pages
			if(pagination.size() > 0 && pagination.get(0).getElementsByTag("a").size() > 2 && doc.getElementsByClass("hit_list_form").size() > 0){
				
				Elements otherPages = pagination.get(0).getElementsByTag("a");
				String action = doc.getElementsByClass("hit_list_form").get(0).attr("action") + "?";
				String formType;
				int lastIndex = 1;
				int currentIndex;

				
				// Remove first and last
				otherPages.remove(0); otherPages.remove(otherPages.size()-1);
					
				
				for(Element page : otherPages){
					
					// Page index
					try{ currentIndex = Integer.parseInt(page.text()); }
					catch (Exception e){ currentIndex = -1; return; }
					
					// Too many results :(
					if(Integer.parseInt(page.text()) - lastIndex > 1) return;
					else lastIndex = currentIndex;
					
					
					// Get form_type
					formType = page.attr("href");
					formType = formType.replaceAll("javascript:document.hitlist.form_type.value = \'", "");
					formType = formType.replaceAll("\'; document.hitlist.submit[(][)]", "");
					formType = formType.replaceAll("[\\^]", "%5E");
					
					
					// Execute
					libGet = new HttpGet("http://catalog.palnet.info" + action + "firsthit=&lasthit=&form_type=" + formType);
					response = this.client.execute(libGet);
					html = HTMLParser.parse(response);
					
					
					// Write to file
					printer = new PrintWriter("artifacts/Library/librarysearch_page" + currentIndex + ".html");
					printer.print(html);	    	
					printer.close();
					
					System.out.println("Successfully stored \"librarysearch_page" + currentIndex + ".html\".");
					
					Elements hitlistPage = Jsoup.parse(html).getElementsByClass("hit_list_item_info");
					
					
					// Add First Page Items
					for(Element item : hitlistPage) this.libraryItems.add(new LibraryItem(item));
					
				}
			}
			
		}
		
		catch(Exception e){ this.libraryItems = new ArrayList<LibraryItem>(); }
		
	}
	
	/********************************************************************
	 * Accessors: getLibrary
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public List<LibraryItem> getLibrary(){ return this.libraryItems; }
	
}
