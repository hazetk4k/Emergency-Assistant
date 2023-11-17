package com.example.server.DBTransactions;

import com.example.server.Entities.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.function.Consumer;

public class DBManager {
    private EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("default");
    public EntityManager entityManager = managerFactory.createEntityManager();
    public EntityTransaction transaction = entityManager.getTransaction();

    public List<TypeEmEntity> getAllTypesEntities() {
        List<TypeEmEntity> list;
        try {
            transaction.begin();
            TypedQuery<TypeEmEntity> q = entityManager.createQuery("SELECT e from TypeEmEntity e", TypeEmEntity.class);
            list = q.getResultList();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.clear();
        }
        return list;
    }

    public UserDataEntity getUserByEmailAndPassword(String email) {
        try {
            TypedQuery<UserDataEntity> query = entityManager.createQuery(
                    "SELECT u FROM UserDataEntity u WHERE u.email = :email ",
                    UserDataEntity.class);
            query.setParameter("email", email);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.clear();
        }
    }


    public void addReportToDatabase(ReportsEntity report) {
        performDatabaseOperation(entityManager -> entityManager.persist(report));
    }

    public void addUserToDatabase(UserDataEntity user) {
        performDatabaseOperation(entityManager -> entityManager.persist(user));
    }

    private void performDatabaseOperation(Consumer<EntityManager> operation) {
        try {
            transaction.begin();
            operation.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }
}
