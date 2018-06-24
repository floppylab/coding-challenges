package codingchallanges.maze;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import codingchallanges.common.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "maze")
@NoArgsConstructor
public class Maze {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="maze")
	private List<MazeCell> cells = new ArrayList<>();
	
	private Integer size;
	
	private Integer coins = 0;
	
	private Integer bumps = 0;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.STARTED;
	
	@Embedded
	   @AttributeOverrides({
	       @AttributeOverride(name="x", column=@Column(name="me_x")),
	       @AttributeOverride(name="y", column=@Column(name="me_y"))
	   })
	private Position me;
	
	public Maze(Integer size) {
		this.size = size;
	}
	
	public MazeCell getCell(int x, int y) {
		return cells.get(x * size + y);
	}
	
	public MazeCell getCell(Position position) {
		return cells.get(position.getX() * size + position.getY());
	}
	
	public void setCell(int x, int y, MazeCellType type) {
		MazeCell cell = getCell(x, y);
		cell.setType(type);
		cells.set(x * size + y, cell);
	}
	
	public void setCell(Position position, MazeCellType type) {
		MazeCell cell = getCell(position);
		cell.setType(type);
		cells.set(position.getX() * size + position.getY(), cell);
	}
	
	public void addCell(MazeCell cell) {
        cells.add(cell);
        cell.setMaze(this);
    }
 
    public void removeCell(MazeCell cell) {
        cells.remove(cell);
        cell.setMaze(null);
    }
	
	@Override
	public String toString() {
		StringBuffer maze = new StringBuffer();
		maze.append('\n');
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(me.getX() == i && me.getY() == j) maze.append(" M ");
				else maze.append(getCell(i, j).getType());
			}
			maze.append('\n');
		}
		return maze.toString();
	}
}
