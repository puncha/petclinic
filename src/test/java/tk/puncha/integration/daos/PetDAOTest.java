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
    petDAO.getAllPets();
  }

  @Test
  public void shouldGetAllPetsReturnAllPetsList() throws Exception {
    List<Pet> Pets = petDAO.getAllPets();
    assertEquals(13, Pets.size());
  }

  @Test
  public void shouldGetPetByIdReturnPetWhenPetExists() throws Exception {
    Pet pet = petDAO.getPetById(1);
    assertNotNull(pet);
    assertEquals(1, pet.getId());
  }

  @Test
  public void shouldGetPetByIdReturnNullWhenPetNotExists() throws Exception {
    Pet pet = petDAO.getPetById(-1);
    assertNull(pet);
  }

  @Test
  public void shouldInsertPetSuccessWhenPetIsValid() throws Exception {
    Pet pet = new Pet();
    pet.setName("puncha");
    pet.setType(petTypeDAO.getTypeById(1));
    pet.setOwner(ownerDAO.getOwnerById(1));
    int id = petDAO.insertPet(pet);
    assertNotEquals(-1, id);
  }

  @Test(expected = ConstraintViolationException.class)
  public void shouldInsertPetThrowExceptionWhenPetIsInvalid() throws Exception {
    Pet Pet = new Pet();
    petDAO.insertPet(Pet);
  }

  @Test(expected = PersistenceException.class)
  public void shouldInsertPetThrowExceptionWhenPetIdIsNotDefault() throws Exception {
    Pet Pet = new Pet();
    Pet.setId(123);
    petDAO.insertPet(Pet);
  }

  @Test
  public void shouldUpdatePetSucceededWhenPetIsValid() {
    Pet pet = petDAO.getPetById(1);
    pet.setName("puncha");
    petDAO.updatePet(pet);
    forceFlush();
  }


  @Test(expected = ConstraintViolationException.class)
  public void shouldUpdatePetThrowExceptionWhenPetIsInvalid() throws Exception {
    Pet pet = petDAO.getPetById(1);
    pet.setName("AB");
    petDAO.updatePet(pet);
    forceFlush();
  }

  @Test(expected = PersistenceException.class)
  public void shouldUpdatePetThrowExceptionWhenPetNotExists() throws Exception {
    Pet pet = petDAO.getPetById(1);
    pet.setId(123);
    petDAO.updatePet(pet);
    forceFlush();

  }

  @Test
  public void shouldDeletePetWhenPetExists() throws Exception {
    petDAO.delete(1);
    assertEquals(12, petDAO.getAllPets().size());
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldDeletePetThrowExceptionWhenPetNotExists() throws Exception {
    petDAO.delete(123);
  }

  @Test
  public void shouldDeletePetsByOwnerIdDeletePetsWhenOwnerExists() {
    petDAO.deletePetsByOwnerId(3);  // Eduardo has two pets
    assertEquals(11, petDAO.getAllPets().size());
  }


  @Test(expected = EntityNotFoundException.class)
  public void shouldDeletePetsByOwnerIdThrowExceptionWhenOwnerNotExists() {
    petDAO.deletePetsByOwnerId(123);
  }
}
