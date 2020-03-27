

public class q2 {
	volatile static myNode head;
	volatile static myNode tail;
	
	volatile static int finalCount;
	
	

	
	public static void main(String[] args) {
		//start timer
		long startTime = System.currentTimeMillis();
		
		//create initial nodes
		myNode root = new myNode("root");
		myNode initLeft = new myNode("initchild");
		myNode initRight = new myNode("INITCHILD");
		
		//connect the tree
		root.leftChild = initLeft;
		root.rightChild = initRight;
		
		//leaf thread
		head = initLeft;
		tail = initRight;
		initLeft.rightChild = initRight;
		initLeft.isRightThreaded = true;
		
		//create threads
		Thread0 t0 = new Thread0();
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2(root);
		
		t0.start();
		t1.start();
		t2.start();
		
		
		
		while(true) {
			long currTime = System.currentTimeMillis();
			
			//if reach 5 secodns then send signal to threads
			if (currTime - startTime >= 5000) {
				t0.interrupt();
				t1.interrupt();
				t2.interrupt();
				
				//print final count + threading
				System.out.println("\n" + finalCount);
				myNode toPrint = head;
				while(toPrint != null) {
					System.out.print(toPrint.name + " ");
					toPrint = toPrint.rightChild;
				}
		
				System.exit(0);
			}
		}
						
	}
}
