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
	
	private void generateId() {
		Random rand = new Random();
		int tmp = rand.nextInt((int)Math.pow(10, COOKIE_LENGTH));
		
		this.id = Integer.toString(tmp);
	}
	
	public String getId() {
		return COOKIE_TYPE + ":" + this.id;
	}
	
	public void setUpHTMLPage(HTMLPage page) {
		
		page.setCorrectCombination(this.answer);
		
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
}
