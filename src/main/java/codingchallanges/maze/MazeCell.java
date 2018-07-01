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

@Data
@Entity
@Table(name = "maze_cell")
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

    public MazeCell() {}

    public MazeCell(Position position, MazeCellType type) {
        super();
        this.position = position;
        this.type = type;
    }

    /**
     * Returns if the cell is empty.
     * 
     * @return if the cell is empty
     */
    public boolean isEmpty() {
        return type == MazeCellType.EMPTY;
    }

    /**
     * Returns if the cell is a coin.
     * 
     * @return if the cell is a coin
     */
    public boolean isCoin() {
        return type == MazeCellType.COIN;
    }

    /**
     * Returns if the cell is a wall.
     * 
     * @return if the cell is a wall
     */
    public boolean isWall() {
        return type == MazeCellType.WALL;
    }

    /**
     * Returns if the cell is the exit.
     * 
     * @return if the cell is the exit
     */
    public boolean isExit() {
        return type == MazeCellType.EXIT;
    }

    /**
     * Returns if the cell is the entrance.
     * 
     * @return if the cell is the entrance
     */
    public boolean isEntrance() {
        return type == MazeCellType.ENTRANCE;
    }

}
