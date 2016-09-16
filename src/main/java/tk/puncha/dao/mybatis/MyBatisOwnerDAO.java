package tk.puncha.dao.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.mappers.OwnerMapper;
import tk.puncha.models.Owner;

import java.util.List;

@Profile("mybatis-orm")
@Component
public class MyBatisOwnerDAO implements OwnerDAO {

  @Autowired
  private OwnerMapper mapper;

  @Override
  public List<Owner> getAll() {
    return mapper.getAllOwners();
  }

  @Override
  public List<Owner> findByFirstName(String firstName) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public Owner getById(int ownerId) {
    return mapper.getOwnerById(ownerId);
  }

  @Override
  public Owner getByIdWithPets(int ownerId) {
    return mapper.getOwnerWithPetsById(ownerId);
  }

  @Override
  public int insert(Owner owner) {
    return mapper.insertOwner(owner);
  }

  @Override
  public void update(Owner owner) {
    mapper.updateOwner(owner);
  }

  @Override
  public void deleteById(int id) {
    mapper.deleteOwner(id);
  }
}
