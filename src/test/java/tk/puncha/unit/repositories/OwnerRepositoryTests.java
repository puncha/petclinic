package tk.puncha.unit.repositories;

import org.junit.Before;
import org.junit.Test;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.models.Owner;
import tk.puncha.repositories.OwnerRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OwnerRepositoryTests {

  private OwnerRepository ownerRepository;
  private OwnerDAO ownerDAOMock;
  private Owner ownerSample;
  private List<Owner> ownerListSample;

  @Before
  public void before() {
    ownerDAOMock = mock(OwnerDAO.class);
    ownerRepository = new OwnerRepository(ownerDAOMock);
    ownerSample = createOwnerSample();
    ownerListSample = createOwnerListSample();
  }

  private Owner createOwnerSample() {
    Owner owner = new Owner();
    owner.setFirstName("puncha");
    owner.setLastName("feng");
    return owner;
  }

  private List<Owner> createOwnerListSample() {
    return Collections.singletonList(ownerSample);
  }

  @Test
  public void shouldGetAllOwnersReturnListOfOwners() throws Exception {
    when(ownerDAOMock.getAllOwners()).thenReturn(ownerListSample);
    assertEquals(ownerListSample, ownerRepository.getAllOwners());
  }

  @Test
  public void shouldGetOwnerByIdReturnAnOwnerIfExists() throws Exception {
    when(ownerDAOMock.getOwnerById(0)).thenReturn(ownerSample);
    assertEquals(ownerSample, ownerRepository.getOwnerById(0));
  }
}
