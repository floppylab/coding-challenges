package codingchallanges.maze;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/challanges")
public class MazeController {
	
	/*@Autowired
	private MazeService mazeService;*/
	
	@GetMapping("/maze")
    public String index() {
        return "challanges/maze";
    }
}
