package codingchallanges.maze;

public enum MazeCellType {

    WALL(" # "),
    EMPTY("   "),
    COIN(" C "),
    ENTRANCE(" N "),
    EXIT(" X ");

    private String sign;

    private MazeCellType(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return sign;
    }

}
