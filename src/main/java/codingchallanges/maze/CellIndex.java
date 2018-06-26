package codingchallanges.maze;

import lombok.Data;

@Data
public class CellIndex {
	
	private int index;
	
	private MazeCell cell;
	
	public CellIndex(int index, MazeCell cell) {
		this.index = index;
		this.cell = cell;
	}

}
