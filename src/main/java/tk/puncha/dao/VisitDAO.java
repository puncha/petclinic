package tk.puncha.dao;

import tk.puncha.models.Visit;

public interface VisitDAO {

  int insert(Visit visit);

  void deleteById(int visitId);
}
