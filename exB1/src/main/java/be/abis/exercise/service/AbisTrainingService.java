package be.abis.exercise.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.abis.exercise.exception.EnrollException;
import be.abis.exercise.exception.PersonAlreadyExistsException;
import be.abis.exercise.model.Course;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.PersonRepository;

@Service
public class AbisTrainingService implements TrainingService {
	
	@Autowired
	PersonRepository pr;
	@Autowired
	CourseService cs;
	
	public CourseService getCs() {
		return cs;
	}

	@Override
	public Person findPerson(int id) {
		// TODO Auto-generated method stub
		Person p = pr.findPerson(id);
		return p;
	}
	
	@Override
	public ArrayList<Person> getAllPersons() {
		// TODO Auto-generated method stub
		ArrayList<Person> ps = pr.getAllPersons();
		return ps;
	}

	@Override
	public Person findPerson(String emailAddress, String passWord) {
		// TODO Auto-generated method stub
		Person p = pr.findPerson(emailAddress, passWord);
		return p;
	}

	@Override
	public void addPerson(Person p) throws IOException, PersonAlreadyExistsException {
		// TODO Auto-generated method stub
		pr.addPerson(p);
	}

	@Override
	public void deletePerson(int id) {
		// TODO Auto-generated method stub
		pr.deletePerson(id);
	}
	

	@Override
	public void changePassword(Person p, String newPswd) throws IOException {
		// TODO Auto-generated method stub
		pr.changePassword(p, newPswd);
	}

	@Override
	public List<Course> showFollowedCourses(Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enrollForSession(Person person, Course course, LocalDate date) throws EnrollException {
		// TODO Auto-generated method stub

	}
	
	

}
