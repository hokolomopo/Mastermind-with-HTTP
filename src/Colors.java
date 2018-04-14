/**
 * enumeration representing a mastermind's color
 */
public enum Colors {
    RED("red"),
    BLUE("blue"),
    YELLOW("yellow"),
    GREEN("green"),
    WHITE("white"),
    BLACK("black"),
    EMPTY("empty");

    private String name;

    private Colors(String s) {
        this.name = s;
    }

    /**
     * get the relative path of the image corresponding to the color element
     * @return the relative path of the image corresponding to the color element
     */
    public String getImagePath() {
        return this.name + ".png";
    }

    /**
     * get the color corresponding to the name given as a string is argument
     * @param color a string containing the name of the color searched
     * @return the Colors which has the same name as the color argument, EMPTY if no name match
     */
    public static Colors getColor(String color) {
        for (Colors c : Colors.values())
            if (color.equals(c.name))
                return c;
        return EMPTY;
    }

    public String getName() {
        return this.name;
    }
}
