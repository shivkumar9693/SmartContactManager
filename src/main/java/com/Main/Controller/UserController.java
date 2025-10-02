package com.Main.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
 
	@GetMapping("/contact/{id}")
	public String getsingle(@PathVariable("id") Integer cid,
	                        Model model,
	                        Principal principal) {

	    System.out.println(cid);

	    // 1. Get logged-in user
	    String name = principal.getName();
	    User user = repo.getUserByUserName(name);

	    // 2. Try to find contact by ID
	    Optional<Contact> optional = contactRepo.findById(cid);

	    if (optional.isPresent()) {
	        Contact contact = optional.get();

	        // 3. Check ownership
	        if (user.getUserid()==(contact.getUser().getUserid())) {
	            model.addAttribute("title", contact.getName());
	            model.addAttribute("contact", contact);
	            return "normal/contact_details"; // ✅ show details
	        }
	    }

	    // 4. If not found OR not authorized
	    model.addAttribute("contact", null);
	    model.addAttribute("title", "Error");
	    return "normal/contact_details"; // ❌ will show "You Don't have Permission..."
	}

	@GetMapping("/delete/{id}")
	public String deleteContact(@PathVariable("id") Integer cid,
	                            Principal principal,
	                            RedirectAttributes redirectAttributes) {

	    String username = principal.getName();
	    User user = repo.getUserByUserName(username);

	    Optional<Contact> optional = contactRepo.findById(cid);
	    if (optional.isPresent()) {
	        Contact contact = optional.get();

	        if (user.getUserid()==(contact.getUser().getUserid())) {
	            // ✅ Remove from user's list
	            user.getList().remove(contact);

	            // ✅ Save user -> orphanRemoval deletes the contact
	            repo.save(user);

	            redirectAttributes.addFlashAttribute("message", "Contact Deleted Successfully!");
	            redirectAttributes.addFlashAttribute("alertType", "alert-success");
	        }
	    }
	    return "redirect:/user/view-contact";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, 
	                             Model model, 
	                             Principal principal) {

	    Optional<Contact> optional = contactRepo.findById(id);
	    User user = repo.getUserByUserName(principal.getName());

	    if (optional.isPresent() && optional.get().getUser().getUserid() == user.getUserid()) {
	        model.addAttribute("contact", optional.get());
	        model.addAttribute("authorized", true);
	    } else {
	        // Not authorized or contact not found
	        model.addAttribute("contact", null);
	        model.addAttribute("authorized", false);
	    }

	    model.addAttribute("title", "Update Contact");
	    return "normal/updateContact";
	}



	@PostMapping("/process-update/{id}")
	public String processUpdate(@PathVariable("id") Integer id,
	                            @ModelAttribute Contact contact,
	                            @RequestParam("imgfile") MultipartFile file,
	                            Principal principal,
	                            RedirectAttributes redirectAttributes) throws IOException {

	    // 1. Fetch existing contact
	    Contact existingContact = contactRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Contact not found"));

	    // 2. Check user ownership
	    User user = repo.getUserByUserName(principal.getName());
	    if (existingContact.getUser().getUserid()!=(user.getUserid())) {
	        redirectAttributes.addFlashAttribute("message", "You are not authorized to update this contact!");
	        redirectAttributes.addFlashAttribute("alertType", "alert-danger");
	        return "redirect:/user/view-contact";
	    }

	    // 3. Update contact fields
	    existingContact.setName(contact.getName());
	    existingContact.setNickname(contact.getNickname());
	    existingContact.setEmail(contact.getEmail());
	    existingContact.setPhoneno(contact.getPhoneno());
	    existingContact.setWork(contact.getWork());
	    existingContact.setAbout(contact.getAbout());

	    // 4. Image Processing
	    if (file.isEmpty()) {
	        System.out.println("Image File Empty");
	        existingContact.setImgurl("default.jpeg");
	    } else {
	    	 existingContact.setImgurl("default.jpeg");
//	        existingContact.setImgurl(file.getOriginalFilename());
//
//	        // Get path where we save images
//	        File saveFile = new ClassPathResource("static/img").getFile();
//
//	        // Get target path
//	        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
//
//	        // Copy file
//	        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//	        System.out.println("File uploaded successfully");
	    }

	    // 5. Save contact
	    contactRepo.save(existingContact);

	    // 6. Flash message
	    redirectAttributes.addFlashAttribute("message", "Contact updated successfully!");
	    redirectAttributes.addFlashAttribute("alertType", "alert-success");

	    // 7. Redirect to view-contact page (page 0 by default)
	    return "redirect:/user/view-contact";
	}
	
	@GetMapping("/profile")
	public String viewProfile(Model model, Principal principal) {
	    String username = principal.getName();
	    User user = repo.getUserByUserName(username); // repo = UserRepository

	    model.addAttribute("title", "Profile");
	    model.addAttribute("user", user);
	    model.addAttribute("contacts", user.getList()); // user's contacts

	    return "normal/profile";
	}

    


}
