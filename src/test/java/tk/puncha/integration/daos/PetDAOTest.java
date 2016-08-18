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
import tk.puncha.dao.PetDAO;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.Pet;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("hibernate,mysql")
@TestPropertySource(locations = {"/test.properties"})
@AutoConfigureTestDatabase
@Transactional
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
public class PetDAOTest {
  @Autowired
  private PetDAO petDAO;
  @Autowired
  private OwnerDAO ownerDAO;
  @Autowired
  private PetTypeDAO petTypeDAO;

  public void forceFlush() {
    petDAO.getAll();
  }

  @Test
  public void shouldGetAllPetsReturnAllPetsList() throws Exception {
    List<Pet> Pets = petDAO.getAll();
    assertEquals(13, Pets.size());
  }

  @Test
  public void shouldGetPetByIdReturnPetWhenPetExists() throws Exception {
    Pet pet = petDAO.getById(1);
    assertNotNull(pet);
    assertEquals(1, pet.getId());
  }

  @Test
  public void shouldGetPetByIdReturnNullWhenPetNotExists() throws Exception {
    Pet pet = petDAO.getById(-1);
    assertNull(pet);
  }

  @Test
  public void shouldInsertPetSuccessWhenPetIsValid() throws Exception {
    Pet pet = new Pet();
    pet.setName("puncha");
    pet.setType(petTypeDAO.getById(1));
    pet.setOwner(ownerDAO.getById(1));
    int id = petDAO.insert(pet);
    assertNotEquals(-1, id);
  }

  @Test(expected = ConstraintViolationException.class)
  public void shouldInsertPetThrowExceptionWhenPetIsInvalid() throws Exception {
    Pet Pet = new Pet();
    petDAO.insert(Pet);
  }

  @Test(expected = PersistenceException.class)
  public void shouldInsertPetThrowExceptionWhenPetIdIsNotDefault() throws Exception {
    Pet Pet = new Pet();
    Pet.setId(123);
    petDAO.insert(Pet);
  }

  @Test
  public void shouldUpdatePetSucceededWhenPetIsValid() {
    Pet pet = petDAO.getById(1);
    pet.setName("puncha");
    petDAO.update(pet);
    forceFlush();
  }


  @Test(expected = ConstraintViolationException.class)
  public void shouldUpdatePetThrowExceptionWhenPetIsInvalid() throws Exception {
    Pet pet = petDAO.getById(1);
    pet.setName("AB");
    petDAO.update(pet);
    forceFlush();
  }

  @Test(expected = PersistenceException.class)
  public void shouldUpdatePetThrowExceptionWhenPetNotExists() throws Exception {
    Pet pet = petDAO.getById(1);
    pet.setId(123);
    petDAO.update(pet);
    forceFlush();

  }

  @Test
  public void shouldDeletePetWhenPetExists() throws Exception {
    petDAO.deleteById(1);
    assertEquals(12, petDAO.getAll().size());
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldDeletePetThrowExceptionWhenPetNotExists() throws Exception {
    petDAO.deleteById(123);
  }

  @Test
  public void shouldDeletePetsByOwnerIdDeletePetsWhenOwnerExists() {
    petDAO.deleteByOwnerId(3);  // Eduardo has two pets
    assertEquals(11, petDAO.getAll().size());
  }


  @Test(expected = EntityNotFoundException.class)
  public void shouldDeletePetsByOwnerIdThrowExceptionWhenOwnerNotExists() {
    petDAO.deleteByOwnerId(123);
  }
}
