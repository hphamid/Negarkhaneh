package ir.abrstudio.negarkhaneh.list;

public class Range {

	private int start;
	private int end;
	
	/* 
	 * start and end both included! :)
	 */
	public Range(int start, int end) {
		
	}

	public void setRange(int start, int end){
		if(start>end){
			throw new IllegalStateException("start must be bigger than end! :P");
		}
		this.start = start;
		this.end = end;
	}
	
	public int getStart(){
		return this.start;
	}
	
	public int getEnd(){
		return this.end;
	}
	
	public boolean isInRange(int number){
		return number >= this.getStart() && number <= this.getEnd();
	}
	
}
