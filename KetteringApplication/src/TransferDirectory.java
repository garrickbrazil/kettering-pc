import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

/********************************************************************
 * Class: TransferDirectory
 * Purpose: search transfer courses from various universities
/***********************************************f********************/
public class TransferDirectory {
	
	// Properties
	private TransferCourses results;
	private DefaultHttpClient client;
	private CollegeOptions options;
	
	/********************************************************************
	 * Constructor: TransferDirectory
	 * Purpose: create default transfer directory object
	/*******************************************************************/
	public TransferDirectory(){
		
		this.client = new DefaultHttpClient();
		this.results = new TransferCourses();
		
		try{
			
			// Execute
			HttpGet listGet = new HttpGet("https://drupal.kettering.edu:8443/kumobile/rest/ces/college/list");
			HttpResponse response = this.client.execute(listGet);
			String json = HTMLParser.parse(response);
			
			// Set JSON
			this.options =  new Gson().fromJson(json, CollegeOptions.class);
		}
		
		catch(Exception e) { this.options = new CollegeOptions(); }
		
	}
	
	/********************************************************************
	 * Accessors: getResults, getOptions
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public List<TransferCourse> getResults(){ return this.results.getCourses(); }
	public List<CollegeOption> getOptions(){ return this.options.getOptions(); }
	
	
	
	/********************************************************************
	 * Method: searchByCollege()
	 * Purpose: search by college
	/*******************************************************************/
	public void searchByCollege(String code){
		
		this.results = new TransferCourses();
		
		try{
			
			// Execute
			HttpGet transferGet = new HttpGet("https://drupal.kettering.edu:8443/kumobile/rest/ces/credits/sbgi/" + code);
			HttpResponse response = this.client.execute(transferGet);	
			String json = HTMLParser.parse(response);
			
			// JSON
			this.results =  new Gson().fromJson(json, TransferCourses.class);	
			for(TransferCourse course : this.results.getCourses()) course.fixWhiteSpace();
			
			
		}
		catch (Exception e){ this.results = new TransferCourses(); }	
	}
	
	
	
	/********************************************************************
	 * Method: searchByCourse()
	 * Purpose: search by course
	/*******************************************************************/
	public void searchByCourse(String course){
		
		this.results = new TransferCourses();
		
		
		try{
			
			HttpPost searchCourse = new HttpPost("http://asection.ketteringdeltachi.org/krunal/kutrans/results.php");
			
	        // Parameters
	        List <NameValuePair> parameters = new ArrayList <NameValuePair>();
	        parameters.add(new BasicNameValuePair("input", course));
	        searchCourse.setEntity(new UrlEncodedFormEntity(parameters));
	        
	        
	        // Execute
	        HttpResponse response = this.client.execute(searchCourse);
	        String html = HTMLParser.parse(response);
	        
	        
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/kutrans.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"kutrans.html\".");
			
			Elements table = Jsoup.parse(html).getElementsByTag("tbody");
			
			// Correct format
			if(table.size() > 0 && table.get(0).getElementsByTag("tr").size() > 0) {
				
				Elements rows = table.get(0).getElementsByTag("tr");
				rows.remove(0);
				
				
				// All Rows
				for(Element row : rows){
					
					if(row.getElementsByTag("td").size() == 6){
						
						Elements td = row.getElementsByTag("td");
						
						// Properties
						String transID = td.get(0).text();
						String college = td.get(1).text();
						String kuID = td.get(2).text();
						String kuTitle = td.get(3).text();
						String credits = td.get(4).text();
						String comment = td.get(5).text();
						
						// Add
						this.results.getCourses().add(new TransferCourse(college, transID, kuID, kuTitle, credits, comment));
						
					}
				}
				
			}
	        
		}
		
		catch(Exception e){}
	}
	
}



/********************************************************************
 * Class: TransferCourses
 * Purpose: holds transfer courses
/*******************************************************************/
class TransferCourses{
	
	// Properties
	private List<TransferCourse> entries;
	
	
	public TransferCourses(){
		this.entries = new ArrayList<TransferCourse>();
	}
	
	
	// Accessors
	public List<TransferCourse> getCourses(){ return this.entries; }
}


