package tk.puncha.dao.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.mappers.PetTypeMapper;
import tk.puncha.models.PetType;

import java.util.List;

@Profile("mybatis-orm")
@Component
public class MyBatisPetTypeDAO implements PetTypeDAO {

  @Autowired
  private PetTypeMapper mapper;

  @Override
  public List<PetType> getAllTypes() {
    return mapper.getAllTypes();
  }

  @Override
  public PetType getTypeById(int id) {
    return mapper.getTypeById(id);
  }
}
