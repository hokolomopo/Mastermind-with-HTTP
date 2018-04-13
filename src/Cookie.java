import java.util.Random;

public class Cookie {
	private final static int COOKIE_LENGTH = 10;
	
	private final static String COOKIE_TYPE = "SESSID";
	
	private Combination answer;
	private Combination[] triedCombi;
	private String id;
	
	private int currentTry = 0;
	
	public Cookie(Combination rightCombi) {
		this.generateId();
		
		this.triedCombi = new Combination[HTMLPage.LIVES];
		
		for(int i = 0;i < HTMLPage.LIVES;i++) { 
			triedCombi[i] = new Combination();
		}

		this.answer = rightCombi;
		
	}
	
	public Cookie(Combination rightCombi, String id) {
		this.id = id;
		
		this.triedCombi = new Combination[HTMLPage.LIVES];
		
		for(int i = 0;i < HTMLPage.LIVES;i++) { 
			triedCombi[i] = new Combination();
		}

		this.answer = rightCombi;

	}
	
	private void generateId() {
		Random rand = new Random();
		int tmp = rand.nextInt((int)Math.pow(10, COOKIE_LENGTH));
		
		this.id =  COOKIE_TYPE + ":" + Integer.toString(tmp);
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setUpHTMLPage(HTMLPage page) {
		System.out.println("Cookie setting up page lgt = ");
		
		page.setCorrectCombination(this.answer);
		int i = 0;
		for(Combination c : triedCombi) {
			page.setNexCombination(c);
		}
	}
	
	public void setCorrectCombination(Combination combi) {
		this.answer = combi;
		
		for(int i = 0;i < HTMLPage.LIVES;i++) { 
			triedCombi[i] = new Combination();
		}
	}
	
	public void addTry(Combination combi) {
		this.triedCombi[currentTry] = combi;
		currentTry++;
	}
	
	public Combination getRightCombination() {
		return answer;
	}
	
	public int getCurrentTry(){
		return currentTry;
	}
	
	public void reset() {
		this.currentTry = 0;
		
		for(int i = 0;i < HTMLPage.LIVES;i++) { 
			this.triedCombi[i] = new Combination();
		}
		
		Combination tmp = new Combination();
		tmp.setRandomCombi();
		
		this.answer = tmp;
	}
}
