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
  public List<Owner> getAllOwners() {
    return mapper.getAllOwners();
  }

  @Override
  public List<Owner> getOwnersByFirstName(String firstName) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public Owner getOwnerById(int ownerId) {
    return mapper.getOwnerById(ownerId);
  }

  @Override
  public Owner getOwnerWithPetsById(int ownerId) {
    return mapper.getOwnerWithPetsById(ownerId);
  }

  @Override
  public int insertOwner(Owner owner) {
    return mapper.insertOwner(owner);
  }

  @Override
  public void updateOwner(Owner owner) {
    mapper.updateOwner(owner);
  }

  @Override
  public void deleteOwner(int id) {
    mapper.deleteOwner(id);
  }
}
