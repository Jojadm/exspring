package be.abis.exercise.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import be.abis.exercise.model.Course;
import be.abis.exercise.model.Person;
import be.abis.exercise.service.TrainingService;

@Controller
public class AppController {
	
	@Autowired
	TrainingService ts;
	
	Person p, p2;
	List<Person> persons = new ArrayList<Person>();
	List<Course> courses = new ArrayList<Course>();
	String redirector; 
	
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
		if (p != null) {
			System.out.println("welcome");
			return "redirect:/welcome";
		} else {
			redirector = "/login";
			return "redirect:/generalErrorPage";
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
		if (p != null) {
		persons.clear();
		persons.add(p);
		return "redirect:/listPerson";
		} else {
			redirector = "/searchPersons";
			return "redirect:/generalErrorPage";
		}
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
			redirector = "/addNewPerson";
			return "redirect:/generalErrorPage";
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
	@GetMapping("/searchForCourses")
	public String getSearchForCourses(Model model) {
		return "searchForCourses";
	}
	@GetMapping("/showAllCourses")
	public String getShowAllCourses(Model model, Course course) {
		courses = ts.getCs().findAllCourses();

		return "redirect:listCourse";
	}
	@GetMapping("/listCourse")
	public String getListCourse(Model model, Course course) {
		model.addAttribute("courses", courses);
		return "listCourse";
	}
	@GetMapping("/findCourseById")
	public String getFindCourseById(Model model, Course course) {
		return "findCourseById";
	}
	@PostMapping("/findCourseById")
	public String PostFindCourseById(Model model, Course course) {
		System.out.println("PostFindCourseById");
		int id = Integer.parseInt(course.getCourseId());
		System.out.println("Course id: " +id);
		Course c = ts.getCs().findCourse(id);
		if (c != null) {
			System.out.println("Course: " +c.getLongTitle());
			courses.clear();
			courses.add(c);
			return "redirect:/listCourse";
		} else {
			redirector = "/findCourseById";
			return "redirect:/generalErrorPage" ;
		}
	}
	@GetMapping("/findCourseByTitle")
	public String getFindCourseByTitle(Model model, Course course) {
		return "findCourseByTitle";
	}
	@PostMapping("/findCourseByTitle")
	public String PostFindCourseByTitle(Model model, Course course) {
		System.out.println("PostFindCourseByTitle");
		String shortTitle = course.getShortTitle();
		Course c = ts.getCs().findCourse(shortTitle);
		if (c != null) {
		System.out.println("Course: " +c.getLongTitle());
		courses.clear();
		courses.add(c);
		return "redirect:/listCourse";
		} else {
			redirector = "/findCourseByTitle";
			return "redirect:/generalErrorPage";
		}
			
	}
	@GetMapping("/generalErrorPage")
	public String getGeneralErrorPage(Model model) {
		return "generalErrorPage";
	}
	@PostMapping("/generalErrorPage")
	public String postGeneralErrorPage(Model model) {
		return "redirect:" + redirector;
	}
}	
	