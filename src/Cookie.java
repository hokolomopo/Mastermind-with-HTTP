import java.util.Calendar;
import java.util.Random;

public class Cookie {
	private final static int COOKIE_LENGTH = 10;
	
	private final static String COOKIE_TYPE = "SESSID";
	
	private Combination answer;
	private Combination[] triedCombi;
	private String id;
	Calendar creationTime = Calendar.getInstance();
	
	private int currentTry = 0;
	
	//Create a new cookie with the given combination and a random id
	public Cookie(Combination rightCombi) {
		this.generateId();
		
		this.triedCombi = new Combination[HTMLPage.LIVES];
		
		for(int i = 0;i < HTMLPage.LIVES;i++) { 
			triedCombi[i] = new Combination();
		}

		this.answer = rightCombi;
		
	}
	
	//Create a new cookie with the given combination and the given id
	public Cookie(Combination rightCombi, String id) {
		this.id = id;
		
		this.triedCombi = new Combination[HTMLPage.LIVES];
		
		for(int i = 0;i < HTMLPage.LIVES;i++) { 
			triedCombi[i] = new Combination();
		}

		this.answer = rightCombi;

	}
	
	//Generate a random ID
	private void generateId() {
		Random rand = new Random();
		int tmp = rand.nextInt((int)Math.pow(10, COOKIE_LENGTH));
		
		this.id =  COOKIE_TYPE + ":" + Integer.toString(tmp);
	}
	
	public String getId() {
		return this.id;
	}
	
	//Setup an HTML page with the combinations stored in the cookie
	public void setUpHTMLPage(HTMLPage page) {
		
		page.setCorrectCombination(this.answer);

		for(Combination c : triedCombi) {
			page.setNexCombination(c);
		}
	}
	
	//Set the correct combination for the cookie
	public void setCorrectCombination(Combination combi) {
		this.answer = combi;
		
		for(int i = 0;i < HTMLPage.LIVES;i++) { 
			triedCombi[i] = new Combination();
		}
	}
	
	//Add a combination tried by the client
	public void addTry(Combination combi) {
		this.triedCombi[currentTry] = combi;
		currentTry++;
	}
	
	public Combination getRightCombination() {
		return answer;
	}
	
	//Get number of try that the client has executed on this cookie
	public int getCurrentTry(){
		return currentTry;
	}
	
	//Reset the cookie
	public void reset() {
		this.currentTry = 0;
		this.creationTime = Calendar.getInstance();
		
		for(int i = 0;i < HTMLPage.LIVES;i++) { 
			this.triedCombi[i] = new Combination();
		}
		
		Combination tmp = new Combination();
		tmp.setRandomCombi();
		
		this.answer = tmp;
	}
	
	public Calendar getCreationTime() {
		return this.creationTime;
	}
}
