package com.example.server.DBTransactions;

import com.example.server.Entities.*;
import jakarta.persistence.*;

import java.util.List;

public class DBManager {
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("default");


    public List<TypeEmEntity> getAllTypesEntities() {
        List<TypeEmEntity> list;
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<TypeEmEntity> q = entityManager.createQuery("SELECT e from TypeEmEntity e", TypeEmEntity.class);
            list = q.getResultList();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
        return list;
    }

    public void addReportToDatabase(ReportsEntity report) {
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(report);
            System.out.println("Добавлено");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.close(); // Закрываем EntityManager после использования
        }
    }

    public void addUserToDatabase(UserDataEntity user) {
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(user);
            System.out.println("Добавлено.");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.close(); // Закрываем EntityManager после использования
        }
    }

}
