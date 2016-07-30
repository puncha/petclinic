package tk.puncha.integration.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tk.puncha.Application;
import tk.puncha.models.Owner;
import tk.puncha.repositories.OwnerRepository;

import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OwnerRepositoryTests{
  @Configuration
  @EnableAutoConfiguration()
  static class Dummy extends Application {
  }

  @Autowired
  private OwnerRepository ownerRepository;

  @Test
  public void shouldGetAllOwnersReturnListOfOwners() throws Exception {
    List<Owner> allOwners = ownerRepository.getAllOwners();
    assertEquals(10, allOwners.size());
  }

  @Test
  public void shouldGetOwnerByIdReturnAnOwnerIfExists() throws Exception {
    Owner owner = ownerRepository.getOwnerById(1);
    assertEquals(1, owner.getId());
    assertEquals("George", owner.getFirstName());
    assertEquals("Franklin", owner.getLastName());
    assertEquals("110 W. Liberty St.", owner.getAddress());
    assertEquals("Madison", owner.getCity());
    assertEquals("6085551023", owner.getTelephone());
    assertFalse(Persistence.getPersistenceUtil().isLoaded(owner.getPets()));
  }

  @Test
  public void shouldGetOwnerByIdReturnNullIfOwnerNotExists() throws Exception {
    Owner owner = ownerRepository.getOwnerById(-1);
    assertNull(owner);
  }

  @Test
  public void shouldGetOwnerWithPetsByIdReturnInitializedPetsCollection() throws Exception {
    Owner owner = ownerRepository.getOwnerWithPetsById(1);
    assertEquals(1, owner.getId());
    assertTrue(Persistence.getPersistenceUtil().isLoaded(owner.getPets()));
  }

  @Test
  public void shouldInsertOwner() throws Exception {
    Owner owner = new Owner();
    owner.setFirstName("puncha");
    owner.setLastName("feng");
    ownerRepository.insertOwner(owner);
    assertNotNull(ownerRepository.getOwnerById(owner.getId()));
  }

  @Test(expected = ConstraintViolationException.class)
  public void shouldInsertOwnerThrowIfOwnerIsInvalid() throws Exception {
    Owner owner = new Owner();
    ownerRepository.insertOwner(owner);
  }

  @Test
  public void shouldUpdateOwner() throws Exception {
    Owner owner = ownerRepository.getOwnerById(2);
    owner.setFirstName("puncha");
    ownerRepository.updateOwner(owner);
    assertEquals("puncha", ownerRepository.getOwnerById(2).getFirstName());
  }
}
