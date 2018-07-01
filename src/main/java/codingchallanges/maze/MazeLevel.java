package codingchallanges.maze;

public enum MazeLevel {

    BEGINNER(7),
    ADVANCED(15),
    EXPERT(31);

    private int size;

    private MazeLevel(int size) {
        this.size = size;
    }

    /**
     * Returns the size of the maze.
     * 
     * @return size of the maze
     */
    public int getSize() {
        return size;
    }

}
