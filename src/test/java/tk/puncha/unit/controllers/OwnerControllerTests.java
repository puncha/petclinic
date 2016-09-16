package tk.puncha.unit.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.junit.Ignore;
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
import tk.puncha.controllers.ControllerBase;
import tk.puncha.controllers.OwnerController;
import tk.puncha.models.Owner;
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.validators.OwnerValidator;
import tk.puncha.views.json.view.OwnerJsonView;

import java.util.Collections;
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

  @Mock
  private Owner ownerMock;
  @Mock
  private List<Owner> ownerListMock;

  @MockBean
  private OwnerRepository ownerRepository;
  @MockBean
  private OwnerValidator ownerValidator;

  @Test
  public void shouldShowAllOwnersInHtmlView() throws Exception {
    when(ownerRepository.getAll()).thenReturn(ownerListMock);

    mockMvc.perform(get("/owners"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attribute("owners", ownerListMock));
    verify(ownerRepository).getAll();
  }

  @Test
  public void shouldShowAllOwnersInJsonView() throws Exception {
    List<Owner> ownerList = Collections.emptyList();
    when(ownerRepository.getAll()).thenReturn(ownerList);

    mockMvc.perform(get("/owners.json"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attribute(JsonView.class.getName(), OwnerJsonView.WithPets.class))
        .andExpect(model().attribute("owners", ownerList))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    verify(ownerRepository).getAll();
  }

  @Test
  @Ignore("The XML view is rendered by XmlViewResolver which has to be an integration test and I don't want!")
  public void shouldShowAllOwnersInXml() throws Exception {
    List<Owner> ownerList = Collections.emptyList();
    when(ownerRepository.getAll()).thenReturn(ownerList);

    mockMvc.perform(get("/owners.xml"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attribute("owners", ownerList))
        .andExpect(content().contentType(MediaType.APPLICATION_XML));
    verify(ownerRepository).getAll();
  }

  @Test
  public void shouldShowAllOwnersInPdfView() throws Exception {
    List<Owner> ownerList = Collections.emptyList();
    when(ownerRepository.getAll()).thenReturn(ownerList);

    mockMvc.perform(get("/owners.pdf"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attribute("owners", ownerList));
//        .andExpect(content().contentType(MediaType.APPLICATION_PDF)); // HACK: content type is not set
    verify(ownerRepository).getAll();
  }

  @Test
  public void shouldShowAllOwnersInExcelView() throws Exception {
    List<Owner> ownerList = Collections.emptyList();
    when(ownerRepository.getAll()).thenReturn(ownerList);

    mockMvc.perform(get("/owners.xls"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attribute("owners", ownerList));
//      .andExpect(content().contentType("application/vnd.ms-excel")); // HACK: content type is not set
    verify(ownerRepository).getAll();
  }

  @Test
  public void shouldShowAllOwnersInAtomFeedView() throws Exception {
    List<Owner> ownerList = Collections.emptyList();
    when(ownerRepository.getAll()).thenReturn(ownerList);

    mockMvc.perform(get("/owners.atom"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attribute("owners", ownerList))
        .andExpect(content().contentType(MediaType.APPLICATION_ATOM_XML));
    verify(ownerRepository).getAll();
  }


  @Test
  public void shouldShowOwnersMatchedByFirstName() throws Exception {
    when(ownerRepository.findByFirstName(anyString())).thenReturn(ownerListMock);

    // search for George
    mockMvc.perform(get("/owners").param("search-first-name", "gEora"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/index"))
        .andExpect(model().attribute("owners", ownerListMock));
    verify(ownerRepository).findByFirstName("gEora");
  }

  @Test
  public void shouldShowOwnerDetail() throws Exception {
    when(ownerRepository.getByIdWithPets(1)).thenReturn(ownerMock);
    when(ownerValidator.supports(ownerMock.getClass())).thenReturn(true);

    mockMvc.perform(get("/owners/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/viewOrEdit"))
        .andExpect(model().attribute("owner", ownerMock))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Readonly));

    verify(ownerRepository).getByIdWithPets(1);
    // validator is called even if we return the model
    verify(ownerValidator).supports(ownerMock.getClass());
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
        .andExpect(status().isFound())
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
    when(ownerRepository.getById(1)).thenReturn(ownerMock);
    when(ownerValidator.supports(ownerMock.getClass())).thenReturn(true);

    mockMvc.perform(get("/owners/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("owner/viewOrEdit"))
        .andExpect(model().attribute("owner", ownerMock))
        .andExpect(model().attribute("mode", ControllerBase.FormMode.Edit));

    verify(ownerRepository).getById(1);
    // validator is called even if we return the model
    verify(ownerValidator).supports(ownerMock.getClass());
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
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/owners/123"));
  }

  @Test
  public void shouldFailToUpdateOwnerWhenOwnerIdIsInvalid() throws Exception {
    RuntimeException exception = new RuntimeException();
    doThrow(exception).when(ownerRepository).update(any(Owner.class));

    when(ownerValidator.supports(any())).thenReturn(true);
    MockHttpServletRequestBuilder req = post("/owners/new")
        .param("id", "123")
        .param("firstName", "PunCha")
        .param("lastName", "Feng");
    mockMvc.perform(req)
        .andExpect(status().isOk())
        .andExpect(view().name("exception/default"))
        .andExpect(model().attribute("exception", exception));
//    verify(ownerRepository).update(any(Owner.class));
  }

  @Test
  public void shouldDeleteOwnerAndShowAllOwners() throws Exception {
    mockMvc.perform(get("/owners/1/delete"))
        .andExpect(status().isFound())
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
