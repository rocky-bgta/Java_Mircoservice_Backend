package nybsys.tillboxweb.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import nybsys.tillboxweb.BaseDao;
import nybsys.tillboxweb.BaseHistoryEntity;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.TillBoxDbConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Repository
//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Dao<T> extends BaseDao {

    private SessionFactory sessionFactory;

    public Dao(){
        super();
    }

    public Dao(SessionFactory sessionFactory){
        super();
        this.sessionFactory = sessionFactory;
    }

    private static final Logger log = LoggerFactory.getLogger(Dao.class);
    //private Session session;

    //private SessionFactory sessionFactory;
    //{
    //this.session = Core.sessionThreadLocal.get();
    //this.sessionFactory = Core.sessionFactoryThreadLocal.get();

    //}

    public T save(T t, Boolean insertDataInHistory) throws Exception {
        BaseHistoryEntity historyEntity;
        try {
            Session session = getSession();
            session.beginTransaction();
            session.save(t);
            session.flush();
            session.refresh(t);

            //================ code regarding history table======================
            if(insertDataInHistory) {
                historyEntity = buildHistoryEntity(t, TillBoxAppEnum.QueryType.Insert.get());
                session.save(historyEntity);
                session.flush();
            }
            //===================================================================

            session.getTransaction().commit();
            session.close();
            //this.sessionFactory.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao Save method");
            throw ex;
        }
        return t;
    }

    public T update(T t, Boolean insertDataInHistory) throws Exception {
        Object primaryKeyValue;
        Object oldEntity;
        BaseHistoryEntity historyEntity;
        try {
            Session session = getSession();
            session.beginTransaction();

            //================ code regarding history table======================
            if(insertDataInHistory) {
                // Find previous row's primary key before update
                primaryKeyValue = this.getPrimaryFieldValue(t);
                oldEntity = getById(primaryKeyValue);

                historyEntity = buildHistoryEntity(oldEntity, TillBoxAppEnum.QueryType.Update.get());
                session.save(historyEntity);
                session.flush();
            }
            //===================================================================

            // update row to main table
            session.update(t);
            session.flush();
            session.refresh(t);

            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao update method");
            throw ex;
        }
        return t;
    }
