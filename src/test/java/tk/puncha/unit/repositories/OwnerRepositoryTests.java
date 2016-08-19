package tk.puncha.unit.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.models.Owner;
import tk.puncha.repositories.OwnerRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OwnerRepositoryTests {

  @Mock
  private Owner ownerMock;
  @Mock
  private List<Owner> ownerListMock;
  @Mock
  private OwnerDAO ownerDAOMock;
  @InjectMocks
  private OwnerRepository ownerRepository;

  @Test
  public void shouldGetAllOwnersDispatchCallToDAO() throws Exception {
    when(ownerDAOMock.getAll()).thenReturn(ownerListMock);
    assertEquals(ownerListMock, ownerRepository.getAllOwners());
    verify(ownerDAOMock).getAll();
    verifyNoMoreInteractions(ownerDAOMock);
  }

  @Test
  public void shouldGetOwnerByIdDispatchCallToDAO() throws Exception {
    when(ownerDAOMock.getById(anyInt())).thenReturn(ownerMock);
    assertEquals(ownerMock, ownerRepository.getOwnerById(anyInt()));
    verify(ownerDAOMock).getById(anyInt());
    verifyNoMoreInteractions(ownerDAOMock);
  }

  @Test
  public void shouldGetByIdWithPetsDispatchCallToDAO() throws Exception {
    when(ownerDAOMock.getByIdWithPets(anyInt())).thenReturn(ownerMock);
    assertEquals(ownerMock, ownerRepository.getOwnerWithPetsById(anyInt()));
    verify(ownerDAOMock).getByIdWithPets(anyInt());
    verifyNoMoreInteractions(ownerDAOMock);
  }

  @Test
  public void shouldFindByFirstNameDispatchCallToDAO() throws Exception {
    when(ownerDAOMock.findByFirstName(anyString())).thenReturn(ownerListMock);
    assertEquals(ownerListMock, ownerRepository.getOwnersByFirstName(anyString()));
    verify(ownerDAOMock).findByFirstName(anyString());
    verifyNoMoreInteractions(ownerDAOMock);
  }

  @Test
  public void shouldInsertDispatchCallToDAO() throws Exception {
    when(ownerDAOMock.insert(any(Owner.class))).thenReturn(123);
    assertEquals(123, ownerRepository.insertOwner(any(Owner.class)));
    verify(ownerDAOMock).insert(any(Owner.class));
    verifyNoMoreInteractions(ownerDAOMock);
  }

  @Test
  public void shouldDeleteByIdDispatchCallToDAO() throws Exception {
    doNothing().when(ownerDAOMock).deleteById(anyInt());
    ownerRepository.deleteOwner(anyInt());
    verify(ownerDAOMock).deleteById(anyInt());
    verifyNoMoreInteractions(ownerDAOMock);
  }

  @Test
  public void shouldUpdateDispatchCallToDAO() throws Exception {
    doNothing().when(ownerDAOMock).update(any(Owner.class));
    ownerRepository.updateOwner(any(Owner.class));
    verify(ownerDAOMock).update(any(Owner.class));
  }
}
