import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HTMLPage {
	
	private static final int LIVES = 12;
	private static final String HTML_FILE = "play.html";
	
	private final static String tab = "\t";
	private final static String endl = "\n";

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
	
	private Combination[] triedCombi = new Combination[LIVES];
	private BufferedReader reader;
	
	private String headString = "";
	private String body;
	private String tailString = "";
	
	private int currentTry = 0;
	
	public HTMLPage() throws IOException {
		reader = new BufferedReader(new FileReader(HTML_FILE));

		for(int i = 0;i < LIVES;i++) { 
			triedCombi[i] = new Combination();
			triedCombi[i].setRandomCombi();
		}
		
		String tmp;
		boolean tail = false;
		while((tmp = reader.readLine()) != null) {
			if(tail)
				tailString += tmp + endl;
			else
				headString += tmp + endl;
	
			if(!tail && tmp.contains("GAMETOKEN")){
				System.out.println("Game found");
				body = createGameUL();
				tail = true;
			}
		}
		
		//System.out.println(headString + body + tailString);
	
	}
	
	private String createGameUL() {
		String ret = tab + "<ul id=\""+ ul.GAME.id +"\">" + endl;
		
		for(int i = 0;i < LIVES;i++) {
			ret += tab + tab + "<li>" + endl;
			ret += createHorizontalUL(i);
			ret += tab + tab + "</li>" + endl;
		}
			
		ret += "</ul>" + "\n";
		
		return ret;
	}

	private String createHorizontalUL(int row) {
		String ret = tab + tab + tab + "<ul id=\""+ ul.LINE.id +"\">" + endl;
		
		Colors[] colors = triedCombi[row].getColors();

		for(int i = 0;i < 4;i++)
			ret += createLI(ul.GAME, colors[i]);
		for(int i = 0;i < 4;i++)
			ret += createLI(ul.RESULTS, Colors.EMPTY);

		ret += tab + tab + tab + "</ul>" + endl;
		
		return ret;
	}
	
	private String createLI(ul type, Colors c) {
		String ret = tab + tab + tab + tab + "<li id=\""+type.id+"\">";
		ret += "<img src=\""+ c.getImagePath() +"\" alt=\""+c.getName() + "\" id=\""+ type.id +"\">";
		ret += "</li>" + endl;
		
		return ret;
	}

	public String getHtmlCode() {
		return headString + body + tailString;
	}
	
	public void setNexCombination(Combination combi) {
		triedCombi[currentTry++] = combi;
	}
}
