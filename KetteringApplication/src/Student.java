import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private String transcript;
	private DefaultHttpClient clientBanner;
	private DefaultHttpClient clientBlackboard;
	private List<Course> courses;
	private List<CurrentGrade> currentGrades;
	private List<FinalGrade> finalGrades;
	private List<MidtermGrade> midtermGrades;
	private UndergradSummary undergradSummary;
	private AccountTotal accountTotal;
	private Map<Course, String> dynamicCourses;
	
	
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
		this.currentGrades = new ArrayList<CurrentGrade>();
		this.finalGrades = new ArrayList<FinalGrade>();
		this.midtermGrades = new ArrayList<MidtermGrade>();
		this.dynamicCourses = new HashMap<Course, String>();
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
		this.currentGrades = new ArrayList<CurrentGrade>();
		this.finalGrades = new ArrayList<FinalGrade>();
		this.midtermGrades = new ArrayList<MidtermGrade>();
		this.dynamicCourses = new HashMap<Course, String>();
	}
	
	
	/********************************************************************
	 * Accessors: getUsername, getPassword, getCourses, getCurrentGrades
	 * 		getTranscript
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getUsername() { return this.username; }
	public String getPassword(){ return this.password; }
	public List<Course> getCourses(){ return this.courses; }
	public List<CurrentGrade> getCurrentGrades(){ return this.currentGrades; }
	public String getTranscript() { return this.transcript; }
	public List<FinalGrade> getFinalGrades(){ return this.finalGrades; }
	public List<MidtermGrade> getMidtermGrades(){ return this.midtermGrades; }
	public UndergradSummary getUndergradSummary(){ return this.undergradSummary; }
	public AccountTotal getAccountTotal(){ return this.accountTotal; }
	public Map<Course, String> getDynamicCourses(){ return this.dynamicCourses; }
	
	
	
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
		
		try{
			
			// Connect
			HttpGet transcript = new HttpGet("https://jweb.kettering.edu/cku1/ku_web_trans.view_transcript?tprt=SHRTRTC&levl=U");
			HttpResponse response = this.clientBanner.execute(transcript);
			
			String html = HTMLParser.parse(response);
			
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/transcript.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"transcript.html\".");
			
			
			this.transcript = html;
		}
		
		catch(Exception e){ e.printStackTrace(); }
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
			String html = HTMLParser.parse(response);
		    
			Document doc = Jsoup.parse(html);
			Elements classSchedules = doc.getElementsByClass("datadisplaytable");
			
			
			// Store Schedules
			for (int i = 0; i < classSchedules.size()/2; i++) this.courses.add(new Course(classSchedules.get(i*2), classSchedules.get(i*2 + 1)));
			
			
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/schedule.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"schedule.html\".");
			
		}
		
		catch(Exception e){ e.printStackTrace(); }
	}
	
	
	
	/********************************************************************
	 * Method: storeCurrentGrades()
	 * Purpose: stores grades to memory
	/*******************************************************************/
	public void storeCurrentGrades(){
		
		try{
			
			HttpGet grade = new HttpGet("https://blackboard.kettering.edu/webapps/portal/execute/tabs/tabAction?tab_tab_group_id=_104_1&forwardUrl=detach_module%2F_573_1%2F");
			
			// Execute
		    HttpResponse response = this.clientBlackboard.execute(grade);
			String html = HTMLParser.parse(response);
		    
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/grades.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"grades.html\".");
			
			
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
				String gradeHTML = HTMLParser.parse(gradeResponse);
				
				// Write to file
				printer = new PrintWriter("artifacts/Grade Details/gradeDetail" + i + ".html");
				printer.print(gradeHTML);	    	
				printer.close();
				
				System.out.println("Successfully stored \"gradeDetail" + i + ".html\"");
				
				// Create
				this.currentGrades.add(new CurrentGrade(className, gradeHTML));
			}
			
		}
		
		catch(Exception e){ e.printStackTrace(); }
	}

	
	
	/********************************************************************
	 * Method: storeFinalGrades()
	 * Purpose: store final grades to memory
	/*******************************************************************/
	public void storeFinalGrades(int term){
		
		try {
			
			// Connect
			HttpGet finalGet = new HttpGet("https://jweb.kettering.edu/cku1/wbwskogrd.P_ViewGrde?term_in=" + term + "&inam=on&snam=on&sgid=on");
			HttpResponse response = this.clientBanner.execute(finalGet);
			
			String html = HTMLParser.parse(response);
			
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/finalgrades.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"finalgrades.html\"");
			
			Elements tables = Jsoup.parse(html).getElementsByClass("datadisplaytable");
			
			// Correct amount ?
			if (tables.size() >= 3 && tables.get(2).getElementsByTag("tbody").size() > 0 && tables.get(1).getElementsByTag("tbody").size() > 0){
				
				// Store undergrad sum
				this.undergradSummary = new UndergradSummary(tables.get(2).getElementsByTag("tbody").get(0).getElementsByTag("tr"));
				
				Elements courses = tables.get(1).getElementsByTag("tbody").get(0).getElementsByTag("tr");
				
				// Titles remove
				if (courses.size() > 0) courses.remove(0);
				
				// Add final grades
				for(int i = 0; i < courses.size();  i++){ this.finalGrades.add(new FinalGrade(courses.get(i).getElementsByTag("td"))); }
				
			}
			
		}
		
		catch(Exception e){ e.printStackTrace(); }

	}
	

	/********************************************************************
	 * Method: storeMidtermGrades()
	 * Purpose: store midterm grades to memory
	/*******************************************************************/
	public void storeMidtermGrades(int term){
		
		try {
			
			// Connect
			HttpGet midtermGet = new HttpGet("https://jweb.kettering.edu/cku1/bwskmgrd.p_write_midterm_grades?term_in=" + term);
			HttpResponse response = this.clientBanner.execute(midtermGet);
			
			String html = HTMLParser.parse(response);
			
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/midtermgrades.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"midtermgrades.html\"");
			
			Elements tables = Jsoup.parse(html).getElementsByClass("datadisplaytable");
			
			// Correct amount ?
			if (tables.size() >= 2 && tables.get(1).getElementsByTag("tbody").size() > 0){
				
				Elements courses = tables.get(1).getElementsByTag("tbody").get(0).getElementsByTag("tr");
				
				// Titles remove
				if(courses.size() > 0) courses.remove(0);
				
				
				// Store midterm grades 
				for(int i = 0; i < courses.size();  i++) this.midtermGrades.add(new MidtermGrade(courses.get(i).getElementsByTag("td")));
				
			}
			
		}
		
		catch(Exception e){ e.printStackTrace(); }
	}
	
	
	/********************************************************************
	 * Method: storeAccount()
	 * Purpose: store financial account information to memory
	/*******************************************************************/
	public void storeAccount(){
		
		try {
			
			// Connect
			HttpGet accountGet = new HttpGet("https://jweb.kettering.edu/cku1/bwskoacc.P_ViewAcctTotal");
			HttpResponse response = this.clientBanner.execute(accountGet);
			
			String html = HTMLParser.parse(response);
			
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/accountTotal.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"accountTotal.html\"");
			
			
			Elements elements = Jsoup.parse(html).getElementsByClass("datadisplaytable");
		
			// Set account info
			if(elements.size() > 0 && elements.get(0).getElementsByTag("tbody").size() > 0){ this.accountTotal = new AccountTotal(elements.get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr")); }
			
		}
		
		catch(Exception e){ e.printStackTrace(); }
		
	}
	
	
	/********************************************************************
	 * Method: storeDynamicCourses()
	 * Purpose: store financial account information to memory
	/*******************************************************************/
	public void storeDynamicCourses(int term){
		
		try {
			
			String begin = "https://jweb.kettering.edu/cku1/bwckschd.p_get_crse_unsec?term_in=" + term + "&sel_subj=dummy&sel_day=dummy&sel_schd=dummy&sel_insm=dummy&sel_camp=dummy&sel_levl=dummy&sel_sess=dummy&sel_instr=dummy&sel_ptrm=dummy&sel_attr=dummy&sel_subj=";
			String end = "&sel_crse=&sel_title=&sel_from_cred=&sel_to_cred=&sel_instr=%25&begin_hh=0&begin_mi=0&begin_ap=0&end_hh=0&end_mi=0&end_ap=a";
			
			DefaultHttpClient client = new DefaultHttpClient();
			
			// 32 Subjects
			List<String> subjects = new ArrayList<String>();
			subjects.add("ACCT"); subjects.add("BIOL"); subjects.add("BUSN"); subjects.add("CHME"); subjects.add("CHEM"); subjects.add("CHN"); subjects.add("COMM"); subjects.add("CE"); subjects.add("CS"); subjects.add("ECON"); subjects.add("ECE"); subjects.add("EE"); subjects.add("ESL"); subjects.add("FINC"); subjects.add("GER"); subjects.add("HMGT"); subjects.add("HIST"); subjects.add("HUMN"); subjects.add("IME"); subjects.add("ISYS"); subjects.add("MFGO"); subjects.add("LS"); subjects.add("LIT"); subjects.add("MGMT"); subjects.add("MRKT"); subjects.add("MATH"); subjects.add("MECH"); subjects.add("MEDI"); subjects.add("ORTN"); subjects.add("PHIL"); subjects.add("PHYS"); subjects.add("SSCI"); subjects.add("SOC");
			
			
			for(int i = 0; i < subjects.size(); i++){
			
				// Connect
				HttpGet dynamicGet = new HttpGet(begin + subjects.get(i) + end);
				HttpResponse response = client.execute(dynamicGet);
				
				String html = HTMLParser.parse(response);
				
				// Write to file
				PrintWriter printer = new PrintWriter("artifacts/Subjects/" + subjects.get(i) + ".html");
				printer.print(html);	    	
				printer.close();
				
				System.out.println("Successfully stored \"" + subjects.get(i) + ".html\"");
				
			}
			
		}
		
		catch(Exception e){ e.printStackTrace(); }
		
	}
	
}

