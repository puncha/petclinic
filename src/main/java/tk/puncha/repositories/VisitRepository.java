package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.VisitDAO;
import tk.puncha.models.Visit;

@Component
public class VisitRepository {

  private final VisitDAO visitDAO;

  @Autowired
  public VisitRepository(VisitDAO visitDAO) {
    this.visitDAO = visitDAO;
  }

  @Transactional
  public int insertVisit(Visit visit) {
    return visitDAO.insertVisit(visit);
  }

  @Transactional
  public void delete(int id) {
    visitDAO.deleteVisit(id);
  }
}
