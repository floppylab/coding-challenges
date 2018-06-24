package codingchallanges.maze;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MazeData {
	
	public static final long HOUR = 3600*1000;
	
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
	
	public MazeData() {}

	public MazeData(Long id, Integer size, Integer coins, Integer bumps, Date creationTime, Date finishTime, Status status) {
		super();
		this.id = id;
		this.coins = coins;
		this.bumps = bumps;
		this.creationTime = creationTime;
		this.finishTime = finishTime;
		this.status = status;
		this.size = size;
		
		// calculate secondsLeft
		Long now = new Date().getTime();
		Long endOfGame = this.creationTime.getTime() + HOUR;
	    this.secondsLeft = (endOfGame - now < 0 || status != Status.STARTED ? 0L : ((endOfGame - now) / 1000L));
	}
	
}
