package codingchallanges.common;

public enum Direction {
	
	NORTH(-1, 0),
	EAST(0, 1),
	SOUTH(1, 0),
	WEST(0, -1),
	ON(0, 0);
	
	private int x;
	private int y;
	
	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
