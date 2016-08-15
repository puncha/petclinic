package tk.puncha.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tk.puncha.controllers.VisitController;
import tk.puncha.models.Pet;
import tk.puncha.repositories.PetRepository;
import tk.puncha.repositories.VisitRepository;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestPropertySource(locations = {"/test.properties", "/unit-test.properties"})
@RunWith(SpringRunner.class)
@WebMvcTest(VisitController.class)
public class VisitControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PetRepository petRepository;
  @MockBean
  private VisitRepository visitRepository;

  @Test
  public void shouldShowVisitCreationForm() throws Exception {
    Pet pet = mock(Pet.class);
    when(petRepository.getPetById(1)).thenReturn(pet);
    mockMvc.perform(get("/pets/1/visits/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("visit/new"))
        .andExpect(model().attribute("pet", pet))
        .andExpect(model().attributeExists("visit"));
  }

  @Test
  public void shouldCreateVisitAndShowPetDetail() throws Exception {
    Pet pet = mock(Pet.class);
    when(petRepository.getPetById(1)).thenReturn(pet);
    MockHttpServletRequestBuilder req = post("/pets/1/visits/new")
        .param("description", "...")
        .param("visitDate", "1999-09-09");
    mockMvc.perform(req)
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/pets/1"));
  }

  @Test
  public void shouldFailToCreateVisitWhenVisitInformationIsIncomplete() throws Exception {
    Pet pet = mock(Pet.class);
    when(petRepository.getPetById(1)).thenReturn(pet);
    mockMvc.perform(post("/pets/1/visits/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("visit/new"))
        .andExpect(model().attribute("pet", pet))
        .andExpect(model().attributeHasErrors("visit"))
        .andExpect(model().attributeHasFieldErrorCode("visit", "visitDate", "NotNull"));
  }

  @Test
  public void shouldDeleteVisitAndShowPetDetail() throws Exception {
    when(petRepository.getPetById(1)).thenReturn(mock(Pet.class));
    mockMvc.perform(get("/pets/1/visits/2/delete"))
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/pets/1"));
    verify(visitRepository).delete(2);
  }

  @Test
  public void shouldFailToDeleteVisitWhenVisitDoesNotExist() throws Exception {
    when(petRepository.getPetById(1)).thenReturn(mock(Pet.class));
    RuntimeException exception = new RuntimeException();
    doThrow(exception).when(visitRepository).delete(2);
    mockMvc.perform(get("/pets/1/visits/2/delete"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attribute("exception", exception));
    verify(visitRepository).delete(2);
  }

  @Test
  public void shouldFailToDeleteVisitWhenPetDoesNotExist() throws Exception {
    when(petRepository.getPetById(anyInt())).thenReturn(null);
    mockMvc.perform(get("/pets/1/visits/2/delete"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attributeExists("exception"));
    verify(petRepository).getPetById(1);
  }

  @Test
  public void shouldChangePetIdWouldNotAffactReadPetId() throws Exception {


  }
}
