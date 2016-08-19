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
    return ownerDAO.getAll();
  }

  public List<Owner> getOwnersByFirstName(String firstName){
    return ownerDAO.findByFirstName(firstName);
  }

  @Transactional
  public Owner getOwnerById(int ownerId) {
    return ownerDAO.getById(ownerId);
  }

  @Transactional
  public Owner getOwnerWithPetsById(int ownerId) {
    return ownerDAO.getByIdWithPets(ownerId);
  }

  @Transactional
  public int insertOwner(Owner owner) {
    return ownerDAO.insert(owner);
  }

  @Transactional
  public void updateOwner(Owner owner) {
    ownerDAO.update(owner);
  }

  @Transactional
  public void deleteOwner(int ownerId) {
    ownerDAO.deleteById(ownerId);
  }
}
