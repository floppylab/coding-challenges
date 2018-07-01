package codingchallanges.maze;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MazeRepository extends JpaRepository<Maze, Long> {

    /**
     * Returns a MazeData object for the selected maze.
     * 
     * @param id identifier of maze
     * @return MazeData object
     */
    @Query("SELECT new codingchallanges.maze.MazeData(m.id, m.size, m.coins, m.bumps, m.creationTime, m.finishTime, m.status, m.username) FROM Maze m WHERE m.id = :id")
    MazeData getMazeData(@Param("id") Long id);

    /**
     * Returns a list of mazes that are currently in progress.
     * 
     * <ul>
     * <li>status is STARTED</li>
     * </ul>
     * @return a list of mazes
     */
    @Query("SELECT new codingchallanges.maze.MazeData(m.id, m.size, m.coins, m.bumps, m.creationTime, m.finishTime, m.status, m.username) FROM Maze m "
        + " where m.status = 'STARTED'")
    List<MazeData> getMazesInProgress();

    /**
     * Returns the name of winning users by maze size.
     * 
     * @param size size of maze
     * @return list of usernames
     */
    @Query("SELECT DISTINCT m.username FROM Maze m WHERE m.status = 'WON' and m.size = :size")
    List<String> getWinnersByLevel(@Param("size") Integer size);

}
