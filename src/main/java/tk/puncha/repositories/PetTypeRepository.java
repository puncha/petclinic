package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.PetType;

import java.util.List;

@Repository
public class PetTypeRepository {

  private final PetTypeDAO petTypeDAO;

  @Autowired
  public PetTypeRepository(PetTypeDAO petTypeDAO) {
    this.petTypeDAO = petTypeDAO;
  }

  public List<PetType> getAllTypes() {
    return petTypeDAO.getAllTypes();
  }

  public PetType getPetTypeById(int id) {
    return petTypeDAO.getTypeById(id);
  }
}
