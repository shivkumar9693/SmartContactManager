package com.Main.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
 
import org.springframework.web.bind.annotation.RequestParam;
 
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Main.Entity.User;
import com.Main.Repo.UserRepo;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;


 
 
@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserRepo repo;
	@GetMapping("/")
	public String gethome(Model model) {
		model.addAttribute("title","Home Smart-Contact-Manager");
		return "home";
	}
	
	@GetMapping("/about")
	public String getabout(Model model) {
		model.addAttribute("title","about Smart-Contact-Manager");
		return "about";
	}
 
	@GetMapping("/signup")
	public String getSignup(Model model) {
	    model.addAttribute("title", "Signup | Smart Contact Manager");
//	    model.addAttribute("user", new User());  
	    // If user is not in model, add empty one
	    if (!model.containsAttribute("user")) {
	        model.addAttribute("user", new User());
	    }
	 
	    return "signup";  
	}
  
 

	@PostMapping("/register")
	public String registerUser(
	        @Valid @ModelAttribute("user") User user, // @Valid triggers server-side validation
	        BindingResult result,                     // holds validation results
	        @RequestParam(value = "term", defaultValue = "false") boolean term,
	        Model model,
	        RedirectAttributes redirectAttributes) {

	    // Terms checkbox validation
	    if (!term) {
	        model.addAttribute("termError", "You must agree to the terms and conditions");
	    }

	    // If there are field errors or term not checked, return to signup page
	    if (result.hasErrors() || !term) {
	        return "signup"; // returns the model with validation errors
	    }

	    try {
	        // Set default values
	        user.setActive(true);
			/* user.setImgurl("default.jpeg"); */
	        user.setRole("ROLE_USER");
	        user.setPassword(encoder.encode(user.getPassword()));

	        // Save user to DB
	        User savedUser = repo.save(user);
	        System.out.println("Saved user: " + savedUser);

	        // Success flash message
	        redirectAttributes.addFlashAttribute("message", "Successfully Registered!");
	        redirectAttributes.addFlashAttribute("alertType", "alert-success");

	    } catch (DataIntegrityViolationException e) {
	        // Handle duplicate email or other DB constraint violations
	        redirectAttributes.addFlashAttribute("message", "Email already exists! Please use another.");
	        redirectAttributes.addFlashAttribute("alertType", "alert-danger");
	        return "redirect:/signup";

	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("message", "Something went wrong: " + e.getMessage());
	        redirectAttributes.addFlashAttribute("alertType", "alert-danger");
	        return "redirect:/signup";
	    }

	    // Redirect to signup to show flash message
	    return "redirect:/signin";
	}
	@GetMapping("/signin")
	public String login(Model model) {
		 model.addAttribute("title", "Signin | Smart Contact Manager");
		return "login";
		
	}
	
	@GetMapping("/error")
	public String error(Model model) {
		return"Error";
	}
 
	

	 
}
