public enum Color {
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    ORANGE("orange"),
    PURPLE("purple"),
    BLACK("black"),
    YELLOW("yellow"),
    GRAY("gray"),
    AQUA("aqua");

    public String getColor() {
        return color;
    }

    private final String color;

    Color(String color) {
        this.color = color;
    }
}
