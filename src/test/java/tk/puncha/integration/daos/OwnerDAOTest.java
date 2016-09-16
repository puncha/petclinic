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
import tk.puncha.dao.OwnerDAO;
import tk.puncha.models.Owner;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

@ActiveProfiles("hibernate,mysql")
@TestPropertySource(locations = {"/test.properties"})
@AutoConfigureTestDatabase
@Transactional
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
public class OwnerDAOTest {
  @Autowired
  private OwnerDAO ownerDAO;

  @Test
  public void shouldGetAllReturnAllOwnersList() throws Exception {
    List<Owner> owners = ownerDAO.getAll();
    assertEquals(10, owners.size());
  }

  @Test
  public void shouldGetByIdReturnOwnerWhenOwnerExists() throws Exception {
    Owner owner = ownerDAO.getById(1);
    assertNotNull(owner);
    assertEquals(1, owner.getId());
    assertEquals("George", owner.getFirstName());
    assertEquals("Franklin", owner.getLastName());
    assertEquals("110 W. Liberty St.", owner.getAddress());
    assertEquals("Madison", owner.getCity());
    assertEquals("6085551023", owner.getTelephone());
    assertFalse(Persistence.getPersistenceUtil().isLoaded(owner.getPets()));
  }

  @Test
  public void shouldGetByIdReturnNullWhenOwnerNotExists() throws Exception {
    Owner owner = ownerDAO.getById(-1);
    assertNull(owner);
  }

  @Test
  public void shouldGetByIdWithPetsReturnOwnerWithPetsWhenOwnerExists() throws Exception {
    Owner owner = ownerDAO.getByIdWithPets(1);
    assertNotNull(owner);
    assertEquals(1, owner.getId());
    assertTrue(Persistence.getPersistenceUtil().isLoaded(owner.getPets()));
  }

  @Test
  public void shouldGetByIdWithPetsReturnNullWhenOwnerNotExists() throws Exception {
    Owner owner = ownerDAO.getByIdWithPets(-1);
    assertNull(owner);
  }

  @Test
  public void shouldFindByFirstNameReturnMatchedOwnersList() throws Exception {
    List owners = ownerDAO.findByFirstName("eT");
    assertEquals(2, owners.size());
  }

  @Test
  public void shouldFindByFirstNameReturnEmptyListWhenOnMatchedOwners() throws Exception {
    List owners = ownerDAO.findByFirstName("puncha");
    assertTrue(owners.isEmpty());
  }

  @Test
  public void shouldInsertSucceededWhenOwnerIsValid() throws Exception {
    Owner owner = new Owner();
    owner.setFirstName("puncha");
    owner.setLastName("feng");
    owner.setAddress("sample address");
    int id = ownerDAO.insert(owner);
    assertNotEquals(-1, id);
  }

  @Test(expected = ConstraintViolationException.class)
  public void shouldInsertThrowExceptionWhenOwnerIsInvalid() throws Exception {
    Owner owner = new Owner();
    ownerDAO.insert(owner);
  }

  @Test(expected = PersistenceException.class)
  public void shouldInsertThrowExceptionWhenOwnerIdIsNotDefault() throws Exception {
    Owner owner = new Owner();
    owner.setId(123);
    ownerDAO.insert(owner);
  }

  @Test
  public void shouldUpdateSucceededWhenOwnerIsValid() {
    Owner owner = ownerDAO.getById(1);
    owner.setFirstName("puncha");
    ownerDAO.update(owner);
    List matchedOwners = ownerDAO.findByFirstName("puncha");
    assertEquals(1, matchedOwners.size());
  }

  @Test(expected = ConstraintViolationException.class)
  public void shouldUpdateThrowExceptionWhenOwnerIsInvalid() throws Exception {
    Owner owner = ownerDAO.getById(1);
    owner.setFirstName("AB");
    ownerDAO.update(owner);
    // HACK: the query actually force Hibernate to execute the UPDATE SQL statement,
    // or the @Rollback suppresses the Update SQL statement to fail the test.
    List matchedOwners = ownerDAO.findByFirstName("AB");
    assertTrue(matchedOwners.isEmpty());
  }

  @Test(expected = PersistenceException.class)
  public void shouldUpdateThrowExceptionWhenOwnerNotExists() throws Exception {
    Owner owner = ownerDAO.getById(1);
    owner.setId(123);
    ownerDAO.update(owner);
    // HACK: the query actually force Hibernate to execute the UPDATE SQL statement,
    // or the @Rollback suppresses the Update SQL statement to fail the test.
    // Note, to use getById() won't force the UPDATE SQL to execute.
    assertNull(ownerDAO.getByIdWithPets(123));
  }

  @Test
  public void shouldDeleteByIdWhenOwnerExists() throws Exception {
    ownerDAO.deleteById(1);
    assertEquals(9, ownerDAO.getAll().size());
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldDeleteByIdThrowExceptionWhenOwnerNotExists() throws Exception {
    ownerDAO.deleteById(123);
  }
}
