package codingchallanges.maze;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codingchallanges.common.Direction;
import codingchallanges.common.Position;
import codingchallanges.config.security.SecurityService;
import codingchallanges.exception.ClientException;

@Service
public class MazeServiceImpl implements MazeService {
	
	private Logger logger = LoggerFactory.getLogger(MazeServiceImpl.class);
	
	@Autowired
	private MazeRepository mazeRepository;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private MazeCellService mazeCellService;
	
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
		maze.setUsername(securityService.username());
		mazeRepository.save(maze);
		
		Long id = maze.getId();
		logger.error(maze.toString());
		
		// end game an hour later
		endMaze(id);
		return maze;
	}
	
	@Override
	public Map<Direction, MazeCellType> getSteps(Long id) {
		Maze maze = mazeRepository.getOne(id);
		if(!maze.getUsername().equals(securityService.username())) throw new ClientException("you are not in this maze");
		if(maze.getStatus() != Status.STARTED) throw new ClientException("maze over");
		
		Position me = maze.getMe();
		Map<Direction, MazeCellType> steps = new HashMap<>();
		for(Direction direction : Direction.values()) {
			MazeCell cell = mazeCellService.getCell(maze, me.getX() + direction.getX(), me.getY() + direction.getY());
			// hide exit until 3 collected coins
			if(cell.getType() == MazeCellType.EXIT && maze.getCoins() != 3) {
				steps.put(direction, MazeCellType.WALL);
			} else {
				steps.put(direction, cell.getType());
			}
		}
		return steps;		
	}

	@Override
	public void step(Long id, Direction direction) {
		if(direction == Direction.ON) return;	// do nothing
		Maze maze = mazeRepository.getOne(id);
		if(!maze.getUsername().equals(securityService.username())) throw new ClientException("you are not in this maze");
		if(maze.getStatus() != Status.STARTED) throw new ClientException("maze over");
		
		Position me = maze.getMe();
		Position newPosition = new Position(me.getX() + direction.getX(), me.getY() + direction.getY());
		MazeCell cell = mazeCellService.getCell(maze, newPosition);
		
		// if you bump into a wall or a closed door
		if(cell.isWall() || cell.isEntrance() || (cell.isExit() && maze.getCoins() != 3)) {
			handleBump(maze);
		} else {
			handleGoodStep(maze, newPosition, cell);
			logger.error(maze.toString());
		}		
	}

	@Override
	public void pickUpCoin(Long id) {
		Maze maze = mazeRepository.getOne(id);
		if(!maze.getUsername().equals(securityService.username())) throw new ClientException("you are not in this maze");
		if(maze.getStatus() != Status.STARTED) throw new ClientException("maze over");
		
		MazeCell me = mazeCellService.getCell(maze, maze.getMe());
		if(me.isCoin()) {
			mazeCellService.setCell(maze, maze.getMe(), MazeCellType.EMPTY);
			maze.setCoins(maze.getCoins() + 1);
			mazeRepository.save(maze);
		} else {
			throw new ClientException("nothing to pick up");
		}
	}
	
	@Override
	public void giveUp(Long id) {
		Maze maze = mazeRepository.getOne(id);
		if(!maze.getUsername().equals(securityService.username())) throw new ClientException("you are not in this maze");
		if(maze.getStatus() != Status.STARTED) throw new ClientException("maze over");
		
		maze.setStatus(Status.GAVE_UP);
		maze.setFinishTime(new Date());
		mazeRepository.save(maze);
	}
	
	@Override
	public List<MazeData> getHighScores() {
		List<MazeData> highScores = mazeRepository.getHighScoresWon();
		if(highScores.size() < 20) {
			highScores.addAll(mazeRepository.getHighScoresNotWon());
		}
		return highScores.subList(0, 20);
	}
	
	private void handleGoodStep(Maze maze, Position newPosition, MazeCell cell) {
		if(cell.isExit()) {
			maze.setStatus(Status.WON);
			maze.setFinishTime(new Date());
		}
		maze.setMe(newPosition);
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
				while(!mazeCellService.getCell(maze, i, j).isEmpty()) {
					i = random.nextInt(maze.getSize() / 2) * 2 + 1; 
					j = random.nextInt(maze.getSize() / 2) * 2 + 1;
				}
				mazeCellService.setCell(maze, i, j, MazeCellType.COIN);
				coinsAdded++;
			}
		}
		mazeRepository.save(maze);
		throw new ClientException("you bumped right into a wall or a closed door");
	}
	
	private void endMaze(Long id) {
		new Timer().schedule( 
			new java.util.TimerTask() {
				@Override
				public void run() {
					Maze maze = mazeRepository.getOne(id);
					if(maze.getStatus() == Status.STARTED) {
						logger.error("time is over for maze " + maze.getId());
						maze.setStatus(Status.LOST);
						maze.setFinishTime(new Date());
					}
					mazeCellService.deleteMazeCells(maze);
					mazeRepository.save(maze);
					cancel();
				}
			}, 
			3600000 
		);
	}

}

