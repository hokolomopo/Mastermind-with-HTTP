import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * class representing a mastermind's combination
 */
public class Combination{
    public static final int COMBI_LENGTH = 4;

    //Colors of the combination
    private Colors[] combi = new Colors[COMBI_LENGTH];

    //Results of the last evaluation of this combination
    //result[0] = right color right place, result[1] = right color wrong place
    private int[] results = new int[2];

    public Combination(){

        for (int i = 0; i < results.length; i++)
            results[i] = 0;

        for (int i = 0; i < combi.length; i++)
            combi[i] = Colors.EMPTY;
    }

    /**
     * create a combination based on a string containing the colors of the combination,
     * the results are set to 0
     *
     * @param s the string representing the colors of the combination, formatted this way:
     *          "color1+color2+color3+color4"
     * @throws BadFormatException in case s is ill formatted
     * @throws BadColorException  in case a color which is not part of mastermind's color is entered
     */
    public Combination(String s) throws BadFormatException, BadColorException{
        for (int i = 0; i < results.length; i++)
            results[i] = 0;

        setCombi(s);
    }

    /**
     * Set the combination according to the argument string
     *
     * @param s String representing the colors that must have the format : "color1+color2+color3+color4"
     * @throws BadFormatException in case s is ill formatted
     * @throws BadColorException  in case a color which is not part of mastermind's color is entered
     */
    public void setCombi(String s) throws BadFormatException, BadColorException{
        //Cut the string into the colors
        ArrayList<String> colors = new ArrayList<String>(Arrays.asList(s.split("\\+")));

        //Remove empty Strings
        for (int i = 0; i < colors.size(); i++)
            if (colors.get(i).length() == 0)
                colors.remove(i--);

        //Check if we have the right amount of colors
        if (colors.size() != COMBI_LENGTH)
            throw new BadFormatException();

        //Transform the Strings into Colors
        for (int i = 0; i < COMBI_LENGTH; i++){

            combi[i] = Colors.getColor(colors.get(i));

            //Throws exception if it was a bas color name
            if (combi[i] == Colors.EMPTY)
                throw new BadColorException();
        }
    }

    /**
     * Set the combination to a random combination
     */
    public void setRandomCombi(){
        Random rand = new Random();

        for (int i = 0; i < COMBI_LENGTH; i++){
            //Take a random Color, put length - 1 to avoid picking EMPTY color
            this.combi[i] = Colors.values()[rand.nextInt(Colors.values().length - 1)];
        }
    }

    /**
     * Compare the combination to another combination and put the
     * results (good color wrong place/good color right place) in the results field of this class
     *
     * @param comparison the combination this combination is compared to
     */
    public void evaluate(Combination comparison){
        boolean answerUsed[] = new boolean[COMBI_LENGTH];
        boolean guessUsed[] = new boolean[COMBI_LENGTH];
        for (int i = 0; i < COMBI_LENGTH; i++){
            answerUsed[i] = false;
            guessUsed[i] = false;
        }

        //Reset results
        for (int i = 0; i < 2; i++)
            results[i] = 0;

        //Count correct and right placed
        for (int i = 0; i < COMBI_LENGTH; i++){
            if (this.combi[i] == comparison.getColors()[i]){
                this.results[0]++;
                answerUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        //Count correct but wrong placed
        for (int i = 0; i < COMBI_LENGTH; i++){
            if (guessUsed[i])
                continue;

            for (int j = 0; j < COMBI_LENGTH; j++){
                if (answerUsed[j])
                    continue;
                if (this.combi[i] == comparison.getColors()[j]){
                    this.results[1]++;
                    answerUsed[j] = true;
                    break; // no need to set guessUsed[i] to true as we break and thus never come back to this guess again
                }
            }
        }
    }

    public Colors[] getColors(){
        return combi;
    }

    public int[] getResults(){
        return results;
    }
}
