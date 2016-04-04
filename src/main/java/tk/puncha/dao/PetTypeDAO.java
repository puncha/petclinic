package tk.puncha.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import tk.puncha.models.PetType;

import javax.sql.DataSource;
import java.util.List;

@Component
public class PetTypeDAO extends JdbcDaoSupport {

  private final String SQL_QUERY_ALL = "SELECT * FROM types";
  private final String SQL_QUERY_BY_ID = "SELECT * FROM types where id = ?";
  private final RowMapper<PetType> rowMapper;

  @Autowired
  public PetTypeDAO(DataSource dataSource) {
    this.setDataSource(dataSource);
    this.rowMapper = (rs, rowNum) -> {
      PetType petType = new PetType();
      petType.setId(rs.getInt("id"));
      petType.setName(rs.getString("name"));
      return petType;
    };
  }

  public List<PetType> getAllTypes() {
    return this.getJdbcTemplate().query(SQL_QUERY_ALL, rowMapper);
  }


  public PetType getTypeById(int id) {
    return this.getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, rowMapper, id);
  }
}
