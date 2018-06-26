package codingchallanges.maze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

