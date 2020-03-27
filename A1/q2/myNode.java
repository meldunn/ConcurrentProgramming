
public class myNode {
	volatile String name;
	volatile myNode Parent;
	volatile myNode leftChild;
	volatile myNode rightChild;
	volatile boolean isRightThreaded; //if true then rightChild points to next threaded leaf

	//constructor
	public myNode(String name, myNode leftChild, myNode rightChild, boolean isRightThreaded) {
		this.name = name;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.isRightThreaded = isRightThreaded;
	}
	//alternative constructor
	public myNode(String name) {
		this.name = name;
		this.leftChild = null;
		this.rightChild = null;
		this.isRightThreaded = false;
	}
	
	

}
