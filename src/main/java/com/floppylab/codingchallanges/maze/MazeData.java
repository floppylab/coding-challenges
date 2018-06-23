package com.floppylab.codingchallanges.maze;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MazeData {
	
	public static final long HOUR = 3600*1000;
	
	private Long id;
	
	private Integer coins;
	
	private Integer bumps;
	
	private Long secondsLeft;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date creationTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date finishTime;
	
	private Status status;

	public MazeData(Long id, Integer coins, Integer bumps, Date creationTime, Date finishTime, Status status) {
		super();
		this.id = id;
		this.coins = coins;
		this.bumps = bumps;
		this.creationTime = creationTime;
		this.finishTime = finishTime;
		this.status = status;
		
		// calculate secondsLeft
		Long now = new Date().getTime();
		Long endOfGame = this.creationTime.getTime() + HOUR;
	    this.secondsLeft = (endOfGame - now < 0 || status != Status.STARTED ? 0L : ((endOfGame - now) / 1000L));
	}
	
}
