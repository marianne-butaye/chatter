package ca.ulaval.glo4002.chatter.persistence;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.chatter.domain.Room;

public class HibernateRoomRepositoryITest {

    private HibernateRoomRepository repository;
    private EntityManager entityManager;

    @Before
    public void createRepository() {
        entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);
        repository = new HibernateRoomRepository();
    }

    @After
    public void closeEntityManager() {
        entityManager.close();
    }

    @Test
    public void canFindRoomByUniqueName() {
        String uniqueName = "uniqueName";
        Room room = new Room("name", uniqueName);
        repository.persist(room);

        Room roomFound = repository.findByUniqueName(uniqueName);

        assertEquals(room.getUniqueName(), roomFound.getUniqueName());
    }

    @Test(expected = NoResultException.class)
    public void canDeleteRoom() {
        String uniqueName = "uniqueName";
        Room room = new Room("name", uniqueName);
        repository.persist(room);

        repository.delete(room);

        repository.findByUniqueName(uniqueName);
    }

    @Test(expected = PersistenceException.class)
    public void hasUniqueConstraintOnUniqueName() {
        String uniqueName = "uniqueName";
        Room room1 = new Room("name", uniqueName);
        Room room2 = new Room("new name", uniqueName);
        repository.persist(room1);

        repository.persist(room2);
    }

}
