import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordSearchOperations {
	private int gridSize;
	private int maxScore;
	private String[][] letters;
	private HashChainDictionary dict;
	private HashChainDictionary dict2;
	
	//our desired load is 0.75 therefore we pcik 74843 words to be stored in the hash table
	public WordSearchOperations(String fileName, String wordTextFile) throws IOException, DictionaryException{
		HashChainDictionary dict = new HashChainDictionary(74843);
		
		//reading the owrds in the words file
		try {
		BufferedReader reader = new BufferedReader(new FileReader(new File("wordTextFile")));
		String inputLine = null;
		
		while((inputLine = reader.readLine()) != null) {
			String[] words = inputLine.split(",");
				int score = Integer.parseInt(words[1].trim());
				dict.put(new Word(words[0],score));
				}
		reader.close();
		//reading and storing the size of the grid as shown on the assignment page
		BufferedReader reader1 = new BufferedReader(new FileReader(new File("fileName")));
			gridSize = Integer.parseInt(reader1.readLine());
			letters = new String[gridSize][gridSize];
		
			//reading the lines that are equal to the grid size and making a 2 dimensional array to store the letters in
			String gl="";
			for(int i=0; i<gridSize; i++) {
				gl = reader1.readLine();
				for(int j=0; j<gridSize; j++) {
					letters[i][j]= gl.substring(j, j+1);
			}
		}
			//recording the words found and calculating the sum of max score
			maxScore=0;
			while(gl != null) {
				gl = reader1.readLine();
				if(gl==null || gl.equals("")) break;
				Word words = dict.get(gl);
				int val = words.getValue();
				maxScore+=val;
					}
			reader1.close();
			
		} catch (IOException e) {
			System.out.println("Cannot find the file");
		}
		
			gridSize=letters.length;
			dict2= new HashChainDictionary(80);
	}
			
			public int getNumWordsFound(){
				return dict2.size();
			}
			
			public int getSize() {
				return gridSize;
			}
			
			public int getMaxScore() {
				return maxScore;
			}
			
			public String getLetter(int i, int j) {
				return ""+letters[i][j];
			}
			
			ArrayList<Word> checkWords(String string){
				ArrayList<Word> back = new ArrayList<Word>();
				//looking for words that are between 4 and 7 letters of length
				for(int l=4; l<=7; l++) {
					//setting the boundaries for the search
					for(int i=0; i<string.length()-l; i++) {
						String tmp = string.substring(i, l);
						
						if(tmp.length() != l) {
							continue;
						}
						Word word= dict.get(tmp);
						//if the word cannot be found
						if(word != null) {
							back.add(word);
						}
					}
				}
				return back;
			}
			ArrayList<Word> findWords(String line){
				ArrayList<Word> back = new ArrayList<Word>();
				String [] wordStrings = line.split("\\s+");
				
				for(String tmp: wordStrings) {
					ArrayList<Word> temp = checkWords(tmp);
					
					if(temp.size()>0) {
						for(Word t: temp) {
							back.add(t);
						}
					}					
				}
				return back;
			}
			
			ArrayList<Word> updateWordList(ArrayList<Word> words) throws DictionaryException {
			     
		        ArrayList<Word> back = new ArrayList<Word>();
		           if (words==null)
		            return back;
		          
		        for (Word currentWord : words){
		            Word word = dict2.get(currentWord.getKey());
		            //we are looking to see if the word is found, and in this case it wasn't
		            if (word==null){
		                dict2.put(word);
		                back.add(word);
		            }
		        }
		        return back;		
	}
}

