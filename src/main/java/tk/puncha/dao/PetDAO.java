package tk.puncha.dao;

import tk.puncha.models.Pet;

import java.util.List;


public interface PetDAO {
  List<Pet> getAllPets();

  Pet getPetById(int id);

  int insertPet(Pet pet);

  void updatePet(Pet pet);

  void delete(int id);

  void deletePetsByOwnerId(int ownerId);
}
