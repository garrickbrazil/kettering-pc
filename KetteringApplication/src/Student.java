import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;




/********************************************************************
 * Class: Student
 * Purpose: Hold all student data and connections
/*******************************************************************/
public class Student {
	
	// Properties
	private String username;
	private String password;
	private DefaultHttpClient clientBanner;
	private DefaultHttpClient clientBlackboard;
	private Transcript transcript;
	private List<Course> courses;
	private List<Grade> grades;
	
	
	/********************************************************************
	 * Constructor: Student()
	 * Purpose: create default student object
	/*******************************************************************/
	public Student(){
		
		// Initialize
		this.username = "";
		this.password = "";
		this.clientBanner = new DefaultHttpClient();
		this.clientBlackboard = new DefaultHttpClient();
		this.courses = new ArrayList<Course>();
		this.grades = new ArrayList<Grade>();
	}
	
	
	/********************************************************************
	 * Constructor: Student(String username, String password)
	 * Purpose: create student object with username and password
	 * Parameters:
	 * 		String username: SID
	 * 		String password: PIN
	/*******************************************************************/
	public Student(String username, String password){
		
		// Initialize
		this.username = username;
		this.password = password;
		this.clientBanner = new DefaultHttpClient();
		this.clientBlackboard = new DefaultHttpClient();
		this.courses = new ArrayList<Course>();
		this.grades = new ArrayList<Grade>();
	}
	
	
	/********************************************************************
	 * Accessors: getUsername(), getPassword()
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getUsername() { return this.username; }
	public String getPassword(){ return this.password; }
	public List<Course> getCourses(){ return this.courses; }
	public List<Grade> getGrades(){ return this.grades; }
	public Transcript getTranscript() { return this.transcript; }
	
	
	
	/********************************************************************
	 * Mutators: setUsername(), setPassword()
	 * Purpose: set the corresponding data
	/*******************************************************************/
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
	
	
	/********************************************************************
	 * Method: loginBanner()
	 * Purpose: logs user into banner web
	/*******************************************************************/
	public void loginBanner(){
		
		try{
			
			HttpGet initialLoad = new HttpGet("http://jweb.kettering.edu/cku1/twbkwbis.P_ValLogin");
			HttpPost login = new HttpPost("http://jweb.kettering.edu/cku1/twbkwbis.P_ValLogin");
			boolean sessionSet = false;
			
			// Initial load
			HttpResponse response = this.clientBanner.execute(initialLoad);
	        HttpEntity entity = response.getEntity();
	        if (entity != null) EntityUtils.consume(entity);	
	        
	        // Parameters
	        List <NameValuePair> parameters = new ArrayList <NameValuePair>();
	        parameters.add(new BasicNameValuePair("sid", this.username));
	        parameters.add(new BasicNameValuePair("PIN", this.password));
	        login.setEntity(new UrlEncodedFormEntity(parameters));
	        
	        // Login
	        response = this.clientBanner.execute(login);
	        entity = response.getEntity();
	        if (entity != null) EntityUtils.consume(entity);
	        
			
	        List<Cookie> cookies = this.clientBanner.getCookieStore().getCookies();
	        
	        // Check cookies
	        for (int i = 0; i < cookies.size(); i++) if (cookies.get(i).getName().equals("SESSID") && !cookies.get(i).getValue().equals("")) sessionSet = true;
	        
	        // Success
	        if (sessionSet) System.out.println("Successfully logged in.");
	        
	        // Failure
	        else { System.out.println("Login failed."); System.exit(-1); }
	        
		}
			
		catch(Exception e){ e.printStackTrace(); }
	}
	
