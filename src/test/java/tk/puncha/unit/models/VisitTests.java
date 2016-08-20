package tk.puncha.unit.models;

import org.junit.Before;
import org.junit.Test;
import tk.puncha.TestUtil;
import tk.puncha.models.Visit;

import javax.validation.ConstraintViolation;
import java.sql.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class VisitTests {

  private Visit visit;

  @Before
  public void before() {
    this.visit = new Visit();
  }

  @Test
  public void shouldDefaultVisitWellInitialized() throws Exception {
    assertEquals(-1, visit.getId());
    assertNull(visit.getDescription());
    assertNull(visit.getPet());
    assertNull(visit.getVisitDate());
  }

  @Test
  public void shouldDefaultVisitNotValidate() throws Exception {
    Set<ConstraintViolation<Visit>> violations = TestUtil.createValidator().validate(visit);
    assertEquals(1, violations.size()); // visitDate
  }

  @Test
  public void shouldVisitValidate() throws Exception {
    visit.setVisitDate(new Date(2016,6,6));
    Set<ConstraintViolation<Visit>> violations = TestUtil.createValidator().validate(visit);
    assertEquals(0, violations.size());
  }
}
