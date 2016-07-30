package tk.puncha.dao;

import tk.puncha.models.Owner;

import java.util.List;


public interface OwnerDAO {
  List<Owner> getAllOwners();

  Owner getOwnerById(int ownerId);

  Owner getOwnerWithPetsById(int ownerId);

  int insertOwner(Owner owner);

  void updateOwner(Owner owner);

  void deleteOwner(int id);
}
