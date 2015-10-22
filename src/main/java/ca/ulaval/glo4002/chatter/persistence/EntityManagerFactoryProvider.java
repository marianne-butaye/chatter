package ca.ulaval.glo4002.chatter.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {

    private static EntityManagerFactory instance;

    public static EntityManagerFactory getFactory() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory("chatter");
        }
        return instance;
    }

    public static void reset() {
        if (instance != null) {
            instance.close();
            instance = null;
        }

    }

}
