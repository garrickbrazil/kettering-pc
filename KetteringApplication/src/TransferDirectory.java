import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

/********************************************************************
 * Class: TransferDirectory
 * Purpose: search transfer courses from various universities
/*******************************************************************/
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
	 * Method: search()
	 * Purpose: search with given parameter
	/*******************************************************************/
	public void search(String code){
		
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
