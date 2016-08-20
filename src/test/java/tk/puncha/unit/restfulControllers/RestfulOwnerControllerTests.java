package tk.puncha.unit.restfulControllers;

import org.junit.Before;
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
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.restfulControllers.RestfulOwnerController;
import tk.puncha.validators.OwnerValidator;
import tk.puncha.views.json.view.OwnerJsonView;

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
@WebMvcTest(RestfulOwnerController.class)
public class RestfulOwnerControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Mock
  private Owner ownerMock;

  @MockBean
  private OwnerValidator ownerValidator;
  @MockBean
  private OwnerRepository ownerRepository;

  private Owner createInvalidOwner() {
    return new Owner();
  }

  private Owner createValidOwner() {
    return new Owner() {{
      setId(1);
      setFirstName("PunCha");
      setLastName("Feng");
      setAddress("Shanghai");
    }};
  }

  @Before
  public void makeValidatorSupportsAnyType() {
    when(ownerValidator.supports(any())).thenReturn(true);
  }

  @Test
  public void shouldReturnAllOwnersList() throws Exception {
    List<Owner> ownerList = Collections.singletonList(createValidOwner());
    when(ownerRepository.getAll()).thenReturn(ownerList);

    mockMvc.perform(get("/api/owners").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(TestUtil.objectToJsonString(ownerList, OwnerJsonView.class)));

    verify(ownerRepository).getAll();
    verifyNoMoreInteractions(ownerRepository);
  }

  @Test
  public void shouldReturnOwnersListFilterByFirstName() throws Exception {
    List<Owner> ownerList = Collections.singletonList(createValidOwner());
    when(ownerRepository.findByFirstName("puncha")).thenReturn(ownerList);

    mockMvc.perform(get("/api/owners?firstName=puncha").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(TestUtil.objectToJsonString(ownerList, OwnerJsonView.class)));

    verify(ownerRepository).findByFirstName("puncha");
    verifyNoMoreInteractions(ownerRepository);
  }

  @Test
  public void shouldReturnOwnerWhenOwnerExists() throws Exception {
    Owner owner = createValidOwner();
    when(ownerRepository.getByIdWithPets(1)).thenReturn(owner);

    System.out.printf(TestUtil.objectToJsonString(owner));

    mockMvc.perform(get("/api/owners/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(TestUtil.objectToJsonString(owner, OwnerJsonView.class)));
  }

  @Test
  public void shouldReturnNotFoundWhenOwnerNotExists() throws Exception {
    mockMvc.perform(get("/api/owners/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(ownerRepository).getByIdWithPets(1);
    verifyNoMoreInteractions(ownerRepository);
  }

  @Test
  public void shouldCreateOwnerWhenOwnerIsValid() throws Exception {
    Owner owner = createValidOwner();
    when(ownerRepository.insert(owner)).thenReturn(1);

    MockHttpServletRequestBuilder req = post("/api/owners")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(owner));

    mockMvc.perform(req)
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldFailToCreateVisitWhenOwnerIsInvalid() throws Exception {
    MockHttpServletRequestBuilder req = post("/api/owners")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createInvalidOwner()));

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(ownerRepository);
  }

  @Test
  public void shouldUpdateOwnerWhenOwnerIsValid() throws Exception {
    MockHttpServletRequestBuilder req = post("/api/owners/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createValidOwner()));

    mockMvc.perform(req)
        .andExpect(status().isNoContent());
  }

  @Test
  public void shouldFailToUpdateOwnerWhenOwnerIsInvalid() throws Exception {
    MockHttpServletRequestBuilder req = post("/api/owners/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createInvalidOwner()));

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());
  }


  @Test
  public void shouldFailToUpdateOwnerWhenOwnerNotExist() throws Exception {
    doThrow(EntityNotFoundException.class).when(ownerRepository).update(any(Owner.class));
    MockHttpServletRequestBuilder req = post("/api/owners/1")
        .accept(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJsonString(createValidOwner()));

    mockMvc.perform(req)
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldDeleteOwnerWhenOwnerExists() throws Exception {
    mockMvc.perform(delete("/api/owners/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(ownerRepository).deleteById(1);
    verifyNoMoreInteractions(ownerRepository);
  }

  @Test
  public void shouldFailToDeleteOwnerWhenOwnerNotExist() throws Exception {
    doThrow(EntityNotFoundException.class).when(ownerRepository).deleteById(1);

    mockMvc.perform(delete("/api/owners/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(ownerRepository).deleteById(1);
    verifyNoMoreInteractions(ownerRepository);
  }
}
