package tk.puncha.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import tk.puncha.models.Owner;

import javax.sql.DataSource;
import java.util.List;

@Component
public class OwnerDAO extends JdbcDaoSupport {

  private final String SQL_QUERY_ALL = "select * from owners";

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
}