/********************************************************************
 * Class: TransferCourse
 * Purpose: holds a single transfer course
/*******************************************************************/
class TransferCourse{
	
	// Properties
	private String institution;
	private String trnscrse;
	private String kucourse;
	private String kucoursetitle;
	private String credits;
	private String sbgi;
	private String comment;
	
	
	/********************************************************************
	 * Constructor: TransferCourse
	 * Purpose: holds a single transfer course
	/*******************************************************************/
	public TransferCourse(String college, String transID, String kuID, String kuTitle, String credits, String comment){
		
		// Set properties
		this.institution = college;
		this.trnscrse = transID;
		this.kucourse = kuID;
		this.kucoursetitle = kuTitle;
		this.credits = credits;
		this.comment = comment;
	}
	
	
	// Accessors
	public String getInstitution(){ return this.institution; }
	public String getTitle(){ return this.kucoursetitle; }
	public String getCredits(){ return this.credits; }
	public String getComment(){ return this.comment; }
	public String getCode(){ return this.sbgi; }
	public String getKUCourseID(){ return this.kucourse; }
	public String getTransferID(){ return this.trnscrse; }
	
	
	/********************************************************************
	 * Method: fixWhiteSpace
	 * Purpose: removes unnecessary white space
	/*******************************************************************/
	public void fixWhiteSpace(){
		
		if(this.institution!=null) this.institution = this.institution.replaceAll("\\d\\d\\d\\d\\d\\d\\s\\s+", "");
		if(this.trnscrse!=null) this.trnscrse = this.trnscrse.replaceAll("\\s\\s+", "");
		if(this.kucourse!=null) this.kucourse = this.kucourse.replaceAll("\\s\\s+", "");
		if(this.kucoursetitle!=null) this.kucoursetitle = this.kucoursetitle.replaceAll("\\s\\s+", "");
		if(this.credits!=null) this.credits = this.credits.replaceAll("\\s\\s+", "");
		if(this.sbgi!=null) this.sbgi = this.sbgi.replaceAll("\\s\\s+", "");
		if(this.comment!=null) this.comment = this.comment.replaceAll("\\s\\s+", "");
	}
	
	
	
	/********************************************************************
	 * Method: toString()
	 * Purpose: converts the data into a formatted string
	/*******************************************************************/
	public String toString(){
		
		String transStr = "";
		
		
		// Add valid properties
		if(this.getInstitution() != null) transStr += "Institution: " + this.getInstitution() + " ";  
		if(this.getTitle() != null) transStr += "Title: " + this.getTitle() + " ";
		if(this.getCredits() != null) transStr += "Credits: " + this.getCredits() + " ";
		if(this.getComment() != null) transStr += "Comment: " + this.getComment() + " ";
		if(this.getCode() != null) transStr += "Code: " + this.getCode() + " ";
		if(this.getKUCourseID() != null) transStr += "KUCourseID: " + this.getKUCourseID() + " ";
		if(this.getTransferID() != null) transStr += "TransferID: " + this.getTransferID() + " ";
		
		return transStr;
	}	
}


/********************************************************************
 * Class: CollegeOptions
 * Purpose: holds college options
/*******************************************************************/
class CollegeOptions{
	
	// Properties
	private List<CollegeOption> entries;
	
	
	/********************************************************************
	 * Constructor: CollegeOptions
	 * Purpose: creates college options
	/*******************************************************************/
	public CollegeOptions(){
		this.entries = new ArrayList<CollegeOption>();
	}
	
	// Accessors
	public List<CollegeOption> getOptions(){ return this.entries; }
}


/********************************************************************
 * Class: CollegeOption
 * Purpose: holds a single college option
/*******************************************************************/
class CollegeOption{
	
	// Properties
	private String ku_trnscrse_sbgi_code;
	private String stvsbgi_desc;
	
	// Accessors
	public String getCode(){ return this.ku_trnscrse_sbgi_code; }
	public String getName(){ return this.stvsbgi_desc; }
	
	/********************************************************************
	 * Method: toString()
	 * Purpose: converts the data into a formatted string
	/*******************************************************************/
	public String toString(){
		return this.getName();
	}
	
}
