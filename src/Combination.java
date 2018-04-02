import java.io.IOException;
import java.util.Random;

public class Combination {

	Colors[] combi = new Colors[4];
	int[] results = new int[2];
		
	public Combination() {
		
		for(int i = 0;i < results.length;i++)
			results[i] = 0;
		
		for(int i = 0;i < results.length;i++)
			combi[i] = Colors.EMPTY;
	}
	
	public void setCombi(String s) throws IOException{
		String[] colors = s.split("\\s+");
		
		if(colors.length != 4)
			throw new IOException();
		
		for(int i = 0;i<4;i++) {
			combi[i] = Colors.getColor(colors[i]);
		}
	}
	
	public void setRandomCombi() {
		Random rand = new Random();
		
		for(int i = 0;i<4;i++) {
			this.combi[i] = Colors.values()[rand.nextInt(Colors.values().length)];
		}
	}
	
	public void evaluate(Combination comparison) {
		//TODO
	}
	
	public Colors[] getColors() {
		return combi;
	}
	
	public int[] getResults() {
		return results;
	}
}
