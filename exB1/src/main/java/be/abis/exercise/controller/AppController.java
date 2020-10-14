package be.abis.exercise.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String start() { // no model needed : no data to pass through
		return "redirect:/login";
	}
	
	//*****************************************************************************************************

	@GetMapping("/login") // leeg model dat moet opgevuld worden door new object te creëren & addAttribute
	public String getLogin(Model model) { 
		System.out.println("getLogin Model voor: " +model);
		// Model needs to pass object 'person' because person object is defined on the html page 
		loginp = new Person();
		model.addAttribute("person", loginp);
		return "login"; // view
	}
		
	@PostMapping("/login") // receive person object from model
	public String postLogin(Model model, Person person, BindingResult bindingresult) { 
		System.out.println("postLogin model : " +model);
		System.out.println("postLogin person pw: " +person.getPassword());
		System.out.println("postLogin person name: " +person.getFirstName());
		// you can use getters to get data from the person object that's been entered in the html page
		loginp = ts.findPerson(person.getEmailAddress(), person.getPassword());
		
		if (loginp != null) {
			System.out.println("welcome");
			System.out.println("postLogin loginp tostring: " +loginp.toString());
			return "redirect:/welcome";
		} else {
			System.out.println("login not correct");
			bindingresult.reject("global", "Login incorrect. Try again");
			return "login";
			//redirector = "/login";
			//return "redirect:/generalErrorPage";
		}
	}
	
	//*****************************************************************************************************
	
	@GetMapping("/welcome") // leeg model dat moet opgevuld worden door new object te creëren & addAttribute
	public String getWelcome(Model model) {
		System.out.println("getWelcome Model voor : " +model);
		// no person object passed because no object is defined on the page
		// <span th:text="${person.firstName}"></span>
		model.addAttribute("person", loginp); 
		System.out.println("getWelcome Model na : " +model);
		return "welcome";
	}
	
	//*****************************************************************************************************

	@GetMapping("/personAdmin") // geen model nodig 
	public String getPersonAdmin () {
		System.out.println("getPersonAdmin");
		return "personAdmin";
	}
	
	//*****************************************************************************************************

	@GetMapping("/changePassword")
	public String getChangePassword(Model model) { // leeg model dat moet opgevuld worden door new object te creëren & addAttribute
		System.out.println("getChangePassword model voor : " +model);
		model.addAttribute("person", loginp);
		System.out.println("getChangePassword model na : " +model);
		return "changePassword";
	}
	
	@PostMapping("/changePassword")
	public String postChangePassword(Model model, Person person) { // receive person object from model
		System.out.println("postChangePassword model voor : " +model);
		System.out.println("postChangePassword person voor : " +person);
		try {
		ts.changePassword(loginp, person.getPassword());
		
		} catch (IOException e) {
			System.out.println("new password: " +person.getPassword());
		}
		return "redirect:/confirmation";
	}
	
	//*****************************************************************************************************
	
	@GetMapping("/searchPersons")
	public String getSearchPersons(Model model) { // leeg model dat moet opgevuld worden door new object te creëren & addAttribute
		p = new Person();
		model.addAttribute("person", p);
		System.out.println("getSearchPersons model na : " +model);
		return "searchPersons";
	}
	
	//*****************************************************************************************************
		
	@PostMapping("/searchPersonById") // receive person model from object
	public String postSearchPersonById(Model model, Person person) {
		System.out.println("postSearchPersons model voor : " +model);
		System.out.println("postSearchPersons person voor : " +person);
		System.out.println("person id: " +person.getPersonId());
		persons = new ArrayList<Person>();
		System.out.println("postSearchPersons persons na : " +persons);
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
	public String getSearchAllPersons() { // no  model needed
		persons = ts.getAllPersons();
		return "redirect:/listPerson";
	}

	//*****************************************************************************************************

	@GetMapping("/listPerson")
	public String getListPerson(Model model) {
		System.out.println("getListPerson model voor : " +model);
		model.addAttribute("persons", persons);
		System.out.println("getListPerson model na : " +model);
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
	public String postAddNewPerson(Model model, @Valid Person person, BindingResult bindingresult) {
		if (bindingresult.hasErrors()) {
			return "addNewPerson";
		}
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
	public String getConfirmation() {
		System.out.println("getConfirmation");
		return "confirmation";
	}
	
	//*****************************************************************************************************

	@GetMapping("/confirmationNewPersonAdded")
	public String getConfirmationNewPersonAdded() {
		return "confirmationNewPersonAdded";
	}
	
	//*****************************************************************************************************

	@GetMapping("/confirmationNewPersonRemoved")
	public String getConfirmationNewPersonRemoved() {
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
	public String getSearchForCourses() {
		return "searchForCourses";
	}
	
	//*****************************************************************************************************

	@GetMapping("/showAllCourses")
	public String getShowAllCourses() {
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
	public String getGeneralErrorPage() {
		return "generalErrorPage";
	}
	
	@PostMapping("/generalErrorPage")
	public String postGeneralErrorPage() {
		return "redirect:" + redirector;
	}
	
}	
	