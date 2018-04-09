
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

    public String getImagePath() {
        return this.name + ".png";
    }

    //Get a Color from a string
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
