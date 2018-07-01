package codingchallanges.maze;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codingchallanges.common.Direction;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/mazes")
public class MazeRestController {

    @Autowired
    private MazeService mazeService;

    /* ---------------- GET ------------------ */

    /**
     * Returns some general data about the selected maze.
     * 
     * @param id identifier of maze
     * @return data of selected maze
     */
    @ApiOperation(value = "returns some general data about the selected maze")
    @GetMapping("/{id}")
    public MazeData getMazeData(@PathVariable Long id) {
        MazeData maze = mazeService.getMazeData(id);
        return maze;
    }

    /**
     * Returns cell types around.
     * 
     * @param id identifier of maze
     * @return a map binding directions and cell types
     */
    @ApiOperation(value = "returns cell types around")
    @GetMapping("/{id}/steps")
    public Map<Direction, MazeCellType> getSteps(@PathVariable Long id) {
        Map<Direction, MazeCellType> steps = mazeService.getSteps(id);
        return steps;
    }

    /* ---------------- POST ------------------ */

    /**
     * Creates a new maze.
     * 
     * @param level level of maze
     * @return identifier of the new maze
     */
    @ApiOperation(value = "creates a new maze")
    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createMaze(MazeLevel level) {
        Maze maze = mazeService.createMaze(level);
        return maze.getId();
    }

    /**
     * Makes a step in the given direction.
     * 
     * @param id identifier of maze
     * @param direction direction
     * @return empty response entity
     */
    @ApiOperation(value = "makes a step in the given direction")
    @PostMapping("/{id}/steps")
    public ResponseEntity<Void> step(@PathVariable Long id, Direction direction) {
        mazeService.step(id, direction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Picks up a coin.
     * 
     * @param id identifier of maze
     * @return empty response entity
     */
    @ApiOperation(value = "picks up a coin")
    @PostMapping("/{id}/coins")
    public ResponseEntity<Void> pickUpCoin(@PathVariable Long id) {
        mazeService.pickUpCoin(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* ---------------- DELETE ------------------ */

    /**
     * Gives up the game.
     * 
     * @param id identifier of maze
     * @return empty response entity
     */
    @ApiOperation(value = "gives up the game")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> giveUp(@PathVariable Long id) {
        mazeService.giveUp(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
