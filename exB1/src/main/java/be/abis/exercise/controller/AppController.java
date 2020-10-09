package be.abis.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import be.abis.exercise.model.Person;
import be.abis.exercise.service.TrainingService;

@Controller
public class AppController {
	
	@Autowired
	TrainingService ts;
	
	Person p = new Person();

	@GetMapping("/")
	public String getLogin(Model model) {
		model.addAttribute("person", p);
		return "login";
	}
		
	@PostMapping("/login") //first slash after basic definition in properties file
	public String postLogin(Model model, Person person) { 
		p = ts.findPerson(person.getEmailAddress(), person.getPassword());
		System.out.println("person: " +p.getFirstName());
		if (p != null) {
			System.out.println("welcome");
			return "redirect:/welcome";
		} else {
			System.out.println("login");
			return "login";
		}
	}
	
	@GetMapping("/welcome")
	public String getWelcome(Model model) {
		model.addAttribute("person", p);
		return "welcome";
	}
	
	@PostMapping("/welcome")
	public String postWelcome (Model model) {
		return "redirect:/personAdmin";
	}
	
	@GetMapping("/Course")
	public String getCourse (Model model) {
		return "course";
	}
	
	@PostMapping("/Course")
	public String postCourse (Model model) {
		return "course";
	}
	@GetMapping("/personAdmin")
	public String getPersonAdmin (Model model) {
		return "personAdmin";
	}
	
	@PostMapping("/personAdmin")
	public String postPersonAdmin (Model model) {
		return "personAdmin";
	}
}
	