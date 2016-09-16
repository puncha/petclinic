package tk.puncha.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.dao.PetDAO;
import tk.puncha.models.JdbcPet;
import tk.puncha.models.Pet;

import javax.sql.DataSource;
import java.util.List;

@Profile("jdbc-orm")
@Component
public class JdbcPetDAO extends JdbcDaoSupport implements PetDAO {

  private final String SQL_DELETE_BY_ID = "DELETE FROM pets WHERE id = ?";
  private final String SQL_QUERY_ALL = "SELECT * FROM pets";
  private final String SQL_QUERY_BY_ID = "SELECT * FROM pets WHERE id = ?";
  private final String SQL_DELETE_BY_OWNER = "DELETE FROM pets WHERE owner_id = ?";
  private final String SQL_UPDATE = "UPDATE pets SET name = ?, owner_id = ?, type_id = ?, birth_date = ? WHERE id = ?";
  private final RowMapper<Pet> rowMapper;
  private final SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public JdbcPetDAO(DataSource dataSource) {
    this.setDataSource(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("pets").usingGeneratedKeyColumns("id");
    this.rowMapper = (rs, rowNum) -> {
      JdbcPet pet = new JdbcPet();
      pet.setId(rs.getInt("id"));
      pet.setName(rs.getString("name"));
      pet.setBirthDate(rs.getDate("birth_date"));
      pet.setOwnerId(rs.getInt("owner_id"));
      pet.setTypeId(rs.getInt("type_id"));
      return pet;
    };
  }

  @Override
  public List<Pet> getAll() {
    return this.getJdbcTemplate().query(SQL_QUERY_ALL, rowMapper);
  }

  @Override
  public Pet getById(int id) {
    return this.getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, rowMapper, id);
  }

  @Override
  @Transactional
  public void deleteByOwnerId(int ownerId) {
    this.getJdbcTemplate().update(SQL_DELETE_BY_OWNER, ownerId);
  }

  @Override
  public void update(Pet pet) {
    // TODO: FIX ME
    throw new RuntimeException();
//    this.getJdbcTemplate().update(
//        SQL_UPDATE, pet.getName(), pet.getOwnerId(), pet.getTypeId(), pet.getBirthDate(), pet.getId());
  }

  @Override
  public int insert(Pet pet) {
    // TODO: FIX ME
    throw new RuntimeException();
//    HashMap<String, Object> parameters = new HashMap<>();
//    parameters.put("name", pet.getName());
//    parameters.put("owner_id", pet.getOwnerId());
//    parameters.put("type_id", pet.getTypeId());
//    parameters.put("birth_date", pet.getBirthDate());
//    int id = (int) this.simpleJdbcInsert.executeAndReturnKey(parameters);
//    pet.setId(id);
//    return id;
  }

  @Override
  public void deleteById(int id) {
    this.getJdbcTemplate().update(SQL_DELETE_BY_ID, id);
  }
}
