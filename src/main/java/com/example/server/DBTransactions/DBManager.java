package com.example.server.DBTransactions;

import com.example.server.Entities.*;
import com.example.server.Service.DateTimeFormatExample;
import com.example.server.Service.SingletonIfClosed;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    public ChoiceRep getChoicesForReport(int reportId) {
        ChoiceRep choiceRep = null;
        try {
            TypedQuery<DispChoiceEntity> query = entityManager.createQuery(
                            "SELECT d FROM DispChoiceEntity d WHERE d.reportsByRepotId.id = :reportId", DispChoiceEntity.class)
                    .setParameter("reportId", reportId);
            DispChoiceEntity dispChoiceEntity = query.getSingleResult();
            choiceRep = new ChoiceRep(dispChoiceEntity.getNameChar(), dispChoiceEntity.getNameType(), dispChoiceEntity.getServices());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return choiceRep;
    }

    public Long isThereChoice(int reportId) {
        Long count = null;
        try {
            String queryString = "SELECT COUNT(d) FROM DispChoiceEntity d WHERE d.reportsByRepotId.id = :reportId";
            count = entityManager.createQuery(queryString, Long.class)
                    .setParameter("reportId", reportId)
                    .getSingleResult();
        } catch (Exception e) {
        } finally {
            entityManager.clear();
        }
        return count;
    }

    public void addReportToDatabase(ReportsEntity report) {
        Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());
        report.setRecievedDateTime(currentDate);
        performDatabaseOperation(entityManager -> entityManager.persist(report));
    }

    public void addUserToDatabase(UserDataEntity user) {
        performDatabaseOperation(entityManager -> entityManager.persist(user));
    }

    public void makeDicision(String char_name, String kind_name, String services, int reportId) {
        try {
            DispChoiceEntity newEntity = new DispChoiceEntity();
            newEntity.setNameChar(char_name);
            newEntity.setNameType(kind_name);
            newEntity.setServices(services);
            ReportsEntity relatedReport = entityManager.find(ReportsEntity.class, reportId);
            // Установка связи с ReportsEntity
            newEntity.setReportsByRepotId(relatedReport);
            // Сохранение новой записи в базе данных
            entityManager.getTransaction().begin();
            entityManager.persist(newEntity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
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
        DateTimeFormatExample format = new DateTimeFormatExample();
        try {
            String queryStr = "SELECT u FROM UserDataEntity u";

            TypedQuery<UserDataEntity> query = entityManager.createQuery(queryStr, UserDataEntity.class);
            List<UserDataEntity> users = query.getResultList();

            for (UserDataEntity user : users) {
                String fullName = "";
                if(user.getPatronymic() == null || Objects.equals(user.getPatronymic(), "")){
                    fullName = user.getSurname() + " " + user.getName().charAt(0) + ". ";
                } else{
                    fullName = user.getSurname() + " " + user.getName().charAt(0) + ". " + user.getName().charAt(0);
                }

                for (ReportsEntity report : user.getReportsByEmail()) {
                    int id = report.getIdReport();
                    String type = report.getType();
                    Timestamp timestamp = report.getTimestamp();
                    String place = report.getPlace();
                    Boolean wasSeen = report.getWasSeen();

                    ReportInfo reportInfo = new ReportInfo(id, type, format.dateTimeChange(timestamp.toString()), place, fullName, wasSeen);
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

    public int isThereType(String type) {
        String query = "SELECT COUNT(*) FROM TypeEmEntity t WHERE t.name = :type_name";
        Query q = entityManager.createQuery(query);
        q.setParameter("type_name", type);
        Long count = (Long) q.getSingleResult();
        System.out.println(count);
        if (count > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int isThereKind(String kind) {
        String query = "SELECT COUNT(*) FROM KindEmEntity t WHERE t.kindName = :kindName";
        Query q = entityManager.createQuery(query);
        q.setParameter("kindName", kind);
        Long count = (Long) q.getSingleResult();
        System.out.println(count);
        if (count > 0) {
            return 1;
        } else {
            return 0;
        }
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

    public List<String> getAllCharNames() {
        List<String> result = null;
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT ce.charName FROM CharEmEntity ce", String.class);
            result = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return result;
    }

    public List<String> getAllKindNames() {
        List<String> result = null;
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT ce.kindName FROM KindEmEntity ce", String.class);
            result = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return result;
    }

    public List<String> getAllServices() {
        List<String> result = null;
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT ce.serviceName FROM ServiceEntity ce", String.class);
            result = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return result;
    }

    public List<String> getServicesByKind(String kindName) {
        List<String> services = null;
        try {
            String queryStr = "SELECT n FROM KindEmEntity n WHERE n.kindName = :kindName";
            TypedQuery<KindEmEntity> query = entityManager.createQuery(queryStr, KindEmEntity.class);
            query.setParameter("kindName", kindName);
            KindEmEntity that_kind = query.getSingleResult();
            Collection<ServiceKindRelationEntity> that_rel = that_kind.getServiceKindRelationsByKindId();
            services = that_rel.stream()
                    .map(ServiceKindRelationEntity::getServiceByServiceId)
                    .map(ServiceEntity::getServiceName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return services;
    }

    public ObservableList<SystemUsersRep> getSystUsers() {
        ObservableList<SystemUsersRep> usersReps = FXCollections.observableArrayList();
        try {
            String queryStr = "SELECT u FROM SystUserEntity u";
            TypedQuery<SystUserEntity> query = entityManager.createQuery(queryStr, SystUserEntity.class);
            List<SystUserEntity> users = query.getResultList();

            for (SystUserEntity user : users) {
                String status;
                if (user.getStatusSyst() == 1) {
                    status = "Администратор";
                } else {
                    status = "Диспетчер";
                }
                SystemUsersRep rep = new SystemUsersRep(user.getIdSyst(), user.getLoginSyst(), status);
                usersReps.add(rep);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return usersReps;
    }

    public void addNewUser(String login, String password, byte status) {
        try {
            SystUserEntity systUserEntity = new SystUserEntity();
            systUserEntity.setLoginSyst(login);
            systUserEntity.setPassword(password);
            systUserEntity.setStatusSyst(status);

            entityManager.getTransaction().begin();
            entityManager.persist(systUserEntity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }

    public boolean isTypeNameExists(String name) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(t) FROM TypeEmEntity t WHERE t.name = :name", Long.class);
            query.setParameter("name", name);

            Long count = query.getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            System.out.println("Ошибка при проверке имени типа: " + e.getMessage());
            return false;
        }
    }

    public void addNewType(String text, String text1, Integer id) {
        try {
            TypeEmEntity newType = new TypeEmEntity();
            newType.setName(text);
            newType.setRecommendations(text1);
            newType.setIdKind(id);
            entityManager.getTransaction().begin();
            entityManager.merge(newType);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }

    public void deleteSystUser(int userId) {
        try {
            entityManager.getTransaction().begin();
            SystUserEntity user = entityManager.find(SystUserEntity.class, userId);

            if (user != null) {
                entityManager.remove(user);
                entityManager.getTransaction().commit();
                System.out.println("Пользователь успешно удален");
            } else {
                System.out.println("Пользователь с ID " + userId + " не найден");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }

    public void changeUserStatus(int userId, byte newStatus) {
        try {
            entityManager.getTransaction().begin();
            SystUserEntity user = entityManager.find(SystUserEntity.class, userId);
            if (user != null) {
                user.setStatusSyst(newStatus);

                entityManager.merge(user);
                entityManager.getTransaction().commit();
                System.out.println("Статус пользователя успешно изменен");
            } else {
                System.out.println("Пользователь с ID " + userId + " не найден");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.clear();
        }
    }

    public ObservableList<FullTypeRep> getFullTypes() {
        ObservableList<FullTypeRep> fullTypes = FXCollections.observableArrayList();
        try {
            List<TypeEmEntity> types = entityManager.createQuery("SELECT t FROM TypeEmEntity t", TypeEmEntity.class).getResultList();
            for (TypeEmEntity type : types) {
                FullTypeRep fullType = new FullTypeRep(type.getName(), type.getRecommendations(), type.getKindEmByIdKind().getKindName());
                fullTypes.add(fullType);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return fullTypes;
    }

    public TimingRep getTimings(int idReport) {
        TimingRep timingRep = null;
        try {
            String queryStr = "SELECT n.recievedDateTime, n.endUpDateTime FROM ReportsEntity n WHERE n.idReport = :idReport";
            TypedQuery<Object[]> query = entityManager.createQuery(queryStr, Object[].class);
            query.setParameter("idReport", idReport);

            Object[] result = query.getSingleResult();
            if (result != null && result.length == 2) {
                Timestamp recievedDateTime = (Timestamp) result[0];
                Timestamp endUpDateTime = (Timestamp) result[1];
                timingRep = new TimingRep(recievedDateTime, endUpDateTime);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return timingRep;
    }

    public Integer getKindIdByName(String name) {
        int id = 0;
        try {
            TypedQuery<Integer> query = entityManager.createQuery("SELECT k.id FROM KindEmEntity k WHERE k.kindName = :name", Integer.class);
            query.setParameter("name", name);
            List<Integer> results = query.getResultList();

            if (!results.isEmpty()) {
                id = results.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return id;
    }

    public void deleteThisType(String text) {
        try {
            TypedQuery<TypeEmEntity> query = entityManager.createQuery(
                    "SELECT t FROM TypeEmEntity t WHERE t.name = :name", TypeEmEntity.class);
            query.setParameter("name", text);

            TypeEmEntity typeEntity = query.getSingleResult();

            entityManager.getTransaction().begin();
            entityManager.remove(typeEntity);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }

    public ObservableList<FulliKindRep> getFullKinds() {
        ObservableList<FulliKindRep> fullKinds = FXCollections.observableArrayList();
        try {
            List<KindEmEntity> kinds = entityManager.createQuery("SELECT t FROM KindEmEntity t", KindEmEntity.class).getResultList();
            for (KindEmEntity kind : kinds) {
                FulliKindRep fullKind = new FulliKindRep(kind.getKindId(), kind.getKindName(), kind.getCharEmByIdChar().getCharName());
                fullKinds.add(fullKind);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return fullKinds;
    }

    public void deleteThisKind(String text) {
        try {
            TypedQuery<KindEmEntity> query = entityManager.createQuery(
                    "SELECT t FROM KindEmEntity t WHERE t.kindName = :name", KindEmEntity.class);
            query.setParameter("name", text);
            KindEmEntity kindEntity = query.getSingleResult();

            entityManager.getTransaction().begin();
            entityManager.remove(kindEntity);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }

    public void addNewKind(String text, int id) {
        try {
            KindEmEntity kind = new KindEmEntity();
            kind.setKindName(text);
            kind.setIdChar(id);

            entityManager.getTransaction().begin();
            entityManager.merge(kind);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }

    public int getCharIdByName(String selectedItem) {
        int id = 0;
        try {
            TypedQuery<Integer> query = entityManager.createQuery("SELECT k.id FROM CharEmEntity k WHERE k.charName = :name", Integer.class);
            query.setParameter("name", selectedItem);
            List<Integer> results = query.getResultList();

            if (!results.isEmpty()) {
                id = results.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return id;
    }

    public boolean ifThereTypesOfKind(String text) {
        boolean bool = false;
        try {
            String queryStr = "SELECT COUNT(k) FROM TypeEmEntity k WHERE k.name = :kindName";
            TypedQuery<Long> query = entityManager.createQuery(queryStr, Long.class);
            query.setParameter("kindName", text);
            long count = query.getSingleResult();

            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return bool;
    }

    public void addKindServiceRelation(Integer id_kind, Integer id_service) {
        try {
            ServiceKindRelationEntity relation = new ServiceKindRelationEntity();
            relation.setKindId(id_kind);
            relation.setServiceId(id_service);

            entityManager.getTransaction().begin();
            entityManager.merge(relation);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
    }

    public Integer getServiceIdByName(String service) {
        int id = 0;
        try {
            TypedQuery<Integer> query = entityManager.createQuery("SELECT k.id FROM ServiceEntity k WHERE k.serviceName = :name", Integer.class);
            query.setParameter("name", service);
            List<Integer> results = query.getResultList();

            if (!results.isEmpty()) {
                id = results.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return id;
    }

    public boolean ifThereSuchRelation(Integer kind_id, Integer service_id) {
        boolean bool = false;
        try {
            String queryStr = "SELECT COUNT(k) FROM ServiceKindRelationEntity k WHERE k.kindId =:kind_id and k.serviceId =:service_id";
            TypedQuery<Long> query = entityManager.createQuery(queryStr, Long.class);
            query.setParameter("kind_id", kind_id);
            query.setParameter("service_id", service_id);
            long count = query.getSingleResult();

            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }
        return bool;
    }

    public void deleteAllRels(Integer kind_id) {
        List<ServiceKindRelationEntity> relations;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<ServiceKindRelationEntity> query = entityManager.createQuery("SELECT k FROM ServiceKindRelationEntity k WHERE k.kindId = :kindId", ServiceKindRelationEntity.class);
            query.setParameter("kindId", kind_id);
            relations = query.getResultList();
            for (ServiceKindRelationEntity relation : relations) {
                entityManager.remove(relation);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }

    }

    public void deleteThatRel(Integer kindId, Integer serviceId) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<ServiceKindRelationEntity> query = entityManager.createQuery("SELECT k FROM ServiceKindRelationEntity k WHERE k.kindId = :kindId and k.serviceId =:serviceId", ServiceKindRelationEntity.class);
            query.setParameter("kindId", kindId);
            query.setParameter("serviceId", serviceId);
            entityManager.remove(query.getSingleResult());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.clear();
        }

    }
}
