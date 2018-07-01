package codingchallanges.maze;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MazeData {

    public static final long HOUR = 3600 * 1000;

    private Long id;

    private Integer size;

    private Integer coins;

    private Integer bumps;

    private Long secondsLeft;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date finishTime;

    private Status status;

    private String username;

    /**
     * Initializes MazeData object from database representation.
     * 
     * @param id identifier of maze
     * @param size size of maze
     * @param coins number of coins collected
     * @param bumps number of bumps
     * @param creationTime when the maze started
     * @param finishTime when the maze finished
     * @param status status of maze
     * @param username username of player
     */
    public MazeData(Long id, Integer size, Integer coins, Integer bumps, Date creationTime,
            Date finishTime, Status status, String username) {
        super();
        this.id = id;
        this.coins = coins;
        this.bumps = bumps;
        this.creationTime = creationTime;
        this.finishTime = finishTime;
        this.status = status;
        this.size = size;
        this.username = username;

        // calculate secondsLeft
        Long now = new Date().getTime();
        Long endOfGame = this.creationTime.getTime() + HOUR;
        this.secondsLeft = (endOfGame - now < 0 || status != Status.STARTED ? 0L : ((endOfGame - now) / 1000L));
    }

}
