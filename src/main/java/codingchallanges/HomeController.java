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
import org.springframework.web.servlet.ModelAndView;

import codingchallanges.config.security.BasicUserDto;
import codingchallanges.config.security.BasicUserService;
import codingchallanges.exception.NonMatchingPasswordsException;
import codingchallanges.exception.UsernameExistsException;


@Controller
public class HomeController {

    @Autowired
    private BasicUserService userService;

    /**
     * Redirects to the maze page.
     * 
     * @return maze page
     */
    @GetMapping("/")
    public String index() {
        //return "index";
        return "redirect:/challanges/maze";
    }

    /**
     * Controller of the Swagger documentation.
     * Redirects to the swagger-ui.html
     * 
     * @return Swagger documentation page
     */
    @GetMapping("/swagger")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

    /**
     * Controller of the registration page.
     * 
     * @param model model to add attributes to
     * @return registration page
     */
    @GetMapping(value = "/registration")
    public String showRegistrationForm(Model model) {
        BasicUserDto userDto = new BasicUserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    /**
     * Controller of the registration request.
     * 
     * @param userDto user to register
     * @param result form results
     * @param errors errors
     * @return page depending on the registration
     */
    @PostMapping(value = "/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid BasicUserDto userDto, BindingResult result, Errors errors) {
        if (!result.hasErrors()) {
            try {
                userService.createUserAccount(userDto);
            } catch (UsernameExistsException e) {
                result.rejectValue("username", null, e.getErrorMessage());
            } catch (NonMatchingPasswordsException e) {
                result.rejectValue("password", null, e.getErrorMessage());
            }
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", userDto);
        } else {
            return new ModelAndView("challanges/maze", "user", userDto);
        }
    }
}
