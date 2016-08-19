package tk.puncha.unit.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.PetType;
import tk.puncha.repositories.PetTypeRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetTypeRepositoryTests {

  @Mock
  private PetType petTypeMock;
  @Mock
  private List<PetType> petTypeListMock;
  @Mock
  private PetTypeDAO petTypeDAOMock;
  @InjectMocks
  private PetTypeRepository petTypeRepository;

  @Test
  public void shouldGetAllDispatchCallToDAO() throws Exception {
    when(petTypeDAOMock.getAll()).thenReturn(petTypeListMock);
    assertEquals(petTypeListMock, petTypeRepository.getAll());
    verify(petTypeDAOMock).getAll();
    verifyNoMoreInteractions(petTypeDAOMock);
  }

  @Test
  public void shouldGetByIdDispatchCallToDAO() throws Exception {
    when(petTypeDAOMock.getById(anyInt())).thenReturn(petTypeMock);
    assertEquals(petTypeMock, petTypeRepository.getById(anyInt()));
    verify(petTypeDAOMock).getById(anyInt());
    verifyNoMoreInteractions(petTypeDAOMock);
  }
}
