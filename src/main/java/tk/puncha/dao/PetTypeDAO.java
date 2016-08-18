package tk.puncha.dao;

import tk.puncha.models.PetType;

import java.util.List;

public interface PetTypeDAO {

  List<PetType> getAll();

  PetType getById(int id);
}
