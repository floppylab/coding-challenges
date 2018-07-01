package codingchallanges.maze;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MazeCleanerTask {

    private Logger logger = LoggerFactory.getLogger(MazeCleanerTask.class);

    @Autowired
    private MazeService mazeService;

    /**
     * Cleans up mazes when there is no time left.
     * Task is running every minute.
     */
    @Scheduled(cron = "0 * * * * *")
    public void cleanUp() {
        List<MazeData> mazes = mazeService.getMazesInProgress(); // started status
        mazes.stream().filter(m -> m.getSecondsLeft() == 0).forEach(m -> {
            mazeService.endMaze(m.getId());
            logger.error("time is over for maze " + m.getId());
        });
    }

}
