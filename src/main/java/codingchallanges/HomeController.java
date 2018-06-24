package codingchallanges;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import codingchallanges.config.security.BasicUserDto;
import codingchallanges.config.security.BasicUserService;
import codingchallanges.exception.NonMatchingPasswordsException;
import codingchallanges.exception.UsernameExistsException;

@Controller
public class HomeController {

	@Autowired
	private BasicUserService userService;

	@GetMapping("/")
	public String index() {
		//return "index";
		return "redirect:/challanges/maze";
	}

	@GetMapping("/swagger")
	public String swagger() {
		return "redirect:/swagger-ui.html";
	}

	@GetMapping(value = "/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
		BasicUserDto userDto = new BasicUserDto();
		model.addAttribute("user", userDto);
		return "registration";
	}

	@PostMapping(value = "/registration")
	public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid BasicUserDto userDto, BindingResult result, WebRequest request, Errors errors) {    
		if (!result.hasErrors()) {
			try {
				userService.createUserAccount(userDto);
			} catch(UsernameExistsException e) {
				result.rejectValue("username", null, e.getErrorMessage());
			} catch(NonMatchingPasswordsException e) {
				result.rejectValue("password", null, e.getErrorMessage());
			}
		}
		if (result.hasErrors()) {
			return new ModelAndView("registration", "user", userDto);
		} 
		else {
			return new ModelAndView("challanges/maze", "user", userDto);
		}
	}
}
