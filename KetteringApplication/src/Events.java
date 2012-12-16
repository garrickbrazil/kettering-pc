import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

/********************************************************************
 * Class: Events
 * Purpose: holds event objects
/*******************************************************************/
public class Events {
	
	private DefaultHttpClient client;
	private List<EventsJSON> events;
	
	public Events(){
			
		this.client = new DefaultHttpClient();
		this.events = new ArrayList<EventsJSON>();
		
		try{
			
			// Execute
			HttpGet eventsGet = new HttpGet("https://drupal.kettering.edu:8443/kumobile/rest/events");
			HttpResponse response = this.client.execute(eventsGet);
			String json = HTMLParser.parse(response);
			
			// Regular expression
			Pattern p = Pattern.compile("(^.+)([0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9])(.*)");
			Matcher m = p.matcher(json);
			
			
			while(m.find()){	
				
				try{  
					
					if(m.groupCount() >=3){

						// Format JSON
						String fixedJSON = ("{\"events\":" + m.group(3).substring(2, m.group(3).length()-1) + "}");
						
						// Add and refresh matcher
						EventsJSON current = new Gson().fromJson(fixedJSON, EventsJSON.class);
						current.convertDate(m.group(2));
						this.events.add(current) ;
						m = p.matcher(m.group(1).substring(0, m.group(1).length()-2) + "}");
					}
				}
				
				catch(Exception e){}
			}
			
		}
		
		catch(Exception e) {this.events = new ArrayList<EventsJSON>(); }
		
	}

	/********************************************************************
	 * Accessors: getEvents
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public List<EventsJSON> getEvents(){ return this.events; }
	
	
	/********************************************************************
	 * Method: getEvents
	 * Purpose: gets events based off parameters
	/*******************************************************************/
	public List<EventsJSON> getEvents(int amount, Date start){
		
		List<EventsJSON> eventList = new ArrayList<EventsJSON>();
		int index;
		
		// Find starting point
		for(index = 0; index < this.events.size(); index++) if(this.events.get(index).getDate().compareTo(start) <= 0) break;
		
		
		// Out of range
		if(index >= this.events.size()){
			if(index-amount < 0) eventList = this.events.subList(0, index);
			else eventList = this.events.subList(index-amount, index);
			return eventList;
		}
		
		
		// Equal
		if(this.events.get(index).getDate().compareTo(start) == 0) index++; 
		
		// Get latest
		if(index-amount < 0) eventList = this.events.subList(0, index);
		else eventList = this.events.subList(index-amount, index);
		
		return eventList;
		
	}
}


/********************************************************************
 * Class: EventsJSON
 * Purpose: holds JSON event objects
/*******************************************************************/
class EventsJSON{
	
	private Date date;
	private List<Event> events;
	
	/********************************************************************
	 * Class: EventsJSON
	 * Purpose: creates JSON event object
	/*******************************************************************/
	public EventsJSON(){
		
		this.events = new ArrayList<Event>();
	}
	
	/********************************************************************
	 * Method: convertDate
	 * Purpose: converts dateStr to date object
	/*******************************************************************/
	public void convertDate(String dateStr){

		// Date & calendar
		String[] dateStrs = dateStr.split("-");
		
		try{ 
			
			// Correct format
			if(dateStrs.length == 3){
				
				// Properties
				int year = Integer.parseInt(dateStrs[0]);
				int month = Integer.parseInt(dateStrs[1]);
				int day = Integer.parseInt(dateStrs[2]);
				
				// Set
				this.date = new Date(month, day, year);
			}
			
			else this.date = new Date(9, 3, 1992);
		}
		 
		catch(Exception e) {
			
			this.date = new Date(9, 3, 1992); 
		}
	}
	
	
	/********************************************************************
	 * Accessors: getEvents, getDate
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public List<Event> getEvents() { return this.events; }
	public Date getDate() { return this.date; }
	
}

/********************************************************************
 * Class: Event
 * Purpose: holds JSON event object
/*******************************************************************/
class Event{
	
	// Properties
	private String summary;
	private String uid;
	private String description;
	private String location;
	private String duration;
	private String contact;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String email;
		
	
	/********************************************************************
	 * Method: toString
	 * Purpose: format object into a string
	/*******************************************************************/
	public String toString(){ 
		String eventStr = "";
		
		if(this.summary!=null) eventStr += "Summary: " +  this.summary + " ";
		if(this.startDate!=null) eventStr += "Start date: " + this.startDate;
		
		
		return eventStr;
	}
	
	
	/********************************************************************
	 * Accessors: getSummary, getUid, getDescription, getLocation, getDuration
	 * 		getContact, getStartDate, getEndDate, getStartTime, getEndTime, getEmail
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getSummary() { return this.summary; }
	public String getUid() { return this.uid; }
	public String getDescription() { return this.description; }
	public String getLocation() { return this.location; }
	public String getDuration() { return this.duration; }
	public String getContact() { return this.contact; }
	public String getStartDate() { return this.startDate; }
	public String getEndDate() { return this.endDate; }
	public String getStartTime() { return this.startTime; }
	public String getEndTime() { return this.endTime; }
	public String getEmail() { return this.email; }
	
}

/********************************************************************
 * Class: Date
 * Purpose: holds date information
/*******************************************************************/
class Date{
	
	private int year;
	private int month;
	private int day;
	
	/********************************************************************
	 * Accessors: getEvents, getDate
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public Date(int month, int day, int year){
		
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	
	/********************************************************************
	 * Accessors: getYear, getMonth, getDay
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public int getYear(){ return this.year; }
	public int getMonth(){ return this.month; }
	public int getDay(){ return this.day; }
	
	
	/********************************************************************
	 * Method: compareTo
	 * Purpose: format object into a string
	/*******************************************************************/
	public int compareTo(Date compareDate){
		
		if(this.year > compareDate.getYear()) return 1;
		if(this.year < compareDate.getYear()) return -1;
		
		if(this.month > compareDate.getMonth()) return 1;
		if(this.month < compareDate.getMonth()) return -1;
		
		if(this.day > compareDate.getDay()) return 1;
		if(this.day < compareDate.getDay()) return -1;
		
		return 0;
	}
	
	
	/********************************************************************
	 * Method: toString
	 * Purpose: format object into a string
	/*******************************************************************/
	public String toString(){
		return this.month + "-" + this.day + "-" + this.year;
	}
	
}
