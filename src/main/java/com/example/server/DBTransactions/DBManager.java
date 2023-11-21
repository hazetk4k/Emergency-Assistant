package com.example.server.DBTransactions;

import com.example.server.Entities.*;
import com.example.server.Service.SingletonIfClosed;
import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
        Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
        report.setRecievedDateTime(currentDate);
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

    public ObservableList<ReportInfo> getUserReportsInfo() {
        ObservableList<ReportInfo> reportsInfoList = FXCollections.observableArrayList();
        try {
            String queryStr = "SELECT u FROM UserDataEntity u";

            TypedQuery<UserDataEntity> query = entityManager.createQuery(queryStr, UserDataEntity.class);
            List<UserDataEntity> users = query.getResultList();

            for (UserDataEntity user : users) {
                String fullName = user.getSurname() + " " + user.getName().charAt(0) + ". " + user.getPatronymic().charAt(0) + ".";
                for (ReportsEntity report : user.getReportsByEmail()) {
                    int id = report.getIdReport();
                    String type = report.getType();
                    Timestamp timestamp = report.getTimestamp();
                    String place = report.getPlace();
                    Boolean wasSeen = report.getWasSeen();

                    ReportInfo reportInfo = new ReportInfo(id, type, timestamp, place, fullName, wasSeen);
                    reportsInfoList.add(reportInfo);
                }
            }

        } finally {
            entityManager.clear();
        }

        return reportsInfoList;
    }

    public TypeKindCharRep getTypeKindChar(String type_name) {
        TypeKindCharRep typeKindCharRep = null;
        try {
            String queryStr = "SELECT u FROM TypeEmEntity u WHERE u.name = :type_name";
            TypedQuery<TypeEmEntity> query = entityManager.createQuery(queryStr, TypeEmEntity.class);
            query.setParameter("type_name", type_name);
            TypeEmEntity that_type = query.getSingleResult();
            KindEmEntity that_kind = that_type.getKindEmByIdKind();
            CharEmEntity that_char = that_kind.getCharEmByIdChar();
            Collection<ServiceKindRelationEntity> that_rel = that_kind.getServiceKindRelationsByKindId();
            List<String> services = that_rel.stream()
                    .map(ServiceKindRelationEntity::getServiceByServiceId)
                    .map(ServiceEntity::getServiceName)
                    .collect(Collectors.toList());
            typeKindCharRep = new TypeKindCharRep(that_char.getCharName(), that_kind.getKindName(), that_type.getName(), that_type.getRecommendations(), services);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return typeKindCharRep;

    }

    public void resolveEmergency(int id) {
        ReportsEntity report = entityManager.find(ReportsEntity.class, id);
        try {
            if (report != null) {
                transaction.begin();
                report.setWasSeen(true);
                LocalDateTime currentDateTime = LocalDateTime.now();
                Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
                report.setEndUpDateTime(currentTimestamp);
                entityManager.merge(report);
                transaction.commit();
            }
            SingletonIfClosed.getInstance().updateTableWithData(getUserReportsInfo());
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }

    public FullReportRep getFullReport(int id) {
        FullReportRep rep = null;
        try {
            String queryStr = "SELECT n FROM ReportsEntity n WHERE n.idReport = :id";
            TypedQuery<ReportsEntity> query = entityManager.createQuery(queryStr, ReportsEntity.class);
            query.setParameter("id", id);
            ReportsEntity thatReport = query.getSingleResult();
            UserDataEntity user = thatReport.getUserByEmail();
            rep = new FullReportRep(thatReport.getIdReport(),
                    thatReport.getType(),
                    thatReport.getAdditionalInfo(),
                    thatReport.getPlace(),
                    thatReport.getTimestamp(),
                    thatReport.getAreThereAnyCasualties(),
                    thatReport.getCasualtiesAmount(),
                    thatReport.getUserInDanger(),
                    thatReport.getWasSeen(),
                    thatReport.getUserEmail(),
                    thatReport.getRecievedDateTime(),
                    thatReport.getEndUpDateTime(),
                    user.getHomeAddress());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return rep;
    }

    public String checkAuthParametrs(String login_syst, String password) {
        String result = null;
        try {
            String queryStr = "SELECT n FROM SystUserEntity n WHERE n.loginSyst = :login_syst";
            TypedQuery<SystUserEntity> query = entityManager.createQuery(queryStr, SystUserEntity.class);
            query.setParameter("login_syst", login_syst);
            SystUserEntity systUserEntity = query.getSingleResult();
            if (systUserEntity == null) {
                result = "no such data";
            } else {
                if (Objects.equals(systUserEntity.getPassword(), password)) {
                    if (systUserEntity.getStatusSyst() == 1) {
                        return "админ";
                    } else {
                        return "диспетчер";
                    }
                } else {
                    result = "incorrect pas";
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            entityManager.clear();
        }
        return result;
    }
}
