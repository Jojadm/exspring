package be.abis.exercise.ut;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import be.abis.exercise.repository.CourseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCourseRepository {
	
	@Autowired
	CourseRepository cr;
	
	@Before
	public void setUp() {
		//cr = new MemoryCourseRepository();
	}
	
	@Test
	public void numberOfCoursesInMemoryIs5() {
		int size = cr.findAllCourses().size();
		assertEquals(5,size);
	}
	
	@Test
	public void courseWithId8050isMaven() {
		String title = cr.findCourse(8050).getShortTitle();
		assertEquals("Maven",title);
	}
	
	@Test
	public void findTitleOfCourseWithCourseId7900() {
		String title = cr.findCourse(7900).getLongTitle();
		assertEquals("Workshop SQL",title);
	}
	
	@Test
	public void checkIfPriceOfCourseId7900isHigherThan400() {
		double price = cr.findCourse(7900).getPricePerDay();
		assertThat(price, greaterThan(400.0));
	}

	

}
