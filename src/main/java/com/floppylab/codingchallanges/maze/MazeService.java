package com.floppylab.codingchallanges.maze;

import java.util.Map;

import com.floppylab.codingchallanges.common.Direction;

public interface MazeService {

	Maze findMaze(Long id);

	MazeData getMazeData(Long id);

	Maze createMaze();

	Map<Direction, MazeCellType> getAvailableSteps(Long id);

	void step(Long id, Direction direction);

	void pickUpCoin(Long id);

	void giveUp(Long id);

}