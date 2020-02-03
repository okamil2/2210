
public class linkedList {
	//initializing variables
	Node head;
	int numOfData;

	public linkedList(){ // constructor
		head=null;
		numOfData=0;

	}
	
	public int insert (Word data){
		if (numOfData>0)//checks for duplicates in the list while it is not empty, if not then the insertion happens

		{
			if(LinearSearch(data))
			{
				return -1;
			}
			else
			{
				//add the data to the list
				Node newNode = new Node(data);
				newNode.setNext(head);
				head=newNode;
				numOfData++;
			}
			return 1;			
		}
		else // if the list is empty then add the data as the first element
		{	
			head = new Node(data);
			numOfData++;
			return 0;
		}
	}
	// this Removes words from the list
	public boolean remove(String inputWord) {
		Node pointer=head;
		int position;
		// if number of data is greater than zero then there are elements in the list
		if(numOfData>0) {
			position = LinearSearchString(inputWord);
			//if position = 0 then the head and word are at the same place therefore we remove it form the list
			if(position==0) {
				head = pointer.getNext();
				numOfData--;
				return true;
			}
			//if the position is less than the # of data, that means the data is at the position, can be removed
			else if(position<numOfData) {
				for(int i=0; i<position-1; i++)
					pointer = pointer.getNext();
				Node next = pointer.getNext().getNext();
				
				pointer.setNext(next);
				numOfData--;
				return true;
			}
			//word is not in the list
			else
					return false;
		}
		else
				return false;
	}
	// we are looking for the score associated with the word
	public int find(String word) {
		
		String compare;
		Node pointer = head;
		//searches for the word if found the score is returned
		for(int i=0; i<numOfData; i++) {
			
			compare = pointer.getData().getKey();
			if(compare.equals(word))
				return pointer.getData().getValue();
			pointer=pointer.getNext();
		}
		// returns -1 if the word is not found
		return -1;
	}
	//checks for the word's existence
	private boolean LinearSearch(Word data)
	{
		int i =0;
		Node pointer=head;
		//while we have elements in the list and the word of the pointers don't equal to the string word of the data
		//then we keep going
		while (i<numOfData && pointer.getData().getKey()!=data.getKey())
			{
			i++;
			pointer = pointer.getNext();
			}
		// if the value doesnt eual 0 but there, t is smaller than the number of data elements in the list
		if (i!=0 && i<numOfData || i==0 & head.getData().getKey()==data.getKey())
		return true;		
		// if the data doesn't exist we return false
		return false;
	}
		//looking to see if the word already exists in the list
	private int LinearSearchString(String word)
	{
		String compare;
		Node pointer = head;
		// if the element's value equals the word gives back the position , if not then the number of data elements
		// will be returned
		for (int i=0; i<numOfData; i++)
		{
			compare = pointer.getData().getKey();
			if (compare.equals(word))
				return i;
			pointer=pointer.getNext();
		}
		return numOfData;

	}

	public Word get(String inputWord) {
		Node pointer = head;
		if(pointer != null) {
			return head.getData();
		}
		return null;
	}

}
