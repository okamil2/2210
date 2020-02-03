
public class Node {
	
	//initialize variables
	private Node next;
	private Word word;
	
	//constructor
	public Node(Word word) {
		this.next=null;
		this.word=word;
	}
	//looking for next value
	public Node getNext() {
		return next;
	}
	//setting the next value
	public void setNext(Node nextNode) {
		this.next = nextNode;
	}
	//getting the data
	public Word getData() {
		return this.word;
	}
	//setting the score
	public void setScore(Word data1) {
		this.word = data1;
	}
	
}