	/********************************************************************
	 * Method: loginBlackboard()
	 * Purpose: logs user into blackboard
	/*******************************************************************/
	public void loginBlackboard(){
		
		try{
			
			HttpGet initialLoad = new HttpGet("https://blackboard.kettering.edu/webapps/login/");
			HttpPost login = new HttpPost("https://blackboard.kettering.edu/webapps/login/");
			
			
			// Parameters
			List <NameValuePair> parameters = new ArrayList <NameValuePair>();
	        parameters.add(new BasicNameValuePair("user_id", this.username));
	        parameters.add(new BasicNameValuePair("password", ""));
	        parameters.add(new BasicNameValuePair("login", "Login"));
	        parameters.add(new BasicNameValuePair("action", "login"));
	        parameters.add(new BasicNameValuePair("remote-user", ""));
	        parameters.add(new BasicNameValuePair("new_loc", "\u00C2\u00A0"));
	        parameters.add(new BasicNameValuePair("auth_type", ""));
	        parameters.add(new BasicNameValuePair("one_time_token", ""));
	        parameters.add(new BasicNameValuePair("encoded_pw", new Base64().encodeAsString(this.password.getBytes())));
	        parameters.add(new BasicNameValuePair("encoded_pw_unicode", ""));
	        login.setEntity(new UrlEncodedFormEntity(parameters));
			
			// Initial load
	        HttpResponse response = this.clientBlackboard.execute(initialLoad);
	        HttpEntity entity = response.getEntity();
	        if (entity != null) EntityUtils.consume(entity);	
	        
	        
	        // Login
	        response = this.clientBlackboard.execute(login);
	        entity = response.getEntity();
	        if (entity != null) EntityUtils.consume(entity);
	        
		}
			
		catch(Exception e){ e.printStackTrace(); }
	}
	
	
	/********************************************************************
	 * Method: storeTranscript()
	 * Purpose: stores transcript to memory
	/*******************************************************************/
	public void storeTranscript(){
		
		// TODO
		this.transcript = new Transcript(this.clientBanner);
	}
	
	
	/********************************************************************
	 * Method: storeSchedule(int term)
	 * Purpose: stores schedule to memory
	 * Parameters:
	 * 		int term: term to get schedule for (eg. 201201)
	/*******************************************************************/
	public void storeSchedule(int term){
		
		try{
			
			HttpGet schedule = new HttpGet("https://jweb.kettering.edu/cku1/bwskfshd.P_CrseSchdDetl?term_in=" + term);
		    
			// Execute
			HttpResponse response = this.clientBanner.execute(schedule);
			String html = HTMLParser.parseResponse(response);
		    
			Document doc = Jsoup.parse(html);
			Elements classSchedules = doc.getElementsByClass("datadisplaytable");
			
			
			// Store Schedules
			for (int i = 0; i < classSchedules.size()/2; i++) this.courses.add(new Course(classSchedules.get(i*2), classSchedules.get(i*2 + 1)));
			
			
			// Write to file
			PrintWriter printer = new PrintWriter("HTML Responses/schedule.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored schedule to \"HTML Responses/schedule.html\".");
			
		}
		
		catch(Exception e){ e.printStackTrace(); }
	}
	
	
	
	
	/********************************************************************
	 * Method: storeGrades()
	 * Purpose: stores grades to memory
	/*******************************************************************/
	public void storeGrades(){
		
		try{
			
			HttpGet grade = new HttpGet("https://blackboard.kettering.edu/webapps/portal/execute/tabs/tabAction?tab_tab_group_id=_104_1&forwardUrl=detach_module%2F_573_1%2F");
			
			// Execute
		    HttpResponse response = this.clientBlackboard.execute(grade);
			String html = HTMLParser.parseResponse(response);
		    
			// Write to file
			PrintWriter printer = new PrintWriter("HTML Responses/grades.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored grades to \"HTML Responses/grades.html\".");
			
			
			// Class grades
			Elements classGrades = Jsoup.parse(html).getElementsByTag("td");
			classGrades.remove(classGrades.size() -1);
			
			// Store grades
			for (int i = 0; i < classGrades.size()/2; i++) {
				
				// Details
				HttpGet gradeDetail = new HttpGet("https://blackboard.kettering.edu" + classGrades.get(i*2+1).childNode(0).childNode(0).attr("href"));
				HttpResponse gradeResponse = this.clientBlackboard.execute(gradeDetail);
				
				// Parameters
				String className = classGrades.get(i*2).text();
				String gradeHTML = HTMLParser.parseResponse(gradeResponse);
				
				// Write to file
				printer = new PrintWriter("HTML Responses/courseGradeDetail" + i + ".html");
				printer.print(gradeHTML);	    	
				printer.close();
				
				System.out.println("Successfully stored \"HTML Responses/courseGradeDetail" + i + ".html\"");
				
				// Create
				this.grades.add(new Grade(className, gradeHTML));
			}
			
		}
		
		catch(Exception e){ e.printStackTrace(); }
	}

}
