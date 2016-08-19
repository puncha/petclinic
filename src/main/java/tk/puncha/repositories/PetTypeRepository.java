package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.PetType;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PetTypeRepository {

  private final PetTypeDAO petTypeDAO;

  @Autowired
  public PetTypeRepository(PetTypeDAO petTypeDAO) {
    this.petTypeDAO = petTypeDAO;
  }

  public List<PetType> getAll() {
    return petTypeDAO.getAll();
  }

  public PetType getById(int id) {
    return petTypeDAO.getById(id);
  }
}
