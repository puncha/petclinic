package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.models.Owner;

import java.util.List;

@Repository
public class OwnerRepository {

  @Autowired
  private OwnerDAO ownerDAO;
  @Autowired
  private PetRepository petRepostory;

  public List<Owner> getAllOwners() {
    return ownerDAO.getAllOwners();
  }


  public Owner getOwnerById(int ownerId) {
    return ownerDAO.getOwnerById(ownerId);
  }

  public void insertOwner(Owner owner) {
    ownerDAO.insertOwner(owner);
  }

  public void updateOwner(Owner owner) {
    ownerDAO.updateOwner(owner);
  }

  public void deleteOwner(int ownerId) {
    petRepostory.deletePetsByOwnerId(ownerId);
    ownerDAO.deleteOwner(ownerId);
  }
}
