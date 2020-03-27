import  java.lang.Math;

public class Thread1 extends Thread{
	
	public void run() {

		myNode curr = q2.head;

		while (true) {
			while (curr != null) {

				int randNum = ((int) (Math.random() * (10-1))) + 1; //generate randum num between 1 and 10
				
			
				
				if (randNum ==1) {
					//expand current node
					
					String leftName = generateStr();
					String rightName = leftName.toUpperCase();
					
					myNode l = new myNode(leftName);
					myNode r = new myNode(rightName);
					
					l.isRightThreaded = true;
					l.rightChild = r;
					
	
					
					//update threading
					if (curr == q2.head) {
						r.rightChild = curr.rightChild;
						r.isRightThreaded = true;
						q2.head = l;
						//fix tree
						curr.isRightThreaded = false;
						curr.leftChild = l;
						curr.rightChild = r;
						
					}
					else if (curr == q2.tail) {
						myNode c = q2.head;
						while (c.rightChild != q2.tail) {
							c = c.rightChild;
						}
						//update threading
						c.rightChild = l;
						q2.tail = r;
						
						//fix tree
						curr.isRightThreaded = false;
						curr.leftChild = l;
						curr.rightChild = r;
								
					}
					else {
						myNode c = q2.head;
						while (c.rightChild != curr) {
							c = c.rightChild;
						}
						//update threading
						c.rightChild = l;
						r.rightChild = curr.rightChild;
						r.isRightThreaded = true;
						
						//fix tree
						curr.isRightThreaded = false;
						curr.leftChild = l;
						curr.rightChild = r;
					}
					
					
				}
							
				curr = curr.rightChild; // go to next leaf
				
				try {
					this.sleep(20);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			System.out.println();
			try {
				this.sleep(200);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				return;
			}
			curr = q2.head;
			if (this.isInterrupted()) {
				return;
			}
			
		}
		
	}
	
	public String generateStr() {
		String str = "";
		int num;
		for (int i =0; i < 4; i++) {
			num = ((int) (Math.random() * (122-97))) + 97;
			str += (char) num;
		}
		return str;
	}

}
