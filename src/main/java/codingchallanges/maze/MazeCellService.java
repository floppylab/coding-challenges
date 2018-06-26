package codingchallanges.maze;

import codingchallanges.common.Position;

public interface MazeCellService {

	void deleteMazeCells(Maze maze);
	
	MazeCell getCell(Maze maze, int x, int y);
	
	MazeCell getCell(Maze maze, Position position);
	
	void setCell(Maze maze, int x, int y, MazeCellType type);
	
	void setCell(Maze maze, Position position, MazeCellType type);

}