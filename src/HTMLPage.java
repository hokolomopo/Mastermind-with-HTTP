import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HTMLPage {
	
	//HTML file that will be parsed
	//Need a string "GAMETOKEN" to be in it to get the place where we want to put the HTML code of the game
	public static final String HTML_FILE = "play.html";

	public static final int LIVES = 12;
	
	private final static String tab = "\t";
	private final static String endl = "\n";

	//Enum for CSS class ID of the HTML files
	private enum ul {
		RESULTS("results"),
		GAME("game"),
		USR("usr"),
		LINE("horiz");
		
		public String id;
		
		private ul(String s) {
			this.id = s;
		}
	}
	
	//Combinations to display
	private Combination[] triedCombi = new Combination[LIVES];
	
	//Correct combination
	private Combination correctCombi;
	
	private BufferedReader reader;
	
	//headstring will contain the HTML code before the game itself
	private String headString = "";
	
	//Body will contain the HTML code of the game itself
	private String body;
	
	//tailstring will contain the HTML code after the game itself
	private String tailString = "";
	
	private int currentTry = 0;
	
	//Constructor will throw a IOExcpetion if the HTML file of the static field HTML_FILE isn't found
	public HTMLPage() throws IOException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(HTML_FILE), "UTF-8"));

		for(int i = 0;i < LIVES;i++) { 
			triedCombi[i] = new Combination();
		}
		
		String tmp;
		
		//Put the HTML file into the string the he class
		boolean tail = false;
		while((tmp = reader.readLine()) != null) {
			if(tail)
				this.tailString += tmp + endl;
			else
				this.headString += tmp + endl;
	
			//Check for GAMETOKEN to place the game itself
			if(!tail && tmp.contains("GAMETOKEN")){
				body = createGameUL();
				tail = true;
			}
		}
		
		//Set the correct combination
		correctCombi = new Combination();
		correctCombi.setRandomCombi();
					
	}
	
	//Return the HTML code of the main list of the game
	private String createGameUL() {
		String ret = tab + "<ul class=\""+ ul.GAME.id +"\">" + endl;
		
		for(int i = 0;i < LIVES;i++) {
			ret += tab + tab + "<li>" + endl;
			ret += createHorizontalUL(i);
			ret += tab + tab + "</li>" + endl;
		}
			
		ret += "</ul>" + "\n";
		
		return ret;
	}

	//Return the HTML code of an horizontal list of the game
	private String createHorizontalUL(int row) {
		String ret = tab + tab + tab + "<ul class=\""+ ul.LINE.id +"\">" + endl;
		
		Colors[] colors = triedCombi[LIVES - 1 - row].getColors();
		int[] results = triedCombi[LIVES - 1 - row].getResults();

		for(int i = 0;i < Combination.COMBI_LENGTH;i++)
			ret += createLI(ul.GAME, colors[i], LIVES - 1 - row ,i);
		
		int i;
		//Right color right place
		for(i = 0;i < results[0];i++)
			ret += createLI(ul.RESULTS, Colors.RED, LIVES - 1 - row, i);

		//Right color wrong place
		for(;i < results[0] +  results[1];i++)
			ret += createLI(ul.RESULTS, Colors.WHITE ,LIVES - 1 - row , i);

		//Remaining circles
		for(;i < Combination.COMBI_LENGTH;i++)
			ret += createLI(ul.RESULTS, Colors.EMPTY, LIVES - 1 - row, i);


		ret += tab + tab + tab + "</ul>" + endl;
		
		return ret;
	}
	
	//Return the HTML for a list element of the game
	private String createLI(ul type, Colors c, int row, int col) {
		String ret = tab + tab + tab + tab + "<li class=\""+type.id+" line\">";
		ret += "<img id=\""+ type.id + row + col +"\" src=\""+ c.getImagePath() +"\" alt=\""+ c.getName() + "\" class=\""+ type.id +"\">";
		ret += "</li>" + endl;
		
		return ret;
	}

	//Return the HTML code to send to the client
	public String getHtmlCode() {
		return headString + body + tailString;
	}
	
	//Set the next combination that was send by the client
	public void setNexCombination(Combination combi) {
		triedCombi[currentTry] = combi;
		triedCombi[currentTry].evaluate(correctCombi);
		body = createGameUL();
		
		currentTry++;
	}
	
	//Set the correct combination
	public void setCorrectCombination(Combination combi) {
		this.resetPage();
		
		this.correctCombi = combi;
		
	}
	
	//Reset the combinations of the page
	public void resetPage() {
		for(int i = 0;i < LIVES;i++) { 
			triedCombi[i] = new Combination();
		}
		body = createGameUL();
	}

	public Combination getCorrectCombi() {
		return correctCombi;
	}
}
