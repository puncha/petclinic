package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.VisitDAO;
import tk.puncha.models.Visit;

@Repository
@Transactional
public class VisitRepository {

  private final VisitDAO visitDAO;

  @Autowired
  public VisitRepository(VisitDAO visitDAO) {
    this.visitDAO = visitDAO;
  }

  public int insert(Visit visit) {
    return visitDAO.insert(visit);
  }

  public void deleteById(int id) {
    visitDAO.deleteById(id);
  }
}
