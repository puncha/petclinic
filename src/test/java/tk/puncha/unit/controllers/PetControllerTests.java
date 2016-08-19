package tk.puncha.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureTestDatabase
@TestPropertySource(locations = {"/test.properties", "/unit-test.properties"})
@RunWith(SpringRunner.class)
@WebMvcTest(PetController.class)
public class PetControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Mock
  private Pet petMock;
  @Mock
  private List<Pet> petListMock;
  @Mock
  private Owner ownerMock;
  @Mock
  private PetType petTypeMock;
  @Mock
  private List<Owner> ownerListMock;
  @Mock
  private List<PetType> petTypeListMock;

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
  public void shouldShowAllPetsInHtml() throws Exception {
    when(petRepository.getAllPets()).thenReturn(petListMock);

    mockMvc.perform(get("/pets"))
        .andExpect(status().isOk())
        .andExpect(view().name("pet/index"))
//      .andExpect(content().contentType(MediaType.TEXT_HTML))  // it is NOT SET, why?
        .andExpect(model().attribute("pets", petListMock));
    verify(petRepository).getAllPets();
  }

  @Test
  public void shouldShowAllPetsInXml() throws Exception {
    when(petRepository.getAllPets()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/pets.xml"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/xml;charset=UTF-8"));
    verify(petRepository).getAllPets();
  }

  @Test
  public void shouldShowAllPetsInJson() throws Exception {
    when(petRepository.getAllPets()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/pets.json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"));
    verify(petRepository).getAllPets();
  }

  @Test
  public void shouldShowPetDetail() throws Exception {
    when(petRepository.getById(1)).thenReturn(petMock);
    when(ownerRepository.getAll()).thenReturn(ownerListMock);
    when(petTypeRepository.getAll()).thenReturn(petTypeListMock);

    mockMvc.perform(get("/pets/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("pet/viewOrEdit"))
        .andExpect(model().attribute("owners", ownerListMock))
        .andExpect(model().attribute("types", petTypeListMock))
        .andExpect(model().attribute("pet", petMock))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Readonly));

    verify(petRepository).getById(1);
  }

  @Test
  public void shouldFailToShowPetDetailWhenPetDoesNotExist() throws Exception {
    when(petRepository.getById(anyInt())).thenReturn(null);

    mockMvc.perform(get("/pets/100"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attributeExists("exception"));

    verify(petRepository).getById(100);
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
  public void shouldCreatePet() throws Exception {
    when(petRepository.insert(any())).thenReturn(123);
    when(ownerFormatter.parse(any(), any())).thenReturn(ownerMock);
    when(petTypeFormatter.parse(any(), any())).thenReturn(petTypeMock);
    MockHttpServletRequestBuilder req = post("/pets/new")
        .sessionAttr("id", "-1")
        .param("id", "111") // cheat the server
        .param("name", "puncha")
        .param("owner", "1")
        .param("type", "1");
    mockMvc.perform(req)
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/pets/123"));
  }

  @Test
  public void shouldFailToCreatePetWhenPetInformationIsIncomplete() throws Exception {
    when(petRepository.insert(any())).thenReturn(123);
    MockHttpServletRequestBuilder req = post("/pets/new")
        .sessionAttr("id", "-1")
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
    when(petRepository.getById(1)).thenReturn(petMock);

    mockMvc.perform(get("/pets/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("pet/viewOrEdit"))
        .andExpect(model().attributeExists("owners"))
        .andExpect(model().attributeExists("types"))
        .andExpect(model().attribute("pet", petMock))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Edit));

    verify(petRepository).getById(1);
  }

  @Test
  public void shouldUpdatePetAnsShowPetDetail() throws Exception {
    when(petTypeFormatter.parse(any(), any())).thenReturn(petTypeMock);
    when(ownerFormatter.parse(any(), any())).thenReturn(ownerMock);
    MockHttpServletRequestBuilder req = post("/pets/new")
        .sessionAttr("id", "123")
        .param("id", "456")  // cheat the server
        .param("owner", "1")
        .param("type", "1");
    mockMvc.perform(req)
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/pets/123")); // make sure the server not cheated
  }

  @Test
  public void shouldDeletePetAndShowAllPets() throws Exception {
    mockMvc.perform(get("/pets/1/delete"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/pets"));
  }

  @Test
  public void shouldFailToDeletePetWhenPetDoesNotExist() throws Exception {
    RuntimeException exception = new RuntimeException();
    doThrow(exception).when(petRepository).deleteById(100);
    mockMvc.perform(get("/pets/100/delete"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attribute("exception", exception));
  }
}
