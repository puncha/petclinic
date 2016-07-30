package tk.puncha.mappers;

import org.apache.ibatis.annotations.Mapper;
import tk.puncha.models.PetType;

import java.util.List;

@Mapper
public interface PetTypeMapper {
  List<PetType> getAllTypes();

  PetType getTypeById(int id);
}
