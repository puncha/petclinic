package tk.puncha.dao.hibernate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.PetType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Profile("hibernate")
@Component
public class HibernatePetTypeDAO implements PetTypeDAO {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<PetType> getAll() {
    String query = "select petType from PetType petType";
    return em.createQuery(query, PetType.class).getResultList();
  }

  @Override
  public PetType getById(int id) {
    return em.find(PetType.class, id);
  }
}
