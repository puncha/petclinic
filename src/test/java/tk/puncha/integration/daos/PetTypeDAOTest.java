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
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.PetType;

import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("hibernate,mysql")
@TestPropertySource(locations = {"/test.properties"})
@AutoConfigureTestDatabase
@Transactional
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
public class PetTypeDAOTest {
  @Autowired
  private PetTypeDAO petTypeDAO;

  @Test
  public void shouldGetAllReturnAllPetTypesList() throws Exception {
    List<PetType> Pets = petTypeDAO.getAll();
    assertEquals(6, Pets.size());
  }

  @Test
  public void shouldGetByIdReturnPetWhenPetExists() throws Exception {
    PetType petType = petTypeDAO.getById(1);
    assertNotNull(petType);
    assertEquals(1, petType.getId());
    assertEquals("cat", petType.getName());
  }

  @Test
  public void shouldGetByIdReturnNullWhenPetNotExists() throws Exception {
    PetType petType = petTypeDAO.getById(-1);
    assertNull(petType);
  }

}
