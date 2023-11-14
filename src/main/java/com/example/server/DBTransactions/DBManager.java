package com.example.server.DBTransactions;

import com.example.server.Entities.*;
import jakarta.persistence.*;

import java.util.List;

public class DBManager {
    EntityTransaction transaction;
    EntityManagerFactory managerFactory;
    EntityManager entityManager;

    public List<TypeEmEntity> getAllTypesEntities() {
        List<TypeEmEntity> list;
        try {
            managerFactory = Persistence.createEntityManagerFactory("default");
            entityManager = managerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<TypeEmEntity> q = entityManager.createQuery("SELECT e from TypeEmEntity e", TypeEmEntity.class);
            list = q.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return list;
    }

    //entityManager.persist

    //entityManger.merge

    //entityManger.remove


}
