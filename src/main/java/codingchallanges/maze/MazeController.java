package codingchallanges.maze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/challanges")
public class MazeController {

    @Autowired
    private MazeService mazeService;

    /**
     * Controller of the maze page.
     * 
     * @param model model to add attributes to
     * @return maze page
     */
    @GetMapping("/maze")
    public String maze(Model model) {
        model.addAttribute("winners", mazeService.getWinners());
        return "challanges/maze";
    }
}
