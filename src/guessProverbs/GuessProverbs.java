package guessProverbs; 

//REFACTOR - lowerCase/uppercase - Info -- lettera gia messa
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*; 

public class GuessProverbs {
	private String proverb= "";
	private boolean [] displayThisLetters;
	ArrayList<String> enterd = new ArrayList<>();
	private static final int ATTEMPTS = 6;
	private int turn;
	private String proverbFileName  ;	
	
	
	/** get random a line (the proverb to guess) from a file .txt */
	public  String chooseProverb() throws FileNotFoundException { 
		File file = new File("guessProverbsFiles\\" + getFile() + ".txt");
		Scanner scanner = new Scanner(file,"UTF-8");
		int counter = 0;
		 
		Random random = new Random(); 
		int proverbLine = random.nextInt(countLine());
		counter = 0;  
		while(proverb.isEmpty()) {			
			if(counter==proverbLine) {
				proverb = scanner.nextLine().toUpperCase();
			}else {
				scanner.nextLine();
				counter++; 
			} 
		} 
		return proverb;
	}
	
	/**count the total number of lines in a .txt file*/
	public int countLine( ) throws FileNotFoundException {
		File file = new File("guessProverbsFiles\\" +  getFile() + ".txt");
		Scanner s = new Scanner(file,"UTF-8");
		int counter = 0;
		
			while(s.hasNextLine()) {
				s.nextLine();
				counter++;
			}  
		return counter; 
	}
	
	
	
	/**initialize the boolean array displayThisLetters with true if the character is not a letter*/
	public void initDisplayThisLetters(String text) { 
		displayThisLetters = new boolean[text.length()];  
		for(int i=0; i<displayThisLetters.length; i++) {
			if(!Character.isLetter(text.charAt(i)))
				displayThisLetters[i] = true; 
		} 
	}
	
	/**the input is valid if its lenght > 0 and it is a letter*/
	public boolean validInput(String input) {
		if(input.length()==0) { 										 
			return false;
		} else if(!Character.isLetter(input.charAt(0))) { 													 
			return false;
		} 
		return true;
	}
	
	
	/**If the ArrayList entered contains the input return true
	 * else add the input into the ArrayList and return false*/
	public boolean enteredLetter(String input){
		if(!enterd.contains(input)) {
			enterd.add(input);
			return false;
			}
		else {
																
			return true;
		}
	}
	
	
	/**return true if the proverb to guess contains the input*/
	public boolean isThere(String input) { 
		if(proverb.contains(input))
			return true;
		return false;
	}
	
	
	public void increaseTurn() {
		++turn;
	}
 
	
	/**update the boolean array displayThisLetters*/
	public void updateDisplayThisLetters(String input) {
		for(int i=0; i<proverb.length(); i++) { 
			if(proverb.substring(i, i+1).equals(input))
				displayThisLetters[i] = true;
		}
	}
	
	/**determine which letters should be displayed*/
	public String displayThisLetters() {
		String displayText = "";
		for (int i = 0; i < proverb.length(); i++) {
			if(displayThisLetters[i])
				displayText += proverb.charAt(i);
			else
			displayText += "-";
		}
		return displayText;
	}
	
	/**clear the ArrayList with the entered Letters*/
	public void resetEntered() {
		this.enterd.clear();
	}
		
	/**win if all the value in displayThisLetters are true*/
	public boolean win() {  
		for(int i=0; i<displayThisLetters.length; i++) {
			if(!displayThisLetters[i])
				return false;
		}
		return true;
	}
	
	/**lose if the turn = attempts*/
	public boolean lose() {
		if(turn<ATTEMPTS)
			return false;
		return true;
	}
	
	public void setTurn(int turno) {
		this.turn = turno;
	}
	
	public boolean [] getDisplayThisLetters() {
		return displayThisLetters;
	}
	
	public String getText() {
		return proverb;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public int getAttempts() {
		return ATTEMPTS;
	}
	
	public ArrayList<String> getEnterd(){
		return enterd;
	}
	 
	public void setFile(String f) {
		this.proverbFileName = f;
	}
	
	public String getFile() {
		return proverbFileName;
	}
 	
}
