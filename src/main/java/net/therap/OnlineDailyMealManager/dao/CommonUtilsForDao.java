package net.therap.OnlineDailyMealManager.dao;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.utils.Constants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static net.therap.OnlineDailyMealManager.utils.Constants.DB_OPERATIONS;
import static net.therap.OnlineDailyMealManager.utils.Constants.DB_OPERATIONS.*;

/**
 * @author anwar
 * @since 1/17/18
 */
public class CommonUtilsForDao {

    private EntityManagerFactory entityManagerFactory = JPAUtil.getEntityManagerFactory();
    private EntityManager entityManager = null;
    private Object object = null;

    public Object findOneByColumnNames(Object entity, Pair<String, Object>... columns) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            Query preparedQuery = prepareQueryWithRestrictions(entity, columns);
            object = preparedQuery.getResultList().size() != 0
                    ? preparedQuery.getSingleResult()
                    : null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
            return object;
        }
    }

    public List<Object> findAllByColumnNames(Object entity, Pair<String, Object>... columns) {
        List<Object> objects = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            for (Object object : prepareQueryWithRestrictions(entity, columns).getResultList()) {
                objects.add(object);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
            return objects;
        }
    }

    private Query prepareQueryWithRestrictions(Object entity, Pair<String, Object>... columns) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entity.getClass());
        Root root = criteriaQuery.from(entity.getClass());
        criteriaQuery.select(root);
        Predicate[] restrictions = new Predicate[columns.length];
        int idx = 0;
        for (Pair<String, Object> column : columns) {
            restrictions[idx++] = criteriaBuilder.equal(root.get(column.getKey()), column.getValue());
        }
        criteriaQuery.where(restrictions);
        return entityManager.createQuery(criteriaQuery);
    }

    public void saveOrUpdateOrDelete(DB_OPERATIONS operation, Object... objects) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            for (Object object : objects) {
                switch (operation) {
                    case SAVE_OR_UPDATE_OPERATION: {
                        entityManager.merge(object);
                        break;
                    }
                    case DELETE_OPERATION: {
                        entityManager.remove(entityManager.contains(object)
                                ? object
                                : entityManager.merge(object));
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}