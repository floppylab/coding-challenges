package codingchallanges.maze;

import java.util.List;
import java.util.Map;

import codingchallanges.common.Direction;

public interface MazeService {

    /**
     * Returns some general data about the selected maze.
     * 
     * @param id identifier of maze
     * @return data of selected maze
     */
    MazeData getMazeData(Long id);

    /**
     * Creates a new maze.
     * @param level level of maze
     * 
     * @return identifier of the new maze
     */
    Maze createMaze(MazeLevel level);

    /**
     * Returns cell types around.
     * 
     * @param id identifier of maze
     * @return a map binding directions and cell types
     */
    Map<Direction, MazeCellType> getSteps(Long id);

    /**
     * Makes a step in the given direction.
     * 
     * @param id identifier of maze
     * @param direction direction
     */
    void step(Long id, Direction direction);

    /**
     * Picks up a coin.
     * 
     * @param id identifier of maze
     */
    void pickUpCoin(Long id);

    /**
     * Gives up the game.
     * 
     * @param id identifier of maze
     */
    void giveUp(Long id);

    /**
     * Returns all-time winners by levels.
     * 
     * @return a map binding levels and the name of winners
     */
    Map<MazeLevel, List<String>> getWinners();

    /**
     * Returns a list of mazes that are currently in progress.
     * 
     * <ul>
     * <li>status is STARTED</li>
     * </ul>
     * @return a list of mazes
     */
    List<MazeData> getMazesInProgress();

    /**
     * Ends maze by setting status to LOST and setting current time to finishTime.
     * 
     * @param id identifier of maze
     */
    void endMaze(Long id);

}