package tk.puncha.unit.restfulControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import tk.puncha.models.PetType;
import tk.puncha.repositories.PetTypeRepository;
import tk.puncha.restfulControllers.RestfulPetTypeController;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureTestDatabase
@TestPropertySource(locations = {"/test.properties", "/unit-test.properties"})
@RunWith(SpringRunner.class)
@WebMvcTest(RestfulPetTypeController.class)
public class RestfulPetTypeControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PetTypeRepository petTypeRepository;

  @Test
  public void shouldGetAllPetTypes() throws Exception {
    List<PetType> petTypes = Collections.singletonList(TestUtil.createValidPetType());

    when(petTypeRepository.getAll()).thenReturn(petTypes);

    MockHttpServletRequestBuilder req = get("/api/petTypes")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(req)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(TestUtil.objectToJsonString(petTypes)));

    verify(petTypeRepository).getAll();
    verifyNoMoreInteractions(petTypeRepository);
  }

}
