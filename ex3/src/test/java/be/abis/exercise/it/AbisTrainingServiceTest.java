package be.abis.exercise.it;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import be.abis.exercise.model.Person;
import be.abis.exercise.service.TrainingService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbisTrainingServiceTest {

	@Autowired
	TrainingService ts;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void findPersonwithID2() {
		Person p = ts.findPerson(2);
		assertEquals("Mary", p.getFirstName());
		assertEquals("Jones", p.getLastName());
	}

}

