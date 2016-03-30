package tk.puncha.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Component
public class PetDAO extends JdbcDaoSupport {

  private final String SQL_DELETE_PETS_BY_OWNER = "delete from pets where owner_id = ?";

  @Autowired
  public PetDAO(DataSource dataSource) {
    this.setDataSource(dataSource);
  }

  @Transactional
  public void deletePetsByOwnerId(int ownerId){
    this.getJdbcTemplate().update(SQL_DELETE_PETS_BY_OWNER, ownerId);
  }
}
