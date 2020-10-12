package be.abis.exercise.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	Person p, p2;
	List<Person> persons = new ArrayList<Person>();

	@GetMapping("/")
	public String start(Model model) {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		p= new Person();
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
	public String postWelcome(Model model) {
		return "redirect:/login";
	}
	
	@GetMapping("/Course")
	public String getCourse (Model model) {
		return "course";
	}

	@GetMapping("/personAdmin")
	public String getPersonAdmin (Model model) {
		System.out.println("getPersonAdmin");
		return "personAdmin";
	}
	
	@PostMapping("/personAdmin")
	public String postPersonAdmin (Model model) {
		System.out.println("postPersonAdmin");
		return "personAdmin";
	}
	@GetMapping("/changePassword")
	public String getChangePassword(Model model, Person person) {
		return "changePassword";
	}
	@PostMapping("/changePassword")
	public String putChangePassword(Model model, Person person) {
		try {
		ts.changePassword(p, person.getPassword());
		
		} catch (IOException e) {
			System.out.println("new password: " +person.getPassword());
		}
		return "redirect:/confirmation";
	}
	

	@GetMapping("/searchPersons")
	public String getSearchPersons(Model model, Person person) {
		return "searchPersons";
	}
	
	@PostMapping("/searchPersons")
	public String postSearchPersons(Model model, Person person) {
		System.out.println("person id: " +person.getPersonId());
		int id = person.getPersonId();
		p = ts.findPerson(id);
		persons.clear();
		persons.add(p);
		return "redirect:/listPerson";
	}
	@PostMapping("/searchAllPersons")
	public String postSearchAllPersons(Model model) {
		persons.clear();
		persons = ts.getAllPersons();
		return "redirect:/listPerson";
	}
	
	@GetMapping("/listPerson")
	public String getListPerson(Model model) {
		model.addAttribute("persons", persons);
		return "listPerson";
	}
	
	@GetMapping("/addNewPerson")
	public String getAddNewPerson(Model model, Person person) {
		person = null;
		System.out.println("getAddNewPerson");
		return "addNewPerson";
	}
	
	@PostMapping("/addNewPerson")
	public String postAddNewPerson(Model model, Person person) {
		System.out.println("PostAddNewPerson");
		try {
		System.out.println("person: " +person.getFirstName());	
		ts.addPerson(person);
		} catch (IOException e) {
			System.out.println("add person failed");
		}
		return "redirect:/confirmationNewPersonAdded";
	}
	@GetMapping("/confirmation")
	public String getConfirmation(Model model) {
		System.out.println("getConfirmation");
		return "confirmation";
	}
	@GetMapping("/confirmationNewPersonAdded")
	public String getConfirmationNewPersonAdded(Model model) {
		return "confirmationNewPersonAdded";
	}
	
	@GetMapping("/confirmationNewPersonRemoved")
	public String getConfirmationNewPersonRemoved(Model model) {
		return "confirmationNewPersonRemoved";
	}
	
	@GetMapping("/removePerson")
	public String getRemovePerson(Model model, Person person) {
		return "removePerson";
	}
	@PostMapping("/removePerson")
	public String postRemovePerson(Model model, Person person) {
		System.out.println("PostRemovePerson");
		ts.deletePerson(person.getPersonId());
		return "redirect:/confirmationNewPersonRemoved";
	}
}	
	