/*
    public T saveOrUpdate(T t) throws HibernateException, JsonProcessingException{
        BaseHistoryEntity historyEntity;
        try {
            Session session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(t);
            session.flush();
            session.refresh(t);

            historyEntity = buildHistoryEntity(t,TillBoxAppEnum.QueryType.Insert.get());
            session.save(historyEntity);
            session.flush();

            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao saveOrUpdate method");
            throw ex;
        }
        return t;
    }*/

    public List<T> getAllByConditions(String hql, Map<Object, Object> keyValueParis) throws HibernateException {
        List<T> list = null;
        String key;
        Object value;
        try {
            Session session = getSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            for (Map.Entry<Object, Object> entry : keyValueParis.entrySet()) {
                key = entry.getKey().toString();
                value = entry.getValue();
                q.setParameter(key, value);
            }
            list = q.getResultList();

            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao getAllByConditions method");
            throw ex;
        }
        return list;
    }

    public T getById(Object id) throws HibernateException {
        return getById(id,null);
    }

    public T getById(Object id, Integer status) throws HibernateException {
        Object entity = null;
        StringBuilder query;
        String entityName;
        String primaryKeyField;
        try {
            Session session = getSession();
            primaryKeyField = this.getPrimaryKeyFieldName();
            query = new StringBuilder();
            Class clazz = Core.runTimeEntityType.get();
            entityName = clazz.getName();
            entityName = org.apache.commons.lang3.StringUtils.substringAfterLast(entityName, ".").trim();
            query.append("SELECT t ")
                    .append("FROM " + entityName + " t ")
                    .append("WHERE ")
                    .append("t."+primaryKeyField+" =:"+primaryKeyField);

            if(status != null)
                query.append(" AND t.status = " + Integer.parseInt(status.toString()));


            session.beginTransaction();
            Query q = session.createQuery(query.toString());
            q.setParameter(primaryKeyField, id);

            List<T> list = q.getResultList();
            if (list == null || list.isEmpty()) {
                return null;
            }else {
                entity = list.get(0);
            }
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao getById method");
            throw ex;
        }
        return (T) entity;
    }

    public List<T> getAll() throws HibernateException {
        List<T> list = null;

        try {
            Session session = getSession();
            session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> q = cb.createQuery(Core.runTimeEntityType.get());
            Root<T> table = q.from(Core.runTimeEntityType.get());
            q.select(table);
            list = session.createQuery(q).getResultList();
            session.getTransaction().commit();
            session.close();
            //this.sessionFactory.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao getAll method");
            throw ex;
        }
        return list;
    }

    public Integer updateByConditions(String updateHql, String selectHql,
                                      Map<Object, Object> keyValueParisForUpdate,
                                      Map<Object,Object> keyValuePairsForWhereCondition,
                                      Boolean insertDataInHistory) throws Exception {

        Integer numberOfUpdatedRows=0;
        String key;
        Object value;
        List selectedUpdateRowList;
        String entityName;
        BaseHistoryEntity historyEntity;
        try {
            Session session = getSession();
            session.beginTransaction();
            Query updateQuery = session.createQuery(updateHql);

            //================ code regarding history table======================
            if(insertDataInHistory) {
                selectedUpdateRowList = getAllByConditions(selectHql, keyValuePairsForWhereCondition);
                entityName = this.getEntityNameFromHql(updateHql);
                // Insert data into history table
                historyEntity = buildHistoryEntity(selectedUpdateRowList,
                        TillBoxAppEnum.QueryType.UpdateByConditions.get(),
                        entityName);
                session.save(historyEntity);
                session.flush();
            }
            //===================================================================

            //Set value to be update
            for (Map.Entry<Object, Object> entry : keyValueParisForUpdate.entrySet()) {
                key = entry.getKey().toString();
                value = entry.getValue();
                updateQuery.setParameter(key, value);
            }

            //Set value for whereConditions
            for (Map.Entry<Object, Object> entry : keyValuePairsForWhereCondition.entrySet()) {
                key = entry.getKey().toString();
                value = entry.getValue();
                updateQuery.setParameter(key, value);
            }

            numberOfUpdatedRows = updateQuery.executeUpdate();
            session.flush();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao updateByConditions method");
            throw ex;
        }
        return numberOfUpdatedRows;
    }

    public boolean delete(T t) throws HibernateException {
        boolean isDeleted = false;
        try {

            Session session = getSession();
            session.beginTransaction();
            session.delete(t);
            session.flush();
            session.getTransaction().commit();
            session.close();
            isDeleted = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao delete method");
            throw ex;
        }
        return isDeleted;
    }

    public boolean deleteById(Object id) throws HibernateException {
        boolean isDeleted = false;
        StringBuilder query;
        String entityName;
        String primaryKeyField;
        Integer numOfDeletedRow=0;
        try {
            Session session = getSession();
            primaryKeyField = this.getPrimaryKeyFieldName();
            query = new StringBuilder();
            Class clazz = Core.runTimeEntityType.get();
            entityName = clazz.getName();
            entityName = org.apache.commons.lang3.StringUtils.substringAfterLast(entityName, ".").trim();
            query.append("DELETE t ")
                    .append("FROM " + entityName + " t ")
                    .append("WHERE ")
                    .append("t."+primaryKeyField+" =:"+primaryKeyField);

            session.beginTransaction();
            Query q = session.createQuery(query.toString());
            q.setParameter(primaryKeyField, id);

            numOfDeletedRow = q.executeUpdate();
            if(numOfDeletedRow>0)
                isDeleted= true;

            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao deleteById method");
            throw ex;
        }
        return isDeleted;
    }

    public Integer deleteByConditions(String hql, Map<Object, Object> keyValueParis) throws HibernateException {
        Integer numberOfDeletedRows=0;
        String key;
        Object value;
        try {
            Session session = getSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //Set value
            for (Map.Entry<Object, Object> entry : keyValueParis.entrySet()) {
                key = entry.getKey().toString();
                value = entry.getValue();
                q.setParameter(key, value);
            }
            numberOfDeletedRows = q.executeUpdate();
            session.flush();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao deleteByConditions method");
            throw ex;
        }
        return numberOfDeletedRows;
    }

    public <M> List<M> executeHqlQuery(String hql,Class<M> clazz, int queryType, Boolean insertDataInHistory) throws Exception {
        List<Object[]> result;
        Integer numberOfUpdatedRow;
        List<M> convertedModels = new ArrayList<>();
        String selectHql;
        List selectedUpdateRowList;
        BaseHistoryEntity historyEntity;
        String entityName;
        Query selectQuery;

        try {
            Session session = getSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);

            if(TillBoxAppEnum.QueryType.Join.get()==queryType) {
                result = q.getResultList();
                if( result.size()>0) {
                    convertedModels = this.getObjectListFromObjectArray(result, clazz);
                }
            }
            if(TillBoxAppEnum.QueryType.Select.get()==queryType){
                result = q.getResultList();
                if( result.size()>0) {
                    convertedModels = (List<M>) result;
                }
            }

            if(TillBoxAppEnum.QueryType.GetOne.get()==queryType){
                q.setMaxResults(1);
                result = q.getResultList();
                if( result.size()>0) {
                    convertedModels = (List<M>) result;
                }
            }

            if(TillBoxAppEnum.QueryType.Update.get()==queryType){

                //================ code regarding history table======================
                if(insertDataInHistory) {
                    selectHql = this.buildSelectHql(hql);
                    selectQuery = session.createQuery(selectHql);
                    selectedUpdateRowList = selectQuery.getResultList();
                    entityName = this.getEntityNameFromHql(hql);
                    // Insert data into history table
                    historyEntity = buildHistoryEntity(selectedUpdateRowList,
                            TillBoxAppEnum.QueryType.UpdateByConditions.get(),
                            entityName);
                    session.save(historyEntity);
                    session.flush();
                }
                //===================================================================

                numberOfUpdatedRow =  q.executeUpdate();
                convertedModels = new ArrayList<>();
                convertedModels.add((M)numberOfUpdatedRow);
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao executeHqlQuery method");
            throw ex;
        }
        return convertedModels;
    }

    public List<Object[]> executeNativeSqlQuery(String hql, int queryType) throws HibernateException {
        List<Object[]> result= new ArrayList<>();
        try {
            Session session = getSession();
            session.beginTransaction();
            NativeQuery query = session.createNativeQuery(hql);
            if(TillBoxAppEnum.QueryType.Select.get()==queryType)
                if(result.size()>0) {
                    result = query.getResultList();
                }
            if(TillBoxAppEnum.QueryType.Update.get()==queryType)
                query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from Dao executeNativeSqlQuery method");
            throw ex;
        }
        return result;
    }

    public Boolean isRowExist(List<Map<Object,Object>> keyValueWithTypeList, Class entityClass) throws Exception {
        Boolean isExist = false;
        String key = null;
        Object value = null;
        String entityName;
        String entityClassPath = entityClass.toString();
        String hql;
        Long count;
        Map<Object,Object> keyValueParis = keyValueWithTypeList.get(0);
        Map<Object,Object> typeMap = keyValueWithTypeList.get(1);

        entityClassPath = StringUtils.substringAfterLast(entityClassPath, "class").trim();
        entityName = StringUtils.substringAfterLast(entityClassPath,".");

        try {
            hql = this.queryBuilder(keyValueParis, TillBoxAppEnum.QueryType.CountRow.get(),entityName);

            Session session = getSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //Set value
            for (Map.Entry<Object, Object> entry : keyValueParis.entrySet()) {
                key = entry.getKey().toString();
                value = entry.getValue();
                q.setParameter(key, value);
            }

            count = (Long) q.uniqueResult();
            session.getTransaction().commit();
            session.close();

            if(count!=null && count>0)
                isExist = true;

        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Exception from Dao isRowExist method");
            throw ex;
        }
        return isExist;
    }

    private Session getSession() {
        SessionFactory sessionFactory;
        Session session;

        if(this.sessionFactory == null)
            sessionFactory = Core.sessionFactoryThreadLocal.get();
        else {
            sessionFactory = this.sessionFactory;
            Core.sessionFactoryThreadLocal.set(sessionFactory);
        }

        session = sessionFactory.openSession();
        return session;
    }

    private BaseHistoryEntity buildHistoryEntity(Object t, int QueryType) throws JsonProcessingException {
        return buildHistoryEntity(t,QueryType, null);
    }

    private BaseHistoryEntity buildHistoryEntity(Object entity, int QueryType, String entityName) throws JsonProcessingException {
        String jsonString,entityClassPath;
        jsonString = Core.jsonMapper.writeValueAsString(entity);
        Core.HistoryEntity.setMessageId(Core.messageId.get());
        Core.HistoryEntity.setActionType(QueryType);
        Core.HistoryEntity.setDateTime(new Date());
        Core.HistoryEntity.setJsonObject(jsonString);
        entityClassPath = entity.getClass().toString();


        if(entityName!=null){
            entityClassPath = TillBoxDbConstant.DEFAULT_DB_ENTITY_PATH + "."+entityName;
        }else {
            entityClassPath = StringUtils.substringAfterLast(entityClassPath, "class").trim();
            entityName = StringUtils.substringAfterLast(entityClassPath,".");
        }
        Core.HistoryEntity.setEntityClassPath(entityClassPath);
        Core.HistoryEntity.setEntityName(entityName);
        return Core.HistoryEntity;
    }
}
