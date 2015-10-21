package ca.ulaval.glo4002.chatter.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ca.ulaval.glo4002.chatter.domain.Room;
import ca.ulaval.glo4002.chatter.domain.RoomRepository;

public class HibernateRoomRepository implements RoomRepository {

    private EntityManagerProvider entityManagerProvider;

    public HibernateRoomRepository() {
        this.entityManagerProvider = new EntityManagerProvider();
    }

    public void persist(Room room) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        entityManager.getTransaction().begin(); // not a good idea to manage tx here, but do not change.
        entityManager.persist(room);
        entityManager.getTransaction().commit();
    }

    @Override
    public Room findByUniqueName(String uniqueName) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query = entityManager.createQuery("from Room r where r.uniqueName = :name");
        query.setParameter("name", uniqueName);
        return (Room) query.getSingleResult();
    }

    @Override
    public void delete(Room room) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        entityManager.getTransaction().begin(); // not a good idea to manage tx here, but do not change.
        entityManager.remove(room);
        entityManager.getTransaction().commit();
    }

}
