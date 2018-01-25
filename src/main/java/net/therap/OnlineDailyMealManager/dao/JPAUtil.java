package net.therap.OnlineDailyMealManager.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.ENTITY_MANAGER_FACTORY_BUILD_FAILED_ERROR;

/**
 * @author anwar
 * @since 1/18/18
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (Exception e) {
                System.out.println(ENTITY_MANAGER_FACTORY_BUILD_FAILED_ERROR + e.getMessage());
            }
        }
        return entityManagerFactory;
    }

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
