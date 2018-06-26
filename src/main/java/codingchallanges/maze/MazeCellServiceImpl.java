package codingchallanges.maze;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codingchallanges.common.Position;

@Service
public class MazeCellServiceImpl implements MazeCellService {
	
	private Logger logger = LoggerFactory.getLogger(MazeCellServiceImpl.class);
	
	@Autowired
	private MazeCellRepository mazeCellRepository;

	@Override
	public void deleteMazeCells(Maze maze) {
		logger.error("deleting maze cells " + maze.getId());
		mazeCellRepository.deleteByMaze(maze);
		logger.error("should be 0: " + maze.getCells().size());
	}

	@Override
	public MazeCell getCell(Maze maze, int x, int y) {
		return mazeCellRepository.getCell(maze, x, y);
	}

	@Override
	public MazeCell getCell(Maze maze, Position position) {
		return mazeCellRepository.getCell(maze, position);
	}
	
	@Override
	@Transactional
	public void setCell(Maze maze, int x, int y, MazeCellType type) {
		mazeCellRepository.setCell(maze, x, y, type);		
	}

	@Override
	@Transactional
	public void setCell(Maze maze, Position position, MazeCellType type) {
		mazeCellRepository.setCell(maze, position, type);
	}

}

