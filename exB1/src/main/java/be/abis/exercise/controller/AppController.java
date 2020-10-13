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
	
	Person loginp, p;
	List<Person> persons;
	List<Course> courses;
	String redirector; 
	
	//*****************************************************************************************************

	@GetMapping("/") //first slash after basic definition in properties file
	public String start() { //empty model (or no model) : no data to pass through
		return "redirect:/login";
	}
	
	//the model works a container that contains the data of the application. 
	// Here, a data can be in any form such as objects, strings, information from the database, etc.
	// The object of HttpServletRequest reads the information provided by the user and pass it to the 
	// Model interface. Now, a view page easily accesses the data from the model part.
	// The HttpServletRequest is used to read the HTML form data provided by the user.
	// The Model contains the request data and provides it to view page.

	//*****************************************************************************************************

	@GetMapping("/login")
	public String getLogin(Model model) { 
		// Model needs to pass object 'person' because person object is defined on the html page 
		loginp = new Person();
		model.addAttribute("person", loginp);
		return "login"; // view
	}
		
	@PostMapping("/login") 
	public String postLogin(Model model, Person person) { 
		// you can use getters to get data from the person object that's been entered in the html page
		loginp = ts.findPerson(person.getEmailAddress(), person.getPassword());
		if (loginp != null) {
			System.out.println("welcome");
			return "redirect:/welcome";
		} else {
			redirector = "/login";
			return "redirect:/generalErrorPage";
		}
	}
	
	//*****************************************************************************************************
	
	@GetMapping("/welcome")
	public String getWelcome(Model model) {
		// no person object passed because no object is defined on the page
		// <span th:text="${person.firstName}"></span>
		model.addAttribute("person", loginp); 
		return "welcome";
	}
	
	@PostMapping("/welcome")
	public String postWelcome(Model model) {
		return "redirect:/login";
	}
	
	//*****************************************************************************************************

	@GetMapping("/Course")
	public String getCourse (Model model) {
		return "course";
	}

	//*****************************************************************************************************

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
	
	//*****************************************************************************************************

	@GetMapping("/changePassword")
	public String getChangePassword(Model model, Person person) {
		return "changePassword";
	}
	@PostMapping("/changePassword")
	public String postChangePassword(Model model, Person person) {
		try {
		ts.changePassword(p, person.getPassword());
		
		} catch (IOException e) {
			System.out.println("new password: " +person.getPassword());
		}
		return "redirect:/confirmation";
	}
	
	//*****************************************************************************************************

	@GetMapping("/searchPersons")
	public String getSearchPersons(Model model) {
		p = new Person();
		model.addAttribute("person", p);
		return "searchPersons";
	}
	
	@PostMapping("/searchPersons")
	public String postSearchPersons(Model model, Person person) {
		System.out.println("person id: " +person.getPersonId());
		persons = new ArrayList<Person>();
		int id = person.getPersonId();
		p = ts.findPerson(id);
		if (p != null) {
		persons.add(p);
		return "redirect:/listPerson";
		} else {
			redirector = "/searchPersons";
			return "redirect:/generalErrorPage";
		}
	}

	//*****************************************************************************************************

	@GetMapping("/searchAllPersons")
	public String getSearchAllPersons(Model model) {
		persons = ts.getAllPersons();
		return "redirect:/listPerson";
	}

	//*****************************************************************************************************

	@GetMapping("/listPerson")
	public String getListPerson(Model model) {
		model.addAttribute("persons", persons);
		return "listPerson";
	}

	//*****************************************************************************************************

	@GetMapping("/addNewPerson")
	public String getAddNewPerson(Model model) {
		p = new Person();
		model.addAttribute("person",p);
		return "addNewPerson";
	}
	
	@PostMapping("/addNewPerson")
	public String postAddNewPerson(Model model, Person person) {
		try {
		System.out.println("person: " +person.getFirstName());	
		ts.addPerson(person);
		} catch (IOException e) {
			redirector = "/addNewPerson";
			return "redirect:/generalErrorPage";
		}
		return "redirect:/confirmationNewPersonAdded";
	}
	
	//*****************************************************************************************************

	@GetMapping("/confirmation")
	public String getConfirmation(Model model) {
		System.out.println("getConfirmation");
		return "confirmation";
	}
	
	//*****************************************************************************************************

	@GetMapping("/confirmationNewPersonAdded")
	public String getConfirmationNewPersonAdded(Model model) {
		return "confirmationNewPersonAdded";
	}
	
	//*****************************************************************************************************

	@GetMapping("/confirmationNewPersonRemoved")
	public String getConfirmationNewPersonRemoved(Model model) {
		return "confirmationNewPersonRemoved";
	}

	//*****************************************************************************************************

	@GetMapping("/removePerson")
	public String getRemovePerson(Model model) {
		p = new Person();
		model.addAttribute("person", p);
		return "removePerson";
	}
	@PostMapping("/removePerson")
	public String postRemovePerson(Model model, Person person) {
		System.out.println("PostRemovePerson");
		ts.deletePerson(person.getPersonId());
		return "redirect:/confirmationNewPersonRemoved";
	}
	
	//*****************************************************************************************************

	@GetMapping("/searchForCourses")
	public String getSearchForCourses(Model model) {
		return "searchForCourses";
	}
	
	//*****************************************************************************************************

	@GetMapping("/showAllCourses")
	public String getShowAllCourses(Model model) {
		courses = new ArrayList<Course>();
		courses = ts.getCs().findAllCourses();
		return "redirect:listCourse";
	}
	
	//*****************************************************************************************************

	@GetMapping("/listCourse")
	public String getListCourse(Model model) {
		model.addAttribute("courses", courses);
		return "listCourse";
	}
	
	//*****************************************************************************************************

	@GetMapping("/findCourseById")
	public String getFindCourseById(Model model) {
		Course c = new Course();
		model.addAttribute("course", c);
		return "findCourseById";
	}
	
	@PostMapping("/findCourseById")
	public String postFindCourseById(Model model, Course course) {
		System.out.println("PostFindCourseById");
		int id = Integer.parseInt(course.getCourseId());
		System.out.println("Course id: " +id);
		Course c = ts.getCs().findCourse(id);
		courses = new ArrayList<Course>();
		if (c != null) {
			System.out.println("Course: " +c.getLongTitle());
			courses.add(c);
			return "redirect:/listCourse";
		} else {
			redirector = "/findCourseById";
			return "redirect:/generalErrorPage" ;
		}
	}
	
	//*****************************************************************************************************

	@GetMapping("/findCourseByTitle")
	public String getFindCourseByTitle(Model model) {
		Course c = new Course();
		model.addAttribute("course", c);
		return "findCourseByTitle";
	}
	@PostMapping("/findCourseByTitle")
	public String postFindCourseByTitle(Model model, Course course) {
		System.out.println("PostFindCourseByTitle");
		String shortTitle = course.getShortTitle();
		Course c = ts.getCs().findCourse(shortTitle);
		courses = new ArrayList<Course>();
		if (c != null) {
		System.out.println("Course: " +c.getLongTitle());
		courses.add(c);
		return "redirect:/listCourse";
		} else {
			redirector = "/findCourseByTitle";
			return "redirect:/generalErrorPage";
		}
			
	}
	
	//*****************************************************************************************************

	@GetMapping("/generalErrorPage")
	public String getGeneralErrorPage(Model model) {
		return "generalErrorPage";
	}
	@PostMapping("/generalErrorPage")
	public String postGeneralErrorPage(Model model) {
		return "redirect:" + redirector;
	}
	
}	
	