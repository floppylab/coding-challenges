package codingchallanges.maze;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import codingchallanges.common.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "maze_cell")
@NoArgsConstructor
public class MazeCell {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Position position;
	
	@Enumerated(EnumType.STRING)
    private MazeCellType type;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maze_id")
	private Maze maze;

	public MazeCell(Position position, MazeCellType type) {
		super();
		this.position = position;
		this.type = type;
	}
	
	public boolean isEmpty() {
		return type == MazeCellType.EMPTY;
	}
	
	public boolean isCoin() {
		return type == MazeCellType.COIN;
	}
	
	public boolean isWall() {
		return type == MazeCellType.WALL;
	}
	
	public boolean isExit() {
		return type == MazeCellType.EXIT;
	}
	
	public boolean isEntrance() {
		return type == MazeCellType.ENTRANCE;
	}
	
}
