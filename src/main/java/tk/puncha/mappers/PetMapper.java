package tk.puncha.mappers;

import org.apache.ibatis.annotations.Mapper;
import tk.puncha.models.Pet;

import java.util.List;

@Mapper
public interface PetMapper {
  List<Pet> getAllPets();

  Pet getPetById(int id);

  void deletePetsByOwnerId(int ownerId);

  void updatePet(Pet pet);

  int insertPet(Pet pet);

  void delete(int id);
}
