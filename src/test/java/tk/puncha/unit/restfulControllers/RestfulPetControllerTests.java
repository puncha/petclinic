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
import tk.puncha.models.Owner;
import tk.puncha.models.Pet;
import tk.puncha.models.PetType;
import tk.puncha.repositories.PetRepository;
import tk.puncha.restfulControllers.RestfulPetController;
import tk.puncha.views.json.view.PetJsonView;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureTestDatabase
@TestPropertySource(locations = {"/test.properties", "/unit-test.properties"})
@RunWith(SpringRunner.class)
@WebMvcTest(RestfulPetController.class)
public class RestfulPetControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Mock
  private Pet petMock;

  @MockBean
  private PetRepository petRepository;

  private Pet createInvalidPet() {
    return new Pet();
  }

  private Pet createValidPet() {
    return new Pet() {{
      setId(1);
      setName("HelloKitty");
      setOwner(new Owner() {{
        setId(1);
      }});
      setType(new PetType() {{
        setId(1);
      }});
    }};
  }

  @Test
  public void shouldReturnAllPetsList() throws Exception {
    List<Pet> petList = Collections.singletonList(createValidPet());
    when(petRepository.getAll()).thenReturn(petList);

    mockMvc.perform(get("/api/pets").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(TestUtil.objectToJsonString(petList, PetJsonView.class)));

    verify(petRepository).getAll();
    verifyNoMoreInteractions(petRepository);
  }

  @Test
  public void shouldReturnPetWhenPetExists() throws Exception {
    Pet pet = createValidPet();
    when(petRepository.getById(1)).thenReturn(pet);

    System.out.printf(TestUtil.objectToJsonString(pet));

    mockMvc.perform(get("/api/pets/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(TestUtil.objectToJsonString(pet, PetJsonView.class)));
  }

  @Test
  public void shouldReturnNotFoundWhenPetNotExists() throws Exception {
    when(petRepository.getById(1)).thenReturn(null);

    mockMvc.perform(get("/api/pets/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(petRepository).getById(1);
    verifyNoMoreInteractions(petRepository);
  }

  @Test
  public void shouldCreatePetWhenPetIsValid() throws Exception {
    Pet pet = createValidPet();
    when(petRepository.insert(pet)).thenReturn(1);

    MockHttpServletRequestBuilder req = post("/api/pets")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(pet));

    mockMvc.perform(req)
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldFailToCreateVisitWhenPetIsInvalid() throws Exception {
    MockHttpServletRequestBuilder req = post("/api/pets")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createInvalidPet()));

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(petRepository);
  }

  @Test
  public void shouldUpdatePetWhenPetIsValid() throws Exception {
    MockHttpServletRequestBuilder req = post("/api/pets/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createValidPet()));

    mockMvc.perform(req)
        .andExpect(status().isNoContent());
  }

  @Test
  public void shouldFailToUpdatePetWhenPetIsInvalid() throws Exception {
    MockHttpServletRequestBuilder req = post("/api/pets/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createInvalidPet()));

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());
  }


  @Test
  public void shouldFailToUpdatePetWhenPetNotExist() throws Exception {
    doThrow(EntityNotFoundException.class).when(petRepository).update(any(Pet.class));
    MockHttpServletRequestBuilder req = post("/api/pets/1")
        .accept(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createValidPet()));

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldDeletePetWhenPetExists() throws Exception {
    mockMvc.perform(delete("/api/pets/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(petRepository).deleteById(1);
    verifyNoMoreInteractions(petRepository);
  }

  @Test
  public void shouldFailToDeletePetWhenPetNotExist() throws Exception {
    doThrow(EntityNotFoundException.class).when(petRepository).deleteById(1);

    mockMvc.perform(delete("/api/pets/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(petRepository).deleteById(1);
    verifyNoMoreInteractions(petRepository);
  }
}
