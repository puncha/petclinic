package tk.puncha.mappers;

import org.apache.ibatis.annotations.Mapper;
import tk.puncha.models.Owner;

import java.util.List;

@Mapper
public interface OwnerMapper {
  List<Owner> getAllOwners();

  Owner getOwnerById(int id);

  Owner getOwnerWithPetsById(int id);

  int insertOwner(Owner owner);

  void updateOwner(Owner owner);

  void deleteOwner(int id);
}
