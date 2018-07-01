package codingchallanges.maze;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MazeCellRepository extends JpaRepository<MazeCell, Long> {

    /**
     * Deletes maze cells by their maze.
     * 
     * @param maze maze of the cells
     */
    void deleteByMaze(Maze maze);

}
