package com.floppylab.codingchallanges.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.floppylab.codingchallanges.common.Position;

public class MazeGenerator {

	private static final int MAZE_SIZE = 15; 
	private static Random random = new Random();
	private static int[][] maze;

	public static Maze generateMaze() {
		int[][] simpleMaze = generate();
		Maze maze = new Maze(MAZE_SIZE);
		for (int i = 0; i < MAZE_SIZE; i++) {
			for (int j = 0; j < MAZE_SIZE; j++) {
				maze.addCell(new MazeCell(new Position(i, j), simpleMaze[i][j] == 0 ? MazeCellType.EMPTY : MazeCellType.WALL));
			}
		}
		// adding entrance
		int x = random.nextInt(MAZE_SIZE / 2) * 2 + 1;
		maze.setCell(x, 0, MazeCellType.ENTRANCE);
		maze.setCell(random.nextInt(MAZE_SIZE / 2) * 2 + 1, MAZE_SIZE - 1, MazeCellType.EXIT);
		
		// adding me 
		maze.setMe(new Position(x, 1));

		// adding coins
		int coinsAdded = 0;
		int i = random.nextInt(MAZE_SIZE / 2) * 2 + 1; 
		int j = random.nextInt(MAZE_SIZE / 2) * 2 + 1;
		while(coinsAdded != 3) {
			while(!maze.getCell(i, j).isEmpty()) {
				i = random.nextInt(MAZE_SIZE / 2) * 2 + 1; 
				j = random.nextInt(MAZE_SIZE / 2) * 2 + 1;
			}
			maze.setCell(i, j, MazeCellType.COIN);
			coinsAdded++;
		}
		return maze;
	}
	
	private static int[][] generate() {
		maze = new int[MAZE_SIZE][MAZE_SIZE];
		// initialize
		for (int i = 0; i < MAZE_SIZE; i++)
			for (int j = 0; j < MAZE_SIZE; j++)
				maze[i][j] = 1;

		// starting cell
		maze[1][1] = 0;

		//　allocate the maze with recursive method
		recursion(1, 1);

		return maze;
	}

	private static void recursion(int r, int c) {
		// 4 random directions
		Integer[] randDirs = generateRandomDirections();
		// examine each direction
		for (int i = 0; i < randDirs.length; i++) {

			switch(randDirs[i]){
			case 1: // 2 cells up is out or not
				if (r - 2 <= 0)
					continue;
				if (maze[r - 2][c] != 0) {
					maze[r-2][c] = 0;
					maze[r-1][c] = 0;
					recursion(r - 2, c);
				}
				break;
			case 2: // 2 cells to the right is out or not
				if (c + 2 >= MAZE_SIZE - 1)
					continue;
				if (maze[r][c + 2] != 0) {
					maze[r][c + 2] = 0;
					maze[r][c + 1] = 0;
					recursion(r, c + 2);
				}
				break;
			case 3: // 2 cells down is out or not
				if (r + 2 >= MAZE_SIZE - 1)
					continue;
				if (maze[r + 2][c] != 0) {
					maze[r+2][c] = 0;
					maze[r+1][c] = 0;
					recursion(r + 2, c);
				}
				break;
			case 4: // 2 cells to the left is out or not
				if (c - 2 <= 0)
					continue;
				if (maze[r][c - 2] != 0) {
					maze[r][c - 2] = 0;
					maze[r][c - 1] = 0;
					recursion(r, c - 2);
				}
				break;
			}
		}

	}

	private static Integer[] generateRandomDirections() {
		ArrayList<Integer> randoms = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++)
			randoms.add(i + 1);
		Collections.shuffle(randoms);

		return randoms.toArray(new Integer[4]);
	}

	public static void main(String[] args) {
		Maze maze = generateMaze();
		System.out.println(maze);
	}

}
