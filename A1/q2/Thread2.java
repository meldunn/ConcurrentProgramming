
/*
 * Thread 2 repeatedly performs depth-first searches through the tree from the root to count the number of
nodes in the tree. At each node it enters it increments its count and pauses 10ms. After returning to the
root it emits the total count (and a newline). It then waits 200ms before beginning a new count.
 */




public class Thread2 extends Thread {
	
	volatile int count;
	volatile myNode root;
	
	public Thread2(myNode root) {
		this.count = 0;
		this.root = root;
	}
	
	public void run() {
		
		while(true) {
			count = 0; //reset count to 0
			
			numNodes(root);
			System.out.println(count);
			q2.finalCount = count; //store to print from main at the end
			
			
			try {
				this.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (this.isInterrupted()) {
				//return if program has been running for 5 seconds
				return;
			}
		}
		
		
	}
	
	public void numNodes(myNode root) {
		if (root == null) {
			return;
		}
		else {
			count++;
			try {
				this.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		numNodes(root.leftChild);
		
		
		if (root.isRightThreaded == false) {
			//only call if root.rightchild is actually child and not pointer to threading list
			numNodes(root.rightChild);
		}
		
	}
	
	





}
