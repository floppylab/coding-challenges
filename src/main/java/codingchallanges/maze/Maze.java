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
import javax.persistence.Transient;

import codingchallanges.common.Position;
import lombok.Data;

@Data
@Entity
@Table(name = "maze")
public class Maze {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "maze")
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
        @AttributeOverride(name = "x", column = @Column(name = "me_x")),
        @AttributeOverride(name = "y", column = @Column(name = "me_y"))
        })
    private Position me;

    private String username;

    @Transient
    private CellIndex[][] mazeCells;

    public Maze() {}

    public Maze(Integer size) {
        this.size = size;
    }

    /**
     * Fills up the mazeCells for an internal representation.
     */
    public void fillUpMazeCells() {
        if (mazeCells != null) return;
        mazeCells = new CellIndex[size][size];
        for (int i = 0; i < size * size; i++) {
            MazeCell cell = cells.get(i);
            mazeCells[cell.getPosition().getX()][cell.getPosition().getY()] = new CellIndex(i, cell);
        }
    }

    /**
     * Returns the MazeCell at the given position.
     * 
     * @param x row position
     * @param y column position
     * @return MazeCell at the given position
     */
    public MazeCell getCell(int x, int y) {
        fillUpMazeCells();
        return mazeCells[x][y].getCell();
    }

    /**
     * Returns the MazeCell at the given position.
     * 
     * @param position position
     * @return MazeCell at the given position
     */
    public MazeCell getCell(Position position) {
        fillUpMazeCells();
        return mazeCells[position.getX()][position.getY()].getCell();
    }

    /**
     * Sets the given cell type to the given position.
     * 
     * @param x row position
     * @param y column position
     * @param type type of cell
     */
    public void setCell(int x, int y, MazeCellType type) {
        fillUpMazeCells();
        CellIndex cellIndex = mazeCells[x][y];
        MazeCell cell = cellIndex.getCell();
        cell.setType(type);
        cells.set(cellIndex.getIndex(), cell);
    }

    /**
     * Sets the given cell type to the given position.
     * 
     * @param position position
     * @param type type of cell
     */
    public void setCell(Position position, MazeCellType type) {
        fillUpMazeCells();
        CellIndex cellIndex = mazeCells[position.getX()][position.getY()];
        MazeCell cell = cellIndex.getCell();
        cell.setType(type);
        cells.set(cellIndex.getIndex(), cell);
    }

    /**
     * Add a new cell to the maze.
     * 
     * @param cell cell to add
     */
    public void addCell(MazeCell cell) {
        cells.add(cell);
        cell.setMaze(this);
    }

    /**
     * Removes a cell from the maze.
     * 
     * @param cell cell to remove
     */
    public void removeCell(MazeCell cell) {
        cells.remove(cell);
        cell.setMaze(null);
    }

    @Override
    public String toString() {
        fillUpMazeCells();
        StringBuffer maze = new StringBuffer();
        maze.append("maze " + id + ": \n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (me.getX() == i && me.getY() == j) maze.append(" M ");
                else maze.append(mazeCells[i][j].getCell().getType());
            }
            maze.append('\n');
        }
        return maze.toString();
    }
}
