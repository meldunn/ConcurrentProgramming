
/*
 * Thread 0 scans through the leaves using the threading. At the head node it prints out a “*”, and then (on
the same line) prints out the names of the nodes it encounters (on the same output line, space-separated),
sleeping 50ms between outputting each individual node-name. Once it reaches the tail, it prints a newline,
pauses for 200ms, and then starts again from the head
 */

public class Thread0 extends Thread {

	public void run() {
		myNode curr = q2.head; //store so can get it easily again

		while (true) {
			while (curr != null) {

				if (curr.name == q2.head.name){
					System.out.print("*");
				}
				
				System.out.print(curr.name + " ");

				curr = curr.rightChild; //go to next leaf
				
				try {
					this.sleep(50);
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

}
