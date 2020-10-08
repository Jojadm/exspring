package be.abis.exercise.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.abis.exercise.exception.EnrollException;
import be.abis.exercise.model.Course;
import be.abis.exercise.model.Person;

public interface TrainingService {

	public Person findPerson(int id);
	public ArrayList<Person> getAllPersons();
    public Person findPerson(String emailAddress, String passWord);
    public void addPerson(Person p) throws IOException;
    public void deletePerson(int id);
    public void changePassword(Person p, String newPswd) throws IOException;
	public List<Course> showFollowedCourses(Person person);
	public void enrollForSession(Person person, Course course, LocalDate date) throws EnrollException;

}
