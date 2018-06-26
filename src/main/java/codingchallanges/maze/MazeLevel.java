package codingchallanges.maze;

public enum MazeLevel {
	
	STARTER(15),
	ADVANCED(31);
	
	private int size;
	
	private MazeLevel(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}

}
