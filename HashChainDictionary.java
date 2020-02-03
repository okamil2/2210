import java.lang.Math;

public class HashChainDictionary implements DictionaryADT{
	// initializing the variables
	int size;
	private linkedList table[];
	private Node pointer;
	Node head = pointer;
	
	public HashChainDictionary(int size) {
		//constructor for the class
		this.size= size;
		table = new linkedList[size];
		for (int i=0; i<size; i++) {
			table[i] = new linkedList(); // creating the table
		}
	}
	
	//putting the word in the dictionary and throwing an exception if word exists there already.
	public int put(Word word) throws DictionaryException{
		int val = hash(word.getKey(),(int)Math.sqrt(word.getKey().length())); //finding the hash code of the data
		int confirm = table[val].insert(word);
		
		if (confirm==1) {
			size = size+1;
			return 1;
		}
		else if(confirm==0) {
			return 0;
		}
		else { //if the word already exists we throw an exception
			throw new DictionaryException("the data is already in the dictionary");
		}
	}
	//getting the word in the head
		public Word get(String inputWord) {
			while (pointer != null) {	
				if(pointer.getElement().getKey().equals(inputWord)) {
					return pointer.getElement.getValue();
				}
				else {
					return null;
				}
			}	
		}
		
	//removing a word and throwing an exception if the word isn't there
	public Word remove(String inputWord) throws NoKeyException{
		int val = hash(inputWord, (int)Math.sqrt(inputWord.length())); // again, calculating the hash code of the data
		boolean confirm = table[val].remove(inputWord);
		
		if(confirm) {
			return pointer.getData();
		}
		else if(!confirm) { // if the word wasnt removed we throw the exception
			throw new NoKeyException("The key wasn't in the dictionary");
		}
		else {	
			return null;
	}
}
	//Returning the size of the table
	public int size() {
		return size;
	}
	//making the hash code and calculating the load factor
	private int hash(String str, int mult) {
		int val=(int) str.charAt(str.length()-1);
			
		for(int i=str.length()-2; i>=0; i--) {
			val=((val*mult +(int)str.charAt(i))%table.length); //the load factor
		}
		return val;
	}
}