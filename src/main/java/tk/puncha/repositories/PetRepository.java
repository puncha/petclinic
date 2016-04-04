package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.dao.PetDAO;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.Pet;
import tk.puncha.models.PetType;
import tk.puncha.views.PetView;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PetRepository {

  @Autowired
  private PetDAO petDAO;

  @Autowired
  private OwnerDAO ownerDAO;

  @Autowired
  private PetTypeDAO petTypeDAO;

  public List<PetView> getAllPets() {
    List<tk.puncha.models.Pet> pets = petDAO.getAllPets();
    List<PetView> petViewViews = new ArrayList<>(pets.size());
    for (tk.puncha.models.Pet pet : pets) {
      PetView petView = new PetView();
      petView.setPet(pet);
      petView.setOwner(ownerDAO.getOwnerById(pet.getOwnerId()));
      petView.setType(petTypeDAO.getTypeById(pet.getTypeId()));
      petViewViews.add(petView);
    }
    return petViewViews;
  }

  public Pet getPetById(int id) {
    return petDAO.getPetById(id);
  }

  public void updatePet(Pet pet) {
    petDAO.updatePet(pet);
  }

  public Pet insertPet(Pet pet) {
    return petDAO.insertPet(pet);
  }

  public void delete(int id) {
    petDAO.delete(id);
  }

  public List<PetType> getAllTypes(){
    return petTypeDAO.getAllTypes();
  }

  public void deletePetsByOwnerId(int ownerId) {
    petDAO.deletePetsByOwnerId(ownerId);
  }
}
