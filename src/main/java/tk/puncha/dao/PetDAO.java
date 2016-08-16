package tk.puncha.dao;

import tk.puncha.models.Pet;

import java.util.List;


public interface PetDAO {
  List<Pet> getAllPets();

  Pet getPetById(int id);

  void deletePetsByOwnerId(int ownerId);

  void updatePet(Pet pet);

  int insertPet(Pet pet);

  void delete(int id);
}
