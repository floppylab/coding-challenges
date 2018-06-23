package com.floppylab.codingchallanges.maze;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.floppylab.codingchallanges.common.Direction;

@RestController
@RequestMapping("api/mazes")
public class MazeController {
	
	@Autowired
	private MazeService mazeService;
	
	/* ---------------- GET ------------------ */

	@GetMapping("/{id}")
    public MazeData getMazeData(@PathVariable Long id) {
        MazeData maze = mazeService.getMazeData(id);
        return maze;
    }
	
	@GetMapping("/{id}/steps")
	public Map<Direction, MazeCellType> getSteps(@PathVariable Long id) {
		Map<Direction, MazeCellType> steps = mazeService.getAvailableSteps(id);
		return steps;
	}
	
	/* ---------------- POST ------------------ */
	
	@PostMapping("/")
    public Long createMaze() {
		Maze maze = mazeService.createMaze();
        return maze.getId();
    }	
	
	@PostMapping("/{id}/steps")
    public ResponseEntity<Void> step(@PathVariable Long id, Direction direction) {
		mazeService.step(id, direction);
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/{id}/coins")
    public ResponseEntity<Void> pickUpCoin(@PathVariable Long id) {
		mazeService.pickUpCoin(id);
		return new ResponseEntity<>(HttpStatus.OK);
    }	
	
	/* ---------------- DELETE ------------------ */
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> giveUp(@PathVariable Long id) {
		mazeService.giveUp(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
