package com.Main.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Main.Entity.Contact;
import com.Main.Entity.User;
import com.Main.Repo.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepo repo;
	
	@ModelAttribute
	public void addcommonData(Model model,Principal principal) {
		String name=principal.getName();
		 
		User user=repo.getUserByUserName(name);
		model.addAttribute(user);
		
	}
	
	@GetMapping("/home")
	public String getUser(Model model,Principal principal) {
		return "normal/Dashboard";
	}
	
	@GetMapping("/addcontact")
	public String getContact(Model model,Principal principal) {
		model.addAttribute("title","Add-Contact");
		return "normal/add_contact";
		
	}
	@PostMapping("/add-contact")
	@ResponseBody
	public String addContact(@ModelAttribute Contact contact ,Principal principal) {
		String name=principal.getName();
		 User user=repo.getUserByUserName(name);
		 contact.setUser(user);
		 user.getList().add(contact);
		 repo.save(user);
		 System.out.println("Data"+contact);
		 System.out.println(user);
		 System.out.println("Save Data In DataBase");
		return "working";
		
	}

}
