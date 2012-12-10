import java.sql.Time;


public class TimeBlock {
	
	private Time start;
	private Time end;
	
	
	public TimeBlock(Time start, Time end){
		
		this.start = start;
		this.end = end;
	}
	
	public boolean conflicts(TimeBlock block){
		
		if(this.start.compareTo(block.getStart()) <= 0 && this.end.compareTo(block.getStart()) >= 0){
			return true;
		}
		
		else if(this.start.compareTo(block.getEnd()) <= 0 && this.end.compareTo(block.getEnd()) >= 0){
			return true;
		}
		
		else return false;
		
	}
	
	
	public Time getStart(){ return this.start; }
	public Time getEnd(){ return this.end; }
	
	
}
