package com.Main.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Main.Entity.Contact;
import com.Main.Entity.User;
import com.Main.Repo.ContactRepo;
import com.Main.Repo.UserRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepo repo;
	@Autowired
	private ContactRepo contactRepo;
	
	@ModelAttribute
	public void addcommonData(Model model,Principal principal) {
		String name=principal.getName();
		 
		User user=repo.getUserByUserName(name);
		model.addAttribute(user);
		
	}
	
	@GetMapping("/home")
	public String getUser(Model model,Principal principal) {
		model.addAttribute("title","Home");
		return "normal/Dashboard";
	}
	
	@GetMapping("/addcontact")
	public String getContact(Model model,Principal principal) {
		model.addAttribute("title","Add-Contact");
		model.addAttribute("contact", new Contact()); 
		return "normal/add_contact";
		
	}
	@PostMapping("/add-contact")
	public String addContact(@Valid @ModelAttribute Contact contact ,
			BindingResult bindingResult,
			@RequestParam("imgfile") MultipartFile file,
			Principal principal,RedirectAttributes redirectAttributes,Model model) {
		
		model.addAttribute("title","Add-New Contact");
		if (bindingResult.hasErrors()) {
	        // Send back the form with errors
	        model.addAttribute("contact",contact);
	        return "normal/add_contact";  // show the form again
	    }
		try {
			
			String name=principal.getName();
			 User user=repo.getUserByUserName(name);
			 
			 
			 //Image Processing 
			 
			 if(file.isEmpty()) {
				 System.out.println("Image File Empty");
				 contact.setImgurl("default.jpeg");
			 }else {
				 contact.setImgurl("default.jpeg");
//				 contact.setImgurl(file.getOriginalFilename());
//				 
//				 //get path where we save img
//				File saveFile= new ClassPathResource("static/img").getFile();
//				//get target path
//				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
//				
//				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//				System.out.println("File uploaded succesfully");
			 }
			 //end image proccesing
			 user.getList().add(contact);
			 contact.setUser(user);
			 repo.save(user);
			 System.out.println("Data"+contact);
			 System.out.println(user);
			 System.out.println("Save Data In DataBase");
			 
			 //message print 
			 redirectAttributes.addFlashAttribute("message", "Contact Added Successfully add More!!");
		        redirectAttributes.addFlashAttribute("alertType", "alert-success");
		} catch (Exception e) {
			 System.out.println("Error"+e.getMessage());
			 //message print 
			 redirectAttributes.addFlashAttribute("message", "Something Went Wrong Try again");
		        redirectAttributes.addFlashAttribute("alertType", "alert-danger");
		}
		
		return "redirect:/user/addcontact";
		
	}
	
	//display all contact
	@GetMapping({"/view-contact", "/view-contact/{page}"})
	public String viewContact( @PathVariable(name = "page", required = false)Integer page, Model model,Principal principal) {
		model.addAttribute("title","View-Contact");
		if (page == null) page = 0;
		String name=principal.getName();
		 User user=repo.getUserByUserName(name);
		Pageable pageable= PageRequest.of(page, 5);
		Page<Contact> contacts= contactRepo.findByUserId(user.getUserid(),pageable);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpage",contacts.getTotalPages());
		return"normal/viewContact";
	}

}
