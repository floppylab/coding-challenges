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
	
	@GetMapping("/maze")
    public String maze(Model model) {
		model.addAttribute("highScores", mazeService.getHighScores());
        return "challanges/maze";
    }
}
