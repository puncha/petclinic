package tk.puncha.unit.restfulControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tk.puncha.TestUtil;
import tk.puncha.models.Pet;
import tk.puncha.models.Visit;
import tk.puncha.repositories.PetRepository;
import tk.puncha.repositories.VisitRepository;
import tk.puncha.restfulControllers.RestfulVisitController;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureTestDatabase
@TestPropertySource(locations = {"/test.properties", "/unit-test.properties"})
@RunWith(SpringRunner.class)
@WebMvcTest(RestfulVisitController.class)
public class RestfulVisitControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Mock
  private Date dateMock;
  @Mock
  private Pet petMock;

  @MockBean
  private PetRepository petRepository;
  @MockBean
  private VisitRepository visitRepository;

  private Visit createInvalidVisit() {
    return new Visit();
  }

  private Visit createValidVisit() {
    Visit visit = createInvalidVisit();
    visit.setVisitDate(dateMock);
    return visit;
  }


  @Test
  public void shouldCreateVisitWhenVisitIsValid() throws Exception {
    when(petRepository.getById(1)).thenReturn(petMock);

    MockHttpServletRequestBuilder req = post("/api/pets/1/visits")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createValidVisit()));

    mockMvc.perform(req)
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldFailToCreateVisitWhenVisitIsInvalid() throws Exception {
    when(petRepository.getById(1)).thenReturn(petMock);

    MockHttpServletRequestBuilder req = post("/api/pets/1/visits")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createInvalidVisit()));

    mockMvc.perform(req)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    verify(petRepository).getById(1);
    verifyNoMoreInteractions(petRepository, visitRepository);
  }

  @Test
  public void shouldFailToCreateVisitWhenPetNotExists() throws Exception {
    when(petRepository.getById(1)).thenReturn(null);

    MockHttpServletRequestBuilder req = post("/api/pets/1/visits")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createValidVisit()));

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());

    verify(petRepository).getById(1);
    verifyNoMoreInteractions(petRepository, visitRepository);
  }

  @Test
  public void shouldDeleteVisit() throws Exception {
    when(petRepository.getById(1)).thenReturn(petMock);

    MockHttpServletRequestBuilder req = delete("/api/pets/1/visits/2")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(req)
        .andExpect(status().isNoContent());

    verify(petRepository).getById(1);
    verify(visitRepository).deleteById(2);
    verifyNoMoreInteractions(petRepository, visitRepository);
  }

  @Test
  public void shouldFailToDeleteVisitWhenVisitDoesNotExist() throws Exception {
    when(petRepository.getById(1)).thenReturn(petMock);
    doThrow(EntityNotFoundException.class).when(visitRepository).deleteById(2);

    MockHttpServletRequestBuilder req = delete("/api/pets/1/visits/2")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());

    verify(petRepository).getById(1);
    verify(visitRepository).deleteById(2);
    verifyNoMoreInteractions(petRepository, visitRepository);
  }

  @Test
  public void shouldFailToDeleteVisitWhenPetDoesNotExist() throws Exception {
    when(petRepository.getById(1)).thenReturn(null);

    MockHttpServletRequestBuilder req = delete("/api/pets/1/visits/2")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());

    verify(petRepository).getById(1);
    verifyNoMoreInteractions(petRepository, visitRepository);
  }

}
