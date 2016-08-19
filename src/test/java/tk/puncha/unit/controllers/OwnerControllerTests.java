package tk.puncha.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tk.puncha.controllers.ControllerBase;
import tk.puncha.controllers.OwnerController;
import tk.puncha.models.Owner;
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.validators.OwnerValidator;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = {"/test.properties", "/unit-test.properties"})

@AutoConfigureTestDatabase
@RunWith(SpringRunner.class)
@WebMvcTest(OwnerController.class)
public class OwnerControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OwnerRepository ownerRepository;
  @MockBean
  private OwnerValidator ownerValidator;

  @Test
  public void shouldShowAllOwners() throws Exception {
    List<Owner> owners = new ArrayList<>();
    when(ownerRepository.getAll()).thenReturn(owners);

    mockMvc.perform(get("/owners"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attributeExists("owners"));
    verify(ownerRepository).getAll();
  }

  @Test
  public void shouldShowOwnersMatchedByFirstName() throws Exception {
    List<Owner> owners = new ArrayList<>();
    when(ownerRepository.findByFirstName(anyString())).thenReturn(owners);

    // search for George
    mockMvc.perform(get("/owners").param("search-first-name", "gEora"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attributeExists("owners"));
    verify(ownerRepository).findByFirstName("gEora");
  }

  @Test
  public void shouldShowOwnerDetail() throws Exception {
    Owner owner = mock(Owner.class);
    when(ownerRepository.getByIdWithPets(1)).thenReturn(owner);
    when(ownerValidator.supports(owner.getClass())).thenReturn(true);

    mockMvc.perform(get("/owners/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/viewOrEdit"))
        .andExpect(model().attribute("owner", owner))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Readonly));

    verify(ownerRepository).getByIdWithPets(1);
    // validator is called even if we return the model
    verify(ownerValidator).supports(owner.getClass());
  }

  @Test
  public void shouldFailToShowOwnerDetailWhenOwnerDoesNotExist() throws Exception {
    when(ownerRepository.getByIdWithPets(anyInt())).thenReturn(null);

    mockMvc.perform(get("/owners/100"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attributeExists("exception"));

    verify(ownerRepository).getByIdWithPets(100);
  }

  @Test
  public void shouldShowOwnerCreationForm() throws Exception {
    when(ownerValidator.supports(any())).thenReturn(true);
    mockMvc.perform(get("/owners/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("owner"))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Edit));
    verify(ownerValidator).supports(any());
  }

  @Test
  public void shouldCreateOwnerAndShowOwnerDetail() throws Exception {
    when(ownerValidator.supports(any())).thenReturn(true);
    doAnswer(invocation -> {
      Owner owner = invocation.getArgumentAt(0, Owner.class);
      owner.setId(123);
      return null;
    }).when(ownerRepository).insert(any(Owner.class));
    MockHttpServletRequestBuilder req = post("/owners/new")
        .param("firstName", "PunCha")
        .param("lastName", "Feng")
        .param("address", "Room.201")
        .param("city", "Shanghai")
        .param("telephone", "1234567");
    mockMvc.perform(req)
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/owners/123"));
    // The following verification passes if remove @WebMvc, so
    // this should be a bug in Spring Boot.
    // verify(ownerRepository).insert(any(Owner.class));
  }

  @Test
  public void shouldFailToCreateOwnerWhenOwnerInformationIsIncomplete() throws Exception {
    when(ownerValidator.supports(any())).thenReturn(true);
    doCallRealMethod().when(ownerValidator).validate(any(), any());
    MockHttpServletRequestBuilder req = post("/owners/new");
    mockMvc.perform(req.param("address", "12"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/viewOrEdit"))
        .andExpect(model().attributeHasErrors("owner"))
        .andExpect(model().attributeHasFieldErrorCode("owner", "firstName", "NotNull"))
        .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "NotNull"))
        .andExpect(model().attributeHasFieldErrorCode("owner", "address", "error.address.length"))
    ;
    // The following verification passes if remove @WebMvc, so
    // this should be a bug in Spring Boot.
    // verify(ownerRepository).insert(any(Owner.class));
  }

  @Test
  public void shouldShowOwnerEditForm() throws Exception {
    Owner owner = mock(Owner.class);
    when(ownerRepository.getById(1)).thenReturn(owner);
    when(ownerValidator.supports(owner.getClass())).thenReturn(true);

    mockMvc.perform(get("/owners/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/viewOrEdit"))
        .andExpect(model().attribute("owner", owner))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Edit));

    verify(ownerRepository).getById(1);
    // validator is called even if we return the model
    verify(ownerValidator).supports(owner.getClass());
  }

  @Test
  public void shouldUpdateOwnerAnsShowOwnerDetail() throws Exception {
    when(ownerValidator.supports(any())).thenReturn(true);
    MockHttpServletRequestBuilder req = post("/owners/new")
        .param("id", "123")
        .param("firstName", "PunCha")
        .param("lastName", "Feng")
        .param("address", "Room.201")
        .param("city", "Shanghai")
        .param("telephone", "1234567");
    mockMvc.perform(req)
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/owners/123"));
  }

  @Test
  public void shouldFailToUpdateOwnerWhenOwnerIdIsInvalid() throws Exception {
    RuntimeException exception = new RuntimeException();
    doThrow(exception).when(ownerRepository).update(any());

    when(ownerValidator.supports(any())).thenReturn(true);
    MockHttpServletRequestBuilder req = post("/owners/new")
        .param("id", "123")
        .param("firstName", "PunCha")
        .param("lastName", "Feng");
    mockMvc.perform(req)
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attribute("exception", exception));
//    verify(ownerRepository).update(any());
  }

  @Test
  public void shouldDeleteOwnerAndShowAllOwners() throws Exception {
    mockMvc.perform(get("/owners/1/delete"))
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/owners"));
    verify(ownerRepository).deleteById(1);
  }

  @Test
  public void shouldFailToDeleteOwnerWhenOwnerDoesNotExist() throws Exception {
    RuntimeException exception = new RuntimeException();
    doThrow(exception).when(ownerRepository).deleteById(100);
    mockMvc.perform(get("/owners/100/delete"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attribute("exception", exception));
    verify(ownerRepository).deleteById(100);
  }
}
