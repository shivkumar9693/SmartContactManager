package com.Main.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Main.Entity.User;
import com.Main.Repo.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepo repo;
	
	@GetMapping("/home")
	public String getUser(Model model,Principal principal) {
		String name=principal.getName();
		 
		User user=repo.getUserByUserName(name);
		model.addAttribute(user);
	 
		return "normal/Dashboard";
	}

}
