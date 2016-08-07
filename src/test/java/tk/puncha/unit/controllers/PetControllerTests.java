package tk.puncha.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tk.puncha.controllers.ControllerBase;
import tk.puncha.controllers.PetController;
import tk.puncha.formatters.OwnerFormatter;
import tk.puncha.formatters.PetTypeFormatter;
import tk.puncha.models.Owner;
import tk.puncha.models.Pet;
import tk.puncha.models.PetType;
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.repositories.PetRepository;
import tk.puncha.repositories.PetTypeRepository;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PetController.class)
public class PetControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PetRepository petRepository;
  @MockBean
  private PetTypeRepository petTypeRepository;
  @MockBean
  private OwnerRepository ownerRepository;
  @MockBean
  private OwnerFormatter ownerFormatter;
  @MockBean
  private PetTypeFormatter petTypeFormatter;

  @Test
  public void shouldShowAllPets() throws Exception {
    List<Pet> pets = mock(List.class);
    when(petRepository.getAllPets()).thenReturn(pets);

    mockMvc.perform(get("/pets"))
        .andExpect(status().isOk())
        .andExpect(view().name("pet/index"))
        .andExpect(model().attribute("pets", pets));
    verify(petRepository).getAllPets();
  }

  @Test
  public void shouldShowPetDetail() throws Exception {
    Pet pet = mock(Pet.class);
    when(petRepository.getPetById(1)).thenReturn(pet);

    mockMvc.perform(get("/pets/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("pet/viewOrEdit"))
        .andExpect(model().attributeExists("owners"))
        .andExpect(model().attributeExists("types"))
        .andExpect(model().attribute("pet", pet))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Readonly));

    verify(petRepository).getPetById(1);
  }

  @Test
  public void shouldFailToShowPetDetailWhenPetDoesNotExist() throws Exception {
    when(petRepository.getPetById(anyInt())).thenReturn(null);

    mockMvc.perform(get("/pets/100"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attributeExists("exception"));

    verify(petRepository).getPetById(100);
  }

  @Test
  public void shouldShowPetCreationForm() throws Exception {
    mockMvc.perform(get("/pets/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("pet/viewOrEdit"))
        .andExpect(model().attributeExists("owners"))
        .andExpect(model().attributeExists("types"))
        .andExpect(model().attributeExists("pet"))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Edit));
  }

  @Test
  public void shouldCreateOwnerAndShowOwnerDetail() throws Exception {
    when(petRepository.insertPet(any())).thenReturn(123);
    when(petTypeFormatter.parse(any(), any())).thenReturn(mock(PetType.class));
    when(ownerFormatter.parse(any(), any())).thenReturn(mock(Owner.class));
    MockHttpServletRequestBuilder req = post("/pets/new")
        .param("name", "puncha")
        .param("owner", "1")
        .param("type", "1");
    mockMvc.perform(req)
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/pets/123"));
  }

  @Test
  public void shouldFailToCreatePetWhenPetInformationIsIncomplete() throws Exception {
    when(petRepository.insertPet(any())).thenReturn(123);
    MockHttpServletRequestBuilder req = post("/pets/new")
        .param("name", "puncha");
    mockMvc.perform(req)
        .andExpect(status().isOk())
        .andExpect(view().name("pet/viewOrEdit"))
        .andExpect(model().attributeExists("owners"))
        .andExpect(model().attributeExists("types"))
        .andExpect(model().attributeExists("pet"))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Edit))
        .andExpect(model().attributeHasErrors("pet"))
        .andExpect(model().attributeHasFieldErrorCode("pet", "owner", "NotNull"))
        .andExpect(model().attributeHasFieldErrorCode("pet", "type", "NotNull"));
  }

  @Test
  public void shouldShowPetEditForm() throws Exception {
    Pet pet = mock(Pet.class);
    when(petRepository.getPetById(1)).thenReturn(pet);

    mockMvc.perform(get("/pets/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("pet/viewOrEdit"))
        .andExpect(model().attributeExists("owners"))
        .andExpect(model().attributeExists("types"))
        .andExpect(model().attribute("pet", pet))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Edit));

    verify(petRepository).getPetById(1);
  }

  @Test
  public void shouldUpdatePetAnsShowPetDetail() throws Exception {
    when(petTypeFormatter.parse(any(), any())).thenReturn(mock(PetType.class));
    when(ownerFormatter.parse(any(), any())).thenReturn(mock(Owner.class));
    MockHttpServletRequestBuilder req = post("/pets/new")
        .param("id", "123")
        .param("owner", "1")
        .param("type", "1");
    mockMvc.perform(req)
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/pets/123"));
  }

  @Test
  public void shouldDeletePetAndShowAllPets() throws Exception {
    mockMvc.perform(get("/pets/1/delete"))
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/pets"));
  }

  @Test
  public void shouldFailToDeletePetWhenPetDoesNotExist() throws Exception {
    RuntimeException exception = new RuntimeException();
    doThrow(exception).when(petRepository).delete(100);
    mockMvc.perform(get("/pets/100/delete"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attribute("exception", exception));
  }
}
