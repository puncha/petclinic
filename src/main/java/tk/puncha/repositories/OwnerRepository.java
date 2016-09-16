package tk.puncha.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.models.Owner;

import java.util.List;

@Repository
@Transactional
public class OwnerRepository {

  private final OwnerDAO ownerDAO;

  @Autowired
  public OwnerRepository(OwnerDAO ownerDAO) {
    this.ownerDAO = ownerDAO;
  }

  @Transactional(readOnly = true)
  public List<Owner> getAll() {
    return ownerDAO.getAll();
  }

  @Transactional(readOnly = true)
  public List<Owner> findByFirstName(String firstName){
    return ownerDAO.findByFirstName(firstName);
  }

  @Transactional(readOnly = true)
  public Owner getById(int ownerId) {
    return ownerDAO.getById(ownerId);
  }

  @Transactional(readOnly = true)
  public Owner getByIdWithPets(int ownerId) {
    return ownerDAO.getByIdWithPets(ownerId);
  }

  public int insert(Owner owner) {
    return ownerDAO.insert(owner);
  }

  public void update(Owner owner) {
    ownerDAO.update(owner);
  }

  public void deleteById(int ownerId) {
    ownerDAO.deleteById(ownerId);
  }
}
