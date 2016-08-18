package tk.puncha.dao.hibernate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tk.puncha.dao.VisitDAO;
import tk.puncha.models.Visit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("hibernate")
@Component
public class HibernateVisitDAO implements VisitDAO {

  @PersistenceContext
  private EntityManager em;

  @Override
  public void deleteById(int visitId) {
    em.remove(em.getReference(Visit.class, visitId));
  }

  @Override
  public int insert(Visit visit) {
    em.persist(visit);
    return visit.getId();
  }

}
