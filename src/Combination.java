import java.io.IOException;
import java.util.Random;

public class Combination {
    public static final int COMBI_LENGTH = 4;

    //Colors of the combination
    private Colors[] combi = new Colors[COMBI_LENGTH];

    //Results of the last evaluation of this combination
    //result[0] = right color right place, result[1] = right color wrong place
    private int[] results = new int[2];

    public Combination() {

        for (int i = 0; i < results.length; i++)
            results[i] = 0;

        for (int i = 0; i < combi.length; i++)
            combi[i] = Colors.EMPTY;
    }

    public Combination(String s) throws BadFormatException, BadColorException {
        for (int i = 0; i < results.length; i++)
            results[i] = 0;

        setCombi(s);
    }

    /*
     * Set the combination to the argument string
     * String must have the format : "color1+color2+color3+color4"
     *
     * Throw IOException if string isn't of expected format or if a color doesn't exist
     */
    public void setCombi(String s) throws BadFormatException, BadColorException {

        //Cut the string into the colors
        String[] colors = s.split("\\+");

        //Check if we have the right amount of colors
        if (colors.length != COMBI_LENGTH)
            throw new BadFormatException();

        //Transform the Strings into Colors
        for (int i = 0; i < COMBI_LENGTH; i++) {
            combi[i] = Colors.getColor(colors[i]);

            //Throws exception if it was a bas color name
            if (combi[i] == Colors.EMPTY)
                throw new BadColorException();
        }
    }

    //Set the combination to a random combination
    public void setRandomCombi() {
        Random rand = new Random();

        for (int i = 0; i < COMBI_LENGTH; i++) {
            //Take a random Color, put length - 1 to avoid picking EMPTY color
            this.combi[i] = Colors.values()[rand.nextInt(Colors.values().length - 1)];
        }
    }

    /*
     * Compare the combination to another combination and put the
     * results (good color wrong place/good color right place) in the results field of this class
     */
    public void evaluate(Combination comparison) {

        //Reset results
        for (int i = 0; i < 2; i++)
            results[i] = 0;

        for (int i = 0; i < COMBI_LENGTH; i++) {

            //Count correct and right place
            if (this.combi[i] == comparison.getColors()[i])
                this.results[0]++;

                //Count correct but wrong place
            else
                for (int j = 0; j < COMBI_LENGTH; j++)
                    if (this.combi[j] == comparison.getColors()[i]) {
                        this.results[1]++;
                        break;
                    }
        }
    }

    public Colors[] getColors() {
        return combi;
    }

    public int[] getResults() {
        return results;
    }
}
