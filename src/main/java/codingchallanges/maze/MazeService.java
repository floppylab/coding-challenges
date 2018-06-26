package codingchallanges.maze;

import java.util.List;
import java.util.Map;

import codingchallanges.common.Direction;

public interface MazeService {

	Maze findMaze(Long id);

	MazeData getMazeData(Long id);

	Maze createMaze();

	Map<Direction, MazeCellType> getSteps(Long id);

	void step(Long id, Direction direction);

	void pickUpCoin(Long id);

	void giveUp(Long id);
	
	List<MazeData> getHighScores();

}