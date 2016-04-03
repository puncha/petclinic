package tk.puncha.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.models.Owner;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Component
public class OwnerDAO extends JdbcDaoSupport {

  private final String SQL_QUERY_ALL = "SELECT * FROM owners";
  private final String SQL_QUERY_BY_ID = "SELECT * FROM owners WHERE id = ?";
  private final String SQL_DELETE_BY_ID = "DELETE FROM owners WHERE id = ?";
  private final String SQL_UPDATE = "UPDATE owners SET first_name = ?, last_name = ?, address = ?, city = ?, telephone = ? WHERE id = ?";

  private SimpleJdbcInsert insertActor;

  private RowMapper<Owner> ownerRowMapper;


  @Autowired
  public OwnerDAO(DataSource dataSource) {
    setDataSource(dataSource);
    insertActor = new SimpleJdbcInsert(dataSource).withTableName("owners").usingGeneratedKeyColumns("id");

    ownerRowMapper = (rs, rowNum) -> {
      Owner owner = new Owner();
      owner.setId(rs.getInt("id"));
      owner.setFirstName(rs.getString("first_name"));
      owner.setLastName(rs.getString("last_name"));
      owner.setAddress(rs.getString("address"));
      owner.setCity(rs.getString("city"));
      owner.setTelephone(rs.getString("telephone"));
      return owner;
    };
  }

  public List<Owner> getAllOwners() {
    return this.getJdbcTemplate().query(SQL_QUERY_ALL, ownerRowMapper);
  }

  public Owner getOwnerById(int ownerId) {
    return this.getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, ownerRowMapper, ownerId);
  }

  public Owner insertOwner(Owner owner) {
    HashMap<String, Object> parameters = new HashMap<>(5);
    parameters.put("first_name", owner.getFirstName());
    parameters.put("last_name", owner.getLastName());
    parameters.put("address", owner.getAddress());
    parameters.put("city", owner.getCity());
    parameters.put("telephone", owner.getTelephone());
    Number newId = insertActor.executeAndReturnKey(parameters);
    owner.setId((int) newId);
    return owner;
  }

  public Owner updateOwner(Owner owner) {
    this.getJdbcTemplate().update(
        SQL_UPDATE, owner.getFirstName(), owner.getLastName(),
        owner.getAddress(), owner.getCity(), owner.getTelephone(),
        owner.getId());
    return owner;
  }

  // Note, owners table has a foreign key constraint,
  // the caller should make sure the owner's pets are
  // deleted.
  @Transactional
  public void deleteOwner(int id) {
    this.getJdbcTemplate().update(SQL_DELETE_BY_ID, id);
  }


}
