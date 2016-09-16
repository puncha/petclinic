package tk.puncha.dao;

import tk.puncha.models.Owner;

import java.util.List;


public interface OwnerDAO {
  List<Owner> getAll();

  List<Owner> findByFirstName(String firstName);

  Owner getById(int ownerId);

  Owner getByIdWithPets(int ownerId);

  int insert(Owner owner);

  void update(Owner owner);

  void deleteById(int id);
}
