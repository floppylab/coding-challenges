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
     * Returns a list of won mazes in the following order.
     * <ul>
     * <li>maze size descending</li>
     * <li>number of coins collected descending</li>
     * <li>number of bumps ascending</li>
     * <li>finish time descending</li>
     * </ul>
     * 
     * @return list of mazes
     */
    @Query("SELECT new codingchallanges.maze.MazeData(m.id, m.size, m.coins, m.bumps, m.creationTime, m.finishTime, m.status, m.username) FROM Maze m where m.status = 'WON' "
            + " ORDER BY m.size desc, m.coins desc, m.bumps asc, m.finishTime desc")
    List<MazeData> getHighScoresWon();

    /**
     * Returns a list of not won mazes in the following order.
     * <ul>
     * <li>maze size descending</li>
     * <li>number of coins collected descending</li>
     * <li>number of bumps ascending</li>
     * <li>finish time descending</li>
     * </ul>
     * 
     * @return list of mazes
     */
    @Query("SELECT new codingchallanges.maze.MazeData(m.id, m.size, m.coins, m.bumps, m.creationTime, m.finishTime, m.status, m.username) FROM Maze m where m.status != 'WON' "
            + " ORDER BY m.size desc, m.coins desc, m.bumps asc, m.finishTime desc")
    List<MazeData> getHighScoresNotWon();

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

}
