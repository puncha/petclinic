package tk.puncha.unit.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tk.puncha.dao.PetDAO;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.Pet;
import tk.puncha.models.PetType;
import tk.puncha.repositories.PetRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetRepositoryTests {

  @Mock
  private Pet petMock;
  @Mock
  private List<Pet> petListMock;
  @Mock
  private PetDAO petDAOMock;
  @Mock
  private PetTypeDAO petTypeDAOMock;
  @Mock
  private List<PetType> petTypeListMock;
  @InjectMocks
  private PetRepository petRepository;

  @Test
  public void shouldGetAllDispatchCallToDAO() throws Exception {
    when(petDAOMock.getAll()).thenReturn(petListMock);
    assertEquals(petListMock, petRepository.getAll());
    verify(petDAOMock).getAll();
    verifyNoMoreInteractions(petDAOMock);
  }

  @Test
  public void shouldGetByIdDispatchCallToDAO() throws Exception {
    when(petDAOMock.getById(anyInt())).thenReturn(petMock);
    assertEquals(petMock, petRepository.getById(anyInt()));
    verify(petDAOMock).getById(anyInt());
    verifyNoMoreInteractions(petDAOMock);
  }

  @Test
  public void shouldInsertDispatchCallToDAO() throws Exception {
    when(petDAOMock.insert(any(Pet.class))).thenReturn(123);
    assertEquals(123, petRepository.insert(any(Pet.class)));
    verify(petDAOMock).insert(any(Pet.class));
    verifyNoMoreInteractions(petDAOMock);
  }

  @Test
  public void shouldUpdateDispatchCallToDAO() throws Exception {
    doNothing().when(petDAOMock).update(any(Pet.class));
    petRepository.update(any(Pet.class));
    verify(petDAOMock).update(any(Pet.class));
  }

  @Test
  public void shouldDeleteByIdDispatchCallToDAO() throws Exception {
    doNothing().when(petDAOMock).deleteById(anyInt());
    petRepository.deleteById(anyInt());
    verify(petDAOMock).deleteById(anyInt());
    verifyNoMoreInteractions(petDAOMock);
  }

  @Test
  public void shouldDeleteByOwnerIdDispatchCallToDAO() throws Exception {
    doNothing().when(petDAOMock).deleteByOwnerId(anyInt());
    petRepository.deleteByOwnerId(anyInt());
    verify(petDAOMock).deleteByOwnerId(anyInt());
    verifyNoMoreInteractions(petDAOMock);
  }
}
