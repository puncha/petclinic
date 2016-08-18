package tk.puncha.dao;

import tk.puncha.models.Pet;

import java.util.List;


public interface PetDAO {
  List<Pet> getAll();

  Pet getById(int id);

  int insert(Pet pet);

  void update(Pet pet);

  void deleteById(int id);

  void deleteByOwnerId(int ownerId);
}
