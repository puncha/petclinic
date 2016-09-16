package tk.puncha.unit.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tk.puncha.dao.VisitDAO;
import tk.puncha.models.Visit;
import tk.puncha.repositories.VisitRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VisitRepositoryTests {

  @Mock
  private VisitDAO visitDAOMock;
  @InjectMocks
  private VisitRepository visitRepository;

  @Test
  public void shouldInsertDispatchCallToDAO() throws Exception {
    when(visitDAOMock.insert(any(Visit.class))).thenReturn(123);
    assertEquals(123, visitRepository.insert(any(Visit.class)));
    verify(visitDAOMock).insert(any(Visit.class));
    verifyNoMoreInteractions(visitDAOMock);
  }

  @Test
  public void shouldDeleteByIdDispatchCallToDAO() throws Exception {
    doNothing().when(visitDAOMock).deleteById(anyInt());
    visitRepository.deleteById(anyInt());
    verify(visitDAOMock).deleteById(anyInt());
    verifyNoMoreInteractions(visitDAOMock);
  }
}
