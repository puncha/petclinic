package tk.puncha.dao.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.PetDAO;
import tk.puncha.mappers.PetMapper;
import tk.puncha.models.Pet;

import java.util.List;

@Profile("mybatis-orm")
@Component
public class MyBatisPetDAO implements PetDAO {

  @Autowired
  private PetMapper mapper;

  @Override
  public List<Pet> getAllPets() {
    return mapper.getAllPets();
  }

  @Override
  public Pet getPetById(int petId) {
    return mapper.getPetById(petId);
  }

  @Override
  public int insertPet(Pet pet) {
    return mapper.insertPet(pet);
  }

  @Override
  public void updatePet(Pet pet) {
    mapper.updatePet(pet);
  }

  @Override
  public void deletePetsByOwnerId(int ownerId) {
    mapper.deletePetsByOwnerId(ownerId);
  }

  @Override
  @Transactional
  public void delete(int id) {
    mapper.delete(id);
  }

}
