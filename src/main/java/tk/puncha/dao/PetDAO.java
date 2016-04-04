package tk.puncha.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.models.Pet;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Component
public class PetDAO extends JdbcDaoSupport {

  private final String SQL_DELETE_BY_ID = "DELETE FROM pets WHERE id = ?";
  private final String SQL_QUERY_ALL = "SELECT * FROM pets";
  private final String SQL_QUERY_BY_ID = "SELECT * FROM pets WHERE id = ?";
  private final String SQL_DELETE_BY_OWNER = "DELETE FROM pets WHERE owner_id = ?";
  private final String SQL_UPDATE = "UPDATE pets SET name = ?, owner_id = ?, type_id = ?, birth_date = ? WHERE id = ?";
  private final RowMapper<Pet> rowMapper;
  private final SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public PetDAO(DataSource dataSource) {
    this.setDataSource(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("pets").usingGeneratedKeyColumns("id");
    this.rowMapper = (rs, rowNum) -> {
      Pet pet = new Pet();
      pet.setId(rs.getInt("id"));
      pet.setName(rs.getString("name"));
      pet.setBirthDate(rs.getDate("birth_date"));
      pet.setOwnerId(rs.getInt("owner_id"));
      pet.setTypeId(rs.getInt("type_id"));
      return pet;
    };
  }

  public List<Pet> getAllPets() {
    return this.getJdbcTemplate().query(SQL_QUERY_ALL, rowMapper);
  }

  public Pet getPetById(int id) {
    return this.getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, rowMapper, id);
  }

  @Transactional
  public void deletePetsByOwnerId(int ownerId) {
    this.getJdbcTemplate().update(SQL_DELETE_BY_OWNER, ownerId);
  }

  public void updatePet(Pet pet) {
    this.getJdbcTemplate().update(
        SQL_UPDATE, pet.getName(), pet.getOwnerId(), pet.getTypeId(), pet.getBirthDate(), pet.getId());
  }

  public Pet insertPet(Pet pet) {
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("name", pet.getName());
    parameters.put("owner_id", pet.getOwnerId());
    parameters.put("type_id", pet.getTypeId());
    parameters.put("birth_date", pet.getBirthDate());
    Number id = this.simpleJdbcInsert.executeAndReturnKey(parameters);
    pet.setId((Integer) id);
    return pet;
  }

  public void delete(int id) {
    this.getJdbcTemplate().update(SQL_DELETE_BY_ID, id);
  }
}
