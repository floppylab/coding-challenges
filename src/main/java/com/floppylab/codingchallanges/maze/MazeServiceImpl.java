package com.floppylab.codingchallanges.maze;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.floppylab.codingchallanges.common.Direction;
import com.floppylab.codingchallanges.common.Position;
import com.floppylab.codingchallanges.exception.ClientException;

@Service
public class MazeServiceImpl implements MazeService {
	
	private Logger logger = LoggerFactory.getLogger(MazeServiceImpl.class);
	
	@Autowired
	private MazeRepository mazeRepository;
	
	@Override
	public Maze findMaze(Long id) {
		Maze maze = mazeRepository.getOne(id);
		return maze;
	}
	
	@Override
	public MazeData getMazeData(Long id) {
		MazeData mazeData = mazeRepository.getMazeData(id);
		return mazeData;
	}

	@Override
	public Maze createMaze() {
		Maze maze = MazeGenerator.generateMaze();
		mazeRepository.save(maze);
		logger.error(maze.toString());
		
		// end game an hour later
		new Timer().schedule( 
			new java.util.TimerTask() {
				@Override
				public void run() {
					if(maze.getStatus() == Status.STARTED) {
						logger.error("time is over for maze " + maze.getId());
						maze.setStatus(Status.LOST);
						maze.setFinishTime(new Date());
						mazeRepository.save(maze);
					}
				}
			}, 
			3600000 
		);
		return maze;
	}
	
	@Override
	public Map<Direction, MazeCellType> getAvailableSteps(Long id) {
		Maze maze = mazeRepository.getOne(id);
		if(maze.getStatus() != Status.STARTED) throw new ClientException("maze over");
		
		Position me = maze.getMe();
		Map<Direction, MazeCellType> steps = new HashMap<>();
		for(Direction direction : Direction.values()) {
			MazeCell cell = maze.getCell(me.getX() + direction.getX(), me.getY() + direction.getY());
			// hide exit until 3 collected coins
			if(cell.getType() == MazeCellType.EXIT && maze.getCoins() != 3) {
				steps.put(direction, MazeCellType.WALL);
			} else {
				steps.put(direction, cell.getType());
			}
		}
		logger.error(maze.toString());
		return steps;		
	}

	@Override
	public void step(Long id, Direction direction) {
		Maze maze = mazeRepository.getOne(id);
		Position me = maze.getMe();
		Position newPosition = new Position(me.getX() + direction.getX(), me.getY() + direction.getY());
		MazeCell cell = maze.getCell(newPosition);
		
		// if you bump into a wall or a closed door
		if(cell.isWall() || cell.isEntrance() || (cell.isExit() && maze.getCoins() != 3)) {
			handleBump(maze);
		} else {
			handleGoodStep(maze, newPosition, cell);
		}		
	}

	@Override
	public void pickUpCoin(Long id) {
		Maze maze = mazeRepository.getOne(id);
		MazeCell me = maze.getCell(maze.getMe());
		if(me.isCoin()) {
			maze.setCell(maze.getMe(), MazeCellType.EMPTY);
			maze.setCoins(maze.getCoins() + 1);
			mazeRepository.save(maze);
		} else {
			throw new ClientException("nothing to pick up");
		}
	}
	
	@Override
	public void giveUp(Long id) {
		Maze maze = mazeRepository.getOne(id);
		maze.setStatus(Status.GAVE_UP);
		maze.setFinishTime(new Date());
		mazeRepository.save(maze);
	}
	
	private void handleGoodStep(Maze maze, Position newPosition, MazeCell cell) {
		if(cell.isExit()) {
			maze.setStatus(Status.WON);
			maze.setFinishTime(new Date());
		}
		maze.setMe(newPosition);
		logger.error(maze.toString());
		mazeRepository.save(maze);
	}

	private void handleBump(Maze maze) {
		maze.setBumps(maze.getBumps() + 1);
		
		if(maze.getCoins() > 0) {
			maze.setCoins(0);
			
			// collect all coins
			for(MazeCell mazeCell : maze.getCells()) {
				if (mazeCell.isCoin()) mazeCell.setType(MazeCellType.EMPTY);
			}
			
			// leave coins randomly
			int coinsAdded = 0;
			Random random = new Random();
			int i = random.nextInt(maze.getSize() / 2) * 2 + 1; 
			int j = random.nextInt(maze.getSize() / 2) * 2 + 1;
			while(coinsAdded != 3) {
				while(!maze.getCell(i, j).isEmpty()) {
					i = random.nextInt(maze.getSize() / 2) * 2 + 1; 
					j = random.nextInt(maze.getSize() / 2) * 2 + 1;
				}
				maze.setCell(i, j, MazeCellType.COIN);
				coinsAdded++;
			}
		}
		mazeRepository.save(maze);
		logger.error(maze.toString());
		throw new ClientException("you bumped right into a wall or a closed door");
	}

}

