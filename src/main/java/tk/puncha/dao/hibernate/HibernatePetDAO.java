package tk.puncha.dao.hibernate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tk.puncha.dao.PetDAO;
import tk.puncha.models.Owner;
import tk.puncha.models.Pet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Profile("hibernate")
@Component
public class HibernatePetDAO implements PetDAO {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Pet> getAll() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pet> cbQuery = cb.createQuery(Pet.class);
    cbQuery.select(cbQuery.from(Pet.class));
    return em.createQuery(cbQuery).getResultList();
  }

  @Override
  public Pet getById(int petId) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pet> cbQuery = cb.createQuery(Pet.class);
    Root<Pet> root = cbQuery.from(Pet.class);
    cbQuery.select(root).where(cb.equal(root.get("id"), petId));
    return em.createQuery(cbQuery).getResultList().stream().findFirst().orElse(null);
  }

  @Override
  public int insert(Pet pet) {
    em.persist(pet);
    return pet.getId();
  }

  @Override
  public void update(Pet pet) {
    em.merge(pet);
  }

  @Override
  public void deleteByOwnerId(int ownerId) {
    // TODO: the logic should be in OwnerDAO
    Owner owner = em.getReference(Owner.class, ownerId);
    owner.getPets().clear();
    em.persist(owner);
  }

  @Override
  public void deleteById(int id) {
    Pet pet = em.getReference(Pet.class, id);
    em.remove(pet);
  }

}
