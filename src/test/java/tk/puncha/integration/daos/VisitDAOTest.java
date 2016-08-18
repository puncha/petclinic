package tk.puncha.integration.daos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.PetDAO;
import tk.puncha.dao.VisitDAO;
import tk.puncha.models.Visit;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.sql.Date;

import static org.junit.Assert.assertNotEquals;

@ActiveProfiles("hibernate,mysql")
@TestPropertySource(locations = {"/test.properties"})
@AutoConfigureTestDatabase
@Transactional
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
public class VisitDAOTest {
  @Autowired
  private PetDAO petDAO;
  @Autowired
  private VisitDAO visitDAO;

  @Test
  public void shouldInsertVisitSuccessWhenVisitIsValid() throws Exception {
    Visit visit = new Visit();
    visit.setVisitDate(Date.valueOf("2011-08-09"));
    visit.setDescription("A test visit");
    visit.setPet(petDAO.getById(1));
    int id = visitDAO.insert(visit);
    assertNotEquals(-1, id);
  }

  @Test(expected = ConstraintViolationException.class)
  public void shouldInsertVisitThrowExceptionWhenVisitIsInvalid() throws Exception {
    Visit Visit = new Visit();
    visitDAO.insert(Visit);
  }

  @Test(expected = PersistenceException.class)
  public void shouldInsertVisitThrowExceptionWhenVisitIdIsNotDefault() throws Exception {
    Visit Visit = new Visit();
    Visit.setId(123);
    visitDAO.insert(Visit);
  }

  @Test
  public void shouldDeleteVisitWhenVisitExists() throws Exception {
    visitDAO.deleteById(1);
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldDeleteVisitThrowExceptionWhenVisitNotExists() throws Exception {
    visitDAO.deleteById(123);
  }
}
