package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.models.Owner;

import java.util.List;

@Repository
public class OwnerRepository {

  private final OwnerDAO ownerDAO;

  @Autowired
  public OwnerRepository(OwnerDAO ownerDAO) {
    this.ownerDAO = ownerDAO;
  }

  @Transactional
  public List<Owner> getAllOwners() {
    return ownerDAO.getAllOwners();
  }

  public List<Owner> getOwnersByFirstName(String firstName){
    return ownerDAO.getOwnersByFirstName(firstName);
  }

  @Transactional
  public Owner getOwnerById(int ownerId) {
    return ownerDAO.getOwnerById(ownerId);
  }

  @Transactional
  public Owner getOwnerWithPetsById(int ownerId) {
    return ownerDAO.getOwnerWithPetsById(ownerId);
  }

  @Transactional
  public void insertOwner(Owner owner) {
    ownerDAO.insertOwner(owner);
  }

  @Transactional
  public void updateOwner(Owner owner) {
    ownerDAO.updateOwner(owner);
  }

  @Transactional
  public void deleteOwner(int ownerId) {
    ownerDAO.deleteOwner(ownerId);
  }
}
