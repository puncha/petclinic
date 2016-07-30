package tk.puncha.dao;

import tk.puncha.models.Visit;

public interface VisitDAO {
  void deleteVisit(int visitId);

  int insertVisit(Visit visit);
}
