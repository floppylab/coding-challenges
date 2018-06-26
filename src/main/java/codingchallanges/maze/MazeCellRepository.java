package codingchallanges.maze;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import codingchallanges.common.Position;

public interface MazeCellRepository extends JpaRepository<MazeCell, Long> {
	
	void deleteByMaze(Maze maze);
	
	@Query("SELECT c from MazeCell c where c.maze = :maze and c.position.x = :x and c.position.y = :y")
	MazeCell getCell(@Param("maze") Maze maze, @Param("x") int x, @Param("y") int y);
	
	@Query("SELECT c from MazeCell c where c.maze = :maze and c.position = :position")
	MazeCell getCell(@Param("maze") Maze maze, @Param("position") Position position);
	
	@Modifying
	@Query("UPDATE MazeCell c set c.type = :cellType where c.maze = :maze and c.position.x = :x and c.position.y = :y")
	void setCell(@Param("maze") Maze maze, @Param("x") int x, @Param("y") int y, @Param("cellType") MazeCellType type);
	
	@Modifying
	@Query("UPDATE MazeCell c set c.type = :cellType where c.maze = :maze and c.position = :position")
	void setCell(@Param("maze") Maze maze, @Param("position") Position position, @Param("cellType") MazeCellType type);
}
