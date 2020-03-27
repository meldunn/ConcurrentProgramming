

public class myThread extends Thread {
   	int numRecToMake;
	Rectangle recInProgress;
	int tid;
	
	myThread(int numRecToMake, int tid){
		this.numRecToMake = numRecToMake;
		this.tid = tid;
		this.recInProgress = null;
	}
	/** 
	 * @Override run() in Thread
	 */
	public void run() {
		for (int i = 0; i < numRecToMake; i++) {
				
			//get dimensions of rectangle
			int[] dimensions = q1.getRecDimension();
			//get starting location for rectangle
			int[] startingLoc = q1.getStartingLoc(dimensions[0], dimensions[1]);
			
			//check overlap
			while(q1.isOverlap(startingLoc[0], startingLoc[1], startingLoc[0] + dimensions[0], startingLoc[1] + dimensions[1])) {
				//wait until theres no overlap
			}
			q1.createRecObj(dimensions[0], dimensions[1],startingLoc[0], startingLoc[1], tid); //creates rectanle object and add it as attribute
			
			q1.drawRectangle(recInProgress);
			
			
			recInProgress = null;
			
			
		}
	}
}
