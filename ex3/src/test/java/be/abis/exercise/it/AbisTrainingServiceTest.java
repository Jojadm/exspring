package be.abis.exercise.it;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbisTrainingServiceTest {

	@Autowired
	PersonRepository pr;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void findPersonwithID2() {
		Person p = pr.findPerson(2);
		assertEquals("Mary", p.getFirstName());
		assertEquals("Jones", p.getLastName());
	}

}

