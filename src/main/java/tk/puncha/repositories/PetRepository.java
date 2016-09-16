package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.PetDAO;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.Pet;

import java.util.List;


@Repository
@Transactional
public class PetRepository {

  private final PetDAO petDAO;
  private final PetTypeDAO petTypeDAO;

  @Autowired
  public PetRepository(PetDAO petDAO, PetTypeDAO petTypeDAO) {
    this.petDAO = petDAO;
    this.petTypeDAO = petTypeDAO;
  }

  @Transactional(readOnly = true)
  public List<Pet> getAll() {
    return petDAO.getAll();
  }

  @Transactional(readOnly = true)
  public Pet getById(int id) {
    return petDAO.getById(id);
  }

  public void update(Pet pet) {
    petDAO.update(pet);
  }

  public int insert(Pet pet) {
    return petDAO.insert(pet);
  }

  public void deleteById(int id) {
    petDAO.deleteById(id);
  }

  public void deleteByOwnerId(int ownerId) {
    petDAO.deleteByOwnerId(ownerId);
  }
}
