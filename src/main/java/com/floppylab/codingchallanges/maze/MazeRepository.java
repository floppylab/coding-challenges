package com.floppylab.codingchallanges.maze;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MazeRepository extends JpaRepository<Maze, Long> {

	@Query("SELECT new com.floppylab.codingchallanges.maze.MazeData(m.id, m.coins, m.bumps, m.creationTime, m.finishTime, m.status) FROM Maze m WHERE m.id = :id")
	MazeData getMazeData(@Param("id") Long id);

}
