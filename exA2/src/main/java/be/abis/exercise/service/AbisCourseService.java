package be.abis.exercise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.abis.exercise.model.Course;
import be.abis.exercise.repository.CourseRepository;

@Service
public class AbisCourseService implements CourseService {

	@Autowired
	CourseRepository cr;
	
	@Override
	public List<Course> findAllCourses() {
		// TODO Auto-generated method stub
		List<Course> courses = cr.findAllCourses();
		return courses;
	}

	@Override
	public Course findCourse(int id) {
		// TODO Auto-generated method stub
		Course course = cr.findCourse(id);
		return course;
	}

	@Override
	public Course findCourse(String shortTitle) {
		// TODO Auto-generated method stub
		Course course = cr.findCourse(shortTitle);
		return course;
	}

}
