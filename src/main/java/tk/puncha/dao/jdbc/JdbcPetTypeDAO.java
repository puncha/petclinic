package tk.puncha.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import tk.puncha.dao.PetTypeDAO;
import tk.puncha.models.PetType;

import javax.sql.DataSource;
import java.util.List;

@Profile("jdbc-orm")
@Component
public class JdbcPetTypeDAO extends JdbcDaoSupport implements PetTypeDAO {

  private final String SQL_QUERY_ALL = "SELECT * FROM types";
  private final String SQL_QUERY_BY_ID = "SELECT * FROM types where id = ?";
  private final RowMapper<PetType> rowMapper;

  @Autowired
  public JdbcPetTypeDAO(DataSource dataSource) {
    this.setDataSource(dataSource);
    this.rowMapper = (rs, rowNum) -> {
      PetType petType = new PetType();
      petType.setId(rs.getInt("id"));
      petType.setName(rs.getString("name"));
      return petType;
    };
  }

  @Override
  public List<PetType> getAll() {
    return this.getJdbcTemplate().query(SQL_QUERY_ALL, rowMapper);
  }


  @Override
  public PetType getById(int id) {
    return this.getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, rowMapper, id);
  }
}
