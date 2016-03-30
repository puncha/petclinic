package tk.puncha.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.puncha.models.Owner;

import javax.sql.DataSource;
import java.util.List;

@Component
public class OwnerDAO extends JdbcDaoSupport {

  private final String SQL_QUERY_ALL = "select * from owners";
  private final String SQL_DELETE_BY_ID = "delete from owners where id = ?";

  @Autowired
  public OwnerDAO(DataSource dataSource) {
    setDataSource(dataSource);
  }

  public List<Owner> getAllOwners() {
    return this.getJdbcTemplate().query(SQL_QUERY_ALL, (rs, rowNum) -> {
      Owner owner = new Owner();
      owner.setId(rs.getInt("id"));
      owner.setFirstName(rs.getString("first_name"));
      owner.setLastName(rs.getString("last_name"));
      owner.setAddress(rs.getString("address"));
      owner.setCity(rs.getString("city"));
      owner.setTelephone(rs.getString("telephone"));
      return owner;
    });
  }

  // Note, owners table has a foreign key constraint,
  // the caller should make sure the owner's pets are
  // deleted.
  @Transactional
  public void deleteOwner(int id) {
    this.getJdbcTemplate().update(SQL_DELETE_BY_ID, id);
  }
}
