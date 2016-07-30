package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.PetDAO;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.Pet;
import tk.puncha.models.PetType;

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
  public List<Pet> getAllPets() {
    return petDAO.getAllPets();
  }


  @Transactional(readOnly = true)
  public Pet getPetById(int id) {
    return petDAO.getPetById(id);
  }

  public void updatePet(Pet pet) {
    petDAO.updatePet(pet);
  }

  public int insertPet(Pet pet) {
    return petDAO.insertPet(pet);
  }

  public void delete(int id) {
    petDAO.delete(id);
  }

  @Transactional(readOnly = true)
  public List<PetType> getAllTypes() {
    return petTypeDAO.getAllTypes();
  }

  public void deletePetsByOwnerId(int ownerId) {
    petDAO.deletePetsByOwnerId(ownerId);
  }
}
