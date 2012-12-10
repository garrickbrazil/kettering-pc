import java.sql.Time;
import java.util.List;

/********************************************************************
 * Class: TimeBlock
 * Purpose: holds a start and end time
/*******************************************************************/
public class TimeBlock {
	
	// Properties
	private Time start;
	private Time end;
	

	/********************************************************************
	 * Constructor: TimeBlock
	 * Purpose: creates TimeBlock object with given parameters
	/*******************************************************************/
	public TimeBlock(Time start, Time end){
		
		this.start = start;
		this.end = end;
	}
	
	
	/********************************************************************
	 * Method: testDay
	 * Purpose: test to see if a TimeBlock fits into a day
	/*******************************************************************/
	public static boolean testDay(List<TimeBlock> day, TimeBlock block){
		
		 for (TimeBlock currentBlock : day ) if(currentBlock.conflicts(block)) return false;
		 return true;
		 
	}

	
	/********************************************************************
	 * Method: convertToTimeBlock
	 * Purpose: converts a time string into a TimeBlock object
	/*******************************************************************/
	public static TimeBlock convertToTimeBlock(String time){
		
		// Initial
		int startHours, startMinutes, endHours, endMinutes;
		String[] splitTime = time.split("\\s-\\s");
		
		
		if(splitTime.length == 2 && splitTime[0].matches("\\d+:\\d\\d\\s(am|pm)") && splitTime[1].matches("\\d+:\\d\\d\\s(am|pm)")){
			
			try{

				 // Start
				 startHours = Integer.parseInt(splitTime[0].split(":")[0]);
				 startMinutes = Integer.parseInt((splitTime[0].split(":")[1].replaceAll("[^\\d]", "")));
				 
				 // End
				 endHours = Integer.parseInt(splitTime[1].split(":")[0]);
				 endMinutes = Integer.parseInt((splitTime[1].split(":")[1].replaceAll("[^\\d]", "")));
				 
				 
				 // Convert start to military 
				 if(splitTime[0].replaceAll("[^(am|pm)]", "").equals("pm") && startHours < 12) startHours += 12;
				 else if (splitTime[0].replaceAll("[^(am|pm)]", "").equals("am") && startHours == 12) startHours = 0;
				 

				 // Convert end to military 
				 if(splitTime[1].replaceAll("[^(am|pm)]", "").equals("pm") && endHours < 12) endHours += 12;
				 else if (splitTime[1].replaceAll("[^(am|pm)]", "").equals("am") && endHours == 12) endHours = 0;
				 
				return new TimeBlock(Time.valueOf(startHours + ":" + startMinutes + ":0"), Time.valueOf(endHours + ":" + endMinutes + ":0"));
				
			}
			
			catch(Exception e){ return null; }
		}
		
		else return null;
			
	}
	

	/********************************************************************
	 * Method: conflicts
	 * Purpose: checks to see if the given TimeBlock conflicts
	/*******************************************************************/
	public boolean conflicts(TimeBlock block){
		
		// Problems ?
		if(this.start.compareTo(block.getStart()) <= 0 && this.end.compareTo(block.getStart()) >= 0) return true;
		else if(this.start.compareTo(block.getEnd()) <= 0 && this.end.compareTo(block.getEnd()) >= 0) return true;
		
		else return false;
		
	}
	
	/********************************************************************
	 * Accessors: getStart, getEnd
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public Time getStart(){ return this.start; }
	public Time getEnd(){ return this.end; }
	
	
}
